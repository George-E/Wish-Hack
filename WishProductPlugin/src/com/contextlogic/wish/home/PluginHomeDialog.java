package com.contextlogic.wish.home;

import com.contextlogic.wish.tool_handlers.BaseToolHandler;
import com.contextlogic.wish.PluginToolsDesc;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PluginHomeDialog extends DialogWrapper {


    public PluginHomeDialog(String title, String message) {
        super(true);
        setTitle("Wish Product Plugin");
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {

        PluginToolsDesc.Tool tools[] = PluginToolsDesc.Tool.values();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(tools.length+1,0));
        panel.setPreferredSize(new Dimension(400, 300));

        JLabel label = new JLabel("Tools:");
        panel.add(label);

        for (int i =0; i< tools.length; i++) {
            JButton btn = new JButton(PluginToolsDesc.getName(tools[i]));
            btn.setPreferredSize(panel.getSize());
            final int index = i;
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    DialogWrapper dialog = PluginToolsDesc.createDialog(tools[index], panel);
                    BaseToolHandler handler = PluginToolsDesc.createHandler(tools[index]);
                    handler.handleDialog(dialog);
                }
            });
            panel.add(btn);
        }

        return panel;
    }

    @NotNull
    @Override
    protected Action[] createActions() {
        return new Action[]{};
    }
}
