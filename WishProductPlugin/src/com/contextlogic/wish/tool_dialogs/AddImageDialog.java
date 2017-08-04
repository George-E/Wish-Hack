package com.contextlogic.wish.tool_dialogs;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AddImageDialog extends BaseToolDialog {

    private JTextField mSrcFileTextField;
    private JTextField mDestFileTextField;

    public AddImageDialog(Component parent) {
        super(parent);
        setTitle("Select image to be compressed and added");
        myOKAction.putValue(Action.NAME, "Add");
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(400, 300));

        JLabel srcPrompt = new JLabel("Choose Image:");
        JPanel srcFilePanel = new JPanel(new BorderLayout());
        mSrcFileTextField = new JTextField();
        JButton srcFileSelectorButton = new JButton("Find");
        srcFileSelectorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(panel);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    mSrcFileTextField.setText(selectedFile.getPath());
                }
            }
        });
        srcFilePanel.add(BorderLayout.CENTER, mSrcFileTextField);
        srcFilePanel.add(BorderLayout.EAST, srcFileSelectorButton);

        srcPrompt.setAlignmentX( Component.LEFT_ALIGNMENT );
        srcFilePanel.setAlignmentX( Component.LEFT_ALIGNMENT );
        panel.add(srcPrompt);
        panel.add(srcFilePanel);

        JLabel destPrompt = new JLabel("Choose Destination:");
        JPanel destFilePanel = new JPanel(new BorderLayout());
        mDestFileTextField = new JTextField();
        JButton destFileSelectorButton = new JButton("Find");
        destFileSelectorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int returnValue = fileChooser.showOpenDialog(panel);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    mDestFileTextField.setText(selectedFile.getPath());
                }
            }
        });
        destFilePanel.add(BorderLayout.CENTER, mDestFileTextField);
        destFilePanel.add(BorderLayout.EAST, destFileSelectorButton);

        destPrompt.setAlignmentX( Component.LEFT_ALIGNMENT );
        destFilePanel.setAlignmentX( Component.LEFT_ALIGNMENT );
        panel.add(destPrompt);
        panel.add(destFilePanel);

        return panel;
    }

    public String getSource() {
        return mSrcFileTextField.getText();
    }

    public String getDestination() {
        return mDestFileTextField.getText();
    }

}
