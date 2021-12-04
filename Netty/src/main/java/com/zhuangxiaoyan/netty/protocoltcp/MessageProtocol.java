package com.zhuangxiaoyan.netty.protocoltcp;

/**
 * @Classname TestServer
 * @Description
 * @Date 2021/11/7 14:10
 * @Created by xjl
 */
//协议包
public class MessageProtocol {
    private int len; //关键
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
