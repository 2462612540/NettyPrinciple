package com.zhuangxiaoyan.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;

/**
 * @Classname NettyServerHandler
 * @Description 1我们自定义一个Handler需要继续netty规定好的某个HandlerAdapter(规范)
 * 2这时我们自定义一个Handler ,才能称为一个handLer
 * @Date 2021/11/6 17:52
 * @Created by xjl
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取数据的实际 这里我们可以读取客户端的发送的消息

    /**
     * @description ChannelHandlercontext ctx:上下文对象，含有管道pipeline  地址
     * Object msg 就是客户端发送的数据默认object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程" + Thread.currentThread().getName());
        System.out.println("sever ctx=" + ctx);
        System.out.println("server ctx=" + ctx);

        //显示channel和pipline的关系
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();//本质是一个双向链表入栈和出栈问题

        //将msg 转移到一个bytebuf 与NIO的buffer 是有区别的。
        ByteBuf buf = (ByteBuf) msg;

        System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址是：" + ctx.channel().remoteAddress());
    }

    //读取消息完毕数据
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFlush 是write +flush
        //将数据写到缓冲中并刷新
        //一般的 我们将发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 狗狗……", CharsetUtil.UTF_8));
    }

    //处理异常 医保是需要关闭管道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
