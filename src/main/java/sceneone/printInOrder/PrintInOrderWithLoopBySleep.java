package sceneone.printInOrder;

public class PrintInOrderWithLoopBySleep extends Thread{

    private static int count = 0;

    private static final int MAX_COUNT = 30;

    PrintInOrderWithLoopBySleep(String name){
        this.setName(name);
    }

    @Override
    public void run(){
        while(count<MAX_COUNT){
            int i = count%3;
            switch (i){
                case 0: if(this.getName().equals("A")) printByName(this.getName()); break;
                case 1: if(this.getName().equals("B")) printByName(this.getName()); break;
                case 2: if(this.getName().equals("C")) printByName(this.getName()); break;
                default:
            }

        }
    }

    private void printByName(String name){
        System.out.print(name);
        if(name.equals("C")){
            System.out.println();
        }
        count++;
    }


    public static void main(String[] args) {
        PrintInOrderWithLoopBySleep pa = new PrintInOrderWithLoopBySleep("A");
        PrintInOrderWithLoopBySleep pb = new PrintInOrderWithLoopBySleep("B");
        PrintInOrderWithLoopBySleep pc = new PrintInOrderWithLoopBySleep("C");
        pa.start();
        pb.start();
        pc.start();
    }
}
