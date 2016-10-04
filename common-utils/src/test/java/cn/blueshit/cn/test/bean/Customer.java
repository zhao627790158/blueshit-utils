package cn.blueshit.cn.test.bean;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import java.util.Date;

/**
 * Created by zhaoheng on 2016/6/2.
 */
public class Customer {

    @Max(value = 10, message = "不能超过10")
    private int customerId;

    @NotBlank(message = "用户名不能为空")
    private String customerName;

    private Date time;

    static {
        System.out.println("类初始化");
    }


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

    public static void main(String[] args) {

        System.out.println(Man.test);
    }
}
