package com.example.demmo1;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView time, textInfo;
	private MyView myView;
	private Button back;
	private int currentTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		time = (TextView) findViewById(R.id.timer);
		back = (Button) findViewById(R.id.back);
		myView = (MyView) findViewById(R.id.myView1);
		textInfo = (TextView) findViewById(R.id.textInfo);
		
		myView.sendView(textInfo);
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);

						mHandler.obtainMessage(1, ++currentTime).sendToTarget();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		}.start();

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myView.goBack();
			}
		});

	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			time.setText(msg.obj + "");
		};
	};

}
