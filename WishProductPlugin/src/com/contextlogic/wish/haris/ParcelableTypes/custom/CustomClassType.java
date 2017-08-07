package com.contextlogic.wish.haris.ParcelableTypes.custom;

import com.contextlogic.wish.haris.ParcelableTypes.primitives.PrimitiveType;
import com.intellij.psi.PsiField;

/**
 * Created by Haris on 2017-08-06.
 */
public class CustomClassType extends PrimitiveType {

    public CustomClassType(String type) {
        super(type);
    }

    @Override
    public String getReadValue(String source) {
        return String.format("%s.readParcelable(%s.class.getClassLoader())", source, mType);
    }

    @Override
    public String getWriteValue(String dest, PsiField field, int flag) {
        return String.format("%s.writeParcelable(%s, %d)", dest, field.getName(), flag);
    }
}