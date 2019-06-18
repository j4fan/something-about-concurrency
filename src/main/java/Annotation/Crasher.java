package Annotation;

import java.util.ArrayList;
import java.util.List;

public class Crasher<T> {


    private List<T> versions = new ArrayList<>();

    public final void safe(T... toAdd) {
        for (T version : toAdd) {
            versions.add(version);
        }
    }

    public T[] broken(T... elements) {
       return elements;
    }

    public T getFirst(T... elements) {
        return elements.length > 0 ? elements[0] : null;
    }

    public T[] broker(T seed) {
        T[] plant = broken(seed, seed, seed);
        return plant;
    }

    public static void main(String[] args) {
        Crasher<String> bt = new Crasher<>();
        String[] plans = bt.broker("123");//这里会报错
        System.out.println(bt.getFirst(plans));
    }
}
