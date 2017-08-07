package com.contextlogic.wish.util.ParcelableTypes;

import com.intellij.psi.PsiField;

/**
 * Created by Haris on 2017-08-05.
 */
public abstract class ParcelableElementType {

    protected String mType;

    public ParcelableElementType(String type) {
        mType = type;
    }

    public abstract String getReadValue(String source);
    public abstract String getWriteValue(String dest, PsiField field, int flag);
}
