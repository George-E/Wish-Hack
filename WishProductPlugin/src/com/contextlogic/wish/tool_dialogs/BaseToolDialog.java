package com.contextlogic.wish.tool_dialogs;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class BaseToolDialog extends DialogWrapper{
    public BaseToolDialog(Component parent) {
        super(parent, true);
    }
}
