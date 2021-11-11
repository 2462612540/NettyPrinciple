package com.zhuangxiaoyan.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Classname SelectorTest1
 * @Description TODO
 * @Date 2021/10/30 22:31
 * @Created by xjl
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        //创建serversocketchanel ->serversocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //得到一个selector对象
        Selector selector = Selector.open();

        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //把serverSocketChannel注册到selector关心的时间是 Accpect
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //等待客户端连接
        while (true) {
            //如没有时间那就返回
            if (selector.select(1000) == 0) {
                //没有事件发生
                System.out.println("服务器等待一秒 无连接");
                continue;
            }
            //如果返回的是》0 获取到相关的集合 已经获取到关注的事件。通过selectionkey 反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> KeyIterator = selectionKeys.iterator();

            while (KeyIterator.hasNext()) {
                //获取到selectorkey
                SelectionKey key = KeyIterator.next();
                if (key.isAcceptable()) {
                    //表示有客户端连接
                    // 生成一个socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功");
                    System.out.println("客户端生成了一个连接,hash=" + serverSocketChannel.hashCode());
                    //将socketchannel 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //将这个socketchanel 注册到selector 同时给SocketChannel 关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                if (key.isReadable()) {
                    //发生一个读取的时间 通过key 来反向后去channel
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    //获取到channel关联的buffer
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    socketChannel.read(byteBuffer);
                    System.out.println("客户端的数据" + new String(byteBuffer.array()));
                }

                //手动删除集合中移动的selectionKey 防止重复
                KeyIterator.remove();
            }
        }
    }
}
