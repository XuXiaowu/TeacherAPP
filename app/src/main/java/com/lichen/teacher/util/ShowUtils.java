package com.lichen.teacher.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lichen.teacher.R;


public class ShowUtils {

	private static boolean b;
	private static TextView dialog_title, dialog_message;
	private static Button dialog_left_button, dialog_right_button;
	private static Dialog dialog;
	private static View view;

	/**
	 * 显示Toast的方法
	 */
	public static void showMsg(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void showMsg(Context context,int srtRes){
		Toast.makeText(context, srtRes, Toast.LENGTH_SHORT).show();
	}



	public static void showDialog(Context context, int titleId, int messageId,
								 DialogInterface.OnClickListener okButtonClickListener,
								 DialogInterface.OnClickListener cancelButtonClickListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(titleId);
		builder.setMessage(messageId);
		builder.setPositiveButton(R.string.sure, okButtonClickListener);
		builder.setNegativeButton(R.string.cancel, cancelButtonClickListener);
//		mDialog.setCancelable(false);
		builder.show();
	}

	public static void showUpdateVersioniDialog(Context context, String message,
								 DialogInterface.OnClickListener okButtonClickListener) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.find_new_version);
		builder.setMessage(message);
		builder.setPositiveButton(R.string.now_update, okButtonClickListener);
		builder.setNegativeButton(R.string.next_update, null);
		builder.setCancelable(false);
		builder.show();
	}

	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		return width;
	}

	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int height = wm.getDefaultDisplay().getHeight();
		return height;
	}
}
