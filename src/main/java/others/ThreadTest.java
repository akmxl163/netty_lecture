package others;

public class ThreadTest {
    public static void main(String[] args) {
        String msg = "Teacher01";
        Teacher teacher = new Teacher(msg);
        Student student = new Student();
        student.setNo("TK00123");
        new Thread(
                new ParameterizedThread<>(
                        student,
                        (Student context) -> {
                            System.out.println("into thread");
                            context.study(teacher.getName());
                        })
        ).start();
    }
}
