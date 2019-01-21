package sharding.keygen;

public interface KeyGenerator {

    /**
     * 生成主键
     *
     * @return
     */
    Number generateKey();
}
