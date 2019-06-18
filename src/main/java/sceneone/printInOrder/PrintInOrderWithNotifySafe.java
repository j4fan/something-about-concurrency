package sceneone.printInOrder;

/**
 *
 */
public class PrintInOrderWithNotifySafe {

    private static Object preMonitor = new Object();
    private static Object subMonitor = new Object();

    public static void main(String[] args) {

        Thread threadOne = new Thread(() -> {
            //threadOne业务逻辑
            System.out.println("thread one start");
            synchronized (preMonitor) {
                System.out.println("A");
                preMonitor.notify();
            }
            System.out.println("thread one end");
        });

        Thread threadTwo = new Thread(() -> {
            //threadTwo业务逻辑
            System.out.println("thread two start");
            synchronized (preMonitor) {

                try {
                    preMonitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B");
                synchronized (subMonitor) {
                    subMonitor.notify();
                }
            }
            System.out.println("thread two end");
        });

        Thread threadThree = new Thread(() -> {
            System.out.println("thread three start");
            //threadThree业务逻辑
            synchronized (subMonitor) {

                try {
                    subMonitor.wait();
                    System.out.println("C");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("thread three end");
        });

        threadThree.start();
        threadOne.start();
        threadTwo.start();

    }

}
