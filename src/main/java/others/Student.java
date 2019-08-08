package others;

public class Student {
    String no;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void study(String grade){
        System.out.println(no + " study "+grade+"...");

    }
}
