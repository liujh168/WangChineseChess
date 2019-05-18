
package cn.wang.chinesechess.core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import cn.wang.chinesechess.ChessUtil;
import cn.wang.chinesechess.config.NAME;

/**
 * ���ӹ����࣬��Ҫ��һЩ�����㷨��
 * 
 * ʹ������Ԫģʽ
 * 
 * @author wanghualiang
 */
public final class PieceUtil implements NAME {

	private PieceUtil() {

	}

	private static HashMap<String, ImageIcon> imageIcons;

	static {
		imageIcons = new HashMap<String, ImageIcon>();
		String[] imgNames = { "bing.png", "hongju.png", "hongma.png",
				"hongxiang.png", "hongshi.png", "shuai.png", "hongpao.png",
				"zu.png", "heiju.png", "heima.png", "heixiang.png",
				"heishi.png", "heipao.png", "jiang.png" };

		int size = imgNames.length;
		for (int index = 0; index < size; index++) {
			ImageIcon imageIcon = ChessUtil.getImageIcon("piece/"
					+ imgNames[index]);
			imageIcons.put(imgNames[index], imageIcon);
		}

	}

	public static ImageIcon getImageIcon(String imgName) {
		return imageIcons.get(imgName);
	}

	/**
	 * ��������id���ɲ���������
	 * 
	 * �򵥹���ģʽ
	 * 
	 * @param id
	 *            ���ӵ�id
	 * 
	 * @return ���ɵ�����
	 */
	public static ChessPiece createPiece(PieceId id) {
		if (id == null) {
			return null;
		}

		int pieceWidth = PIECE_WIDTH - 4;
		int pieceHeight = PIECE_HEIGHT - 4;
		switch (id) {
		case HONGJU1:
		case HONGJU2:
			return new ChessPiece(id, RED_NAME, PieceCategory.JU, pieceWidth,
					pieceHeight);

		case HONGMA1:
		case HONGMA2:
			return new ChessPiece(id, RED_NAME, PieceCategory.MA, pieceWidth,
					pieceHeight);

		case HONGPAO1:
		case HONGPAO2:
			return new ChessPiece(id, RED_NAME, PieceCategory.PAO, pieceWidth,
					pieceHeight);

		case HONGXIANG1:
		case HONGXIANG2:

			return new ChessPiece(id, RED_NAME, PieceCategory.HONGXIANG,
					pieceWidth, pieceHeight);

		case HONGSHI1:
		case HONGSHI2:
			return new ChessPiece(id, RED_NAME, PieceCategory.HONGSHI,
					pieceWidth, pieceHeight);

		case SHUAI:
			return new ChessPiece(id, RED_NAME, PieceCategory.SHUAI,
					pieceWidth, pieceHeight);

		case BING1:
		case BING2:
		case BING3:
		case BING4:
		case BING5:
			return new ChessPiece(id, RED_NAME, PieceCategory.BING, pieceWidth,
					pieceHeight);

		case JIANG:
			return new ChessPiece(id, BLACK_NAME, PieceCategory.JIANG,
					pieceWidth, pieceHeight);

		case HEISHI1:
		case HEISHI2:
			return new ChessPiece(id, BLACK_NAME, PieceCategory.HEISHI,
					pieceWidth, pieceHeight);

		case HEIJU1:
		case HEIJU2:
			return new ChessPiece(id, BLACK_NAME, PieceCategory.JU, pieceWidth,
					pieceHeight);

		case HEIPAO1:
		case HEIPAO2:
			return new ChessPiece(id, BLACK_NAME, PieceCategory.PAO,
					pieceWidth, pieceHeight);

		case HEIXIANG1:
		case HEIXIANG2:
			return new ChessPiece(id, BLACK_NAME, PieceCategory.HEIXIANG,
					pieceWidth, pieceHeight);

		case HEIMA1:
		case HEIMA2:
			return new ChessPiece(id, BLACK_NAME, PieceCategory.MA, pieceWidth,
					pieceHeight);

		case ZU1:
		case ZU2:
		case ZU3:
		case ZU4:
		case ZU5:
			return new ChessPiece(id, BLACK_NAME, PieceCategory.ZU, pieceWidth,
					pieceHeight);

		default:
			break;
		}
		return null;
	}

	/**
	 * ����������͵�ת����Stringת��Ϊö�����͵�
	 * 
	 * @param name
	 *            �������String����
	 * @return �������ӵ����ö������
	 */
	public static PieceCategory getPieceCategory(String name) {
		PieceCategory pc = null;

		if (name.equals(��)) {
			pc = PieceCategory.SHUAI;
		} else if (name.equals(܇) || name.equals("��")) {
			pc = PieceCategory.JU;
		} else if (name.equals(�R) || name.equals("��")) {
			pc = PieceCategory.MA;
		} else if (name.equals(��)) {
			pc = PieceCategory.PAO;
		} else if (name.equals(��)) {
			pc = PieceCategory.HONGXIANG;
		} else if (name.equals(��)) {
			pc = PieceCategory.HONGSHI;
		} else if (name.equals(ʿ)) {
			pc = PieceCategory.HEISHI;
		} else if (name.equals(��)) {
			pc = PieceCategory.HEIXIANG;
		} else if (name.equals(��)) {
			pc = PieceCategory.JIANG;
		} else if (name.equals(��)) {
			pc = PieceCategory.BING;
		} else if (name.equals(��)) {
			pc = PieceCategory.ZU;
		}

		return pc;
	}

