package cn.blueshit.cn.test.bytebuddy;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhaoheng on 19/1/3.
 */
@Data
public class Order {

    private long orderId;

    private Date createTime;

    private User user = new User();

}
