
package cn.wang.chinesechess.manmachine;

import java.awt.Point;
import java.util.ArrayList;

import cn.wang.chinesechess.config.NAME;
import cn.wang.chinesechess.core.ChessPiece;
import cn.wang.chinesechess.core.ChessPoint;
import cn.wang.chinesechess.core.ChessRule;
import cn.wang.chinesechess.core.ManualItem;
import cn.wang.chinesechess.core.MoveStep;


/**
 * �˻����������߷�
 * 
 * @author wanghualiang
 */
public final class AIUtil implements NAME, AIConstants {
	private static int X = 9;
	private static int Y = 10;
	private AIUtil() {
	}

	/**
	 * ����һ�����ӵ������߷�
	 * 
	 * @param piece 
	 * @param startX 
	 * @param startY
	 * @param chessPoints
	 * @param palyFirst
	 * @return һ�����ӵ������߷�
	 */
	public static ArrayList<Point> generateChessMoveByPiece(ChessPiece piece,
			int startX, int startY, ChessPoint[][] chessPoints, String palyFirst) {
		ArrayList<Point> points = new ArrayList<Point>();
		/*
		 * ʹ��2��ѭ������������ ���Ϊ�����Ч�ʣ����Ը������ӵ���𣬷ֱ��д��Ӧ���㷨���������ܴ�
		 */
		boolean rule = false;
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 10; j++) {
				rule = ChessRule.allRule(piece, startX, startY, i, j,
						chessPoints, palyFirst);
				if (rule) {
					points.add(new Point(i, j));
				}
			}

		}
		return points;
	}

	/**
	 * ����һ�������ȫ���߷�
	 * 
	 * @param color
	 *            ����һ�����췽��ڷ�
	 * @param chessPoints
	 *            ���ӵ�2ά����
	 * @param palyFirst
	 * 			  �������ַ�
	 * @return һ�������ȫ���߷�
	 */
	public static ArrayList<ManualItem> generateAllChessMove(String color,
			ChessPoint[][] chessPoints, String palyFirst) {
		ArrayList<ManualItem> chessMoves = new ArrayList<ManualItem>();
		ArrayList<PiecePosition> lists = getPiecePositionByColoar(color,
				chessPoints);

		int size = lists.size();
		// System.out.println("��������" + size);

		for (int index = 0; index < size; index++) {
			PiecePosition record = lists.get(index);
			ChessPiece piece = record.piece;
			Point pos = record.pos;
			/*
			 * System.out.print("���ӣ�" + piece.getId() + "(" + pos.getX() + "," +
			 * pos.getY() + ")");
			 */
			ArrayList<Point> points = AIUtil.generateChessMoveByPiece(piece,
					pos.x, pos.y, chessPoints, palyFirst);

			int size2 = points.size();
			int endX = 0;
			int endY = 0;
			Point temp = null;
			// System.out.println("���е��߷��У�" + size2 + "��");
			for (int j = 0; j < size2; j++) {
				temp = points.get(j);
				MoveStep moveStep = new MoveStep(pos, temp);
				endX = (int) temp.getX();
				endY = (int) temp.getY();
				ManualItem chessMove = new ManualItem();
				chessMove.setMoveStep(moveStep);
				if(chessPoints[endX][endY]!=null&&
						chessPoints[endX][endY].getPiece()!=null){
					chessMove.setEatedPieceId(chessPoints[endX][endY].getPiece().getId());
				}
				chessMoves.add(chessMove);
			}
		}

		return chessMoves;
	}
	/**
	 * �����������Ƶõ������ϵ����Ӻ�λ��
	 * @param coloar 
	 * @param chessPoints
	 * @return
	 */
	public static ArrayList<PiecePosition> getPiecePositionByColoar(String coloar,
			ChessPoint[][] chessPoints) {
		ArrayList<PiecePosition> lists = new ArrayList<PiecePosition>();

		for (int i = 1; i <= X; i++) {
			for (int j = 1; j <= Y; j++) {
				ChessPiece piece = chessPoints[i][j].getPiece();
				
				if (piece != null && piece.getName().equals(coloar)) {
					PiecePosition record = new PiecePosition();
					record.piece = piece;
					record.pos = new Point(i, j);
					lists.add(record);
				}
			}
		}
		return lists;
	}
	/**
	 * �õ����巽���п������ӵ���Ϣ
	 * @param side ���巽 �췽��ڷ�
	 * @param chessPoints ���ӵĶ�ά����
	 * @return
	 */
	public static ArrayList<ChessPiece> getPieceBySide(String side,
			ChessPoint[][] chessPoints) {
		ArrayList<ChessPiece> lists = new ArrayList<ChessPiece>();

		for (int i = 1; i <= X; i++) {
			for (int j = 1; j <= Y; j++) {
				ChessPiece piece = chessPoints[i][j].getPiece();
				if (piece != null && piece.getName().equals(side)) {
					lists.add(piece);
				}
			}
		}
		return lists;
	}

	/**
	 * �Ե�ǰ������й�ֵ
	 * @param playerName ���巽���췽��ڷ�
	 * @param chessPoints
	 * @param palyFirst ���ַ��������ж���ֲ��֣��Ϻ��º죬���Ϻ��º�
	 * @return ��ǰ���棬���Է��������ҵ�����
	 */
	public static int evaluate(String playerName, ChessPoint[][] chessPoints, String palyFirst) {
		int rValue = 0;
		int bValue = 0;
		for (int i = 1; i <= X; i++) {
			for (int j = 1; j <= Y; j++) {
				ChessPiece piece = chessPoints[i][j].getPiece();
				if (piece != null) {
					int index = pieceIdToIndex(piece.getId());
					String color = piece.getName();
					int value = getPositionValue(piece, i, j,palyFirst) + pieceValues[index];
					if (color.equals(RED_NAME)) {
						rValue += value;
					} else {
						bValue += value;
					}
				}
			}
		}
		int result = 0;
		if (palyFirst.equals(COMPUTER_FIRST)){//�������֣��췽Ϊ���Է����췽��Ժڷ�����
			result = rValue - bValue + getFlexibleValueAll(chessPoints, palyFirst);
		} else {//���Ժ��֣��ڷ�Ϊ���Է����ڷ���Ժ췽����
			result = bValue - rValue + getFlexibleValueAll(chessPoints, palyFirst);
		}
		return result;
	}

	/**
	 * �Ե�ǰ������й�ֵ
	 * 
	 * @param chessPoints
	 * @return ��ǰ���棬�췽��Ժڷ�������
	 */
	public static int evaluate(String playerName, ChessPoint[][] chessPoints) {
		int wValue = 0;
		int bValue = 0;
		for (int i = 1; i <= X; i++) {
			for (int j = 1; j <= Y; j++) {
				ChessPiece piece = chessPoints[i][j].getPiece();
				if (piece != null) {
					String color = piece.getName();
					int pieceIdToIndex = pieceIdToIndex(piece.getId());
					if (color.equals(RED_NAME)) {

						wValue += pieceValues[pieceIdToIndex];
					} else {
						bValue += pieceIdToIndex;
					}
				}
			}
		}
		int result = 0;
		if (playerName.equals(RED_NAME)) {
			result = wValue - bValue;
		} else {
			result = bValue - wValue;
		}
		return result;

	}

	/**
	 * �����ӵ�idת���� ����ֵ��λ��ֵ�������ֵ�����������
	 * 
	 * @param id
	 *            ���ӵ�id
	 * @return ���������
	 */
	public static int getPieceValue(PieceId id) {
		int index = pieceIdToIndex(id);
		if (index < 0 || index > 6) {
			return -1;
		}
		return pieceValues[index];
	}

	/**
	 * �������ӵ�λ�ã���ȡ���ӵ�λ��ֵ
	 * 
	 * @param piece
	 * @param x
	 *            ��ѧ�����ϵĺ�����
	 * @param y
	 *            ��ѧ�����ϵ�������
	 * @return ��ȡ���ӵ�λ��ֵ
	 */
	public static int getPositionValue(ChessPiece piece, int x, int y,String palyerFirst) {
		String name = piece.getName();
		int redOrBlack = -1;
		if(palyerFirst.equals(PALYER_FIRST)){
			if (name.equals(RED_NAME)) {
				redOrBlack = 0;
			} else {
				redOrBlack = 1;
			}
		}else{
			if (name.equals(RED_NAME)) {
				redOrBlack = 1;
			} else {
				redOrBlack = 0;
			}
		}
		int index = pieceIdToIndex(piece.getId());
		// System.out.println(redOrBlack+" "+index+" "+piece.getId()+"
		// getPositionValue"+"x="+x+"y="+y);
		// �˴���Ҫע�⣬��ѧ�ϵ��������������겻һ��
		return positionValues[redOrBlack][index][y-1][x-1];
	}

	/**
	 * ��ȡһ�����������Է�ֵ
	 * @param chessPoints ���ӵ�����
	 * @param palyFirst �������ַ�,���Ի����
	 * @return���ӵ�����Է�ֵ
	 */
	public static int getFlexibleValueAll(ChessPoint[][] chessPoints, String palyFirst) {
		int rValue = 0;
		int bValue = 0;
		for (int i = 1; i <= X; i++) {
			for (int j = 1; j <= Y; j++) {
				ChessPiece piece = chessPoints[i][j].getPiece();
				if (piece != null) {
					if(piece.getName().equals(RED_NAME)){
						rValue += getFlexibleValue(piece,i,j,chessPoints,palyFirst);
					}else{
						bValue += getFlexibleValue(piece,i,j,chessPoints,palyFirst);
					}
				}
			}
		}
		if (PALYER_FIRST.equals(COMPUTER_FIRST)){//�������֣�Ϊ�췽���췽��Ժڷ�����
			return rValue - bValue;
		} else {//���Ժ��֣�Ϊ�ڷ����ڷ���Ժ췽����
			return bValue - rValue;
		}
	}
	/**
	 * ��ȡһ�����ӵ�����Է�ֵ
	 * @param piece ����
	 * @param startX ������ʼλ�ú�����
	 * @param startY ������ʼλ��������
	 * @param chessPoints ���ӵ�����
	 * @param palyFirst �������ַ�,���Ի����
	 * @return���ӵ�����Է�ֵ
	 */
	public static int getFlexibleValue(ChessPiece piece, int startX, int startY, 
			ChessPoint[][] chessPoints, String palyFirst) {
		int redValue = 0;
		int blackValue = 0;
		int num = 0;
		int index = pieceIdToIndex(piece.getId());
		for (int i = 1; i <= X; i++) {
			for (int j = 1; j <= Y; j++) {
				num = 0;
				boolean canMove = ChessRule.allRule(piece, startX, startY, i,
						j, chessPoints, palyFirst);
				if(canMove){
					num++;
				}
			}
		}
		return flexibleValues[index] + num;
	}

	/**
	 * �����ӵ�idת��������
	 * 
	 * @param id
	 * @return �����ӵ�idת��������
	 */
	public static int pieceIdToIndex(PieceId id) {
		int index = 0;

		switch (id) {

		case SHUAI:

		case JIANG:
			index = 0;
			break;

		case HONGJU1:
		case HONGJU2:

		case HEIJU1:
		case HEIJU2:
			index = 1;
			break;

		case HONGPAO1:
		case HONGPAO2:

		case HEIPAO1:
		case HEIPAO2:
			index = 2;
			break;

		case HONGSHI1:
		case HONGSHI2:

		case HONGMA1:
		case HONGMA2:

		case HEIMA1:
		case HEIMA2:
			index = 3;
			break;

		case HONGXIANG1:
		case HONGXIANG2:

		case HEIXIANG1:
		case HEIXIANG2:
			index = 4;
			break;

		case HEISHI1:
		case HEISHI2:
			index = 5;
			break;

		case BING1:
		case BING2:
		case BING3:
		case BING4:
		case BING5:

		case ZU1:
		case ZU2:
		case ZU3:
		case ZU4:
		case ZU5:

			index = 6;
			break;
		}
		return index;

	}

}
