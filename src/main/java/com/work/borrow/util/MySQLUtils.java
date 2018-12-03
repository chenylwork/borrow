package com.work.borrow.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

/*
 * @ClassName MySQLUtils
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2018/12/3- 13:00
 * @Version 1.0
 **/
@Component
public class MySQLUtils {
    /**
     * 备份数据库
     * @param backupPath
     */
    public static void backup(String backupPath) {
        String exec = MYSQL_HOME+"/bin/mysqldump -h localhost -u "+USERNAME+" -p"+PASSWORD+" "+DATABASE;
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(exec);
            InputStream inputStream = process.getInputStream(); // 控制台输出信息作为输入流
            // 设置输出编码为UTF-8解决乱码
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            String inStr;
            StringBuffer sb = new StringBuffer("");
            String outStr;
            // 组合控制台输出信息字符串
            BufferedReader br = new BufferedReader(inputStreamReader);
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();

            // 要用来做导入用的sql目标文件：
            File file = new File(backupPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream fout = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fout, "utf-8");
            writer.write(outStr);
            writer.flush();
            inputStream.close();
            inputStreamReader.close();
            br.close();
            writer.close();
            fout.close();
            System.out.println("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据库还原
     * @param source
     */
    public static void restore(String source) {
        Runtime runtime = Runtime.getRuntime();
        String command = MYSQL_HOME+"/bin/mysql -hlocalhost -u "+USERNAME+" -p"+PASSWORD+" --default-character-set=utf8 "+DATABASE;
//        String command = MYSQL_HOME+"/bin/mysql.exe -hlocalhost -u "+USERNAME+" -p"+PASSWORD+" --default-character-set=utf8 "+DATABASE;
        try {
            Process process = runtime.exec(command);
            OutputStream outputStream = process.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(source), "utf-8"));
            String str = null;
            StringBuffer sb = new StringBuffer();
            while ((str = br.readLine()) != null) {
                sb.append(str + "\r\n");
            }
            str = sb.toString();
            // System.out.println(str);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream,
                    "utf-8");
            writer.write(str);
            writer.flush();
            outputStream.close();
            br.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据库密码
     */
    public static String USERNAME;
    /**
     * 数据库用户名
     */
    public static String PASSWORD;
    /**
     * 数据库目录
     */
    public static String MYSQL_HOME;
    /**
     * 使用的数据库
     */
    public static String DATABASE;

    @Value("${spring.datasource.username}")
    public void setUsername(String username) {
        MySQLUtils.USERNAME = username;
    }
    @Value("${spring.datasource.password}")
    public void setPassword(String password) {
        MySQLUtils.PASSWORD = password;
    }
    @Value("${data.home}")
    public void setMysqlHome(String home) {
        MySQLUtils.MYSQL_HOME = home;
    }
    @Value("${data.name}")
    public void setDatabase(String database) {
        MySQLUtils.DATABASE = database;
    }
}
