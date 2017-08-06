package com.contextlogic.wish.haris.ParcelableTypes.primitives;

import com.contextlogic.wish.haris.Util;
import com.intellij.psi.PsiField;

/**
 * Created by Haris on 2017-08-05.
 */
public class UnboxedPrimitiveType extends PrimitiveType {

    public UnboxedPrimitiveType(String type) {
        super(type);
    }

    @Override
    public String getReadValue(String source) {
        return String.format("%s.read%s()", source, Util.capitalize(mType));
    }

    @Override
    public String getWriteValue(String source, PsiField psiField, int flag) {
        return String.format("%s.write%s(%s)", source, mType, psiField.getName());
    }
}