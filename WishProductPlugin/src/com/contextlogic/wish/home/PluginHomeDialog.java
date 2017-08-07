package com.contextlogic.wish.home;

import com.contextlogic.wish.PluginToolsDesc;
import com.contextlogic.wish.tool_handlers.BaseToolHandler;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PluginHomeDialog extends DialogWrapper {

    private AnActionEvent mEvent;

    public PluginHomeDialog(AnActionEvent event) {
        super(true);
        mEvent = event;
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
            final int index = i;
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    BaseToolHandler handler = PluginToolsDesc.createHandler(tools[index], panel, mEvent);
                    handler.showDialog();
                }
            });
            panel.add(btn);
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            javax.swing.SwingUtilities.updateComponentTreeUI(panel);
        } catch(Exception e) {
        }

        return panel;
    }

    @NotNull
    @Override
    protected Action[] createActions() {
        return new Action[]{};
    }
}
