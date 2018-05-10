import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaoheng on 16/10/5.
 */
public class TestConsistencyHash {

    private TreeMap<Long, Object> nodes = null;

    //真实服务器节点信息
    private List<Object> shards = new ArrayList();
    //设置虚拟节点数目
    private int VIRTUAL_NUM = 100;

    public void init() {
        shards.add("192.168.0.0-服务器0");
        shards.add("192.168.0.1-服务器1");
        shards.add("192.168.0.2-服务器2");
        shards.add("192.168.0.3-服务器3");
        shards.add("192.168.0.4-服务器4");

        Stopwatch stopwatch = Stopwatch.createStarted();
        nodes = new TreeMap<Long, Object>();
        for (int i = 0; i < shards.size(); i++) {
            Object shardInfo = shards.get(i);
            for (int j = 0; j < VIRTUAL_NUM; j++) {
                nodes.put(hash("SHARD-" + i + "-NODE-" + j), shardInfo);
            }
        }
        System.out.println("hash环初始化时间" + stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    /**
     * MurMurHash算法，是非加密HASH算法，性能很高，
     * 比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）
     * 等HASH算法要快很多，而且据说这个算法的碰撞率很低.
     * http://murmurhash.googlepages.com/
     */
    private Long hash(String key) {
        return Math.abs(MurmurHash.hash64(key));

        /*ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 0x1234ABCD;

        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);

        long m = 0xc6a4a7935bd1e995L;
        int r = 47;

        long h = seed ^ (buf.remaining() * m);

        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(
                    ByteOrder.LITTLE_ENDIAN);
            // for big-endian version, do this first:
            // finish.position(8-buf.remaining());
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        buf.order(byteOrder);
        return Math.abs(h);*/
    }

    /**
     * 根据2^32把节点分布到圆环上面。
     *
     * @param digest
     * @param nTime
     * @return
     *//*
    public long hash(byte[] digest, int nTime) {
        long rv = ((long) (digest[3 + nTime * 4] & 0xFF) << 24)
                | ((long) (digest[2 + nTime * 4] & 0xFF) << 16)
                | ((long) (digest[1 + nTime * 4] & 0xFF) << 8)
                | (digest[0 + nTime * 4] & 0xFF);

        return rv & 0xffffffffL; *//* Truncate to 32-bits *//*
    }

    *//**
     * Get the md5 of the given key.
     * 计算MD5值
     *//*
    public byte[] computeMd5(String k) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
        md5.reset();
        byte[] keyBytes = null;
        try {
            keyBytes = k.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unknown string :" + k, e);
        }

        md5.update(keyBytes);
        return md5.digest();
    }
*/

    /**
     * 打印圆环节点数据
     */
    public void printMap() {
        System.out.println(nodes);
        System.out.println(nodes.size());
    }


    public Object getShardInfo(long hash) {
        Long key = hash;
        //tailMap(K fromKey) 方法用于返回此映射，其键大于或等于fromKey的部分视图
        SortedMap<Long, Object> tailMap = nodes.tailMap(key);
        if (tailMap.isEmpty()) {
            key = nodes.firstKey();
        } else {
            key = tailMap.firstKey();
        }
        return nodes.get(key);
    }

    /*
    *
    * {192.168.0.1-服务器1=170645, 192.168.0.4-服务器4=224088, 192.168.0.0-服务器0=209504, 192.168.0.3-服务器3=194750, 192.168.0.2-服务器2=201013}
    *
    * {192.168.0.1-服务器1=205355, 192.168.0.4-服务器4=227321, 192.168.0.0-服务器0=181079, 192.168.0.3-服务器3=203204, 192.168.0.2-服务器2=183041}
    * {192.168.0.1-服务器1=186570, 192.168.0.4-服务器4=214629, 192.168.0.0-服务器0=204651, 192.168.0.3-服务器3=197160, 192.168.0.2-服务器2=196990}
    * {192.168.0.1-服务器1=186570, 192.168.0.4-服务器4=214629, 192.168.0.0-服务器0=204651, 192.168.0.3-服务器3=197160, 192.168.0.2-服务器2=196990}
    *
    * {192.168.0.1-服务器1=199503, 192.168.0.4-服务器4=187101, 192.168.0.0-服务器0=219786, 192.168.0.3-服务器3=186976, 192.168.0.2-服务器2=206634}
    *
    * {192.168.0.1-服务器1=199503, 192.168.0.4-服务器4=187101, 192.168.0.0-服务器0=219786, 192.168.0.3-服务器3=186976, 192.168.0.2-服务器2=206634}





    * */

    public static void main(String[] args) {
        Random ran = new Random();
        TestConsistencyHash hash = new TestConsistencyHash();
        hash.init();
        hash.printMap();
        System.out.println(hash.hash("1"));
        System.out.println(hash.getShardInfo(hash.hash(Long.MAX_VALUE + "")));
        System.out.println("----hash 测试");
        int orderId = 675318950;
        Map<String, Integer> countMap = Maps.newHashMap();

        for (int i = 0; i < 1000000; i++) {
            Object shardInfo = hash.getShardInfo(hash.hash(String.valueOf(orderId)));
            //System.out.println(shardInfo);
            if (countMap.containsKey(shardInfo.toString())) {
                Integer integer = countMap.get(shardInfo.toString());
                countMap.put(shardInfo.toString(), integer + 1);
            }else{
                countMap.put(shardInfo.toString(), 1);
            }
            orderId++;
        }
        System.out.println(countMap);



    }


}
