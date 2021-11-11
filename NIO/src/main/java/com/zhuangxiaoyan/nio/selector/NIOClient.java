package com.zhuangxiaoyan.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Classname NIOClient
 * @Description TODO
 * @Date 2021/11/1 7:25
 * @Created by xjl
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        //创建一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞模式
        socketChannel.configureBlocking(false);
        //提供服务端的ip
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        //连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要，客户端不会阻塞，可以做其他工作");
            }
        }
        //如果成功
        String str = "hell 庄小焱";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        //发送数据
        socketChannel.write(buffer);
        System.in.read();
    }
}
