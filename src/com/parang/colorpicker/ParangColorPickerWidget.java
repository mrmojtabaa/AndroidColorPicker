package com.parang.colorpicker;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class ParangColorPickerWidget extends RelativeLayout {

	private String[] ColorsValue = { "#FFFFFF", "#000000", "#0099CC",
			"#AA66CC", "#99CC00", "#FF8800", "#FF4444", "#CC0000" };
	private String[] PersianColorsName = { "سفید", "سیاه", "آبی", "بنفش",
			"سبز", "نارنجی", "قرمز","زرشکی" };
	private String[] ColorsName = { "White", "Black", "Blue", "Violet",
			"Green", "Orang", "Red","Purple" };

	private Context context;
	private ImageView mColorArea, mSelectedColor;
	private Spinner mColorsList;
	private List<KeyValue> ColorsList = new ArrayList<KeyValue>();
	private KeyValue color;
	private int DefaultColor, SelectedColor;
	private boolean IsPersian;

	private OnTouchListener ColorAreaOnTouchListener;
	private OnItemSelectedListener SpinnerOnItemSelectedListener;

	public ParangColorPickerWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.custom_color_picker, this, true);

		mSelectedColor = (ImageView) findViewById(R.id.ccpSelectedColor);
		mColorArea = (ImageView) findViewById(R.id.ccpColorArea);
		mColorsList = (Spinner) findViewById(R.id.ColorsListSpinner);

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

		SpinnerOnItemSelectedListener = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				SelectedColor = Color.parseColor(ColorsValue[position]);
				mSelectedColor.setBackgroundColor(SelectedColor);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		};


		SelectedColor = DefaultColor;
		mSelectedColor.setBackgroundColor(SelectedColor);
		mColorsList.setSelection(
				GetColoIndex(Color.red(DefaultColor),
						Color.green(DefaultColor), Color.blue(DefaultColor)),
				false);
		mColorsList.setOnItemSelectedListener(SpinnerOnItemSelectedListener);

		ColorAreaOnTouchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View ColorAreaView, MotionEvent event) {

				int x = (int) event.getX();
				int y = (int) event.getY();
				Log.d("x y ", String.valueOf(x)+" "+String.valueOf(y));

				ImageView mColorAreaView = ((ImageView) ColorAreaView);
				Bitmap bitmap = ((BitmapDrawable) mColorAreaView.getDrawable())
						.getBitmap();
				int pixel=0;
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
				mColorsList.setSelection(GetColoIndex(redValue, greenValue,
						blueValue));
				SelectedColor = mycolor;
				mSelectedColor.setBackgroundColor(SelectedColor);

				// switch (event.getAction()) {
				// case MotionEvent.ACTION_DOWN:
				// case MotionEvent.ACTION_MOVE:
				// case MotionEvent.ACTION_UP:
				// }
				//
				return true;
			}
		};

		mColorArea.setOnTouchListener(ColorAreaOnTouchListener);

	}

	private int GetColoIndex(int red, int green, int blue) {
		double minval = 1000000000;
		int minIndex = -1;
		int mRed, mGreen, mBlue;
		int color;

		for (int i = 0; i < ColorsValue.length; i++) {
			color = Color.parseColor(ColorsValue[i]);
			mRed = Color.red(color);
			mGreen = Color.green(color);
			mBlue = Color.blue(color);
			if (Math.sqrt((mRed - red) * (mRed - red) + (mGreen - green)
					* (mGreen - green) + (mBlue - blue) * (mBlue - blue)) < minval) {
				minval = Math.sqrt((mRed - red) * (mRed - red)
						+ (mGreen - green) * (mGreen - green) + (mBlue - blue)
						* (mBlue - blue));
				minIndex = i;
			}
		}
		return minIndex;
	}

	public int getColor() {
		return SelectedColor;
	}

	public void setColor(int defaultColor) {
		DefaultColor = defaultColor;
		SelectedColor = defaultColor;
		mSelectedColor.setBackgroundColor(SelectedColor);
		mColorsList.setSelection(
				GetColoIndex(Color.red(DefaultColor),
						Color.green(DefaultColor), Color.blue(DefaultColor)),
				false);
	}

	public void PersianMode(boolean IsPersian) {
		this.IsPersian = IsPersian;
		
		ColorsList.clear();
		for (int i = 0; i < ColorsValue.length; i++) {
			Log.d("widget", String.valueOf(IsPersian));
			if (this.IsPersian) {
				color = new KeyValue(Color.parseColor(ColorsValue[i]),
						PersianColorsName[i]);
			} else {
				color = new KeyValue(Color.parseColor(ColorsValue[i]),
						ColorsName[i]);
			}
			ColorsList.add(color);
		}
		SpinnerListAdapter ColorsListAdapter = new SpinnerListAdapter(context,
				ColorsList);
		mColorsList.setAdapter(ColorsListAdapter);
		
	}
}
