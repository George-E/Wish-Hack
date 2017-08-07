package com.contextlogic.wish.actions;

import com.contextlogic.wish.home.PluginHomeDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;

public class OpenPluginAction extends AnAction {

    public OpenPluginAction() {
        super(IconLoader.getIcon("/com/contextlogic/wish/icons/wish.png"));
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        if (event.getProject() == null) {
            return;
        }
        PluginHomeDialog dialog = new PluginHomeDialog(event);
        dialog.show();
    }
}
