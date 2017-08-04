package com.contextlogic.wish;

import javax.swing.*;

/**
 * Created by Haris on 2017-08-03.
 */
public class ViewUtil {
    public static String extractTextFieldValue(JTextField textField) {
        if (textField == null || textField.getText().trim() == "") {
            return null;
        }
        return textField.getText();
    }
}
