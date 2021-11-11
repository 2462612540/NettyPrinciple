package com.zhuangxiaoyan.nio.zerocopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Classname NIOClient
 * @Description TODO
 * @Date 2021/11/6 11:22
 * @Created by xjl
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 7001));

        String fileName = "D:\\softwaresavfile\\Github\\JAVA_NIO\\NIO\\src\\main\\resources\\VSCodeUserSetup-x64-1.61.2.exe";

        //得到一个文件channel
        File file;
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        //准备发送
        long startTime = System.currentTimeMillis();

        //在liunx 在使用 transferto 方式可以完成传输 在window 下调用时候只能发送8M 如果大于时候就是需要分段进行传输文件

        long transfercount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的总的字节数=" + transfercount + "耗时：" + (System.currentTimeMillis() - startTime));
        fileChannel.close();
    }
}
