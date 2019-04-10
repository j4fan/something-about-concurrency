package lombok;

@Builder
public class TestDemoBuilder {

    private String name;
    private String password;
    private Integer age;

    public static void main(String[] args) {
        TestDemoBuilder testDemoBuilder =
                TestDemoBuilder.builder().name("fan").password("123456").age(5).build();
    }
}
