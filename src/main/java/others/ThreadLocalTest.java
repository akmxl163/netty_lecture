package others;

public class ThreadLocalTest {
    private ThreadLocal<Long> longTL = new ThreadLocal<>();
    private ThreadLocal<String> stringTL = new ThreadLocal<>();
    public void set(){
        longTL.set(Thread.currentThread().getId());
        stringTL.set(Thread.currentThread().getName());
    }

    public long getLong(){
        return longTL.get();
    }

    public String getString(){
        return stringTL.get();
    }

    public static void main(String[] args) throws InterruptedException{
        System.out.println("好戏开始了......");
        ThreadLocalTest tlt = new ThreadLocalTest();

//        tlt.set();
        System.out.println(tlt.getLong());
        System.out.println(tlt.getString());
        System.out.println("*******************");

        Thread thread = new Thread("MyThread"){
            @Override
            public void run() {
                tlt.set();
                System.out.println(tlt.getLong());
                System.out.println(tlt.getString());
            }
        };

        thread.start();
        thread.join();
        System.out.println("++++++++++++++++++++++");

        System.out.println(tlt.getLong());
        System.out.println(tlt.getString());
    }
}
