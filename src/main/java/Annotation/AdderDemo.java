package Annotation;

public class AdderDemo {

    public static void main(String[] args) {
        Adder adder = (a, b) -> a + b;
        System.out.println(adder.add(1, 2));
    }
}
