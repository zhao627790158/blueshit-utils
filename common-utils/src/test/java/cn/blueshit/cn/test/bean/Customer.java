package cn.blueshit.cn.test.bean;

import java.util.Date;

/**
 * Created by zhaoheng on 2016/6/2.
 */
public class Customer {
    private int customerId;
    private String customerName;

    private Date time;


    public Customer() {
        System.out.println("无参数");
    }

   /* public Customer(int customerId) {
        System.out.println("有参数111");
        this.customerId = customerId;
    }*/

    public Customer(int customerId, String customerName, Date time) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.time = time;
    }

   /* public Customer(int customerId, String customerName) {
        System.out.println("有参数22222");
        this.customerId = customerId;
        this.customerName = customerName;
    }*/

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", time=" + time +
                '}';
    }
}
