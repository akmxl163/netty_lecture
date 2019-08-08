package others;

public class Teacher {
    public void teach(){
        System.out.println("teach...");
    }

    public Teacher(String name){
        this.name = name;
    }
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
