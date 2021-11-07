package com.zhuangxiaoyan.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @Classname NettyServerHandler
 * @Description 1我们自定义一个Handler需要继续netty规定好的某个HandlerAdapter(规范)
 * 2这时我们自定义一个Handler ,才能称为一个handLer
 * @Date 2021/11/6 17:52
 * @Created by xjl
 */
public class NettyServerHandlerTaskQueuq extends ChannelInboundHandlerAdapter {

    //读取数据的实际 这里我们可以读取客户端的发送的消息

    /**
     * @description ChannelHandlercontext ctx:上下文对象，含有管道pipeline  地址
     * Object msg 就是客户端发送的数据默认object
     * 比如这个有一个非常耗时的业务 需要异步执行--》提交channel 对应的NIOEventLoop中task
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        // 用户自定义定时任务-》该任务是提交到scheduleTeskqueue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端狗狗……", CharsetUtil.UTF_8));
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println("发生异常……");
                }
            }
        },5, TimeUnit.SECONDS);

        System.out.println("go on……");
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
