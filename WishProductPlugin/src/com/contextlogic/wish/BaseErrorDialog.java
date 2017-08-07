package com.contextlogic.wish;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class BaseErrorDialog extends DialogWrapper {

    private String mTitle;
    private String mMessage;

    public BaseErrorDialog(Component parent, String title, String message) {
        super(parent, false);
        setTitle("Oops");
        mTitle = title;
        mMessage = message;
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(300, 100));

        JLabel titleLabel = new JLabel(mTitle, SwingConstants.CENTER);
        Font titleFont = titleLabel.getFont();
        titleLabel.setFont(titleFont.deriveFont(titleFont.getStyle() | Font.BOLD));
        JLabel messageLabel = new JLabel(mMessage, SwingConstants.CENTER);
        Font msgFont = messageLabel.getFont();
        messageLabel.setFont(msgFont.deriveFont(msgFont.getStyle() | ~Font.BOLD));

        panel.add(BorderLayout.NORTH, titleLabel);
        panel.add(BorderLayout.CENTER, messageLabel);

        return panel;
    }

    @NotNull
    @Override
    protected Action[] createActions() {
        return new Action[]{myOKAction};
    }
}