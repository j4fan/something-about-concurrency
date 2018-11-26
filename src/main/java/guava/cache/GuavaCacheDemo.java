package guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaCacheDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LoadingCache<String,String> caches = CacheBuilder.newBuilder().
                maximumSize(100).expireAfterWrite(5, TimeUnit.SECONDS).
                build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return null;
                    }
                });
        caches.put("abc","fan");
        while(true){
            if(caches.getIfPresent("abc")==null){
                System.out.println("query from db and get the new value jiang");
                caches.put("abc","jiang");
            }else {
                System.out.println(caches.getIfPresent("abc"));
            }
            Thread.sleep(1000L);
        }
    }
}
