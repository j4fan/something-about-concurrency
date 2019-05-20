package zk;

public interface DistributeLock {

    boolean lock();

    boolean unlock();
}
