package com.contextlogic.wish.tool_handlers;

import com.contextlogic.wish.BaseErrorDialog;
import com.contextlogic.wish.tool_dialogs.AddImageDialog;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.tinify.Tinify;

import java.awt.*;
import java.io.File;

public class AddImageHandler extends BaseToolHandler {

    AddImageDialog mDialog;

    public AddImageHandler(Component parent, AnActionEvent event) {
        super(parent, event);
    }

    @Override
    public void showDialog() {
        mDialog = new AddImageDialog(mParent, mEvent);
        mDialog.show();
        if (mDialog.isOK()) {
            String source = mDialog.getSource();
            String destination = mDialog.getDestination();
            System.out.println(source);
            System.out.println(destination);
            compressAndSave(source, destination);
        }
    }

    private void compressAndSave(String src, String dest) {
        try {
            Tinify.setKey("MVGfmtzZA1YB-igVC9aJHFLO2GehQn_X");
            File srcFile = new File(src);
            File destFile = new File(dest);

            BaseErrorDialog errorDialog = null;
            if (!srcFile.exists()) {
                errorDialog = new BaseErrorDialog(
                        mParent,
                        "Image file does not exist!",
                        "Could not find file: \"" + src + "\""
                );
            } else if (!destFile.exists()) {
                errorDialog = new BaseErrorDialog(
                        mParent,
                        "Destination folder does not exist!",
                        "Could not find folder: \"" + dest + "/\""
                );
            }

            if (errorDialog != null) {
                errorDialog.show();
                return;
            }
            Tinify.fromFile(src).toFile(dest + "/" + srcFile.getName());
        } catch (Exception e) {
            BaseErrorDialog errorDialog = new BaseErrorDialog(
                    mParent,
                    "Unexpected Error:",
                    e.getLocalizedMessage()
            );
            errorDialog.show();
        }
    }
}
