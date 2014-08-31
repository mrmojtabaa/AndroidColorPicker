package com.parang.colorpicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ParangColorPickerWidget extends RelativeLayout {

	private String[] ColorsValue = { "#FFFFFF", "#000000", "#0099CC",
			"#AA66CC", "#99CC00", "#FF8800", "#FF4444", "#CC0000" };

	private Context context;
	private ImageView mColorArea, mSelectedColor;
	private int DefaultColor, SelectedColor;
	private EditText ColorHexEditText;

	private OnTouchListener ColorAreaOnTouchListener;
	boolean IsPersian = false;

	LinearLayout DefaultColorsPanelLinearLayout;
	OnClickListener DefaultColorClickListener;

	public ParangColorPickerWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.custom_color_picker, this, true);

		mSelectedColor = (ImageView) findViewById(R.id.ccpSelectedColor);
		mColorArea = (ImageView) findViewById(R.id.ccpColorArea);

		DefaultColorsPanelLinearLayout = (LinearLayout) findViewById(R.id.DefaultColorsPanelLinearLayout);
		ColorHexEditText = (EditText) findViewById(R.id.ColorHexEditText);

		// TypedArray mtypedArray = context.obtainStyledAttributes(attrs,
		// R.styleable.ColorPickerOptions, 0, 0);
		// if (DefaultColor == 0) {
		// try {
		// DefaultColor =
		// Color.parseColor(mtypedArray.getString(R.styleable.ColorPickerOptions_DefaultColor));
		// } catch (Exception e) {
		// DefaultColor = Color.WHITE;
		// }
		// }
		// PersianMode = mtypedArray.getBoolean(
		// R.styleable.ColorPickerOptions_PersianMode, false);
		// mtypedArray.recycle();

		SelectedColor = DefaultColor;
		mSelectedColor.setBackgroundColor(SelectedColor);
		ColorHexEditText.setText(String.format("#%06X",
				(0xFFFFFF & SelectedColor)));

		ColorHexEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence HexColor, int arg1,
					int arg2, int arg3) {
				
				try {

					SelectedColor = Color.parseColor(HexColor.toString());
					mSelectedColor.setBackgroundColor(SelectedColor);

				} catch (Exception e) {
				}

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});

		ColorAreaOnTouchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View ColorAreaView, MotionEvent event) {

				int x = (int) event.getX();
				int y = (int) event.getY();
				Log.d("x y ", String.valueOf(x) + " " + String.valueOf(y));

				ImageView mColorAreaView = ((ImageView) ColorAreaView);
				Bitmap bitmap = ((BitmapDrawable) mColorAreaView.getDrawable())
						.getBitmap();
				int pixel = 0;
				try {
					pixel = bitmap.getPixel(x, y);
				} catch (Exception e) {
					return false;
				}

				int redValue = Color.red(pixel);
				int blueValue = Color.blue(pixel);
				int greenValue = Color.green(pixel);
				int Alpha = Color.alpha(pixel);
				if (Alpha == 0) {
					return false;
				}

				int mycolor = Color.rgb(redValue, greenValue, blueValue);

				ColorHexEditText.setText(String.format("#%06X",
						(0xFFFFFF & mycolor)));

				return true;
			}
		};
		mColorArea.setOnTouchListener(ColorAreaOnTouchListener);

		DefaultColorClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				ColorHexEditText.setText(String.format("#%06X",
						(0xFFFFFF & (Integer) ((Button) view).getTag())));
			}
		};

		initDefaultColors();

	}

	// private int GetColoIndex(int red, int green, int blue) {
	// double minval = 1000000000;
	// int minIndex = -1;
	// int mRed, mGreen, mBlue;
	// int color;
	//
	// for (int i = 0; i < ColorsValue.length; i++) {
	// color = Color.parseColor(ColorsValue[i]);
	// mRed = Color.red(color);
	// mGreen = Color.green(color);
	// mBlue = Color.blue(color);
	// if (Math.sqrt((mRed - red) * (mRed - red) + (mGreen - green)
	// * (mGreen - green) + (mBlue - blue) * (mBlue - blue)) < minval) {
	// minval = Math.sqrt((mRed - red) * (mRed - red)
	// + (mGreen - green) * (mGreen - green) + (mBlue - blue)
	// * (mBlue - blue));
	// minIndex = i;
	// }
	// }
	// return minIndex;
	// }

	public void initDefaultColors() {
		// DefaultColorsPanelLinearLayout.addView(child);
		Button button;

		for (int i = 0; i < ColorsValue.length; i++) {

			button = new Button(context);
			button.setBackgroundColor(Color.parseColor(ColorsValue[i]));
			// button.setOnTouchListener(DefaultColorClickListener);
			button.setOnClickListener(DefaultColorClickListener);
			// button.setId();
			button.setTag(Color.parseColor(ColorsValue[i]));
			button.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
					100, 100, 1));

			DefaultColorsPanelLinearLayout.addView(button);
		}

	}

	public int getColor() {
		return SelectedColor;
	}

	public void setColor(int defaultColor) {
		DefaultColor = defaultColor;
		SelectedColor = defaultColor;
		mSelectedColor.setBackgroundColor(SelectedColor);
		ColorHexEditText.setText(String.format("#%06X",
				(0xFFFFFF & SelectedColor)));

	}

	public void PersianMode(boolean IsPersian) {
		this.IsPersian = IsPersian;

		for (int i = 0; i < ColorsValue.length; i++) {
			Log.d("widget", String.valueOf(IsPersian));

		}
	}
}
