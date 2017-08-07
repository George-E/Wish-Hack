package com.contextlogic.wish.tool_dialogs;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.LabeledComponent;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Haris on 2017-08-03.
 */
public class GenerateModelDialog extends BaseToolDialog {

    private static int DEFAULT_ROWS = 2;

    private JPanel mMainPanel = new JPanel();

    private LabeledComponent<JTextField> mModelNameTextField;
    private ModelFieldTable mModelFieldTable;

    public GenerateModelDialog(Component parent, AnActionEvent event) {
        super(parent, event);
        setTitle("Generate Model");
        mMainPanel = new JPanel();
        init();
    }

    public String getFileName() {
        return mModelNameTextField.getComponent().getText();
    }

    public ArrayList<ModelFieldTable.ModelField> getFieldsList() {
        return mModelFieldTable.getDataRows();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        mMainPanel.setLayout(new BoxLayout(mMainPanel, BoxLayout.Y_AXIS));
        mMainPanel.setPreferredSize(new Dimension(400, 300));
        mModelNameTextField = createLabeledTextField("Model Name", 30);

        mModelFieldTable = new ModelFieldTable();
        mMainPanel.add(mModelNameTextField);
        mMainPanel.add(mModelFieldTable);
        return mMainPanel;
    }

    private LabeledComponent<JTextField> createLabeledTextField(String label, int columns) {
        JTextField textField = new JTextField(columns);
        LabeledComponent<JTextField> labeledTextField = LabeledComponent.create(textField, label);
        return labeledTextField;
    }
}