package com.example.demmo1;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 五子棋视图
 * 
 * @author Administrator
 * 
 */
public class MyView extends View {
	private TextView textInfo;
	private Paint mPaint;
	private Bitmap whiteChess;
	private Bitmap blackChees;
	private int MAX_LINE = 10;
	private int mPanelWidth;// 屏幕的宽度
	private float lineHeight;// 行高
	private Point point = new Point(1, 0);
	// 白色棋子的集合
	private ArrayList<Point> whitePoints = new ArrayList<Point>();
	// 黑色棋子的集合
	private ArrayList<Point> blackPoints = new ArrayList<Point>();
	private boolean isBlack = true;
	private boolean isPlay = true;

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		textInfo = (TextView) findViewById(R.id.textInfo);
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);

		whiteChess = BitmapFactory.decodeResource(getResources(),
				R.drawable.stone_w2);
		blackChees = BitmapFactory.decodeResource(getResources(),
				R.drawable.stone_b1);

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		// 计算当前屏幕宽度以及行高
		mPanelWidth = w;
		lineHeight = (int) mPanelWidth / MAX_LINE;

		whiteChess = Bitmap.createScaledBitmap(whiteChess, (int) lineHeight,
				(int) lineHeight, false);
		blackChees = Bitmap.createScaledBitmap(blackChees, (int) lineHeight,
				(int) lineHeight, false);

	}

	// 测量和设置父容器的大小
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 设置实际容器大小
		setMeasuredDimension(480, 480);

	}

	/**
	 * 触摸事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (isPlay) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				int x = (int) event.getX();
				int y = (int) event.getY();
				//
				int a = (int) (x / lineHeight);
				int b = (int) (y / lineHeight);
				Log.i("test", a + ":" + b);
				// 判断当前位置是否已经有棋子
				if (blackPoints.contains(new Point(a, b))
						|| whitePoints.contains(new Point(a, b))) {
					Toast.makeText(getContext(), "此处已经有棋子了", 0).show();
					return false;
				}
				if (isBlack) {
					// 保存坐标
					blackPoints.add(new Point(a, b));
					textInfo.setText("白方正在下棋......");
				} else {
					whitePoints.add(new Point(a, b));
					textInfo.setText("黑方正在下棋......");
				}

				invalidate();// 刷新
				// 判断游戏是否结束
				checkGameOver(isBlack);
				isBlack = !isBlack;
				
			}
		} else {
			Toast.makeText(getContext(), "游戏已经结束", 0).show();
		}
		return true;
	}

	private void checkGameOver(boolean isBlack) {
		boolean blackWin = false, whiteWin = false;
		if (isBlack) {
			// 黑方胜利
			blackWin = ChessUtil.checkFiveChessInLine(blackPoints);
		} else {
			whiteWin = ChessUtil.checkFiveChessInLine(whitePoints);
			// 白方胜利
		}
		if (whiteWin || blackWin) {
			// 游戏结束
			if (whiteWin) {
				Toast.makeText(getContext(), "白方胜利", 0).show();

			} else {
				Toast.makeText(getContext(), "黑方胜利", 0).show();
			}
			// 设置游戏结束的标志
			isPlay = false;
			new AlertDialog.Builder(getContext())
					.setTitle("提示")
					.setMessage("是否进入下一局游戏")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								// 添加确定按钮
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// 确定按钮的响应事件
									whitePoints.clear();
									blackPoints.clear();
									isPlay = true;
									invalidate();
								}
							}).setNegativeButton("取消", null).show();

		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		// 绘制9*9棋盘 480
		for (int i = 0; i < MAX_LINE; i++) {
			// canvas.drawLine(24, 24+48*0, 480-24, 24, mPaint);
			canvas.drawLine(lineHeight / 2, lineHeight / 2 + lineHeight * i,
					mPanelWidth - lineHeight / 2, lineHeight / 2 + lineHeight
							* i, mPaint);
			canvas.drawLine(lineHeight / 2 + lineHeight * i, lineHeight / 2,
					lineHeight / 2 + lineHeight * i, mPanelWidth - lineHeight
							/ 2, mPaint);
		}
		// 绘制白色棋子
		for (int i = 0, n = whitePoints.size(); i < n; i++) {
			Point p = whitePoints.get(i);
			canvas.drawBitmap(whiteChess, p.x * lineHeight, p.y * lineHeight,
					mPaint);
		}
		// 绘制黑色棋子
		for (int i = 0, n = blackPoints.size(); i < n; i++) {

			Point p = blackPoints.get(i);
			canvas.drawBitmap(blackChees, p.x * lineHeight, p.y * lineHeight,
					mPaint);
		}
		
	}

	public void sendView(TextView tv){
		textInfo = tv;
	}
	
	public void goBack(){
		if(isBlack){//白棋悔棋
			if(whitePoints.size() == 0) return;
			Toast.makeText(getContext(), "白方悔棋", 0).show();
			whitePoints.remove(whitePoints.size()-1);
			textInfo.setText("白方正在下棋......");
		}else{
			if(blackPoints.size() == 0) return;
			Toast.makeText(getContext(), "黑方悔棋", 0).show();
			blackPoints.remove(blackPoints.size()-1);
			textInfo.setText("黑方正在下棋......");
		}
		isBlack= !isBlack;
		
		invalidate();
	}
}
