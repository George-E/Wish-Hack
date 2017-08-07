package com.contextlogic.wish.actions;

import com.contextlogic.wish.home.PluginHomeDialog;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.GlobalSearchScopesCore;

public class EditModelAction extends AnAction {

    @Override
    public void actionPerformed(final AnActionEvent event) {
        final Project project = event.getProject();
        if (project == null) {
            return;
        }

        PluginHomeDialog dialog = new PluginHomeDialog(event);


        PsiDocumentManager.getInstance(project).commitAllDocuments();
        final Editor editor = event.getData(CommonDataKeys.EDITOR);

        if (editor != null) {
            final PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
            if (!isApplicableFile(psiFile)) {
                //geisa show error
                return;
            }
            //runOverEditor(project, editor, psiFile);
            dialog.show();
        } else {
            VirtualFile[] selectedFilesAndDirs = CommonDataKeys.VIRTUAL_FILE_ARRAY.getData(event.getDataContext());
            if (selectedFilesAndDirs.length > 1 ) {
                //geisa show error
                return;
            }
            VirtualFile virtualFile = DataKeys.VIRTUAL_FILE.getData(event.getDataContext());
            if (!isApplicableFile(project, virtualFile)) {
                //geisa show error
                return;
            }
            //runOverFiles(project, files);
            dialog.show();
        }
    }

    @Override
    public void update(final AnActionEvent event) {
        final Presentation presentation = event.getPresentation();
        final Project project = event.getProject();
        if (project == null) {
            presentation.setEnabledAndVisible(false);
            return;
        }

        final DartSdk sdk = DartSdk.getDartSdk(project);
        if (sdk == null || !DartAnalysisServerService.isDartSdkVersionSufficient(sdk)) {
            presentation.setEnabledAndVisible(false);
            return;
        }

        final Editor editor = event.getData(CommonDataKeys.EDITOR);
        final VirtualFile[] filesAndDirs = CommonDataKeys.VIRTUAL_FILE_ARRAY.getData(event.getDataContext());

        if (editor != null) {
            final PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
            // visible for any Dart file, but enabled for applicable only
            presentation.setVisible(psiFile != null && psiFile.getFileType() == DartFileType.INSTANCE);
            presentation.setEnabled(isApplicableFile(psiFile));
            presentation.setText(getActionTextForEditor());
            return;
        } else if (filesAndDirs != null) {
            presentation.setEnabledAndVisible(false);
            return;
        }

        presentation.setEnabledAndVisible(false);
    }

    private static boolean mayHaveApplicableFiles(final Project project, final VirtualFile[] files) {
        for (VirtualFile fileOrDir : files) {
            if (!fileOrDir.isDirectory() && isApplicableFile(project, fileOrDir)) {
                return true;
            }

            GlobalSearchScope dirScope = null;

            if (fileOrDir.isDirectory())
                if (dirScope == null) {
                    dirScope = GlobalSearchScopesCore.directoryScope(project, fileOrDir, true);
                }
                else {
                    dirScope = dirScope.union(GlobalSearchScopesCore.directoryScope(project, fileOrDir, true));
                }
            }
        }

        return false;
    }

    private static boolean isApplicableFile(final Project project, final VirtualFile virtualFile) {
        if (virtualFile == null) {
            return false;
        }
        final PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
        return isApplicableFile(psiFile);
    }

    private static boolean isApplicableFile(final PsiFile psiFile) {
        if (psiFile == null ||
                psiFile.getVirtualFile() == null ||
                psiFile.getFileType() != StdFileTypes.JAVA) {
            return false;
        }

        /*final Module module = ModuleUtilCore.findModuleForPsiElement(psiFile);
        if (module == null) {
            return false;
        }*/

        return true;
    }
}
