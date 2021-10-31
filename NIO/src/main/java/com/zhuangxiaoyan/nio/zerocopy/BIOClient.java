package com.zhuangxiaoyan.nio.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * @Classname NIOClient
 * @Description TODO
 * @Date 2021/11/1 7:25
 * @Created by xjl
 */
public class BIOClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 7001);


        String fileName = "D:\\softwaresavfile\\Github\\JAVA_NIO\\NIO\\src\\main\\resources\\VSCodeUserSetup-x64-1.61.2.exe";
        InputStream inputStream = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;

        long startTime = System.currentTimeMillis();

        while ((readCount = inputStream.read(buffer)) >= 0) {
            total += readCount;
            dataOutputStream.write(buffer);
        }

        System.out.println("发送总字节数： " + total + ", 耗时： " + (System.currentTimeMillis() - startTime));

        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
