package others;

public class ParameterizedThread<T> implements Runnable{

    private T context;
    private ParameterizedThreadStart<T> parameterStart;

    public ParameterizedThread(T context,ParameterizedThreadStart<T> parameterStart){
        this.context=context;
        this.parameterStart=parameterStart;
    }

    public T getContext(){
        return context;
    }

    @Override
    public void run() {
        parameterStart.run(context);
    }
}