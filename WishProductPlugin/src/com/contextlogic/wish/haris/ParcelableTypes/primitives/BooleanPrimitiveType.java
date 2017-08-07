package com.contextlogic.wish.haris.ParcelableTypes.primitives;

import com.intellij.psi.PsiField;

/**
 * Created by Haris on 2017-08-06.
 */
public class BooleanPrimitiveType extends PrimitiveType {

    public BooleanPrimitiveType(String type) {
        super(type);
    }

    @Override
    public String getReadValue(String source) {
        return String.format("%s.readByte() != 0x00", source);
    }

    @Override
    public String getWriteValue(String dest, PsiField field, int flag) {
        return String.format("%s.writeByte((byte) (%s ? 0x01 : 0x00))", dest, field.getName());
    }
}