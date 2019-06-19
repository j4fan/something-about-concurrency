package sceneone.printInOrder;

/**
 * PrintInOrderWithNotify方法中存在threadOne执行完毕了，threadTwo还没开始执行,造成永久wait()的风险，因此需要通过一个flag字段做标识，当flag为true才调用wait()方法
 */
public class PrintInOrderWithNotifySafe {

    private static Object preMonitor = new Object();
    private static Object subMonitor = new Object();

    private static boolean preFlag = true;
    private static boolean subFlag = true;

    public static void main(String[] args) {

        Thread threadOne = new Thread(() -> {
            //threadOne业务逻辑
            System.out.println("thread one start");
            synchronized (preMonitor) {
                System.out.println("A");
                preMonitor.notify();
                preFlag = false;
            }
            System.out.println("thread one end");
        });

        Thread threadTwo = new Thread(() -> {
            //threadTwo业务逻辑
            System.out.println("thread two start");
            synchronized (preMonitor) {
                try {
                    if (preFlag) {
                        preMonitor.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B");
                preFlag = true;
                synchronized (subMonitor) {
                    subMonitor.notify();
                    subFlag = false;
                }
            }
            System.out.println("thread two end");
        });

        Thread threadThree = new Thread(() -> {
            System.out.println("thread three start");
            //threadThree业务逻辑
            synchronized (subMonitor) {
                try {
                    if (subFlag) {
                        subMonitor.wait();
                    }
                    System.out.println("C");
                    subFlag = true;
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
