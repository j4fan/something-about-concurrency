package sceneone.printInOrder;

public class PrintInOrderWithJoin {

    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(()->{
            System.out.println("A");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadTwo = new Thread(()->{
            System.out.println("B");
        });

        Thread threadThree = new Thread(()->{
            System.out.println("C");
        });

        threadOne.start();
        threadOne.join();
        threadTwo.start();
        threadTwo.join();
        threadThree.start();
        threadThree.join();

    }
}
