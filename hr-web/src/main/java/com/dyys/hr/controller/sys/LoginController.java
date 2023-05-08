package com.dyys.hr.controller.sys;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.constants.HeaderConstants;
import com.dagongma.kernel.web.result.Result;
import com.dagongma.redis.common.util.RedisUtil;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dao.staff.StaffDepartmentMapper;
import com.dyys.hr.entity.staff.StaffDepartment;
import com.dyys.hr.entity.sys.SysUserToken;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.service.sys.ISysUserTokenService;
import com.dyys.hr.utils.SsoAesUtil;
import com.dyys.hr.utils.TokenUtil;
import com.dyys.hr.vo.common.PsPersionVO;
import com.dyys.hr.vo.common.UserLoginVO;
import com.dyys.hr.vo.common.UserTokenVO;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * @author DAAN LI
 **/
@ResponseResult
@Slf4j
@RestController
@RequestMapping("/common")
@Api(value = "登陆", tags = {"登陆管理"})
public class LoginController {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ISysUserTokenService sysUserTokenService;
    @Autowired
    private StaffDepartmentMapper staffDepartmentMapper;
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;

    @Value("${knps.auth.driver}")
    private String authDriver;
    @Value("${knps.auth.url}")
    private String authUrl;
    @Value("${knps.auth.user}")
    private String authUser;
    @Value("${knps.auth.password}")
    private String authPassword;

    @ApiOperation(value = "登陆", notes = "登陆接口")
    @ApiResponse(code = 200, message = "成功", response = Result.class)
    @PostMapping("/login")
    public com.dyys.hr.utils.Result<UserTokenVO> login(@RequestBody String userStr) {
        Map<String, Object> userMap = JSONUtil.parseObj(userStr);
        String emplId = (String) userMap.get("userName"); // 员工工号
        SysUserToken user = sysUserTokenService.findUserToken(emplId);

        String tokenStr; // redis中token标识
        if (user != null) {
            tokenStr = String.format(Constant.REDIS_TOKEN, user.getToken());
            redisUtil.remove(tokenStr); // 从redis中删除当前用户的token

            user.setToken(TokenUtil.generateValue()); // 更新当前用户的token
            long stamp = System.currentTimeMillis(); // 当前时间戳
            long expireTime = stamp + 30 * 60 * 1000L; // 半小时有效期
            user.setExpireDate(new Date(expireTime)); // 修改token失效时间
            user.setUpdateDate(new Date(stamp)); // 更新时间
            sysUserTokenService.update(user); // 更新用户token信息
        } else {
            user = sysUserTokenService.createEmplToken(emplId); // 生成新用户token数据
        }
        // 把token写入redis
        tokenStr = String.format(Constant.REDIS_TOKEN, user.getToken()); // 新的token标识
        UserLoginVO loginVO = getDeptAndRoleList(emplId); // 获取员工角色集和权限部门ID集
        user.setDeptList(loginVO.getDeptList());
        user.setRoleList(loginVO.getRoleList());
        redisUtil.expire(tokenStr, 30 * 60L); // 设置用户redis半个小时过期
        redisUtil.set(tokenStr, user);

        //初始化权限部门id组
        UserTokenVO convert = Convert.convert(UserTokenVO.class, user);

        //获取用户详细信息
        PsPersionVO userInfo = iStaffUserInfoService.getUserInfoById(emplId);
        if (userInfo == null) {
            return new com.dyys.hr.utils.Result<UserTokenVO>().error("用户信息错误，登录失败！");
        }
        convert.setOrganizationId(userInfo.getCompanyCode());
        convert.setOrganizationName(userInfo.getCompanyName());
        return new com.dyys.hr.utils.Result<UserTokenVO>().ok(convert);
    }

