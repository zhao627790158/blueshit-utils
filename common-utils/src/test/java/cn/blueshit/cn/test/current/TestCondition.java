package cn.blueshit.cn.test.current;

import cn.zh.blueshit.excel.ExcelTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhaoheng on 16/10/5.
 * 测试打印 abcabcabcabc
 */
public class TestCondition {

    private static final ReentrantLock LOCK = new ReentrantLock();
    //3个condition
    private static final Condition con_a = LOCK.newCondition();
    private static final Condition con_b = LOCK.newCondition();
    private static final Condition con_c = LOCK.newCondition();

    private static volatile boolean canPrintA = true;
    private static volatile boolean canPrintB = false;
    private static volatile boolean canPrintC = false;


    public static void main(String[] args) throws InterruptedException {
        Map<String, Object> hashMap = new HashMap<String, Object>();
        new Thread(new PrintBTask()).start();
        new Thread(new PrintATask()).start();

        new Thread(new PrintCTask()).start();
        for (int i = 0; i < 1000; i++) {
            hashMap.put(i + "", i);
        }
        Thread.sleep(1000 * 10);
    }

    /**
     * b获取锁
     * b释放锁-去等待
     * a获取锁
     * a
     * a获取锁
     * a释放锁-去等待
     * c获取锁
     * c释放锁-去等待
     * b  //打印完b后 b又获取到了锁 即,又lock()成功了
     * b获取锁
     * b释放锁-去等待
     * c //打印完c后 c又获取到了锁 即,又lock()成功了
     * c获取锁
     * c释放锁-去等待
     * a
     * a获取锁
     * a释放锁-去等待
     * b
     * b获取锁
     * b释放锁-去等待
     * c
     */

    public static class PrintATask implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    LOCK.lock();//观察可以发现 a被打印后,a可以再次获取锁成功 体现出了偏向锁的概念
                    System.out.println("a获取锁");
                    while (!canPrintA) {
                        System.out.println("a释放锁-去等待");
                        con_a.await();
                    }
                    System.out.println('a');
                    TimeUnit.SECONDS.sleep(1);
                    canPrintA = false;
                    canPrintB = true;
                    canPrintC = false;
                    con_b.signalAll();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    LOCK.unlock();
                }
            }
        }
    }

    public static class PrintBTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    LOCK.lock();
                    System.out.println("b获取锁");
                    while (!canPrintB) {
                        System.out.println("b释放锁-去等待");
                        con_b.await();
                    }
                    System.out.println('b');
                    TimeUnit.SECONDS.sleep(1);
                    canPrintA = false;
                    canPrintB = false;
                    canPrintC = true;
                    con_c.signalAll();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    LOCK.unlock();
                }


            }
        }
    }

    public static class PrintCTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    LOCK.lock();
                    System.out.println("c获取锁");
                    while (!canPrintC) {
                        System.out.println("c释放锁-去等待");
                        con_c.await();
                    }
                    System.out.println('c');
                    TimeUnit.SECONDS.sleep(1);
                    canPrintA = true;
                    canPrintB = false;
                    canPrintC = false;
                    con_a.signalAll();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    LOCK.unlock();
                }


            }
        }
    }


}
