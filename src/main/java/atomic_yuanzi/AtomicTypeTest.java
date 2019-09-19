package atomic_yuanzi;

import groupbyday.BaseFinger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicTypeTest {
    public static void main(String[] args) {

        AtomicClsPerson atomicClsPerson = new AtomicClsPerson();
        for (int i = 0; i < 10; i++) {
            new Thread(()-> {
                try{
                    TimeUnit.MILLISECONDS.sleep(10);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(atomicClsPerson.age++);
            }
            ).start();
        }
    }

    static void atomicReference(){
        AtomicReference<BaseFinger> arBf = new AtomicReference<>();
        BaseFinger bf = new BaseFinger("dasf", "189-1569", "asmoie/vewg/nn");
        arBf.set(bf);
        BaseFinger baseFinger = new BaseFinger("ssss", "sddddd", "dsa/1235/fx000");
        arBf.compareAndSet(bf, baseFinger);
        System.out.println(arBf.get());
    }

    static void atomicArray(){
        AtomicIntegerArray aia = new AtomicIntegerArray(10);
        for (int i = 0; i < aia.length(); i++) {
            aia.compareAndSet(i,0, i+1);
        }

        System.out.println(aia);

        for (int i = 0; i < aia.length(); i++) {
            aia.compareAndSet(i,i+1 + (i+1)%2, i+2);
        }

        System.out.println(aia);
    }

    static void atomicObjectField(){
        AtomicIntegerFieldUpdater<BaseFinger> aInt = AtomicIntegerFieldUpdater.newUpdater(BaseFinger.class, "age");
        BaseFinger bf = new BaseFinger("dasf", "189-1569", "asmoie/vewg/nn");
        bf.setAge(1);
        aInt.compareAndSet(bf, 1, 3);
        System.out.println(bf);
    }

}

class AtomicClsPerson{
    int age=1;
}
