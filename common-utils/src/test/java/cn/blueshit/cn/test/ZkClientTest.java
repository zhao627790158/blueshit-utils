package cn.blueshit.cn.test;

import com.github.zkclient.IZkChildListener;
import com.github.zkclient.IZkClient;
import com.github.zkclient.IZkDataListener;
import com.github.zkclient.ZkClient;
import org.junit.Before;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zhaoheng on 2016/7/4.
 */
public class ZkClientTest {


    public static final String ROOT = "/root-11";

    private IZkClient zkClient;


    @Before
    public void testBefore() {
        zkClient = new ZkClient("127.0.0.1:2181");
        zkClient.subscribeDataChanges(ROOT, new IZkDataListener() {
            public void handleDataDeleted(String path) throws Exception {
                System.out.println("节点被删除了~!");
            }

            public void handleDataChange(String path, byte[] data) throws Exception {
                System.out.println("节点修改data--" + data + "--string\n" + new String(data, "utf-8"));
            }
        });

        /**
         * 添加子节点监控
         */
        zkClient.subscribeChildChanges(ROOT, new IZkChildListener() {
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println("添加子节点监控-" + s + "-list-" + list.get(0));

            }
        });

    }


    @org.junit.Test
    public void testZkClient() throws UnsupportedEncodingException {
        byte[] bytes = zkClient.readData("/zk");
        System.out.println(new String(bytes, "utf-8"));
        System.out.println(zkClient.exists("/zk"));
    }


    @org.junit.Test
    public void testCreate() throws UnsupportedEncodingException, InterruptedException {
        if(!zkClient.exists(ROOT)){
            zkClient.createPersistent(ROOT, "总部".getBytes("utf-8"));
        }
        zkClient.createEphemeralSequential(ROOT + "/bj分公司", "北京".getBytes("utf-8"));
        Thread.sleep(30000);
        zkClient.createEphemeralSequential(ROOT + "/hz分公司", "杭州".getBytes("utf-8"));
        Thread.sleep(30000);
        List<String> children = zkClient.getChildren(ROOT);
        for (String test : children) {
            System.out.println(test + "---" + zkClient.countChildren(ROOT));
            zkClient.writeData(ROOT+"/"+test, "test write".getBytes("utf-8"));
        }

        Thread.sleep(30000);


    }


}
