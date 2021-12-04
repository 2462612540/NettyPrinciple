package com.zhuangxiaoyan.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Classname TestServerinitalizer
 * @Description
 * @Date 2021/11/7 14:19
 * @Created by xjl
 */
public class TestServerinitalizer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道里面加入处理器

        //得到管道
        ChannelPipeline pipeline = ch.pipeline();

        //加入netty的提供的httpserver coder--decoder
        //HttpServerCodec 是netty提供https的编码和解码器
        pipeline.addLast("我的http服务码是：", new HttpServerCodec());

        //增加一个自己的Handler
        pipeline.addLast("MyTestServerHadler", new TestHttpServerHandler());
    }
}
