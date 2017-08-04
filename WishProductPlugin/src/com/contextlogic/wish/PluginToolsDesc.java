package com.contextlogic.wish;

import com.contextlogic.wish.tool_dialogs.AddImageDialog;
import com.contextlogic.wish.tool_handlers.AddImageHandler;
import com.contextlogic.wish.tool_handlers.BaseToolHandler;
import com.intellij.openapi.ui.DialogWrapper;

import java.awt.*;

public class PluginToolsDesc {

    public enum Tool {
        CREATE_MODEL,
        EDIT_MODEL,
        CREATE_SERVICE,
        CREATE_ACTIVITY,
        ADD_IMAGE
    }

    public static String getName(Tool tool) {
        switch (tool) {
            case CREATE_MODEL:
                return "Create Model";
            case EDIT_MODEL:
                return "Edit Model";
            case CREATE_SERVICE:
                return "Create Service";
            case CREATE_ACTIVITY:
                return "Create Activity";
            case ADD_IMAGE:
                return "Add Image";
        }
        return null;
    }

    public static DialogWrapper createDialog(Tool tool, Component parent) {
        switch (tool) {
            case CREATE_MODEL:
                return null;
            case EDIT_MODEL:
                return null;
            case CREATE_SERVICE:
                return null;
            case CREATE_ACTIVITY:
                return null;
            case ADD_IMAGE:
                return new AddImageDialog(parent);
        }
        return null;
    }

    public static BaseToolHandler createHandler(Tool tool) {
        switch (tool) {
            case CREATE_MODEL:
                return null;
            case EDIT_MODEL:
                return null;
            case CREATE_SERVICE:
                return null;
            case CREATE_ACTIVITY:
                return null;
            case ADD_IMAGE:
                return new AddImageHandler();
        }
        return null;
    }
}
