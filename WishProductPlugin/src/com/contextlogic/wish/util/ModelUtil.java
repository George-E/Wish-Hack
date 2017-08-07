package com.contextlogic.wish.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Haris on 2017-08-03.
 */
public class ModelUtil {

    public static String extractTextFieldValue(String textFieldValue) {
        if (textFieldValue == null || textFieldValue.trim() == "") {
            return null;
        }
        return textFieldValue.trim();
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
        String subDirForModels = ResourceBundle.getBundle("values").getString("sub_dir_for_models");
        PsiDirectory destinationDir = findSubDirectory(baseDir, subDirForModels);
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