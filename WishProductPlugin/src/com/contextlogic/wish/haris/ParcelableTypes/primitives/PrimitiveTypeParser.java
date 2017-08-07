package com.contextlogic.wish.haris.ParcelableTypes.primitives;

import com.contextlogic.wish.haris.ParcelableTypes.custom.CustomClassType;
import com.intellij.psi.CommonClassNames;
import com.intellij.psi.PsiField;

import java.util.HashMap;

/**
 * Created by Haris on 2017-08-05.
 */
public class PrimitiveTypeParser {

    private static HashMap<String, PrimitiveType> supportedTypes = new HashMap<>();
    static {
        // TODO: add short type as well

        // unwrapped types
        // java.lang.String is treated as primitive for simplicity
        supportedTypes.put(CommonClassNames.JAVA_LANG_STRING, new UnboxedPrimitiveType("String"));
        supportedTypes.put("int", new UnboxedPrimitiveType("int"));
        supportedTypes.put("float", new UnboxedPrimitiveType("float"));
        supportedTypes.put("double", new UnboxedPrimitiveType("double"));
        supportedTypes.put("byte", new UnboxedPrimitiveType("byte"));
        supportedTypes.put("long", new UnboxedPrimitiveType("long"));
        supportedTypes.put("boolean", new BooleanPrimitiveType("boolean"));

        // wrapped types
        supportedTypes.put(CommonClassNames.JAVA_LANG_INTEGER,new BoxedPrimitiveType(CommonClassNames.JAVA_LANG_INTEGER));
        supportedTypes.put(CommonClassNames.JAVA_LANG_FLOAT, new BoxedPrimitiveType(CommonClassNames.JAVA_LANG_FLOAT));
        supportedTypes.put(CommonClassNames.JAVA_LANG_DOUBLE, new BoxedPrimitiveType(CommonClassNames.JAVA_LANG_DOUBLE));
        supportedTypes.put(CommonClassNames.JAVA_LANG_BYTE, new BoxedPrimitiveType(CommonClassNames.JAVA_LANG_BYTE));
        supportedTypes.put(CommonClassNames.JAVA_LANG_LONG, new BoxedPrimitiveType(CommonClassNames.JAVA_LANG_LONG));
        supportedTypes.put(CommonClassNames.JAVA_LANG_BOOLEAN, new BoxedPrimitiveType(CommonClassNames.JAVA_LANG_BOOLEAN));
    }

    public PrimitiveType getParcelableType(PsiField field) {
        if (supportedTypes.containsKey(field.getType().getCanonicalText())) {
            return supportedTypes.get(field.getType().getCanonicalText());
        } else {
            return new CustomClassType(field.getType().getPresentableText());
        }
    }
}