package com.zhuangxiaoyan.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @Classname NIOFileChannel4
 * @Description TODO
 * @Date 2021/10/30 14:55
 * @Created by xjl
 */
public class NIOFileChannel4 {

    public static void main(String[] args) throws IOException {
        File file = new File("D:\\softwaresavfile\\Github\\JAVA_NIO\\NIO\\src\\main\\resources\\微信图片_20210422220149.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);

        File file1 = new File("D:\\softwaresavfile\\Github\\JAVA_NIO\\NIO\\src\\main\\resources\\new copy.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream(file1);

        //获取各个流对应的filechannel
        FileChannel source = fileInputStream.getChannel();
        FileChannel dastch = fileOutputStream.getChannel();

        //使用的transferform完成拷贝
        dastch.transferFrom(source, 0, source.size());

        //关闭相关的流
        source.close();
        dastch.close();

        fileInputStream.close();
        fileOutputStream.close();
    }
}
