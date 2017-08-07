package com.contextlogic.wish.haris;

import com.intellij.ide.util.DefaultPsiElementCellRenderer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.psi.PsiField;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.AnActionButtonRunnable;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBList;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by Haris on 2017-08-03.
 */
public class GenerateModelDialog extends DialogWrapper {

    private static int DEFAULT_ROWS = 2;

    private JPanel mMainPanel = new JPanel();

    private LabeledComponent<JTextField> mModelNameTextField;
    private JPanel mMembersPanel;

    private int mNumMembers;
    private CollectionListModel<ModelFieldInfoRow> mFieldsList;

    private JBList test;

    public GenerateModelDialog(Project project) {
        super(project);
        setTitle("Generate Model");
        mMainPanel = new JPanel();
        mMembersPanel = new JPanel();
        mMembersPanel.setLayout(new BoxLayout(mMembersPanel, BoxLayout.Y_AXIS));
        mFieldsList = new CollectionListModel<>();
        mNumMembers = 0;
        init();
    }

    public String getFileName() {
        return mModelNameTextField.getComponent().getText();
    }

    public CollectionListModel<ModelFieldInfoRow> getFieldsList() {
        return mFieldsList;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        mMainPanel.setLayout(new BoxLayout(mMainPanel, BoxLayout.Y_AXIS));
        setupModelNameTextField();
        setupDefaultMembersTextFields();

        //CollectionListModel<PsiField> myFields = new CollectionListModel<PsiField>(psiClass.getAllFields());
        CollectionListModel<PsiField> myFields = new CollectionListModel<PsiField>();
        JList fieldList = new JList(myFields);
        fieldList.setCellRenderer(new DefaultPsiElementCellRenderer());
        ToolbarDecorator decorator = ToolbarDecorator.createDecorator(fieldList);
        //decorator.disableAddAction();
        JPanel panel = decorator.createPanel();
        //mMainPanel.add(panel);


        test = new JBList(mFieldsList);
        test.setCellRenderer(new CellRenderer());
        //ToolbarDecorator decorator2 = ToolbarDecorator.createDecorator(test);
        //decorator2.setAddAction(getAddActionButtonRunnable());
        //decorator2.setRemoveAction(getRemoveActionButtonRunnable());
        //mMainPanel.add(decorator2.createPanel());
        mMainPanel.add(test);

        //mMainPanel.add(mMembersPanel);
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
        ModelFieldInfoRow row = new ModelFieldInfoRow();
        mMembersPanel.add(row);
        mFieldsList.add(row);
    }

    private LabeledComponent<JTextField> createLabeledTextField(String label, int columns) {
        JTextField textField = new JTextField(columns);
        LabeledComponent<JTextField> labeledTextField = LabeledComponent.create(textField, label);
        return labeledTextField;
    }

    private AnActionButtonRunnable getAddActionButtonRunnable() {
        return new AnActionButtonRunnable() {
            @Override
            public void run(AnActionButton anActionButton) {
                mFieldsList.add(new ModelFieldInfoRow());
                System.out.println(mFieldsList.getSize());
            }
        };
    }

    private AnActionButtonRunnable getRemoveActionButtonRunnable() {
        return new AnActionButtonRunnable() {
            @Override
            public void run(AnActionButton anActionButton) {
                for (Object object : test.getSelectedValues()) {
                    ModelFieldInfoRow selectedValue = (ModelFieldInfoRow) object;
                    mFieldsList.remove(selectedValue);
                }
            }
        };
    }
}