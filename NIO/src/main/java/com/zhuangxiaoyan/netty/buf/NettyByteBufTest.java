package com.zhuangxiaoyan.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @Classname NettyByteBuTest
 * @Description TODO
 * @Date 2021/11/8 22:42
 * @Created by xjl
 */
public class NettyByteBufTest {

    public static void main(String[] args) {
        ByteBuf buf=Unpooled.copiedBuffer("hello world 背景", CharsetUtil.UTF_8);

        //使用相关的方法
        if (buf.hasArray()){
            byte[] content=buf.array();
            System.out.println(new String(content,CharsetUtil.UTF_8));
            System.out.println("bytebuf="+buf);
        }
    }
}
