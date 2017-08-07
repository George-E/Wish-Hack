package com.contextlogic.wish.haris;

import com.contextlogic.wish.haris.ParcelableTypes.primitives.PrimitiveType;
import com.contextlogic.wish.haris.ParcelableTypes.primitives.PrimitiveTypeParser;
import com.contextlogic.wish.haris.view.FieldInfoView;
import com.contextlogic.wish.haris.view.GenerateModelDialog;
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
    private static String TYPE_Parcelable_Creator = "android.os.Parcelable.Creator";
    private static String TYPE_BaseModel = "BaseModel";

    private final PrimitiveTypeParser mParser = new PrimitiveTypeParser();

    private Project mProject;
    private PsiElementFactory mElementFactory;
    private PsiDirectory mTargetDir;

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

    private void createModel(String fileName, ArrayList<FieldInfoView> fields) {
        String classText = "public class " + fileName + " {\n}";
        String completeFileName = fileName + ".java";
        PsiFile newFile = PsiFileFactory.getInstance(mProject).createFileFromText(completeFileName, StdFileTypes.JAVA, classText);
        PsiDirectory baseDir = PsiManager.getInstance(mProject).findDirectory(mProject.getBaseDir());
        mTargetDir = findSubDirectory(baseDir, "app.src.main.java.com.contextlogic.wish.api.model");
        PsiClass modelClass = getChildClass(newFile);

        generateImplements(modelClass, TYPE_Parcelable);
        generateExtends(modelClass, TYPE_BaseModel);
        generateFields(modelClass, fields);
        generateGetters(modelClass);
        generateConstructorFromParcel(modelClass, "in");
        generateParseJson(modelClass, fields, "jsonObject");
        generateDescribeContents(modelClass);
        generateWriteToParcel(modelClass, "dest");
        generateCreator(modelClass);

        JavaCodeStyleManager.getInstance(mProject).shortenClassReferences(modelClass);
        CodeStyleManager.getInstance(mProject).reformat(newFile);
        new WriteCommandAction.Simple(mProject, newFile) {
            @Override
            protected void run() throws Throwable {
                // Must handle file already exists case
                mTargetDir.add(newFile);
            }
        }.execute();

        OpenFileDescriptor fileDescriptor = new OpenFileDescriptor(mProject, mTargetDir.findFile(completeFileName).getVirtualFile(), 100);
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

    private void generateFields(PsiClass psiClass, ArrayList<FieldInfoView> rows) {
        for (FieldInfoView fieldInfo : rows) {
            if (!fieldInfo.isValidField()) {
                break;
            }
            String fieldString = "private " + fieldInfo.getFieldType() + " " + fieldInfo.getFieldName() + ";";
            PsiField psiField = mElementFactory.createFieldFromText(fieldString, psiClass);
            psiClass.add(psiField);
        }
    }

    private void generateGetters(PsiClass psiClass) {
        PsiField[] fields = psiClass.getFields();
        for (PsiField field: fields) {
            String formattedMethodName = ModelUtil.getFormattedName(field.getName());
            String signature = "public " + field.getType().getPresentableText() + " get" + formattedMethodName + "()";
            StringBuilder builder = new StringBuilder(signature);
            builder.append("\n{ return " + field.getName() + ";\n}");
            PsiMethod method = createPsiMethod(builder, psiClass);
            psiClass.add(method);
        }
    }

    private void generateConstructorFromParcel(PsiClass psiClass, String source) {
        PsiField[] psiFields = psiClass.getFields();
        StringBuilder builder = new StringBuilder("protected " + psiClass.getName());
        builder.append("(" + TYPE_Parcel + " " + source + ") {");

        for (PsiField field: psiFields) {
            PrimitiveType parcelableType = mParser.getParcelableType(field);
            if (parcelableType != null) {
                builder.append(String.format("%s = %s;", field.getName(), parcelableType.getReadValue(source)));
            }
        }

        builder.append("}");
        PsiMethod cons = createPsiMethod(builder, psiClass);
        psiClass.add(cons);
    }

    private void generateParseJson(PsiClass psiClass, ArrayList<FieldInfoView> fields, String arg) {
        StringBuilder builder = new StringBuilder("@Override protected void parseJson(");
        builder.append(TYPE_JSONObject + " jsonObject) {");
        for (FieldInfoView field: fields) {
            String parseJsonMethod = getParseJsonMethod(field, "jsonObject");
            builder.append(field.getFieldName() + " = " + parseJsonMethod + ";");
        }
        builder.append("}");
        PsiMethod parseJsonMethod = createPsiMethod(builder, psiClass);
        addExceptions(parseJsonMethod, TYPE_JSONException, TYPE_ParseException);
        psiClass.add(parseJsonMethod);
    }

    private void generateExtends(PsiClass psiClass, String type) {
        PsiJavaCodeReferenceElement referenceElement = mElementFactory.createReferenceFromText(type, psiClass);
        psiClass.getExtendsList().add(referenceElement);
    }

    private void generateImplements(PsiClass psiClass, String type) {
        PsiJavaCodeReferenceElement referenceElement = mElementFactory.createReferenceFromText(type, psiClass);
        psiClass.getImplementsList().add(referenceElement);
    }

    private void generateDescribeContents(PsiClass psiClass) {
        String methodString = "@Override public int describeContents() { return 0; }";
        psiClass.add(createPsiMethod(methodString, psiClass));
    }

    private void generateWriteToParcel(PsiClass psiClass, String dest) {
        PsiField[] psiFields = psiClass.getFields();
        String signature = String.format("@Override public void writeToParcel(%s %s, int flags)", TYPE_Parcel, dest);
        StringBuilder builder = new StringBuilder(signature);
        builder.append("{");
        for (PsiField field: psiFields) {
            PrimitiveType parcelableType = mParser.getParcelableType(field);
            if (parcelableType != null) {
                builder.append(parcelableType.getWriteValue(dest, field, 0) + ";");
            } else {
            }
        }
        builder.append("}");
        PsiMethod writeToParcelMethod = createPsiMethod(builder, psiClass);
        psiClass.add(writeToParcelMethod);
    }

    private void generateCreator(PsiClass psiClass) {
        String className = psiClass.getName();
        String creator = "public static final " + TYPE_Parcelable_Creator + "<" + className + "> CREATOR =";
        StringBuilder builder = new StringBuilder(creator);
        String createFromParcelMethodString =
                String.format("@Override public %s createFromParcel(%s in) { return new %s(in); }", className, TYPE_Parcel, className);
        String newArrayMethodString =
                String.format("@Override public %s[] newArray(int size) { return new %s[size]; }", className, className);
        String creatorInstanceString = String.format("new %s<%s>() { %s %s };", TYPE_Parcelable_Creator, className, createFromParcelMethodString, newArrayMethodString);
        builder.append(creatorInstanceString);
        PsiField creatorField = mElementFactory.createFieldFromText(builder.toString(), psiClass);
        psiClass.addBefore(creatorField, psiClass.getLastChild());
    }

    private PsiMethod createPsiMethod(String methodString, PsiClass psiClass) {
        PsiMethod method = mElementFactory.createMethodFromText(methodString, psiClass);
        return method;
    }

    private PsiMethod createPsiMethod(StringBuilder builder, PsiClass psiClass) {
        PsiMethod method = mElementFactory.createMethodFromText(builder.toString(), psiClass);
        return method;
    }

    private void addExceptions(PsiMethod method, String... exceptions) {
        for (String exception: exceptions) {
            PsiJavaCodeReferenceElement referenceElement = mElementFactory.createReferenceFromText(exception, method);
            method.getThrowsList().add(referenceElement);
        }
    }

    private String getParseJsonMethod(FieldInfoView field, String source) {
        String fieldType = field.getFieldType();
        String jsonOptMethod = null;
        boolean isExistingClass = false;
        if (fieldType.equals("String")) {
            jsonOptMethod = "optString";
        } else if (fieldType.equals("int") || fieldType.equals("Integer")) {
            jsonOptMethod = "optInt";
        } else if (fieldType.equals("boolean") || fieldType.equals("Boolean")) {
            jsonOptMethod = "optBoolean";
        } else if (fieldType.equals("long") || fieldType.equals("Long")) {
            jsonOptMethod = "optLong";
        } else if (fieldType.equals("double") || fieldType.equals("Double")){
            jsonOptMethod = "optDouble";
        } else if (ModelUtil.getExistingModelNames(mProject).contains(fieldType)) {
            jsonOptMethod = "optJSONObject";
            isExistingClass = true;
        }
        if (jsonOptMethod != null) {
            if (isExistingClass) {
                jsonOptMethod = String.format("new %s(%s.%s(\"%s\"))", field.getFieldType(), source, jsonOptMethod, field.getJsonFieldName());
            } else {
                jsonOptMethod = String.format("%s.%s(\"%s\")", source, jsonOptMethod, field.getJsonFieldName());
            }
        }
        return jsonOptMethod;
    }
}