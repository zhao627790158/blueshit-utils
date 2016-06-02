package cn.blueshit.cn.test;

import org.apache.zookeeper.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaoheng on 2016/6/2.
 * 下载一个windows版本的zookeeper
 * 修改zoo_sample.cfg--> zoo.cfg
 */
public class ZookpeeperTest {

    // 根节点
    public static final String ROOT = "/root-11";

    public static void waitUntilConnected(ZooKeeper zooKeeper, CountDownLatch connectedLatch) {
        if (ZooKeeper.States.CONNECTING == zooKeeper.getState()) {
            try {
                connectedLatch.await();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
    }
    @org.junit.Test
    public void test() throws Exception {
        final CountDownLatch connectedLatch = new CountDownLatch(1);
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181",30000,new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {
                System.out.println("状态:" + event.getState() + "类型Type:" + event.getType() + "event包装:" + event.getWrapper() + "事件路径:" + event.getPath());
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                connectedLatch.countDown();
            }
        }).start();

        connectedLatch.await();
        // 创建一个总的目录ktv，并不控制权限，这里需要用持久化节点，不然下面的节点创建容易出错
        zk.create(ROOT, "总部".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 然后杭州开一个KTV ,	   PERSISTENT_SEQUENTIAL 类型会自动加上 0000000000 自增的后缀
        zk.create(ROOT+"/杭州分公司", "杭州".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        // 也可以在北京开一个,	   EPHEMERAL session 过期了就会自动删除
        zk.create(ROOT+"/北京分公司", "北京".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        // 同理，我可以在北京开多个，EPHEMERAL_SEQUENTIAL  session 过期自动删除，也会加数字的后缀
        zk.create(ROOT+"/北京分公司-1", "北京分公司-1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        // 我们也可以 来看看 一共监视了多少个
        List<String> ktvs = zk.getChildren(ROOT, true);
        System.out.println("-----"+Arrays.toString(ktvs.toArray()));
        Thread.currentThread().sleep(30000);

        for(String node : ktvs){
            // 删除节点
            System.out.println("删除节点--"+ROOT+"/"+node);
            zk.delete(ROOT + "/" + node, -1);
        }
        // 根目录得最后删除的
        zk.delete(ROOT, -1);
        zk.close();

    }


}
