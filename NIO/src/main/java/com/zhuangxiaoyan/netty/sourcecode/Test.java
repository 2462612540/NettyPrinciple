package com.zhuangxiaoyan.netty.sourcecode;

import io.netty.util.NettyRuntime;

/**
 * @Classname Test
 * @Description TODO
 * @Date 2021/11/7 10:12
 * @Created by xjl
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(NettyRuntime.availableProcessors() * 2);
    }
}
