package com.contextlogic.wish.tool_handlers;

import com.contextlogic.wish.tool_dialogs.ModelFieldTable;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;

import java.awt.*;
import java.util.ArrayList;

public class EditModelHandler extends GenerateModelHandler {

    PsiFile mPsiFile;

    public EditModelHandler(Component parent, AnActionEvent event, PsiFile model) {
        super(parent, event);
        mPsiFile = model;
    }

    @Override
    protected void saveFile(PsiFile newFile) {
        //do nothing
    }

    @Override
    protected ArrayList<ModelFieldTable.ModelField> getStartingRows() {
        ArrayList<ModelFieldTable.ModelField> startingRows =  new ArrayList<>();
        PsiClass psiClass = PsiTreeUtil.findChildOfType(mPsiFile, PsiClass.class);
        PsiField psiFields[] = psiClass.getAllFields();
        for (PsiField psiField : psiFields) {
            if (psiField.getName().equalsIgnoreCase("creator")) {
                continue;
            }
            startingRows.add( new ModelFieldTable.ModelField(psiField.getType().getPresentableText(), psiField.getName(), "key"));
        }
        return startingRows;
    }

    @Override
    protected String getStartingModelName() {
        return mPsiFile.getName();
    }
}
