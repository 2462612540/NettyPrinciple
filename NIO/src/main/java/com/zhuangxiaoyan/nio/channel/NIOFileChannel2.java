package com.zhuangxiaoyan.nio.channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Classname NIOFileChannel2
 * @Description 将文件的数据读取到控制台
 * @Date 2021/10/30 14:29
 * @Created by xjl
 */
public class NIOFileChannel2 {

    public static void main(String[] args) throws IOException {
        String filepath = "D:\\softwaresavfile\\Github\\JAVA_NIO\\NIO\\src\\main\\resources\\test.txt";
        //创建文件的输入流
        File file = new File(filepath);
        FileInputStream fileInputStream = new FileInputStream(file);
        //通过fileinputStream  获取filechannel-->>实际类型的 FilechannelImpl
        FileChannel fileChannel = fileInputStream.getChannel();
        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        //将通道的数据读入到buffer
        fileChannel.read(byteBuffer);

        //将bytebuffer中的字节数据转为string
        System.out.println(new String(byteBuffer.array()));
    }
}
