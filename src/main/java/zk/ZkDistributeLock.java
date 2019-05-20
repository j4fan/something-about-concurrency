package zk;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import lombok.extern.log4j.Log4j2;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * zk分布式锁原理：每个客户端获取锁的时候创建临时顺序的node,因为node是自增的，获取rootlock下面Node的最小值，如果有比当前id更小的，则等待锁，否则获得锁
 */
public class ZkDistributeLock implements DistributeLock {
    private static final Logger LOG = Logger.getLogger(ZkDistributeLock.class.getName());
    private static final String HOST = "localhost";
    private static final String ROOT_LOCKS = "/locks";
    private String currentId = "";

    private ZooKeeper zk;

    public ZkDistributeLock() {
        try {
            this.zk = new ZkConnection().connect(HOST);
        } catch (IOException e) {
            LOG.info("创建zookeeper连接失败");
        } catch (InterruptedException e) {
            LOG.info("zookeeper连接中断");
        }
    }

    @Override
    public synchronized boolean lock() {
        //创建临时节点
        try {
            checkRootNode();
            String currentPath = zk.create(ROOT_LOCKS + "/", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            LOG.info("currentPath:"+currentPath);
            List<String> children = zk.getChildren(ROOT_LOCKS, false);
            children.forEach(i->LOG.info("child:"+i));
            ImmutableList<String> sortedMembers = Ordering.natural().immutableSortedCopy(children);
            String currentId = currentPath.substring(currentPath.lastIndexOf("/")+1);
            LOG.info("currentId:"+currentId);
            int memberIndex = sortedMembers.indexOf(currentId);
            if (memberIndex==0) {
                LOG.info("线程"+Thread.currentThread().getId()+"获取锁成功"+"Node:"+currentId);
                return false;
            } else {
                LOG.info("线程"+Thread.currentThread().getId()+"获取锁失败"+"Node:"+currentId);
                return true;
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean unlock() {
        return false;
    }

    public static void main(String[] args) {
        ZkDistributeLock zkDistributeLock = new ZkDistributeLock();
        Thread thread1 = new Thread(()->{
            //加锁
            zkDistributeLock.lock();
            //执行业务逻辑
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                LOG.info(Thread.currentThread().getId()+"休眠失败");
            }
            //释放锁

        });
        Thread thread2 = new Thread(()->{
            zkDistributeLock.lock();
        });
        thread1.start();
        thread2.start();
        Thread.yield();
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
