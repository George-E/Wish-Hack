package com.contextlogic.wish.haris;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.ArrayList;

/**
 * Created by Haris on 2017-08-03.
 */
public class GenerateModelAction extends AnAction {

    private Project mProject;

    @Override
    public void actionPerformed(AnActionEvent e) {
        mProject = e.getProject();

        GenerateModelDialog dialog = new GenerateModelDialog(mProject);
        dialog.show();
        if (dialog.isOK()) {
            PsiFile file = createFile(dialog.getFileName());
            new WriteCommandAction.Simple(mProject, file) {
                @Override
                protected void run() throws Throwable {
                    addFields(file, dialog.getFieldsList());
                }
            }.execute();
        }
    }

    private PsiFile createFile(String fileName) {
        String classText = "public class " + fileName + " {\n}";
        PsiFile newFile = PsiFileFactory.getInstance(mProject).createFileFromText(fileName + ".java", StdFileTypes.JAVA, classText);
        VirtualFile baseDir = mProject.getBaseDir();
        PsiDirectory destinationDir = PsiManager.getInstance(mProject).findDirectory(baseDir)
                .findSubdirectory("src")
                .findSubdirectory("models");
        new WriteCommandAction.Simple(mProject, newFile) {
            @Override
            protected void run() throws Throwable {
                destinationDir.add(newFile);

            }
        }.execute();
        return newFile;
    }

    private void addFields(PsiFile file, ArrayList<ModelFieldInfoRow> rows) {
        PsiClass psiClass = PsiTreeUtil.findChildOfType(file, PsiClass.class);
        for (ModelFieldInfoRow fieldInfo : rows) {
            if (!fieldInfo.isValidField()) {
                return;
            }
            String fieldString = "private " + fieldInfo.getFieldType() + " " + fieldInfo.getFieldName() + ";";
            PsiField psiField = JavaPsiFacade.getElementFactory(mProject).createFieldFromText(fieldString, psiClass);
            System.out.println(psiClass.toString());
            PsiElement element = psiClass.add(psiField);
            JavaCodeStyleManager.getInstance(mProject).shortenClassReferences(element);
        }
    }
}