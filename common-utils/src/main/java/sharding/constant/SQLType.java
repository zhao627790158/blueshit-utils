package sharding.constant;

public enum SQLType {

    /**
     * Data Query Language.
     * <p>
     * <p>Such as {@code SELECT}.</p>
     */
    DQL,

    /**
     * Data Manipulation Language.
     * mə,nɪpjʊ'leʃən :处理,操作
     * <p>
     * <p>Such as {@code INSERT}, {@code UPDATE}, {@code DELETE}.</p>
     */
    DML,

    /**
     * Data Definition Language.
     * <p>
     * <p>Such as {@code CREATE}, {@code ALTER}, {@code DROP}, {@code TRUNCATE}.</p>
     */
    DDL
}