package common;

import java.lang.reflect.InvocationTargetException;

public class GrandFather {

    protected void speak() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("grandfather speak");
    }

}
