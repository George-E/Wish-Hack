package com.contextlogic.wish.haris.view;

import com.contextlogic.wish.haris.ModelUtil;
import com.intellij.openapi.project.Project;
import com.intellij.ui.TextFieldWithAutoCompletion;
import com.intellij.ui.TextFieldWithAutoCompletionListProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Haris on 2017-08-03.
 */
public class FieldInfoView extends JPanel {

    private static int NUM_COLUMNS = 3;

    private TextFieldWithAutoCompletion mFieldType;
    private JTextField mFieldName;
    private JTextField mJsonFieldName;

    public String getFieldType() {
        return ModelUtil.extractTextFieldValue(mFieldType);
    }

    public String getFieldName() {
        return ModelUtil.extractTextFieldValue(mFieldName);
    }

    public String getJsonFieldName() {
        return ModelUtil.extractTextFieldValue(mJsonFieldName);
    }

    public boolean isValidField() {
        return getFieldType() != null && getFieldName() != null && getJsonFieldName() != null;
    }

    public FieldInfoView(Project project) {
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;

        ArrayList<String> existingModels = ModelUtil.getExistingModelNames(project);

        for (int column = 0; column < NUM_COLUMNS; ++column) {
            constraints.gridx = column;
            switch (column) {
                case 0:
                    mFieldType = new TextFieldWithAutoCompletion(project, new TextFieldWithAutoCompletionListProvider(existingModels) {
                        @NotNull
                        @Override
                        protected String getLookupString(@NotNull Object o) {
                            return o.toString();
                        }
                    }, true, null);
                    mFieldType.setSize(new JTextField(10).getSize());
                    mFieldType.setPreferredSize(new Dimension(120, 24));
                    this.add(mFieldType, constraints);
                    break;
                case 1:
                    mFieldName = new JTextField();
                    this.add(mFieldName, constraints);
                    mFieldName.setColumns(10);
                    break;
                case 2:
                    mJsonFieldName = new JTextField();
                    this.add(mJsonFieldName, constraints);
                    mJsonFieldName.setColumns(10);
                    break;
            }
        }
    }
}