package com.work.borrow;

import java.io.*;

/*
 * @ClassName MysqlTest
 * @Description mysql数据库备份恢复操作
 * @Author 陈彦磊
 * @Date 2018/12/3- 12:38
 * @Version 1.0
 **/
public class MysqlTest {
    private static final String COMMAND = "mysqldump -h localhost -u root -proot borrow";
    private static final String MYSQL_BIN_PATH = "E:/Server/mysql-5.7.24-winx64";

    public static void backup(String backupPath) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(MYSQL_BIN_PATH+"/bin/" + COMMAND);
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

    public static void main(String[] args) {
        String backupPath = "D:/borrow/data/201812/borrow.sql";
        backup(backupPath);
    }
}
