package com.contextlogic.wish.haris.ParcelableTypes.primitives;

import com.contextlogic.wish.haris.ParcelableTypes.ParcelableElementType;
import com.intellij.psi.PsiField;

/**
 * Created by Haris on 2017-08-05.
 */
public abstract class PrimitiveType extends ParcelableElementType {

    public PrimitiveType(String type) {
        super(type);
    }

    public abstract String getReadValue(String source);
    public abstract String getWriteValue(String source, PsiField field, int flag);
}
