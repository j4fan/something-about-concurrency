package common;

public class FinallyDemo {

    private static int a =0;

    private int test(){
        try{
            a = 10 ;
            throw new RuntimeException("error");
        }catch (Exception e){
            a += 10;
            return a;
        }finally {
            a += 10;
        }
    }

    public static void main(String[] args) {
        FinallyDemo finallyDemo = new FinallyDemo();
        System.out.println("return result of a is : "+finallyDemo.test());
        System.out.println("final result of a is : "+a);
    }
}
