package com.contextlogic.wish.haris;

import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Haris on 2017-08-03.
 */
public class ModelFieldInfoRow extends JPanel {

    private static int NUM_COLUMNS = 3;

    private JTextField mFieldType;
    private JTextField mFieldName;
    private JTextField mJsonFieldName;

    public String getFieldType() {
        return Util.extractTextFieldValue(mFieldType);
    }

    public String getFieldName() {
        return Util.extractTextFieldValue(mFieldName);
    }

    public String getJsonFieldName() {
        return Util.extractTextFieldValue(mJsonFieldName);
    }

    public boolean isValidField() {
        return getFieldType() != null && getFieldName() != null && getJsonFieldName() != null;
    }

    public ModelFieldInfoRow(Project project) {
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