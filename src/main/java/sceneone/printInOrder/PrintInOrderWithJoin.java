package sceneone.printInOrder;

/**
 * join->wait for this thread to die
 *
 * 因此这里threadOne,Two,Three的顺序如何都无所谓，即使出现特殊情况，threadOne执行完毕，threadTwo才开始执行，也不会抛异常
 */
public class PrintInOrderWithJoin {

    public static void main(String[] args) {

        Thread threadOne = new Thread(() -> {
            //threadOne 业务逻辑
            System.out.println("A");
        });

        Thread threadTwo = new Thread(() -> {
            //threadTwo 业务逻辑
            try {
                threadOne.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B");

        });

        Thread threadThree = new Thread(() -> {
            //threadThree 业务逻辑
            try {
                threadTwo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("C");
        });

        threadThree.start();
        threadOne.start();
        threadTwo.start();

    }

}
