package com.example.testscrollheaderhide;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.example.testscrollheaderhide.xlist.XListView;
import com.example.testscrollheaderhide.xlist.XListView.IXListViewListener;
import com.forty7.customdirectionguideviewpagerdemo.R;

public class XListActivity extends Activity implements
		OnScrollListener {

	private XListView listView;
	// 标题栏的布局
	private RelativeLayout title;
	// ListView的头布局
	private View headerView;
	// 头布局的高度
	private int headerHeight;
	private LayoutInflater inflater;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ativity_xlist);
		title = (RelativeLayout) findViewById(R.id.rl_title);
		listView = (XListView) findViewById(R.id.list);

		title.getBackground().setAlpha(0);
		inflater = LayoutInflater.from(this);
		headerView = inflater.inflate(R.layout.list_header, null);
		// 添加头布局
		listView.addHeaderView(headerView);
		listView.setAdapter(new MyAdapter());
		// 设置滚动监听
		listView.setOnScrollListener(this);
		// 设置可以刷新与加载更多
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);
		listView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// 单纯的模拟刷新过程
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						listView.stopRefresh();
					}
				}, 500);
			}

			@Override
			public void onLoadMore() {
				// 单纯的模拟加载过程
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						listView.stopLoadMore();
					}
				}, 500);
			}
		});

	}

	// 自定义适配器
	private class MyAdapter extends BaseAdapter {

		// 默认显示10个item
		@Override
		public int getCount() {
			return 30;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.list_item, null);
			}

			return convertView;
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	// 最重要的方法，标题栏的透明度变化在这个方法实现
	@Override
	public void onScroll(AbsListView listView, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// 判断当前最上面显示的是不是头布局，因为Xlistview有刷新控件，所以头布局的位置是1，即第二个
		if (firstVisibleItem == 1) {
			// 获取头布局
			View view = listView.getChildAt(0);
			if (view != null) {
				// 获取头布局现在的最上部的位置的相反数
				int top = -view.getTop();
				// 获取头布局的高度
				headerHeight = view.getHeight();
				// 满足这个条件的时候，是头布局在XListview的最上面第一个控件的时候，只有这个时候，我们才调整透明度
				if (top <= headerHeight && top >= 0) {
					// 获取当前位置占头布局高度的百分比
					float f = (float) top / (float) headerHeight;
					title.getBackground().setAlpha((int) (f * 255));
					// 通知标题栏刷新显示
					title.invalidate();
				}
			}
		} else if (firstVisibleItem > 1) {
			title.getBackground().setAlpha(255);
		} else {
			title.getBackground().setAlpha(0);
		}
	}

}