package com.contextlogic.wish;

import com.contextlogic.wish.tool_handlers.AddImageHandler;
import com.contextlogic.wish.tool_handlers.BaseToolHandler;
import com.contextlogic.wish.tool_handlers.GenerateModelHandler;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.awt.*;

public class PluginToolsDesc {

    public enum Tool {
        GENERATE_MODEL,
        EDIT_MODEL,
        GENERATE_SERVICE,
        GENERATE_ACTIVITY,
        ADD_IMAGE
    }

    public static String getName(Tool tool) {
        switch (tool) {
            case GENERATE_MODEL:
                return "Generate Model";
            case EDIT_MODEL:
                return "Edit Model";
            case GENERATE_SERVICE:
                return "Generate Service";
            case GENERATE_ACTIVITY:
                return "Generate Activity";
            case ADD_IMAGE:
                return "Add Image";
        }
        return null;
    }

    public static BaseToolHandler createHandler(Tool tool, Component parent, AnActionEvent event) {
        switch (tool) {
            case GENERATE_MODEL:
                return new GenerateModelHandler(parent, event);
            case EDIT_MODEL:
                return null;
            case GENERATE_SERVICE:
                return null;
            case GENERATE_ACTIVITY:
                return null;
            case ADD_IMAGE:
                return new AddImageHandler(parent, event);
        }
        return null;
    }
}
