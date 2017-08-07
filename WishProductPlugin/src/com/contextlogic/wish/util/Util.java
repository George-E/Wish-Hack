package com.contextlogic.wish.util;

import javax.swing.*;

/**
 * Created by Haris on 2017-08-03.
 */
public class Util {

    public static String extractTextFieldValue(JTextField textField) {
        if (textField == null || textField.getText().trim() == "") {
            return null;
        }
        return textField.getText().trim();
    }

    public static String getFormattedName(String s) {
        String[] list = s.split("(?=[A-Z])", 2);
        if (list.length == 2) {
            return list[1];
        } else {
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
    }

    public static String capitalize(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}