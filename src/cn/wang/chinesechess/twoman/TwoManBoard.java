package cn.wang.chinesechess.twoman;


import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import cn.wang.chinesechess.ChessUtil;
import cn.wang.chinesechess.ColorUtil;
import cn.wang.chinesechess.core.ChessBoard;
import cn.wang.chinesechess.core.ChessPiece;
import cn.wang.chinesechess.core.ChessRule;
import cn.wang.chinesechess.print.all.PrintAllBoard;
import cn.wang.chinesechess.print.all.PrintAllGUI;

/**
 * ˫�˶�ս����
 * @author wanghualiang
 *
 */
public class TwoManBoard extends ChessBoard{
	private static final long serialVersionUID = 1L;
	/**private TwoManGUI ����ӵ����*/
	private TwoManGUI boardOwner;
	public TwoManBoard(TwoManGUI boardOwner) {
		this.boardOwner = boardOwner;
	}
	/**
	 * ��ʼ���췽���������ӵĿ�ȣ��߶Ⱥ�ǰ��ɫ
	 * Ĭ�Ϻ췽������
	 * 
	 */
	public void initChess() {
		playerName = RED_NAME;
		chessPoints[1][10].setPiece(��܇1, this);
		��܇1.setPosition(new Point(1, 10));
		chessPoints[2][10].setPiece(���R1, this);
		���R1.setPosition(new Point(2, 10));
		chessPoints[3][10].setPiece(����1, this);
		����1.setPosition(new Point(3, 10));
		chessPoints[4][10].setPiece(����1, this);
		����1.setPosition(new Point(4, 10));
		chessPoints[5][10].setPiece(�쎛, this);
		�쎛.setPosition(new Point(5, 10));
		chessPoints[6][10].setPiece(����2, this);
		����2.setPosition(new Point(6, 10));
		chessPoints[7][10].setPiece(����2, this);
		����2.setPosition(new Point(7, 10));
		chessPoints[8][10].setPiece(���R2, this);
		���R2.setPosition(new Point(8, 10));
		chessPoints[9][10].setPiece(��܇2, this);
		��܇2.setPosition(new Point(9, 10));
		chessPoints[2][8].setPiece(����1, this);
		����1.setPosition(new Point(2, 8));
		chessPoints[8][8].setPiece(����2, this);
		����2.setPosition(new Point(8, 8));
		chessPoints[1][7].setPiece(���1, this);
		���1.setPosition(new Point(1, 7));
		chessPoints[3][7].setPiece(���2, this);
		���2.setPosition(new Point(3, 7));
		chessPoints[5][7].setPiece(���3, this);
		���3.setPosition(new Point(5, 7));
		chessPoints[7][7].setPiece(���4, this);
		���4.setPosition(new Point(7, 7));
		chessPoints[9][7].setPiece(���5, this);
		���5.setPosition(new Point(9, 7));

		chessPoints[1][1].setPiece(��܇1, this);
		��܇1.setPosition(new Point(1, 1));
		chessPoints[2][1].setPiece(���R1, this);
		���R1.setPosition(new Point(2, 1));
		chessPoints[3][1].setPiece(����1, this);
		����1.setPosition(new Point(3, 1));
		chessPoints[4][1].setPiece(��ʿ1, this);
		��ʿ1.setPosition(new Point(4, 1));
		chessPoints[5][1].setPiece(�ڌ�, this);
		�ڌ�.setPosition(new Point(5, 1));
		chessPoints[6][1].setPiece(��ʿ2, this);
		��ʿ2.setPosition(new Point(6, 1));
		chessPoints[7][1].setPiece(����2, this);
		����2.setPosition(new Point(7, 1));
		chessPoints[8][1].setPiece(���R2, this);
		���R2.setPosition(new Point(8, 1));
		chessPoints[9][1].setPiece(��܇2, this);
		��܇2.setPosition(new Point(9, 1));
		chessPoints[2][3].setPiece(����1, this);
		����1.setPosition(new Point(2, 3));
		chessPoints[8][3].setPiece(����2, this);
		����2.setPosition(new Point(8, 3));
		chessPoints[1][4].setPiece(����1, this);
		����1.setPosition(new Point(1, 4));
		chessPoints[3][4].setPiece(����2, this);
		����2.setPosition(new Point(3, 4));
		chessPoints[5][4].setPiece(����3, this);
		����3.setPosition(new Point(5, 4));
		chessPoints[7][4].setPiece(����4, this);
		����4.setPosition(new Point(7, 4));
		chessPoints[9][4].setPiece(����5, this);
		����5.setPosition(new Point(9, 4));
	}
	/**
	 * ��ڷ�����
	 * 
	 */
	public void changeSide() {
		if (playerName.equals(RED_NAME)) {
			playerName = BLACK_NAME;
			boardOwner.updateGameStatus(2, "�ֵ��ڷ���ඣ�");

		} else {
			playerName = RED_NAME;
			boardOwner.updateGameStatus(1, "�ֵ��췽��ඣ�");
		}
	}
	/**
	 * ˫�˶�ս�����������
	 * 
	 * @author ����
	 * 
	 */
	private class TwoManBoardMouseAdapter extends MouseAdapter {
		TwoManBoard board;

		public TwoManBoardMouseAdapter(TwoManBoard board) {
			this.board = board;
		}

