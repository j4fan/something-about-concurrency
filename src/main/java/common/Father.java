package common;

import java.lang.reflect.InvocationTargetException;

public class Father extends GrandFather{

    @Override
    protected void speak() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("father speak");
    }

}
