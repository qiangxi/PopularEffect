package com.qiangxi.populareffect.bean;

/**
 * 作者 qiang_xi on 2016/10/31 12:34.
 */

public class MainItemInfo {
    private String itemDesc;
    private Class<?> mClass;//目标activity.class

    public MainItemInfo() {
    }

    public MainItemInfo(String itemDesc, Class<?> aClass) {
        this.itemDesc = itemDesc;
        mClass = aClass;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Class<?> getActicityClass() {
        return mClass;
    }

    public void setActicityClass(Class<?> aClass) {
        mClass = aClass;
    }
}
