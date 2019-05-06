package optional;

import com.sun.javafx.binding.Logging;

import java.util.ArrayList;

public class OptionalDemo {

    public static void main(String[] args) {
        System.out.println(OptionalDemo.class.getClassLoader());
        System.out.println(Logging.class.getClassLoader());
        System.out.println(ArrayList.class.getClassLoader());
    }

}
