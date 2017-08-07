package com.contextlogic.wish.util.ParcelableTypes;

import com.contextlogic.wish.util.ParcelableTypes.primitives.PrimitiveTypeParser;
import com.intellij.psi.PsiType;

/**
 * Created by Haris on 2017-08-05.
 */
public abstract class ElementTypeParser {

    private static PrimitiveTypeParser[] parsers = {
        new PrimitiveTypeParser()
    };

    public abstract ParcelableElementType getTypeParser(PsiType type);
}
