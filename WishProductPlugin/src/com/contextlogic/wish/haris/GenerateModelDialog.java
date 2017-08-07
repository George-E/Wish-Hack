package com.contextlogic.wish.haris;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.LabeledComponent;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Haris on 2017-08-03.
 */
public class GenerateModelDialog extends DialogWrapper {

    private static int DEFAULT_ROWS = 2;

    private JPanel mMainPanel = new JPanel();

    private LabeledComponent<JTextField> mModelNameTextField;
    private FieldTable mFieldTable;

    public GenerateModelDialog(Project project) {
        super(project);
        setTitle("Generate Model");
        mMainPanel = new JPanel();
        init();
    }

    public String getFileName() {
        return mModelNameTextField.getComponent().getText();
    }

    public ArrayList<String[]> getFieldsList() {
        return mFieldTable.getDataRows();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        mMainPanel.setLayout(new BoxLayout(mMainPanel, BoxLayout.Y_AXIS));
        mMainPanel.setPreferredSize(new Dimension(400, 300));
        mModelNameTextField = createLabeledTextField("Model Name", 30);

        String[] columnNames = {"Field Type", "Field Name", "JSON Key"};
        mFieldTable = new FieldTable(columnNames);
        mMainPanel.add(mModelNameTextField);
        mMainPanel.add(mFieldTable);
        return mMainPanel;
    }

    private LabeledComponent<JTextField> createLabeledTextField(String label, int columns) {
        JTextField textField = new JTextField(columns);
        LabeledComponent<JTextField> labeledTextField = LabeledComponent.create(textField, label);
        return labeledTextField;
    }
}