package com.contextlogic.wish.home;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;

public class OpenPluginAction extends AnAction {

    public OpenPluginAction() {
        super(IconLoader.getIcon("/com/contextlogic/wish/icons/wish.png"));
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        PluginHomeDialog dialog = new PluginHomeDialog(event);
        dialog.show();
    }
}
