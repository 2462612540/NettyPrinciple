package com.zhuangxiaoyan.nio.buffer;

import javax.swing.*;
import java.nio.IntBuffer;

/**
 * @Classname BasicBuffer
 * @Description TODO
 * @Date 2021/10/28 23:04
 * @Created by xjl
 */
public class BasicBuffer {
    public static void main(String[] args) {
        //穿件一个buffer 大小是5 即为存放是5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        //数据的存放
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
        //如何将buffer的数据读取出来
        intBuffer.flip();
        //如何将的buffer的数据读取出来
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
