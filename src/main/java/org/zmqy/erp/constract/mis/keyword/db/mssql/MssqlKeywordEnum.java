package org.zmqy.erp.constract.mis.keyword.db.mssql;

/**
 * mssql 关键字
 */
public enum MssqlKeywordEnum {

    // ddl
    /**
     * create
     */
    CREATE("create"),

    /**
     * alter
     */
    ALTER("alter"),

    /**
     * drop
     */
    DROP("drop"),

    /**
     * truncate
     */
    TRUNCATE("truncate"),


    // ddl

    /**
     * insert
     */
    INSERT("insert"),

    /**
     * update
     */
    UPDATE("update"),

    /**
     * delete
     */
    DELETE("delete"),


    // 其他
    /**
     * go
     */
    GO("go"),

    /**
     * exec
     */
    EXEC("exec"),

    /**
     * execute
     */
    EXECUTE("execute");

    private String keyword;

    private MssqlKeywordEnum(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return this.keyword;
    }
}
