package others;

@FunctionalInterface
public interface ParameterizedThreadStart<T>{

    void run(T context);
}