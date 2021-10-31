package com.zhuangxiaoyan.nio.channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Classname NIOFileChannel3
 * @Description TODO
 * @Date 2021/10/30 14:39
 * @Created by xjl
 */
public class NIOFileChannel3 {
    public static void main(String[] args) throws IOException {

        File file = new File("D:\\softwaresavfile\\Github\\JAVA_NIO\\NIO\\src\\main\\resources\\1.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel1 = fileInputStream.getChannel();

        File file1 = new File("D:\\softwaresavfile\\Github\\JAVA_NIO\\NIO\\src\\main\\resources\\2.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file1);
        FileChannel fileChanne2 = fileOutputStream.getChannel();

        //创建一个bytebuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (true) {
            //这个有一个复位的操作 清空buffer
            byteBuffer.clear();
            int read = fileChannel1.read(byteBuffer);
            if (read == -1) {
                //表示读取完成
                break;
            }
            //将buffer中的数据写入到filechannel2---2.txt
            byteBuffer.flip();
            fileChanne2.write(byteBuffer);
        }
        //关闭相关的流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
