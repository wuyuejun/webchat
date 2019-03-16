package com.xcl.webchat.core.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author :xiaochanglu
 * @Description :文件操作工具类
 * 创建目录
 * 文件写入
 * 读取文件
 * 拷贝文件和目录
 * 删除文件和目录
 * 从URL转换
 * 基于统配和过滤查看文件和目录
 * 比较文件内容
 * 文件的更新时间
 * 检查校验码
 * @date :2019/1/9 13:38
 */
public class FileUtils {

    /**
     * @Description  ：判断是否存在目录或文件. 不存在则创建
     * @author       : xcl
     * @param        : [filepath]
     * @return       : java.io.File
     * @date         : 2019/1/9 9:52
     */
    public static File isChartPathExist(String filepath){
        File file = new File(filepath);
        try{
            if(file.isDirectory()){
                //目录
                if (!file.exists()) {
                    file.mkdir();
                }
            }else{
                if (!file.exists()) {
                    file.createNewFile();
                }
            }
        }catch (Exception e){

        }
        return file;
    }

    public static boolean writer(File file, String string){
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false),"UTF-8"));
            writer.write(string);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static String read(File file){
        BufferedReader reader = null;
        String laststr = "";
        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String tempString = null;
            while ((tempString = reader.readLine()) != null){
                laststr += tempString;
            }
        }catch (Exception e){
            e.printStackTrace();
            return "";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

//    public static void main(String[] args) {
//        System.out.println(writer(isChartPathExist("D:/test.txt"),"1111111111111111111111111111111"));
//        System.out.println(read(isChartPathExist("D:/test.txt")));
//    }
}
