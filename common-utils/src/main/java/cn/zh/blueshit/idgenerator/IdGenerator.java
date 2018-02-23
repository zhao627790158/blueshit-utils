package cn.zh.blueshit.idgenerator;

/**
 * Created by zhaoheng on 17/4/19.
 */
public interface IdGenerator {

    /**
     * id生成
     * @return
     */
    Number generateId() throws InterruptedException;
}
