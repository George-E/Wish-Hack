package com.contextlogic.wish.tool_dialogs;

import com.contextlogic.wish.util.ModelUtil;
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

    private ArrayList<String> mExistingModels;

    private LabeledComponent<JTextField> mModelNameTextField;
    private ModelFieldTable mModelFieldTable;

    public GenerateModelDialog(Component parent, AnActionEvent event) {
        super(parent, event);
        setTitle("Generate Model");
        mExistingModels = ModelUtil.getExistingModelNames(event.getProject());
        init();
    }

    public String getFileName() {
        return mModelNameTextField.getComponent().getText();
    }

    public ArrayList<ModelFieldTable.ModelField> getFieldsList() {
        return mModelFieldTable.getFieldsList();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setPreferredSize(new Dimension(400, 300));
        mModelNameTextField = LabeledComponent.create(new JTextField(), "Model Name");
        mModelFieldTable = new ModelFieldTable(createStartingRows());
        mainPanel.add(mModelNameTextField);
        addSpaceToPanel(mainPanel, 10);
        mainPanel.add(mModelFieldTable);
        return mainPanel;
    }

    private ArrayList<ModelFieldTable.ModelField> createStartingRows() {
        ArrayList<ModelFieldTable.ModelField> startingRows = new ArrayList<>();
        for (int i =0; i< DEFAULT_ROWS; i++) {
            startingRows.add(new ModelFieldTable.ModelField());
        }
        return startingRows;
    }

    private void addSpaceToPanel(JPanel panel, int pixels) {
        panel.add(Box.createRigidArea(new Dimension(pixels,pixels)));
    }
}