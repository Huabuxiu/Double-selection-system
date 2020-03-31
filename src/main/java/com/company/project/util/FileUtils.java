package com.company.project.util;

import com.company.project.configurer.Log;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class FileUtils {


    public static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径
    public static final String RESOURCES_PATH = "/src/main/resources/image";//资源文件路径

    /**
     *
     * @param file 文件
     *
     * @param fileName 源文件名
     * @return
     */
    @Log
    public static boolean upload(MultipartFile file, String fileName){


        // 生成新的文件名
        //String realPath = path + "/" + FileNameUtils.getFileName(fileName);

        //使用原文件名
        String realPath = PROJECT_PATH + RESOURCES_PATH + "/" + fileName;

        File dest = new File(realPath);

        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }

        try {
            //保存文件
            file.transferTo(dest);
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }


//    public static void main(String[] args) {
//        Double S = new Double(150000);
//        Double P = new Double(0.05);
//        Double A = new Double(150000);
//        int n = 10;
//        System.out.println("不理财不涨工资的情况下有"+(S+(n-1)*A)+"元");
//        System.out.println("理财不涨工资第"+n+"年后总共有"+getn(S,P,A,n).intValue()+"元");
//        System.out.println("不理财涨工资第"+n+"年后总共有"+getnot(S,P,A,n).intValue()+"元");
//        System.out.println("理财涨工资第"+n+"年后总共有"+getG(S,P,A,n).intValue()+"元");
//        System.out.println("通货膨胀下不理财涨工资第"+n+"年后总共有"+getnot(S,P,A,n).intValue()+"元"+"价值现在"+getnotInf(S,P,A,n).intValue()+"元");
//        System.out.println("通货膨胀下理财涨工资第"+n+"年后总共有"+getG(S,P,A,n).intValue()+"元"+"价值现在"+getGInf(S,P,A,n).intValue()+"元");
//    }
//
//    private static Double getn(Double S, Double P, Double A, int n){
//        double tem = S * Math.pow(1+P,n);
//        for (int i =1 ; i < n; i ++){
//            tem += A * Math.pow(1+P,n-i);
//        }
//        return tem;
//    }
//
//    private static Double getG(Double S, Double P, Double A, int n){
//        double tem = S * Math.pow(1+P,n);
//        for (int i =1 ; i < n; i ++){
//            A = A *( 1 + 0.04);
//            tem += A * Math.pow(1+P,n-i);
//        }
//        return tem;
//    }
//
//    private static Double getnot(Double S, Double P, Double A, int n){
//        double tem = S ;
//        for (int i =1 ; i < n; i ++){
//            A = A *( 1 + 0.05);
//            tem += A ;
//        }
//        return tem;
//    }
//
//    private static Double getGInf(Double S, Double P, Double A, int n){
//        double tem = S * Math.pow(1+P,n);
//        for (int i =1 ; i < n; i ++){
//            A = A *( 1 + 0.04);
//            tem += A * Math.pow(1+P,n-i);
//        }
//        return tem * Math.pow((1-0.025),10);
//    }
//
//    private static Double getnotInf(Double S, Double P, Double A, int n){
//        double tem = S ;
//        for (int i =1 ; i < n; i ++){
//            A = A *( 1 + 0.05);
//            tem += A ;
//        }
//        return tem * Math.pow((1-0.025),10);
//    }

    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     * @param fileOriginName 源文件名
     * @return
     */
    public static String getFileName(String fileOriginName){
        return UUID.randomUUID() + FileUtils.getSuffix(fileOriginName);
    }
}
