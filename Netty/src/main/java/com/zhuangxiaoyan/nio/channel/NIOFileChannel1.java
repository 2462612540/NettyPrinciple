package com.zhuangxiaoyan.nio.channel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Classname NIOFileChannel
 * @Description TODO
 * @Date 2021/10/28 23:39
 * @Created by xjl
 */
public class NIOFileChannel1 {

    public static void main(String[] args) throws IOException {
        String str = "hi 庄小焱";
        //创建一个输出流->channel
        String filepath = "D:\\softwaresavfile\\Github\\JAVA_NIO\\NIO\\src\\main\\resources\\test.txt";
        FileOutputStream fileOutputStream = new FileOutputStream(filepath);
        //通过FileOutputStream获取一个
        // FileChannel的真实的类型是FileChannelImpl 类型
        FileChannel fileChannel = fileOutputStream.getChannel();
        //创建一个buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //数据放入到buffer
        byteBuffer.put(str.getBytes());
        //对bytebuffer 进行的flip
        byteBuffer.flip();
        //将bytebuffer的数据写入到filechannel
        fileChannel.write(byteBuffer);
        //关闭
        fileOutputStream.close();
    }
}
