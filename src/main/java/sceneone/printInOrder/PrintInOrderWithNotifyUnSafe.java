package sceneone.printInOrder;

/**
 * wait/notify
 * 这种写法可以实现，在执行thread one,two,three的业务逻辑是，三个线程是并行的，当汇总结果时(打印),三个线程是顺序执行的
 * 这种写法容易出现死锁，当threadOne执行完的时候，已经调用过preMonitor.notify(),可能threadTwo还没开始执行，threadTwo第一句wait()会一直等待.
 * 可以尝试在每个线程进入和执行完毕打印一下Log。如下情况，则会发生永久的等待。
 *
 * thread three start
 * thread one start
 * A
 * thread one end
 * thread two start
 *
 *
 *因此该思路还有些问题，请看PrintInOrderWithNotifySafe类
 */
public class PrintInOrderWithNotifyUnSafe {

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
