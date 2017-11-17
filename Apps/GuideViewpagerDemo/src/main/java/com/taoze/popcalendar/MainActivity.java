package com.taoze.popcalendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.taoze.popcalendar.view.CalendarCard.OnCellClickListener;
import com.taoze.popcalendar.view.CustomDate;
import com.taoze.popcalendar.view.PopCalendar;

/**
 * 
 * @author tzb
 *
 */
public class MainActivity extends Activity {

	private Button btn_search;
	private TextView tv_date;
	private PopCalendar mCalendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn_search = (Button) findViewById(R.id.btn_search);
		tv_date = (TextView) findViewById(R.id.tv_date);
		mCalendar = PopCalendar.getInstance(this, btn_search, Gravity.BOTTOM, new OnCellClickListener() {

			@Override
			public void clickDate(CustomDate date) {
				// TODO Auto-generated method stub
				tv_date.setText(date.toString());
			}

			@Override
			public void changeDate(CustomDate date) {
				// TODO Auto-generated method stub
				Log.d("MainActivity", "changeDate:" + date.toString());
			}
		}, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(MainActivity.this, "点击了确定", 2000).show();

			}
		}, new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				Log.d("MainActivity", "onDismiss");
			}
		});
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mCalendar.show();
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mCalendar.destroy();
	}
}
