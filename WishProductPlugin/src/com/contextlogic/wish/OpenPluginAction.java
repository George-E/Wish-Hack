package com.contextlogic.wish;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;

public class OpenPluginAction extends AnAction {

    public OpenPluginAction() {
        super(IconLoader.getIcon("/com/contextlogic/wish/icons/wish.png"));
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        PluginHomeDialog dia = new PluginHomeDialog("hi", "hi");
        dia.show();
    }
}
