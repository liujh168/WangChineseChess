
package cn.wang.chinesechess.core;

import java.awt.Point;

import cn.wang.chinesechess.config.NAME;

/**
 * ���ӵ㣬�����Ӻ����̹�������
 * 
 * @author wanghualiang
 */
public class ChessPoint implements Cloneable,NAME{

	public static final long serialVersionUID = 261L;

	private int x, y;// ���ӵ������

	private ChessPiece piece = null;// ���ӵ�����

	public ChessPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public ChessPoint(int x, int y, ChessPiece piece, ChessBoard board) {
		this(x, y);
		setPiece(piece, board);
	}

	public ChessPoint() {
	}

	public boolean hasPiece() {
		return piece != null;
	}
	/**
	 *���ø����ӵ�����Ӷ����Ƿ���Ч��hasPiece Ϊfalseʱ�������Ӷ���ָ��null
	 * @param hasPiece
	 */
	public void setHasPiece(boolean hasPiece) {
		// ����Ҫ
		if (!hasPiece) {
			piece = null;
		}
	}

	public Point getPoint() {
		return new Point(x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * 
	 * @param piece
	 *            ��ɾ��������
	 * @param board
	 *            ���̽ӿ�
	 */
	public void setPiece(ChessPiece piece, ChessBoard board) {
		if (piece == null) {
			return;
		}
		if (board == null) {
			return;
		}
		this.piece = piece;
		board.addPiece(piece);
		//ָ�������������ϵ�λ�úʹ�С
		piece.setBounds(x - UNIT_WIDTH / 2, y
				- UNIT_HEIGHT / 2, UNIT_WIDTH,
				UNIT_HEIGHT);
	}

	// ���������̽���
	public void setPiece(ChessPiece piece) {
		this.piece = piece;
	}

	/**
	 * ɾ������
	 * 
	 * @param piece
	 *            ��ɾ��������
	 * @param board
	 *            ���̽ӿ�
	 */
	public void removePiece(ChessPiece piece, ChessBoard board) {
		if (piece == null) {
			return;
		}
		if (board == null) {
			return;
		}
		board.removePiece(piece);
		piece = null;
	}

	public ChessPiece getPiece() {
		return piece;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Object obj = super.clone();
		ChessPoint cp = (ChessPoint)obj;
		if(this.piece!=null){
			cp.piece = (ChessPiece) this.piece.clone();
		}
		return cp;
	}
}
