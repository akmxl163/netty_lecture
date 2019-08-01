package protocbuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class TestProtocbuf {
    public static void main(String[] args) {
        DateInfo.Student student = DateInfo.Student.newBuilder()
                .setAddr("成都")
                .setAge(22)
                .setName("duding").build();
        byte[] bytes = student.toByteArray();
        try {
            DateInfo.Student student1 = DateInfo.Student.parseFrom(bytes);
            System.out.println(student1.getAddr());
            System.out.println(student1.getAge());
            System.out.println(student1.getName());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
