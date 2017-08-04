package com.contextlogic.wish;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

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
            if (srcFile.exists() && destFile.exists()) {
                Tinify.fromFile(src).toFile(dest + "/" + srcFile.getName());
            } else {
                BaseErrorDialog errorDialog = new BaseErrorDialog(
                        "File Does Not Exist",
                        "File Does Not Exist"
                );
                errorDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
