package netty.custom_protocol;

import java.nio.charset.Charset;

public class ProtocolBean {
    private int head;
    private byte[] content;

    public ProtocolBean() {
    }

    @Override
    public String toString() {
        return "ProtocolBean{" +
                "head=" + head +
                ", content=" + new String(content, Charset.forName("utf-8")) +
                '}';
    }

    public ProtocolBean(int head, byte[] content) {
        this.head = head;
        this.content = content;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
