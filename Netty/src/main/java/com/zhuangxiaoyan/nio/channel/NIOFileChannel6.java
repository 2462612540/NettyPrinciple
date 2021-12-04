package com.zhuangxiaoyan.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Classname NIOFileChannel6
 * @Description mappedbyteBuffer 可以让文件直接修改（堆外内存） 操作系统不需要拷贝
 * @Date 2021/10/30 15:11
 * @Created by xjl
 */
public class NIOFileChannel6 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\softwaresavfile\\Github\\JAVA_NIO\\NIO\\src\\main\\resources\\1.txt", "rw");
        //获取对应的文件通道
        FileChannel channel = randomAccessFile.getChannel();
        /**
         参数l:Filechannel.MapMode.READ_WRITE使用的读写模式
         参数2:0 :可以直接修改的起始位置
         参数3:5:是映射到内存的大小(不是索引位置的大小) 不是包含5的相当于是【0,5）
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0, (byte) 'S');
        map.put(3, (byte) 'M');
        //关闭文件流
        randomAccessFile.close();
        //提示
        System.out.println("修改成功");
    }
}
