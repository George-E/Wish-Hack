package com.contextlogic.wish.haris;

import com.contextlogic.wish.haris.ParcelableTypes.primitives.PrimitiveType;
import com.contextlogic.wish.haris.ParcelableTypes.primitives.PrimitiveTypeParser;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.ArrayList;

/**
 * Created by Haris on 2017-08-03.
 */
public class GenerateModelAction extends AnAction {

    private static String TYPE_JSONException = "org.json.JSONException";
    private static String TYPE_JSONObject = "org.json.JSONObject";
    private static String TYPE_ParseException = "java.text.ParseException";
    private static String TYPE_Parcel = "android.os.Parcel";
    private static String TYPE_Parcelable = "android.os.Parcelable";

    private Project mProject;
    private PsiElementFactory mElementFactory;

    @Override
    public void actionPerformed(AnActionEvent e) {
        mProject = e.getProject();

        GenerateModelDialog dialog = new GenerateModelDialog(mProject);
        mElementFactory = JavaPsiFacade.getElementFactory(mProject);

        dialog.show();
        if (dialog.isOK()) {
            createModel(dialog.getFileName(), dialog.getFieldsList());
        }
    }

    private void createModel(String fileName, ArrayList<ModelFieldInfoRow> fields) {
        String classText = "public class " + fileName + " {\n}";
        String completeFileName = fileName + ".java";
        PsiFile newFile = PsiFileFactory.getInstance(mProject).createFileFromText(completeFileName, StdFileTypes.JAVA, classText);
        PsiDirectory baseDir = PsiManager.getInstance(mProject).findDirectory(mProject.getBaseDir());
        PsiDirectory destinationDir = findSubDirectory(baseDir, "app.src.main.java.com.contextlogic.wish.api.model");
        PsiClass modelClass = getChildClass(newFile);

        ArrayList<PsiField> psiFields = generateFields(modelClass, fields);
        generateGetters(modelClass);
        generateConstructorFromParcel(modelClass, psiFields, "in");
        generateDescribeContents(modelClass);

        JavaCodeStyleManager.getInstance(mProject).shortenClassReferences(modelClass);
        CodeStyleManager.getInstance(mProject).reformat(modelClass);
        new WriteCommandAction.Simple(mProject, newFile) {
            @Override
            protected void run() throws Throwable {
                // Must handle file already exists case
                destinationDir.add(newFile);
            }
        }.execute();

        OpenFileDescriptor fileDescriptor = new OpenFileDescriptor(mProject, destinationDir.findFile(completeFileName).getVirtualFile(), 100);
        fileDescriptor.navigateInEditor(mProject, true);
    }

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

    private PsiClass getChildClass(PsiFile file) {
        PsiClass psiClass = PsiTreeUtil.findChildOfType(file, PsiClass.class);
        return psiClass;
    }

    private ArrayList<PsiField> generateFields(PsiClass psiClass, ArrayList<ModelFieldInfoRow> rows) {
        ArrayList<PsiField> psiFields = new ArrayList<>();
        for (ModelFieldInfoRow fieldInfo : rows) {
            if (!fieldInfo.isValidField()) {
                break;
            }
            String fieldString = "private " + fieldInfo.getFieldType() + " " + fieldInfo.getFieldName() + ";";
            PsiField psiField = mElementFactory.createFieldFromText(fieldString, psiClass);
            psiFields.add(psiField);
            psiClass.add(psiField);
        }
        return psiFields;
    }

    private void generateGetters(PsiClass psiClass) {
        PsiField[] fields = psiClass.getAllFields();
        for (PsiField field: fields) {
            String formattedMethodName = Util.getFormattedName(field.getName());
            String signature = "public " + field.getType().getPresentableText() + " get" + formattedMethodName + "()";
            StringBuilder builder = new StringBuilder(signature);
            builder.append("\n{ return " + field.getName() + ";\n}");
            PsiMethod method = createPsiMethod(builder.toString(), psiClass);
            psiClass.add(method);
        }
    }

    private void generateConstructorFromParcel(PsiClass psiClass, ArrayList<PsiField> psiFields, String parcelName) {
        StringBuilder builder = new StringBuilder("protected " + psiClass.getName());
        builder.append("(" + TYPE_Parcel + " " + parcelName + ") {");

        PrimitiveTypeParser parser = new PrimitiveTypeParser();
        for (PsiField field: psiFields) {
            PrimitiveType parcelableType = parser.getParser(field);
            if (parcelableType != null) {
                builder.append(String.format("%s = %s;", field.getName(), parcelableType.getReadValue(parcelName)));
            }
        }

        builder.append("}");
        PsiMethod cons = createPsiMethod(builder.toString(), psiClass);
        psiClass.add(cons);

        // Not relevant here
        generateImplements(psiClass, TYPE_Parcelable);
    }

    private void generateImplements(PsiClass psiClass, String type) {
        PsiJavaCodeReferenceElement referenceElement = mElementFactory.createReferenceFromText(type, psiClass);
        psiClass.getImplementsList().add(referenceElement);
    }

    private void generateDescribeContents(PsiClass psiClass) {
        String methodString = "@Override public int describeContents() { return 0; }";
        psiClass.add(createPsiMethod(methodString, psiClass));
    }

    private PsiMethod createPsiMethod(String methodString, PsiClass psiClass) {
        PsiMethod method = mElementFactory.createMethodFromText(methodString.toString(), psiClass);
        return method;
    }
}