		/**
		 * ����ƶ������̻���������
		 */
		public void mouseEntered(MouseEvent e) {
			ChessPiece piece = null;
			// �������ƶ���������������,�����ó����ι�ꣻ
			// �������ó�Ĭ�Ϲ��
			if (e.getSource() instanceof ChessPiece) {
				piece = (ChessPiece) e.getSource();
				if (piece.getName().equals(playerName)) {
					piece.setCursor(handCursor);
				} else {
					piece.setCursor(defaultCursor);
				}
			}
		}
		
		/**
		 * �������
		 */
		public void mousePressed(MouseEvent e) {
			if(!boardOwner.canPaly){return;}//��Ϸ����
			boardOwner.last();// �������б���ƶ������һ��������
			if (!isSelected) {//Ŀǰѡ�������ӣ���Ϊ��һ�ε��
				ChessPiece piece = null;
				if (e.getSource() == this) {// ��һ�ε�������̣����Բ���
					isSelected = false;return;
				}
				if (e.getSource() instanceof ChessPiece) {// ��һ�ε��������
					piece = (ChessPiece) e.getSource();
					if (piece.getName().equals(playerName)) {// ��һ�ε�����巽������
						isSelected = true;ChessUtil.playSound("select.wav");
						winkPiece = piece;needWink = true;
						Point p = getPiecePoint(piece);
						startI = p.x;startJ = p.y;
						board.initTipPoints(piece, startI, startJ);
					}
				}
			}
			else {// Ŀǰѡ�����ӣ��˴μ�Ϊ�ڶ��ε��
				boolean canMove = false;int endI = 0, endJ = 0;
				if (e.getSource() == board) {// �ڶ��ε��������
					double x1 = e.getX();double y1 = e.getY();
					for (int i = 1; i <= X; i++) {
						for (int j = 1; j <= Y; j++) {
							double x0 = chessPoints[i][j].getX();
							double y0 = chessPoints[i][j].getY();
							if ((Math.abs(x0 - x1) <= PIECE_WIDTH / 2)
									&& (Math.abs(y0 - y1) <= PIECE_HEIGHT / 2)) {
								endI = i;endJ = j;// �յ�����
								break;
							}
						}
					}
					if (endI == 0 || endJ == 0) {// ��2�ε��ʱ,û�е�������̷�Χ��
						return;
					}else{//��2�ε��ʱ,��������̷�Χ��
						canMove = ChessRule.allRule(winkPiece, startI, startJ, endI,
								endJ, chessPoints, null);
						if(canMove){
							board.addChessRecord(winkPiece, startI,
									startJ, endI, endJ);//���һ��������Ϣ��Ҫ���ƶ�֮ǰ��ӣ����򱻳Ե��������޷���¼
							movePiece(winkPiece, startI, startJ, endI,
									endJ);// �������������ƶ�
							chessPoints[startI][startJ].setHasPiece(false);//�������ϣ���ʼλ��ȥ������
							chessPoints[endI][endJ].setPiece(winkPiece, board);//Ŀ��λ�ü�������
							needWink = false;winkPiece.setVisible(true);isSelected = false;
							boardOwner.curIndex++;// �޸ĵ�ǰ����
							ChessUtil.playSound("eat.wav");changeSide();
						}
					}
				}
				else if (e.getSource() instanceof ChessPiece) {// �ڶ��ε��������
					ChessPiece piece2 = (ChessPiece) e.getSource();
					Point point = getPiecePoint(piece2);
					endI = point.x;endJ = point.y;
					if (piece2.getName().equals(playerName)) {// �ڶ��ε���˱�������
						needWink = true;winkPiece.setVisible(true);winkPiece = piece2;
						startI = endI;startJ = endJ;isSelected = true;
						ChessUtil.playSound("select.wav");
						board.clearTipPoints();board.initTipPoints(piece2, startI, startJ);
					}else{//�ڶ��ε���˲�ͬ��������
						canMove = ChessRule.allRule(winkPiece, startI, startJ, endI,
								endJ, chessPoints, null);
						if (canMove) {
							ChessPiece pieceRemoved = chessPoints[endI][endJ].getPiece();
							board.addChessRecord(winkPiece, startI,
									startJ, endI, endJ);
							movePiece(winkPiece, startI, startJ, endI,
									endJ);
							needWink = false;winkPiece.setVisible(true);isSelected = false;
							ChessUtil.playSound("eat.wav");boardOwner.curIndex++;changeSide();
							if(pieceRemoved.getCategory().equals(PieceCategory.JIANG)){
								canPaly = false;boardOwner.gameOver(canPaly,PieceCategory.JIANG);
							}else if(pieceRemoved.getCategory().equals(PieceCategory.SHUAI)){
								ChessUtil.playSound("jiangjun2.wav");canPaly = false;
								boardOwner.gameOver(canPaly,PieceCategory.SHUAI);
							}else{
								ChessUtil.playSound("eat.wav");
							}
						} 
					}	
				}
			}
			validateAll();
		}
	}
	public void validateAll() {
		System.out.println("ˢ�£�");
		validate();
		repaint();
		boardOwner.validate();
	}
	

	@Override
	protected BoardType getBoardType() {
		return BoardType.twoman;
	}
	@Override
	protected MouseAdapter getMouseAdapter() {
		return new TwoManBoardMouseAdapter(this);
	}
	
}
