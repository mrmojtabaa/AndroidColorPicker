package com.parang.colorpicker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

public class ParangColorPicker {

	public interface ParangColorPickerListener {
		void onCancel();

		void onOk(int color);
	}
	
	private AlertDialog dialog;
	private ParangColorPickerListener listener;
	private ParangColorPickerWidget ParangColorPickerWidget;

	public ParangColorPicker(Context context, Boolean IsPersian,
			ParangColorPickerListener Listener) {
		View mView = LayoutInflater.from(context).inflate(
				R.layout.color_picker_dialog_layout, null);

		ParangColorPickerWidget = (ParangColorPickerWidget) mView
				.findViewById(R.id.ColorPicker);
		ParangColorPickerWidget.PersianMode(IsPersian);

		this.listener = Listener;

		dialog = new AlertDialog.Builder(context)
				.setView(mView)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						listener.onOk(ParangColorPickerWidget.getColor());
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								listener.onCancel();
							}
						}).create();
	}

	public void show(int InitColor) {

		ParangColorPickerWidget.setColor(InitColor);
		dialog.show();
	}

	public void SetColor(int Color) {
		ParangColorPickerWidget.setColor(Color);
	}

	public void PersianMode(Boolean IsPersian) {
		ParangColorPickerWidget.PersianMode(IsPersian);
	}

}
