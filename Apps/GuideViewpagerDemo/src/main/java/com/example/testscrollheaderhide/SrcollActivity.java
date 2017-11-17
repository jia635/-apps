package com.example.testscrollheaderhide;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.testscrollheaderhide.srcoll.ObservableScrollView;
import com.example.testscrollheaderhide.srcoll.ObservableScrollView.ScrollViewListener;
import com.forty7.customdirectionguideviewpagerdemo.R;

public class SrcollActivity extends Activity implements ScrollViewListener {

	private View layoutHead;
	private ObservableScrollView scrollView;
	private ImageView imageView;
	private WebView webView;

	private int height;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_srcoll);
		
		initView();
	}

	private void initView() {
		webView = (WebView) findViewById(R.id.webview1);
		scrollView = (ObservableScrollView) findViewById(R.id.scrollview);
		layoutHead = findViewById(R.id.view_head);
		imageView = (ImageView) findViewById(R.id.imageView1);
		layoutHead.setBackgroundColor(Color.argb(0, 0xfd, 0x91, 0x5b));

		// 初始化webview
		// 启用支持javascript
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		webView.loadUrl("http://www.topit.me/");
		// 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}
		});

		// 获取顶部图片高度后，设置滚动监听
		ViewTreeObserver vto = imageView.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				imageView.getViewTreeObserver().removeGlobalOnLayoutListener(
						this);
				height = imageView.getHeight();
				imageView.getWidth();

				scrollView.setScrollViewListener(SrcollActivity.this);
			}
		});

	}

	@Override
	public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
			int oldx, int oldy) {

		// Log.i("TAG","y--->"+y+"    height-->"+height);
		if (y <= height) {
			float scale = (float) y / height;
			float alpha = (255 * scale);
			// Log.i("TAG","alpha--->"+alpha);

			// layout全部透明
			// layoutHead.setAlpha(scale);

			// 只是layout背景透明(仿知乎滑动效果)
			layoutHead.setBackgroundColor(Color.argb((int) alpha, 0xfd, 0x91,
					0x5b));
		}

	}
}
