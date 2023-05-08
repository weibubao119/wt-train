package com.dyys.hr.task;

import cn.hutool.core.date.DateUtil;
import com.dagongma.kernel.annotation.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
/**
 * PS用户数据同步
 *
 * @author wsj
 * @date 2022/05/26
 */
@ResponseResult
@Slf4j
@RestController
@Component
public class EmplUserInfoTask {

    @Transactional(rollbackFor = Exception.class)
    /**
     * 同步头像数据，每小时一次
     */
//    @Scheduled(cron = "0 0 * * * ?")
    /**
     * 数据源
     */
    public void ImportDataByJdbc() {

        long startTime = System.currentTimeMillis();    //获取开始时间

        Connection con = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");// 加载mysql驱动程序
            System.out.println("start connect source database！");
            String url = "jdbc:oracle:thin:@158.158.5.128:1521/HCMTST";//这是我测试用的源数据库连接
            String user = "system";// 用户名,系统默认的账户名
            String password = "Oracle#123";// 你安装时选设置的密码
            con = DriverManager.getConnection(url, user, password);// 获取连接
            System.out.println("source connect success！");
            String sql = "SELECT ATTACHSYSFILENAME,FILE_DATA FROM SYSADM.PS_C_PTFILE_TBL";
            pre = con.prepareStatement(sql);// 实例化预编译语句
            result = pre.executeQuery();// 执行查询

            //数据存入目标库处理
            importData(result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
                // 注意关闭的顺序，最后使用的最先关闭
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (con != null)
                    con.close();
                System.out.println("source connect close!");

                long endTime = System.currentTimeMillis();    //获取结束时间
                System.out.println("use time: " + (endTime - startTime) + "ms \n");    //输出程序运行时间

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 目标库
     *
     * @param
     * @return
     */
    public String importData(ResultSet result) {

        Connection con = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        try {
            int num = 0;
            Class.forName("com.mysql.jdbc.Driver");// 加载mysql驱动程序
            System.out.println("start connect target database！");
//            //sie测试
//            String url = "jdbc:mysql://218.13.91.107:39046/dyhr?useUnicode=true&characterEncoding=utf8&useSSL=false&tinyInt1isBit=false";
//            String user = "root";// 用户名,系统默认的账户名
//            String password = "sie2022^^O^^";// 你安装时选设置的密码
            //康尼测试
            String url = "jdbc:mysql://158.58.50.72:3306/kn-hr?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai&useSSL=false";
            String user = "root";// 用户名,系统默认的账户名
            String password = "qwe!@#123";// 你安装时选设置的密码
            con = DriverManager.getConnection(url, user, password);// 获取连接
            System.out.println("connect target success！");
            while (result.next()) {
                //在此处 可做数据处理
                String avatarName = result.getString("ATTACHSYSFILENAME");
                String emplId = StringUtils.substringBefore(avatarName,".");

                Blob fileData = result.getBlob("FILE_DATA");
                String sql = "update staff_user_info set avatar_file_data = ?,update_time = '" + DateUtil.date() + "' where empl_id = '" + emplId + "'";
                pre = con.prepareStatement(sql);// 实例化预编译语句
                pre.setBlob(1,fileData);
                pre.execute();
                pre.close();
                num = num + 1;
            }
            System.out.println("deal (" + num + ") success");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
                // 注意关闭的顺序，最后使用的最先关闭
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (con != null)
                    con.close();
                System.out.println("target connect close！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
