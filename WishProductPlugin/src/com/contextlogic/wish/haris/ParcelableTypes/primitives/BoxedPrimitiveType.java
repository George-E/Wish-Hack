package com.contextlogic.wish.haris.ParcelableTypes.primitives;

import com.intellij.psi.PsiField;

/**
 * Created by Haris on 2017-08-05.
 */
public class BoxedPrimitiveType extends PrimitiveType {

    public BoxedPrimitiveType(String type) {
        super(type);
    }

    @Override
    public String getReadValue(String source) {
        return String.format("(%s) %s.readValue(%s.class.getClassLoader())", mType, source, mType);
    }

    @Override
    public String getWriteValue(String source, PsiField field, int flag) {
        return String.format("%s.writeValue(%s)", source, field.getName());
    }
}