	/**
	 * ����������͵�ת����ö������ת��ΪString����
	 * 
	 * @param category
	 *            �������ö��g����
	 * @return �������ӵ����String����
	 */
	public static String catetoryToZi(PieceCategory category) {
		String name = null;
		if (category != null) {
			switch (category) {
			case JU:
				name = "܇";
				break;
			case MA:
				name = "�R";
				break;
			case PAO:
				name = "��";
				break;
			case HONGSHI:
				name = "��";
				break;
			case HEISHI:
				name = "ʿ";
				break;
			case HONGXIANG:
				name = "��";
				break;
			case HEIXIANG:
				name = "��";
				break;
			case JIANG:
				name = "��";
				break;
			case SHUAI:
				name = "��";
				break;
			case BING:
				name = "��";
				break;
			case ZU:
				name = "��";
				break;
			}
		}

		return name;
	}

	public static ChessPiece searchPieceById(PieceId id,
			ChessPoint[][] chessPoints) {
		ChessPiece temp = null;
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 10; j++) {
				if (chessPoints[i][j] == null) {
					continue;
				}
				temp = chessPoints[i][j].getPiece();
				if ((temp != null) && (temp.getId() == id)) {
					return temp;
				}
			}
		}
		return temp;

	}

	public static ArrayList<ChessPiece> searchPieceByName(String name,
			ChessPoint[][] chessPoints) {
		ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();
		ChessPiece temp;
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 10; j++) {
				temp = chessPoints[i][j].getPiece();
				if ((temp != null) && (temp.getName().equals(name))) {
					pieces.add(temp);
				}
			}
		}
		return pieces;

	}

	/**
	 * �������ӵ����ࣨ�����䡢���ں�܇)��ȡ���ӵ�λ�ã�
	 * 
	 * @param category
	 *            ���ӵ�����
	 * @return
	 */
	public static Point[] getPositionByCategory(String playerName,
			PieceCategory category, ChessPoint[][] chessPoints) {

		Point[] points = { new Point(), new Point(), new Point(), new Point(),
				new Point() };
		int num = 0;
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 10; j++) {
				ChessPiece piece = null;
				if (chessPoints[i][j].hasPiece()) {
					piece = chessPoints[i][j].getPiece();
					// ������
					if (piece != null) {
						boolean flag = piece.getName().equals(playerName);

						if (flag) {
							PieceCategory category2 = piece.getCategory();
							if (category2.equals(category)) {

								Point position = piece.getPosition();
								if (position != null) {
									/*
									 * points[num] = (new Point((int) position
									 * .getX(), (int) position.getY()));
									 */
									points[num] = (new Point(i, j));
									num++;
								}

							}
						}
					}
				}
			}
		}

		return points;
	}

	/**
	 * ��ȡ����˧�򽫵�λ��
	 * 
	 * @param category
	 * @return
	 */
	public static Point getLeaderPosition(PieceCategory category,
			ChessPoint[][] chessPoints) {
		Point point = null;

		for (int i = 4; i <= 6; i++) {
			for (int j = 8; j <= 10; j++) {
				ChessPiece piece = chessPoints[i][j].getPiece();
				if (piece != null) {
					if (piece.getCategory().equals(category)) {
						/*
						 * Point position = piece.getPosition(); if (position !=
						 * null) { point = new Point((int) position.getX(),
						 * (int) position.getY()); return point; }
						 */
						point = new Point(i, j);
					}
				}
			}
		}

		for (int i = 4; i <= 6; i++) {
			for (int j = 1; j <= 3; j++) {
				ChessPiece piece = chessPoints[i][j].getPiece();
				if (piece != null) {
					if (piece.getCategory().equals(category)) {
						point = new Point(i, j);
					}
				}
			}
		}

		return point;
	}

	/**
	 * ��ӡ���ӵ�λ��
	 * 
	 * @param chessPoints
	 */
	public static void printPieceLoations(ChessPoint[][] chessPoints) {
		System.out.println();
		for (int j = 1; j <= 10; j++) {
			for (int i = 1; i <= 9; i++) {
				ChessPiece piece = chessPoints[i][j].getPiece();
				if (piece != null) {
					System.out.print("(" + i + "," + j + ")��" + piece.getId()
							+ "\t");
				}

			}
			System.out.println();
		}

	}

}
