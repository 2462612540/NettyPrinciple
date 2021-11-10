package com.zhuangxiaoyan.netty.heartbeat;
/**
 * @Classname NettyClinent
 * @Description TODO
 * @Date 2021/11/6 17:35
 * @Created by xjl
 */
public class Test {
    public static void main(String[] args) throws Exception {

        System.out.println(System.nanoTime()); //纳秒  10亿分之1
        Thread.sleep(1000);
        System.out.println(System.nanoTime());

    }
}
