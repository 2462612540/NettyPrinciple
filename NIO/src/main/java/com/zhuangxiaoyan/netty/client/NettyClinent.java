package com.zhuangxiaoyan.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Classname NettyClinent
 * @Description TODO
 * @Date 2021/11/6 17:35
 * @Created by xjl
 */
public class NettyClinent {
    private final static int PORT=9000;
    private final static String HOST = "127.0.0.1";
    public static void main(String[] args) throws InterruptedException {
        //客户端只需要一个事件循环组
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            //创建客户端启动对象
            //注意从客户端使用不是ServerBootStrap而是 BootStrap
            Bootstrap bootstrap = new Bootstrap();
            //设置相关参数
            bootstrap.group(eventExecutors)//设置线程组
                    .channel(NioSocketChannel.class)//设置客户通道的实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());//加入自己的处理器
                        }
                    });
            System.out.println("客户端准备好了……");
            //启动客户端去连接服务器端
            //关于的channelFuture 要分析 涉及到netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();

            //给关系的通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
