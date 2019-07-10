package org.anson.miniProject.framework.log.service;

public enum PkClassFrom {

    /**
     * pc 端
     */
    INPUT("input"),

    RETURN("return");

    private String key;

    private PkClassFrom(String key){
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }
}
