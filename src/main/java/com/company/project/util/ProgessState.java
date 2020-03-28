package com.company.project.util;

public class ProgessState {
    static String[] progessArray =
            {"提交志愿","导师通过","导师拒绝","满员失败","双选成功","双选失败"};
    static String[] statesArray =
            {"导师审核中","学院审核中","志愿失败，原因：导师拒绝"
                    ,"志愿失败,原因：所选导师人数已满","志愿成功"
                    ,"志愿失败，原因：学院审核不通过"};

    public static int  getProgessCode(String progress){
       for (int i = 0; i < progessArray.length ;i++){
           if (progress.equals(progessArray[i])){
               return i+1;
           }
       }
        return 0;
    }

    public static String  getState(int progessCode){
       return statesArray[progessCode-1];
    }


}
