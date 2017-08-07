package com.contextlogic.wish.tool_dialogs;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogWrapper;

import java.awt.*;

public abstract class BaseToolDialog extends DialogWrapper{

    protected AnActionEvent mEvent;

    public BaseToolDialog(Component parent, AnActionEvent event) {
        super(parent, true);
        mEvent = event;
    }
}
