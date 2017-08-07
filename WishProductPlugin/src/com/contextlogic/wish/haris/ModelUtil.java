package com.contextlogic.wish.haris;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.ui.EditorTextField;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Haris on 2017-08-03.
 */
public class ModelUtil {

    public static String extractTextFieldValue(JTextField textField) {
        if (textField == null || textField.getText().trim() == "") {
            return null;
        }
        return textField.getText().trim();
    }


    public static String extractTextFieldValue(EditorTextField textField) {
        if (textField == null || textField.getText().trim() == "") {
            return null;
        }
        return textField.getText().trim();
    }

    public static String getFormattedName(String s) {
        String[] list = s.split("(?=[A-Z])", 2);
        if (list.length == 2) {
            return list[1];
        } else {
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
    }

    public static String capitalize(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public static ArrayList<String> getExistingModelNames(Project project) {
        PsiDirectory baseDir = PsiManager.getInstance(project).findDirectory(project.getBaseDir());
        PsiDirectory destinationDir = findSubDirectory(baseDir, "app.src.main.java.com.contextlogic.wish.api.model");
        PsiFile[] models = destinationDir.getFiles();
        ArrayList<String> names = new ArrayList<>();
        for (PsiFile model: models) {
            names.add(model.getName().split("\\.")[0]);
        }
        return names;
    }

    public static PsiDirectory findSubDirectory(PsiDirectory baseDir, String path) {
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