package com.example.demmo1;

import java.util.ArrayList;

import android.graphics.Point;

public class ChessUtil {

	public static boolean checkFiveChessInLine(ArrayList<Point> points) {

		if (points.size() > 0) {
			// ��ȡ���һ�����ӵ�����
			Point p = points.get(points.size() - 1);
			// �жϺ���---��
			boolean win = checkHorizontal(p.x, p.y, points);
			if (win)
				return true;
			win = checkVertical(p.x, p.y, points);
			if (win)
				return true;

			win = leftTopRightBottom(p.x, p.y, points);
			if (win)
				return true;

			win = rightTopLeftBottom(p.x, p.y, points);
			if (win)
				return true;
		}
		return false;
	}

	// �ж����ϵ�����
	private static boolean rightTopLeftBottom(int x, int y, ArrayList<Point> points) {
		int count = 1;

		for (int i = 1; i < points.size(); i++) {
			if (points.contains(new Point(x - i, y + i))) {
				count++;
			} else {
				break;
			}
		}
		if (count >= 5) {
			return true;
		}

		for (int i = 1; i < points.size(); i++) {
			if (points.contains(new Point(x + i, y - i))) {
				count++;
			} else {
				break;
			}
		}
		if (count >= 5) {
			return true;
		}

		return false;
	}

	// �ж����ϵ�����
	private static boolean leftTopRightBottom(int x, int y, ArrayList<Point> points) {
		int count = 1;

		for (int i = 1; i < points.size(); i++) {
			if (points.contains(new Point(x + i, y + i))) {
				count++;
			} else {
				break;
			}
		}
		if (count >= 5) {
			return true;
		}

		for (int i = 1; i < points.size(); i++) {
			if (points.contains(new Point(x - i, y - i))) {
				count++;
			} else {
				break;
			}
		}
		if (count >= 5) {
			return true;
		}

		return false;
	}

	//�Дഹֱ
	private static boolean checkVertical(int x, int y, ArrayList<Point> points) {
		int count = 1;
		// ��
		for (int i = 1; i < points.size(); i++) {
			if (points.contains(new Point(x, y - i))) {
				count++;
			} else {
				break;
			}
		}
		if (count >= 5) {
			return true;
		}
		// ��
		for (int i = 1; i < points.size(); i++) {
			if (points.contains(new Point(x, y + i))) {
				count++;
			} else {
				break;
			}
		}
		if (count >= 5) {
			return true;
		}

		return false;
	}


	//�ж�ˮƽ
	private static boolean checkHorizontal(int x, int y, ArrayList<Point> points) {
		int count = 1;
		for (int i = 1; i < points.size(); i++) {
			if (points.contains(new Point(x - i, y))) {
				count++;
			} else {
				break;
			}
		}
		if (count >= 5) {
			return true;
		}
		// �жϺ���---��
		for (int i = 1; i < points.size(); i++) {
			if (points.contains(new Point(x + i, y))) {
				count++;
			} else {
				break;
			}
		}
		if (count >= 5) {
			return true;
		}
		return false;
	}

}
