package com.vincent.network.login.request;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Vincent on 25/1/2018.
 */
public class DialogHandler extends Handler {

    /**
     * 显示加载框
     */
    public static final int SHOW_PROGRESS_DIALOG = 1;

    /**
     * 隐藏加载框
     */
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private Dialog loadingDialog;

    private DialogCancelListener dialogCancelListener;

    /**
     * 构造方法接收一个加载框的对象  由各个view层创建之后传进来  因为每个对话框所提示的内容有所不同
     * @param dialog
     */
    public DialogHandler(Dialog dialog, DialogCancelListener dialogCancelListener) {
        this.loadingDialog = dialog;
        this.dialogCancelListener = dialogCancelListener;
        initDialogDismissListenner();
    }

    private void initDialogDismissListenner() {
        loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialogCancelListener.onCancel();
            }
        });
    }

    /**
     * 显示加载框
     */
    private void showLodingDialog() {
        loadingDialog.show();
    }

    /**
     * 隐藏加载框
     */
    private void dismissLodingDialog() {
        loadingDialog.dismiss();
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                showLodingDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissLodingDialog();
                break;
        }
    }
}
