package sceneone.printInOrder;

public class PrintInOrderWithLoopByWaitNotify extends Thread {

    private Object monitor = new Object();

    private static String str = "A";

    PrintInOrderWithLoopByWaitNotify(String name) {
        this.setName(name);
    }

    @Override
    public void run() {
        for(int i=0;i<30;i++){
            synchronized (monitor){
                if(str.equals(getName())){
                    printByName(getName());
                    str = nextString(getName());
                    monitor.notifyAll();
                }
            }
        }
    }

    private String nextString(String str){
        if(str.equals("A")){
            str = "B";
            return str;
        }
        if(str.equals("B")){
            str = "C";
            return str;
        }
        if(str.equals("C")){
            str = "A";
            return str;
        }
        return null;
    }

    private void printByName(String name) {
        System.out.print(name);
        if (name.equals("C")) {
            System.out.println();
        }
    }


    public static void main(String[] args) {
        PrintInOrderWithLoopByWaitNotify pa = new PrintInOrderWithLoopByWaitNotify("A");
        PrintInOrderWithLoopByWaitNotify pb = new PrintInOrderWithLoopByWaitNotify("B");
        PrintInOrderWithLoopByWaitNotify pc = new PrintInOrderWithLoopByWaitNotify("C");
        pa.start();
        pb.start();
        pc.start();
    }
}
