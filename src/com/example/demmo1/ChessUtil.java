package com.example.demmo1;

import java.util.ArrayList;

import android.graphics.Point;

public class ChessUtil {

	public static boolean checkFiveChessInLine(ArrayList<Point> points) {

		if (points.size() > 0) {
			// 读取最后一颗棋子的坐标
			Point p = points.get(points.size() - 1);
			// 判断横向---左
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

	// 判断右上到左下
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

	// 判断左上到右下
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

	//判斷垂直
	private static boolean checkVertical(int x, int y, ArrayList<Point> points) {
		int count = 1;
		// 上
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
		// 下
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


	//判断水平
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
		// 判断横向---右
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
