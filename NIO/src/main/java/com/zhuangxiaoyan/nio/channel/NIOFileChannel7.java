package com.zhuangxiaoyan.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Classname NIOFileChannel7
 * @Description Scattering :buffer的分散和聚合 将数据写入到buffer时候 可以采用buffer数组 然后在依次写入
 * Gathering  :从buffer读取数据的时候 可以采用buffer数组 然后在依次读取
 * @Date 2021/10/30 15:23
 * @Created by xjl
 */
public class NIOFileChannel7 {

    public static void main(String[] args) throws IOException {
        //使用serversockerChannel 和SocketChannel网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        //绑定端口socket 并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];

        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        //等待客户通知
        SocketChannel socketChannel = serverSocketChannel.accept();

        int messagelen = 8;

        //循环读取
        while (true) {
            int byteRead = 0;
            while (byteRead < messagelen) {
                long num = socketChannel.read(byteBuffers);
                byteRead += num;
                System.out.println("byteRead=" + byteRead);
                //使用的流打印 当前的buffer 和position和limit
                Arrays.asList(byteBuffers).stream().map(buffer -> "position" + buffer.position() + ", limit-" + buffer.limit()).forEach(System.out::println);
            }
            //将所有的buffer进行flip
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());
            //将数据的读取到客户端
            long bytewrite = 0;
            while (bytewrite < messagelen) {
                long num = socketChannel.write(byteBuffers);
                bytewrite += num;
            }
            //将所有的buffer 进行分析clear
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());
            System.out.println("byteRead" + byteRead + "byteWrite" + bytewrite);
        }
    }
}
