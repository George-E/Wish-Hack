package com.contextlogic.wish;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Haris on 2017-08-03.
 */
public class ModelFieldInfoRow extends JPanel {

    private static int NUM_COLUMNS = 5;

    private JTextField mFieldType;
    private JTextField mFieldName;
    private JTextField mJsonFieldName;

    public String getFieldType() {
        return ViewUtil.extractTextFieldValue(mFieldType);
    }

    public String getFieldName() {
        return ViewUtil.extractTextFieldValue(mFieldName);
    }

    public String getJsonFieldName() {
        return ViewUtil.extractTextFieldValue(mJsonFieldName);
    }

    public boolean isValidField() {
        return getFieldType() != null && getFieldName() != null && getJsonFieldName() != null;
    }

    public ModelFieldInfoRow() {
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;

        for (int column = 0; column < NUM_COLUMNS; ++column) {
            constraints.gridx = column;
            switch (column) {
                case 0:
                    mFieldType = new JTextField(10);
                    this.add(mFieldType, constraints);
                    break;
                case 1:
                    mFieldName = new JTextField(10);
                    this.add(mFieldName, constraints);
                    break;
                case 2:
                    mJsonFieldName = new JTextField(10);
                    this.add(mJsonFieldName, constraints);
                    break;
            }
        }
    }
}
