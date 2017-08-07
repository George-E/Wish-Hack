package com.contextlogic.wish.tool_handlers;

import com.contextlogic.wish.BaseErrorDialog;
import com.contextlogic.wish.tool_dialogs.AddImageDialog;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import com.tinify.Tinify;

import java.awt.*;
import java.io.File;
import java.util.ResourceBundle;

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
            Tinify.setKey(ResourceBundle.getBundle("values").getString("tinypng_api_key"));
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


            PsiDirectory baseDir = PsiManager.getInstance(mEvent.getProject()).findDirectory(mEvent.getProject().getBaseDir());
            String subDirForImages = ResourceBundle.getBundle("values").getString("sub_dir_for_images");
            PsiDirectory targetDir = findSubDirectory(baseDir, subDirForImages);
            /*System.out.println(targetDir.getName());
            for (PsiFile file : targetDir.getFiles()) {
                System.out.println(file.getName());
            }*/
            OpenFileDescriptor fileDescriptor = new OpenFileDescriptor(mEvent.getProject(), targetDir.findFile(srcFile.getName()).getVirtualFile(), 100);
            fileDescriptor.navigateInEditor(mEvent.getProject(), true);
        } catch (Exception e) {
            e.printStackTrace();
            BaseErrorDialog errorDialog = new BaseErrorDialog(
                    mParent,
                    "Unexpected Error:",
                    e.getMessage()
            );
            errorDialog.show();
        }
    }

    //geisa move this to util
    private PsiDirectory findSubDirectory(PsiDirectory baseDir, String path) {
        PsiDirectory destinationDir = baseDir;
        for (String dirName: path.split("\\.")) {
            destinationDir = destinationDir.findSubdirectory(dirName);
            if (destinationDir == null) {
                return null;
            }
        }
        return destinationDir;
    }
}
