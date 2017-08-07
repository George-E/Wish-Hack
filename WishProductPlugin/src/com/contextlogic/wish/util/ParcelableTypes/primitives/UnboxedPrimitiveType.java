package com.contextlogic.wish.util.ParcelableTypes.primitives;

import com.contextlogic.wish.util.ModelUtil;
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
        return String.format("%s.read%s()", source, ModelUtil.capitalize(mType));
    }

    @Override
    public String getWriteValue(String dest, PsiField psiField, int flag) {
        return String.format("%s.write%s(%s)", dest, ModelUtil.capitalize(mType), psiField.getName());
    }
}
