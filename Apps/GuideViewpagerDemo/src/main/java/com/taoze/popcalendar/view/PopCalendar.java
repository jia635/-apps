package com.taoze.popcalendar.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.taoze.popcalendar.R;
import com.taoze.popcalendar.view.CalendarCard.OnCellClickListener;

/**
 * �Զ��������PopWindow
 * 
 * @author tzb
 *
 */
public class PopCalendar extends PopupWindow {

	/**
	 * popwindow����ʾλ�õĶ��뷽ʽ
	 */
	private static int GRAVITY_FOR_POP = Gravity.BOTTOM;
	/**
	 * ��ʼ������
	 */
	private static int X_LOCATION = 0;

	/**
	 * ��ʼY����
	 */
	private static int Y_LOCATION = 0;

	/**
	 * ��ǰ�����ʵ��
	 */
	public static PopCalendar instance;

	/**
	 * ����װ������ViewPager
	 */
	private static ViewPager mViewPager;

	/**
	 * �������������
	 * */
	private static CalendarViewAdapter<CalendarCard> adapter;
	/**
	 * ��һ����ѡ��ť
	 */
	private static ImageButton preImgBtn;
	/**
	 * ��һ����ѡ��ť
	 */
	private static ImageButton nextImgBtn;
	/**
	 * ͷ����ʾ������
	 */
	private static TextView slide_time;
	/**
	 * Ĭ�ϵ�ViewPager�±�
	 */
	private static int mCurrentIndex = 498;
	/**
	 * ����������
	 */
	private static CalendarCard[] mShowViews;

	/**
	 * Ĭ�ϵķ���
	 */
	private static SildeDirection mDirection = SildeDirection.NO_SILDE;

	enum SildeDirection {
		RIGHT, LEFT, NO_SILDE;
	}

	/**
	 * �����ϱ�ע������(��ʽ"2015-12-25")
	 */
	public static List<Date> liang = new ArrayList<Date>();
	/**
	 * �����Ķ���
	 * */
	private Context context;

	private static View parent;

	public PopCalendar(Context context) {
		super(context);
		this.context = context;
	}

	public PopCalendar(View view, int matchParent, int wrapContent, View parent) {
		super(view, matchParent, wrapContent);
		this.parent = parent;
	}

	/**
	 * 
	 * @param context
	 *            ������
	 * @param parent
	 *            Ҫ�ο������
	 * @param gravity
	 *            ��ʾ���λ��
	 * @param onClickdate
	 *            ������ڻص�����
	 * @param onConfirm
	 *            ���ȷ���ص�����
	 * @return
	 */
	public static synchronized PopCalendar getInstance(Context context, View parent, int gravity, OnCellClickListener onClickdate,
			OnClickListener onConfirm, OnDismissListener onDismiss) {
		if (gravity != -1) {
			GRAVITY_FOR_POP = gravity;
		}
		if (context == null) {
			return null;
		}
		if (parent == null) {
			return null;
		}

		if (null == instance) {
			View view = LayoutInflater.from(context).inflate(R.layout.layout_calendar, null);
			mViewPager = (ViewPager) view.findViewById(R.id.activity_user_calendar_Viewpager);
			preImgBtn = (ImageButton) view.findViewById(R.id.activity_user_calendar_PreMonthBtn);
			nextImgBtn = (ImageButton) view.findViewById(R.id.activity_user_calendar_NextMonthBtn);
			slide_time = (TextView) view.findViewById(R.id.slide_time);
			TextView btn_confirm = (TextView) view.findViewById(R.id.tv_confirm);
			// ���������ַ����õ���Ⱥ͸߶� getWindow().getDecorView().getWidth()
			instance = new PopCalendar(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, parent);

			final OnCellClickListener listener = onClickdate;

			preImgBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
				}
			});
			nextImgBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
				}
			});

			Calendar c = Calendar.getInstance();

			CalendarCard[] views = new CalendarCard[3];
			for (int i = 0; i < 3; i++) {
				views[i] = new CalendarCard(context, new OnCellClickListener() {

					@Override
					public void clickDate(CustomDate date) {
						if (listener != null) {
							listener.clickDate(date);
						}
						// window.dismiss();
					}

					@Override
					public void changeDate(CustomDate date) {
						slide_time.setText(date.year + "��" + date.month + "��");
					}
				}, liang);
			}

			adapter = new CalendarViewAdapter<CalendarCard>(views);
			setViewPager();

			// ����popWindow��������ɵ������仰������ӣ�������true
			instance.setFocusable(true);

			// ʵ����һ��ColorDrawable��ɫΪ��͸��
			ColorDrawable dw = new ColorDrawable(0xffffffff);
			instance.setBackgroundDrawable(dw);
			final OnClickListener onclick = onConfirm;
			btn_confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (onclick != null) {
						onclick.onClick(v);
					}
					instance.dismiss();
				}
			});

			// ����popWindow����ʾ����ʧ����
			instance.setAnimationStyle(R.style.mypopwindow_anim_style);
			// �ڵײ���ʾ
			// View parent = activity.findViewById(R.id.contact_main);
			// window.showAtLocation(parent, GRAVITY_FOR_POP, X_LOCATION,
			// Y_LOCATION);
			final OnDismissListener dListener = onDismiss;
			// popWindow��ʧ��������
			instance.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					if (dListener != null) {
						dListener.onDismiss();
					}
					System.out.println("popWindow��ʧ");
				}
			});
		}
		return instance;
	}

	/**
	 * ��ʾpopWindow
	 */
	public static void show() {
		instance.showAsDropDown(parent, X_LOCATION, Y_LOCATION);
	}

	private static void setViewPager() {
		mViewPager.setAdapter(adapter);
		mViewPager.setCurrentItem(498);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				measureDirection(position);
				updateCalendarView(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	/**
	 * ���㷽��
	 * 
	 * @param position
	 *            viewpager��λ��
	 */
	private static void measureDirection(int position) {

		if (position > mCurrentIndex) {
			mDirection = SildeDirection.RIGHT;

		} else if (position < mCurrentIndex) {
			mDirection = SildeDirection.LEFT;
		}
		mCurrentIndex = position;
	}

	/**
	 * ����������ͼ
	 * 
	 * @param position
	 *            viewpager��λ��
	 */
	private static void updateCalendarView(int position) {
		mShowViews = adapter.getAllItems();
		if (mDirection == SildeDirection.RIGHT) {
			mShowViews[position % mShowViews.length].rightSlide();
		} else if (mDirection == SildeDirection.LEFT) {
			mShowViews[position % mShowViews.length].leftSlide();
		}
		mDirection = SildeDirection.NO_SILDE;
	}

	public void destroy() {
		instance = null;
	}
}