    @ApiOperation(value = "退出登陆", notes = "退出登陆")
    @ApiResponse(code = 200, message = "成功", response = Result.class)
    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        String token = request.getHeader(HeaderConstants.X_TOKEN);
        if (StrUtil.isBlank(token)) {
            return;
        }
        redisUtil.remove(String.format(Constant.REDIS_TOKEN, token));
    }

    public com.dyys.hr.utils.Result<Boolean> testLogin() {
        return new com.dyys.hr.utils.Result<Boolean>().success("登录成功");
    }

    @ApiOperation("测试单点字符串")
    @GetMapping("/loginSsoStr")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "emplid", value = "员工ID", paramType = "query",dataType="String"),
    })
    public com.dyys.hr.utils.Result<String> loginSsoStr(@RequestParam Map<String, Object> param) {
        String emplid = param.get("emplid").toString();
        String code = emplid + ",123456," + "2024-04-01 00:00:00";
        String s = SsoAesUtil.encryptAes256(code);
        return new com.dyys.hr.utils.Result<String>().success("获取成功",s);
    }

    @ResponseResult
    @ApiOperation("单点登陆")
    @GetMapping("/loginSso")
    public com.dyys.hr.utils.Result<UserTokenVO> loginSso(@RequestParam Map<String, Object> queryMap) {
        String code = Convert.toStr(queryMap.get("code"));
        String loginInfo = SsoAesUtil.decryptAes256(code);
        if(loginInfo == null){
            return new com.dyys.hr.utils.Result<UserTokenVO>().error("加密校验异常");
        }
        assert loginInfo != null;
        String[] infoArray =  loginInfo.split(",");
        String emplId = infoArray[0];
        String dateStr = infoArray[2];
        Date date = Convert.toDate(dateStr);
        Date currentDate = new Date();
        long dateL = date.getTime();
        long currentDateL = currentDate.getTime();
        long difference = currentDateL - dateL;
        long minute = difference/(60*1000);
        //校验登录是否超过3小时
        if (minute > 180) {
            return new com.dyys.hr.utils.Result<UserTokenVO>().error("登录过期，请重新登录");
        }

        // 根据用户工号获取当前员工的token信息
        SysUserToken user = sysUserTokenService.findUserToken(emplId);
        String tokenStr; // redis中token标识
        if (user != null) {
            tokenStr = String.format(Constant.REDIS_TOKEN, user.getToken());
            redisUtil.remove(tokenStr); // 从redis中删除当前用户的token

            user.setToken(TokenUtil.generateValue()); // 更新当前用户的token
            long stamp = System.currentTimeMillis(); // 当前时间戳
            long expireTime = stamp + 30 * 60 * 1000L; // 半小时有效期
            user.setExpireDate(new Date(expireTime)); // 修改token失效时间
            user.setUpdateDate(new Date(stamp)); // 更新时间
            sysUserTokenService.update(user); // 更新用户token信息
        } else {
            user = sysUserTokenService.createEmplToken(emplId); // 生成新用户token数据
        }
        // 把token写入redis
        tokenStr = String.format(Constant.REDIS_TOKEN, user.getToken()); // 新的token标识
        UserLoginVO loginVO = getDeptAndRoleList(emplId); // 获取员工角色集和权限部门ID集
        user.setDeptList(loginVO.getDeptList());
        user.setRoleList(loginVO.getRoleList());
        redisUtil.expire(tokenStr, 30 * 60L); // 设置用户redis半个小时过期
        redisUtil.set(tokenStr, user);

        //初始化权限部门id组
        UserTokenVO convert = Convert.convert(UserTokenVO.class, user);

        //获取用户详细信息
        PsPersionVO userInfo = iStaffUserInfoService.getUserInfoById(emplId);
        if (userInfo == null) {
            return new com.dyys.hr.utils.Result<UserTokenVO>().error("用户信息错误，登录失败！");
        }
        convert.setOrganizationId(userInfo.getCompanyCode());
        convert.setOrganizationName(userInfo.getCompanyName());
        return new com.dyys.hr.utils.Result<UserTokenVO>().ok(convert);
    }


    private UserLoginVO getDeptAndRoleList(String emplid){
        UserLoginVO userLoginVO = new UserLoginVO();
        // 获取登录员工所拥有的角色，例如：emplid = "01168"
        Connection con = null;// 数据库连接
        PreparedStatement pre = null;// 预编译语句对象
        ResultSet result = null;// 结果集对象
        List<String> roles = new ArrayList<>();
        try {
            Class.forName(authDriver);
            String url = authUrl;
            String user = authUser;
            String password = authPassword;
            con = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM dm_sjt_oprd_role where oprid in" +
                    " " + "(select oprid from dm_sjt_oprd where emplid =" +
                    "'" + emplid + "'" + ")";
            pre = con.prepareStatement(sql);// 实例化预编译语句
            result = pre.executeQuery();// 执行查询
            try {
                while (result.next()) {
                    String role = result.getString("ROLE_NAME");
                    if (roles.contains(role)) {
                        continue;
                    }
                    roles.add(role);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 逐一关闭上面的几个对象
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (con != null)
                    con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<String> depts = new ArrayList<>();
        // 非集团管理员角色，则处理所拥有权限的部门编码
        if (!roles.contains("KN_TRAIN_ADMIN")) {
            con = null;// 数据库连接
            pre = null;// 预编译语句对象
            result = null;// 结果集对象
            try {
                Class.forName(authDriver);
                String url = authUrl;
                String user = authUser;
                String password = authPassword;
                con = DriverManager.getConnection(url, user, password);
                String sql = "SELECT * FROM dept_srch_vw where opr_emplid=" + emplid;
                pre = con.prepareStatement(sql);// 实例化预编译语句
                result = pre.executeQuery();// 执行查询
                try {
                    while (result.next()) {
                        String dept = result.getString("DEPT_ID");
                        if (depts.contains(dept)) {
                            continue;
                        }
                        depts.add(dept);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // 逐一关闭上面的几个对象
                    if (result != null)
                        result.close();
                    if (pre != null)
                        pre.close();
                    if (con != null)
                        con.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //登录员工是否是部门负责人
            /*List<StaffDepartment> orgs = staffDepartmentMapper.queryOrgByParams(emplid);
            // 部门负责人
            if (!orgs.isEmpty()) {
                for (StaffDepartment org : orgs) {
                    String deptId = org.getId();
                    con = null;// 数据库连接
                    pre = null;// 预编译语句对象
                    result = null;// 结果集对象
                    try {
                        Class.forName(authDriver);
                        String url = authUrl;
                        String user = authUser;
                        String password = authPassword;
                        con = DriverManager.getConnection(url, user, password);
                        String sql = "select tree_begin_index,tree_end_index from dm_dept_data where dept_id=" + deptId;
                        pre = con.prepareStatement(sql);// 实例化预编译语句
                        result = pre.executeQuery();// 执行查询
                        try {
                            while (result.next()) {
                                Integer treeBeginIndex = Convert.toInt(result.getString("tree_begin_index"));
                                Integer treeEndIndex = Convert.toInt(result.getString("tree_end_index"));
                                String sqlLeader = "SELECT * FROM dm_dept_data where tree_begin_index BETWEEN"
                                        + " " + Convert.toStr(treeBeginIndex)
                                        + " " + "AND"
                                        + " " + Convert.toStr(treeEndIndex);
                                pre = con.prepareStatement(sqlLeader);// 实例化预编译语句
                                ResultSet resultLeader = null;// 结果集对象
                                resultLeader = pre.executeQuery();// 执行查询
                                while (resultLeader.next()) {
                                    String dept = resultLeader.getString("DEPT_ID");
                                    if (depts.contains(dept)) {
                                        continue;
                                    }
                                    depts.add(dept);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            // 逐一关闭上面的几个对象
                            if (result != null)
                                result.close();
                            if (pre != null)
                                pre.close();
                            if (con != null)
                                con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                con = null;// 数据库连接
                pre = null;// 预编译语句对象
                result = null;// 结果集对象
                try {
                    Class.forName(authDriver);
                    String url = authUrl;
                    String user = authUser;
                    String password = authPassword;
                    con = DriverManager.getConnection(url, user, password);
                    String sql = "SELECT * FROM dept_srch_vw where opr_emplid=" + emplid;
                    pre = con.prepareStatement(sql);// 实例化预编译语句
                    result = pre.executeQuery();// 执行查询
                    try {
                        while (result.next()) {
                            String dept = result.getString("DEPT_ID");
                            if (depts.contains(dept)) {
                                continue;
                            }
                            depts.add(dept);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        // 逐一关闭上面的几个对象
                        if (result != null)
                            result.close();
                        if (pre != null)
                            pre.close();
                        if (con != null)
                            con.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }*/
        }
        userLoginVO.setRoleList(roles);
        userLoginVO.setDeptList(depts);
        return userLoginVO;
    }

}
