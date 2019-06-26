package common;

import java.lang.reflect.Field;

/**
 * 内部类反射问题
 */
public class A {

     class B {
        Object o1;
        Object o2;
        Object o3;

        B() {
        }
    }

    private void reflectionOfB() {
        B b = new B();
        Field[] fields = b.getClass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }

    public static void main(String[] args) {
        A a = new A();
        a.reflectionOfB();
    }
}
