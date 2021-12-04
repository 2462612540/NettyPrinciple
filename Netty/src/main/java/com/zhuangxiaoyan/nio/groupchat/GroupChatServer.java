package com.zhuangxiaoyan.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Classname GroupChatServer
 * @Description TODO
 * @Date 2021/11/4 22:40
 * @Created by xjl
 */
public class GroupChatServer {
    private static final int PORT = 6667;
    //定义属性
    private Selector selector;
    private ServerSocketChannel listenerChannel;

    //构造器
    //初始化工作
    public GroupChatServer() {
        try {
            //获取选择器
            selector = Selector.open();
            //SeverSockerChannel
            listenerChannel = ServerSocketChannel.open();
            //绑定端口
            listenerChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenerChannel.configureBlocking(false);
            //将该listenChannel 注册到selector
            listenerChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //创建一个服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

    //监听程序
    public void listen() {
        try {
            //循环处理
            while (true) {
                int count = selector.select();
                if (count > 0) {
                    //遍历得到selectionkey 集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //取出selectionkey
                        SelectionKey key = iterator.next();
                        //监听到了accpet
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenerChannel.accept();
                            sc.configureBlocking(false);
                            //将sc 注册到selector
                            sc.register(selector, SelectionKey.OP_READ);
                            //提示信息
                            System.out.println(sc.getRemoteAddress() + "上线了……");
                        }
                        if (key.isReadable()) {
                            //通道是可以读取的转态
                            readData(key);
                        }
                        //手动从集合中移动当前的selectionkey，防止重复操作
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待连接中……");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    //读取客户端消息
    public void readData(SelectionKey key) {
        //定义一个SocketChannel
        SocketChannel channel = null;
        try {
            //得到channel
            channel = (SocketChannel) key.channel();
            //创建一个buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);
            //根据count的值进行读取
            if (count > 0) {
                //把缓冲区的数据变成字符串
                String msg = new String(buffer.array());
                //输出数据
                System.out.println("from 客户端：" + msg);

                //向其他的客户端转发消息
                sendInfoToOtherClients(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了……");
                //取消注册 关闭通道
                key.cancel();
                //关闭通道
                channel.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    //转发消息给其他用户得通道
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器的转发消息中……");
        //遍历 所有的注册到selector上的socketChannel 并排除自己
        for (SelectionKey key : selector.keys()) {
            //通过key 取出SocketChannel
            Channel targetChannel = key.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                //转型
                SocketChannel des = (SocketChannel) targetChannel;
                //将msg 存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer的数据写入到通道
                des.write(buffer);
            }

        }

    }
}
