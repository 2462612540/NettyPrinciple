package com.zhuangxiaoyan.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Classname NettyServer
 * @Description TODO
 * @Date 2021/11/6 17:35
 * @Created by xjl
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        //创建BossGroup 和 WorkerGroup
        //1. 创建两个线程组 bossGroup 和 workerGroup
        //2. bossGroup 只是处理连接请求 , 真正的和客户端业务处理，会交给 workerGroup完成
        //3. 两个都是无限循环
        //4. bossGroup 和 workerGroup 含有的子线程(NioEventLoop)的个数
        //   默认实际 cpu核数 * 2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup(8);
        try {
            //创建服务器端的启动，配置启动参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            //使用链式编程进行设置
            bootstrap.group(bossGroup, workGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列 等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持互动的连接的状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //创建一个通道测试对象（匿名通道） 给pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });//给我们workerGroup的EventLoop 对应的管道设置处理器
            System.out.println("服务器准备就绪……");
            //保定一个端口并且同步 生成一个ChannelFuture 对象
            //启动服务器（绑定的端口）
            ChannelFuture cf = bootstrap.bind(9000).sync();

            //给cf 注册监听器，监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()) {
                        System.out.println("监听端口 9000 成功");
                    } else {
                        System.out.println("监听端口 9000 失败");
                    }
                }
            });

            //对关闭的通道进行监听
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
