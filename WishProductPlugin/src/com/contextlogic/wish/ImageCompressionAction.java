package com.contextlogic.wish;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import com.intellij.openapi.util.IconLoader;
import com.intellij.psi.PsiDirectory;
import com.tinify.*;

import java.io.File;
import java.io.IOException;
import java.lang.Exception;

public class ImageCompressionAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        TinyPNGDialog dialog = new TinyPNGDialog();
        dialog.show();
        if (dialog.isOK()) {
            String source = dialog.getSource();
            String destination = dialog.getDestination();
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
                        "Image file does not exist!",
                        "Could not find file: \"" + src + "\""
                );
            } else if (!destFile.exists()) {
                errorDialog = new BaseErrorDialog(
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
                    "Unexpected Error:",
                    e.getLocalizedMessage()
            );
            errorDialog.show();
        }
    }
}
