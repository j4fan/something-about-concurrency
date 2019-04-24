package optional;

import java.util.Optional;

public class OptionalDemo {

    public static void main(String[] args) {

        Optional<String> s = Optional.of("test");
        Optional<String> s2 = s.flatMap(s1->Optional.of("TEST"));
        System.out.println(s2.get());

    }

}
