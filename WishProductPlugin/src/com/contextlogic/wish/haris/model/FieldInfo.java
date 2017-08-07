package com.contextlogic.wish.haris.model;

/**
 * Created by Haris on 2017-08-06.
 */
public class FieldInfo {
    private String mType;
    private String mName;
    private String mJsonFieldName;

    public FieldInfo(String type, String name, String jsonFieldName) {
        mType = type;
        mName = name;
        mJsonFieldName = jsonFieldName;
    }

    public FieldInfo(String type, String name) {
        this(type, name, null);
    }

    public String getType() {
        return mType;
    }

    public String getName() {
        return mName;
    }

    public String getJsonFieldName() {
        return mJsonFieldName;
    }
}
