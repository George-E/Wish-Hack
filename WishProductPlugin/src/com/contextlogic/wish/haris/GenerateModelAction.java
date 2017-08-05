package com.contextlogic.wish.haris;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
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
            createModel(dialog.getFileName(), dialog.getFieldsList());
        }
    }

    private void createModel(String fileName, ArrayList<ModelFieldInfoRow> fields) {
        String classText = "public class " + fileName + " {\n}";
        PsiFile newFile = PsiFileFactory.getInstance(mProject).createFileFromText(fileName + ".java", StdFileTypes.JAVA, classText);
        VirtualFile baseDir = mProject.getBaseDir();
        PsiDirectory destinationDir = PsiManager.getInstance(mProject).findDirectory(baseDir)
                .findSubdirectory("src")
                .findSubdirectory("models");
        PsiClass modelClass = getChildClass(newFile);
        generateFields(modelClass, fields);
        generateGetters(modelClass);

        CodeStyleManager.getInstance(mProject).reformat(modelClass);
        new WriteCommandAction.Simple(mProject, newFile) {
            @Override
            protected void run() throws Throwable {
                destinationDir.add(newFile);
            }
        }.execute();
    }

    private PsiClass getChildClass(PsiFile file) {
        PsiClass psiClass = PsiTreeUtil.findChildOfType(file, PsiClass.class);
        return psiClass;
    }

    private void generateFields(PsiClass psiClass, ArrayList<ModelFieldInfoRow> rows) {
        for (ModelFieldInfoRow fieldInfo : rows) {
            if (!fieldInfo.isValidField()) {
                return;
            }
            String fieldString = "private " + fieldInfo.getFieldType() + " " + fieldInfo.getFieldName() + ";";
            PsiField psiField = JavaPsiFacade.getElementFactory(mProject).createFieldFromText(fieldString, psiClass);
            PsiElement element = psiClass.add(psiField);
            JavaCodeStyleManager.getInstance(mProject).shortenClassReferences(element);
        }
    }

    private void generateGetters(PsiClass psiClass) {
        PsiField[] fields = psiClass.getAllFields();
        for (PsiField field: fields) {
            String signature = "public " + field.getType().getPresentableText() + " get" + field.getName() + "()";
            StringBuilder builder = new StringBuilder(signature);
            builder.append("\n{ return " + field.getName() + ";\n}");
            PsiMethod method = createPsiMethod(builder.toString(), psiClass);
            psiClass.add(method);
        }
    }

    private PsiMethod createPsiMethod(String methodString, PsiClass psiClass) {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(mProject);
        PsiMethod method = elementFactory.createMethodFromText(methodString.toString(), psiClass);
        return method;
    }
}