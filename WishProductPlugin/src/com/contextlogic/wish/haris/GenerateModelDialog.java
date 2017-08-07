package com.contextlogic.wish.haris;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.LabeledComponent;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Haris on 2017-08-03.
 */
public class GenerateModelDialog extends DialogWrapper {

    private static int DEFAULT_ROWS = 2;

    private Project mProject;
    private JPanel mMainPanel = new JPanel();

    private LabeledComponent<JTextField> mModelNameTextField;
    private JPanel mMembersPanel;

    private int mNumMembers;
    private ArrayList<ModelFieldInfoRow> mFieldsList;

    public GenerateModelDialog(Project project) {
        super(project);
        setTitle("Generate Model");
        mProject = project;
        mMainPanel = new JPanel();
        mMembersPanel = new JPanel();
        mMembersPanel.setLayout(new BoxLayout(mMembersPanel, BoxLayout.Y_AXIS));
        mFieldsList = new ArrayList<>();
        mNumMembers = 0;
        init();
    }

    public String getFileName() {
        return mModelNameTextField.getComponent().getText();
    }

    public ArrayList<ModelFieldInfoRow> getFieldsList() {
        return mFieldsList;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        mMainPanel.setLayout(new BoxLayout(mMainPanel, BoxLayout.Y_AXIS));
        setupModelNameTextField();
        setupDefaultMembersTextFields();
        mMainPanel.add(mMembersPanel);
        return mMainPanel;
    }

    private void setupModelNameTextField() {
        mModelNameTextField = createLabeledTextField("Model Name", 30);
        mMainPanel.add(mModelNameTextField);
    }

    private void setupDefaultMembersTextFields() {
        for (int i = 0; i < DEFAULT_ROWS; ++i) {
            addRow();
        }
    }

    private void addRow() {
        ModelFieldInfoRow row = new ModelFieldInfoRow(mProject);
        mMembersPanel.add(row);
        mFieldsList.add(row);
    }

    private LabeledComponent<JTextField> createLabeledTextField(String label, int columns) {
        JTextField textField = new JTextField(columns);
        LabeledComponent<JTextField> labeledTextField = LabeledComponent.create(textField, label);
        return labeledTextField;
    }
}