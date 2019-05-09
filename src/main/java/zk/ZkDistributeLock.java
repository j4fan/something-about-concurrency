package zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * zk分布式锁原理：每个客户端获取锁的时候创建临时顺序的node,因为node是自增的，获取rootlock下面Node的最小值，如果有比当前id更小的，则等待锁，否则获得锁
 */
public class ZkDistributeLock implements DistributeLock {

    private static final String HOST = "localhost";

    private static final String ROOT_LOCKS = "/locks";

    private ZooKeeper zk;

    public ZkDistributeLock() throws IOException, InterruptedException, KeeperException {
        this.zk = new ZkConnection().connect(HOST);
        checkRootNode();
    }

    @Override
    public void lock() {
        //创建临时节点
        try {
            String node = zk.create(ROOT_LOCKS+"/", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(node);
            List<String> children = zk.getChildren(ROOT_LOCKS, false);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void unlock() {

    }

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        ZkDistributeLock zklock = new ZkDistributeLock();
        zklock.lock();
        Thread.sleep(60000L);
    }

    private void checkRootNode() throws KeeperException, InterruptedException {
        Stat stat = zk.exists(ROOT_LOCKS, false);
        if (stat == null) {
            //如果没有root_lock 创建
            zk.create(ROOT_LOCKS, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        }
    }

}
