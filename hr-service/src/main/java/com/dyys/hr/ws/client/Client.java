package com.dyys.hr.ws.client;


import org.apache.axis2.AxisFault;

import java.rmi.RemoteException;

/**
 * @author ZHIQIANG LI
 * @date 2019/7/31 20:55
 **/
public class Client {


    public static void main(String[] args) {
        try {
            WF_RunProcessServiceStub stub = new WF_RunProcessServiceStub();
            WF_RunProcessServiceStub.RunProcessE runProcessE = new WF_RunProcessServiceStub.RunProcessE();
            WF_RunProcessServiceStub.RunProcess runProcess = new WF_RunProcessServiceStub.RunProcess();
            runProcess.setActionid("EndUserTask");
            runProcess.setDocUnid("");
            runProcess.setNextNodeid("T10003");
            runProcess.setProcessid("b0bdd8410e317042fe0a24b03a3f57f2b1bc");
            runProcess.setRemark("");

            runProcess.setDocXml("<Items><WFItem name=\"Subject\">测试申报公文</WFItem></Items>");
            runProcess.setSysid("hrzp");
            runProcess.setSyspwd("zp@123dyys");
            runProcess.setUserid("23376");

            runProcessE.setRunProcess(runProcess);
            WF_RunProcessServiceStub.RunProcessResponseE responseE = stub.runProcess(runProcessE);
            System.out.println("----------------------------------------------------");
            System.out.println(responseE.getRunProcessResponse().get_return());
            System.out.println("----------------------------------------------------");
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
