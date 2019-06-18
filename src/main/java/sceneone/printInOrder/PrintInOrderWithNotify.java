package sceneone.printInOrder;

/**
 * wait/notify
 */
public class PrintInOrderWithNotify {

    private static Object preMonitor = new Object();
    private static Object subMonitor = new Object();

    public static void main(String[] args) {

        Thread threadOne = new Thread(() -> {
            synchronized (preMonitor) {
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A");
                preMonitor.notify();
            }
        });
        Thread threadTwo = new Thread(() -> {
            synchronized (preMonitor){
                try {
                    preMonitor.wait();
                    Thread.sleep(2000L);
                    System.out.println("B");
                    synchronized (subMonitor){
                        subMonitor.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        Thread threadThree = new Thread(()->{
            synchronized (subMonitor){
                try {
                    subMonitor.wait();
                    Thread.sleep(2000L);
                    System.out.println("C");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadTwo.start();
        threadThree.start();
        threadOne.start();

    }

}
