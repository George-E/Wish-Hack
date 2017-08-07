package com.contextlogic.wish.tool_handlers;

import com.intellij.openapi.actionSystem.AnActionEvent;

import java.awt.*;

public abstract class BaseToolHandler {

    protected Component mParent;
    protected AnActionEvent mEvent;

    public BaseToolHandler(Component parent, AnActionEvent event) {
        mParent = parent;
        mEvent = event;
    }

    abstract public void showDialog();
}
