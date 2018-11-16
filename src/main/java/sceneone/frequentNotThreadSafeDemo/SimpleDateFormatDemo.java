package sceneone.frequentNotThreadSafeDemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SimpleDateFormatDemo {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static ExecutorService es = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10), new ThreadPoolExecutor.DiscardPolicy());

    public static void main(String[] args) throws ParseException {
        String s = "2018-10-01 11:00:00";
        es.execute(() -> {
            try {
                sdf.parse(s);
                System.out.println("complete...task1");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        es.execute(() -> {
            try {
                sdf.parse(s);
                System.out.println("complete...task2");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        es.execute(() -> {
            try {
                sdf.parse(s);
                System.out.println("complete...task3");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        es.execute(() -> {
            try {
                sdf.parse(s);
                System.out.println("complete...task4");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        es.execute(() -> {
            try {
                sdf.parse(s);
                System.out.println("complete...task5");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        es.shutdown();
    }

}
