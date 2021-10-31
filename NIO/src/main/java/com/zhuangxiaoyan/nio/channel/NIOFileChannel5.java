package com.zhuangxiaoyan.nio.channel;

import java.nio.ByteBuffer;

/**
 * @Classname NIOFileChannel5
 * @Description TODO
 * @Date 2021/10/30 15:05
 * @Created by xjl
 */
public class NIOFileChannel5 {
    public static void main(String[] args) {
        //创建一个buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            buffer.put((byte) i);
        }
        //读取
        buffer.flip();
        // 得到一个只读取的buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
        //报错 将不允许写入数据
        readOnlyBuffer.put((byte)100);
    }
}
