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
 * ��������ͼ
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
	private int mPanelWidth;// ��Ļ�Ŀ��
	private float lineHeight;// �и�
	private Point point = new Point(1, 0);
	// ��ɫ���ӵļ���
	private ArrayList<Point> whitePoints = new ArrayList<Point>();
	// ��ɫ���ӵļ���
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
		// ���㵱ǰ��Ļ����Լ��и�
		mPanelWidth = w;
		lineHeight = (int) mPanelWidth / MAX_LINE;

		whiteChess = Bitmap.createScaledBitmap(whiteChess, (int) lineHeight,
				(int) lineHeight, false);
		blackChees = Bitmap.createScaledBitmap(blackChees, (int) lineHeight,
				(int) lineHeight, false);

	}

	// ���������ø������Ĵ�С
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// ����ʵ��������С
		setMeasuredDimension(480, 480);

	}

	/**
	 * �����¼�
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
				// �жϵ�ǰλ���Ƿ��Ѿ�������
				if (blackPoints.contains(new Point(a, b))
						|| whitePoints.contains(new Point(a, b))) {
					Toast.makeText(getContext(), "�˴��Ѿ���������", 0).show();
					return false;
				}
				if (isBlack) {
					// ��������
					blackPoints.add(new Point(a, b));
					textInfo.setText("�׷���������......");
				} else {
					whitePoints.add(new Point(a, b));
					textInfo.setText("�ڷ���������......");
				}

				invalidate();// ˢ��
				// �ж���Ϸ�Ƿ����
				checkGameOver(isBlack);
				isBlack = !isBlack;
				
			}
		} else {
			Toast.makeText(getContext(), "��Ϸ�Ѿ�����", 0).show();
		}
		return true;
	}

	private void checkGameOver(boolean isBlack) {
		boolean blackWin = false, whiteWin = false;
		if (isBlack) {
			// �ڷ�ʤ��
			blackWin = ChessUtil.checkFiveChessInLine(blackPoints);
		} else {
			whiteWin = ChessUtil.checkFiveChessInLine(whitePoints);
			// �׷�ʤ��
		}
		if (whiteWin || blackWin) {
			// ��Ϸ����
			if (whiteWin) {
				Toast.makeText(getContext(), "�׷�ʤ��", 0).show();

			} else {
				Toast.makeText(getContext(), "�ڷ�ʤ��", 0).show();
			}
			// ������Ϸ�����ı�־
			isPlay = false;
			new AlertDialog.Builder(getContext())
					.setTitle("��ʾ")
					.setMessage("�Ƿ������һ����Ϸ")
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {
								// ���ȷ����ť
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// ȷ����ť����Ӧ�¼�
									whitePoints.clear();
									blackPoints.clear();
									isPlay = true;
									invalidate();
								}
							}).setNegativeButton("ȡ��", null).show();

		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		// ����9*9���� 480
		for (int i = 0; i < MAX_LINE; i++) {
			// canvas.drawLine(24, 24+48*0, 480-24, 24, mPaint);
			canvas.drawLine(lineHeight / 2, lineHeight / 2 + lineHeight * i,
					mPanelWidth - lineHeight / 2, lineHeight / 2 + lineHeight
							* i, mPaint);
			canvas.drawLine(lineHeight / 2 + lineHeight * i, lineHeight / 2,
					lineHeight / 2 + lineHeight * i, mPanelWidth - lineHeight
							/ 2, mPaint);
		}
		// ���ư�ɫ����
		for (int i = 0, n = whitePoints.size(); i < n; i++) {
			Point p = whitePoints.get(i);
			canvas.drawBitmap(whiteChess, p.x * lineHeight, p.y * lineHeight,
					mPaint);
		}
		// ���ƺ�ɫ����
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
		if(isBlack){//�������
			if(whitePoints.size() == 0) return;
			Toast.makeText(getContext(), "�׷�����", 0).show();
			whitePoints.remove(whitePoints.size()-1);
			textInfo.setText("�׷���������......");
		}else{
			if(blackPoints.size() == 0) return;
			Toast.makeText(getContext(), "�ڷ�����", 0).show();
			blackPoints.remove(blackPoints.size()-1);
			textInfo.setText("�ڷ���������......");
		}
		isBlack= !isBlack;
		
		invalidate();
	}
}
