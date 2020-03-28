package com.company.project.util;

import com.company.project.configurer.Log;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
