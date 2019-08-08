package others;

import java.util.List;

public class Work
{
    public void process(MyThread3.Data data, List<Integer> numbers)
    {
        for (Integer n: numbers) {
            data.value += n;
        }
    }
}
