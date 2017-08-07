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

    private ArrayList<ModelFieldTable.ModelField> mStartingRows;
    private String mStartingModelName;

    private LabeledComponent<JTextField> mModelNameTextField;
    private ModelFieldTable mModelFieldTable;

    public GenerateModelDialog(AnActionEvent event, ArrayList<ModelFieldTable.ModelField> startingRows, String startingModelName) {
        super(event);
        setTitle("Generate Model");
        mStartingRows = startingRows;
        mStartingModelName = startingModelName;
        init();
        //geisa clean this
    }

    public GenerateModelDialog(Component parent, AnActionEvent event, ArrayList<ModelFieldTable.ModelField> startingRows, String startingModelName) {
        super(parent, event);
        setTitle("Generate Model");
        mStartingRows = startingRows;
        mStartingModelName = startingModelName;
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
        mModelNameTextField = LabeledComponent.create(new JTextField(mStartingModelName), "Model Name");
        mModelFieldTable = new ModelFieldTable(mStartingRows, mEvent.getProject());
        mainPanel.add(mModelNameTextField);
        addSpaceToPanel(mainPanel, 10);
        mainPanel.add(mModelFieldTable);
        return mainPanel;
    }

    private void addSpaceToPanel(JPanel panel, int pixels) {
        panel.add(Box.createRigidArea(new Dimension(pixels,pixels)));
    }
}