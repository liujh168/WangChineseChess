
package cn.wang.chinesechess.print.part;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JOptionPane;

import cn.wang.chinesechess.ChessUtil;
import cn.wang.chinesechess.ColorUtil;
import cn.wang.chinesechess.core.ChessBoard;
import cn.wang.chinesechess.core.ChessPiece;
import cn.wang.chinesechess.core.ChessRule;
import cn.wang.chinesechess.core.ManualItem;
import cn.wang.chinesechess.core.MoveStep;

/**
 * ������
 * 
 * @author wanghualiang
 */
public class PrintPartBoard extends ChessBoard {

	private static final long serialVersionUID = 1L;
	
	public JList manualList;

	public PrintPartGUI boardOwner;// ����ʱ�����̵�ӵ����
	/**boolean[][] temp[i][j]=true��Ϊ����[i][j]λ���ϵ���������ʱ��*/
	private boolean[][] temp=new boolean[11][11];
	/** ��˸���������ڵ�λ��*/
	private boolean winkPieceAtBoard = false;

	public PrintPartBoard(PrintPartGUI owner) {
		super();
		boardOwner = owner;
		temp = owner.temp;
		playerName = RED_NAME;// ��ʼ��ʱ���췽
		manualList = chessManual.manualList;

	}

	/**
	 * 
	 * ���������
	 */
	private class PartialMouseAdapter extends MouseAdapter {
		PrintPartBoard board;

		public PartialMouseAdapter(PrintPartBoard board) {
			this.board = board;
		}

		/**
		 * ����ƶ�����������
		 */

		public void mouseEntered(MouseEvent e) {
			if (e.getSource() instanceof ChessPiece) {
				ChessPiece piece = (ChessPiece) e.getSource();
				Point point = isPieceAtBoard(piece);

				if (boardOwner.isLock) {
					// ����Ѿ�����,�����еı��������������
					if (point != null && piece.getName().equals(playerName)) {
						piece.setCursor(handCursor);
					} else {
						piece.setCursor(defaultCursor);
					}

				} else {
					// ���û������,�������Ӷ�����ѡ��,�������
					piece.setCursor(handCursor);
				}
			}
		}

		public void mousePressed(MouseEvent e) {
			if(!boardOwner.canPaly){
				return;
			}
			boardOwner.last();
			if (!isSelected) {
				if(!canPaly){
					return;
				}
				ChessPiece piece = null;
				if (e.getSource() == board) {// ��һ�ε�������̣����Բ���
					isSelected = false;
				}
				if (e.getSource() instanceof ChessPiece) {// ��һ�ε��������
					piece = (ChessPiece) e.getSource();
					winkPieceAtBoard = false;// �ж��������������л��Ǳ������������
					Point point = isPieceAtBoard(piece);
					if (point != null) {
						winkPieceAtBoard = true;
						startI = (int) point.getX();
						startJ = (int) point.getY();
					}
				
					if (winkPieceAtBoard) {//������������
						if (!boardOwner.isLock) {//���̻�δ����
							if(temp[startI][startJ]){//������Ϊ��ʱ����
								isSelected = true;
								ChessUtil.playSound("select.wav");
								winkPiece = piece;needWink = true;
							}
						} else {//����������
							if (piece.getName().equals(playerName)) {
								isSelected = true;
								ChessUtil.playSound("select.wav");
								winkPiece = piece;needWink = true;
								board.initTipPoints(piece, startI, startJ);
							} 
						}
					} 
					else {//�����ڱ��������
						if (!boardOwner.isLock) {//���̻�δ����
							startI = 0;startJ = 0;
							isSelected = true;ChessUtil.playSound("select.wav");
							winkPiece = piece;needWink = true;	
							winkPieceAtBoard = false;
						} 
					}
				}
			}
			else {// �ڶ��ε��
				boolean canMove = true;
				ChessPiece pieceRemoved = null;
				int endI = 0, endJ = 0;
				if (e.getSource() == board) {// �ڶ��ε��������
					double x1 = e.getX();
					double y1 = e.getY();
					for (int i = 1; i <= X; i++) {
						for (int j = 1; j <= Y; j++) {//����ĵ�����ת��Ϊ���ӵ�����
							double x0 = chessPoints[i][j].getX();
							double y0 = chessPoints[i][j].getY();
							if ((Math.abs(x0 - x1) <= PIECE_WIDTH / 2)
									&& (Math.abs(y0 - y1) <= PIECE_HEIGHT / 2)) {
								endI = i;endJ = j;// �յ�����
								break;
							}
						}
					}
					if (endI == 0 || endJ == 0) {// û�е����������Ч��Χ�ڣ�ɾ������
						if (!boardOwner.isLock) {
							if(temp[startI][startJ]){
								board.remove(winkPiece);
								boardOwner.piecesPanel.add(winkPiece);
								needWink = false;
								winkPiece.setVisible(true);
								isSelected = false;
								temp[startI][startJ] = false;//����ʱ���ӱ�ʶȥ��
								if (winkPieceAtBoard) {
									chessPoints[startI][startJ].setHasPiece(false);
								}
							}
						}
					}
					
					else {// �ڶ��ε����������Ч��Χ��
						if(!boardOwner.isLock){//����δ����
							if(temp[startI][startJ]){//֮ǰ���������Ϊ��ʱ����
								canMove = ChessRule.partialRule1(winkPiece, startI,
										startJ, endI, endJ, chessPoints);
								if (canMove) {//���������ù��򣬽�����е����ӷ�����������
									temp[startI][startJ] = false;//����ʱ���ӵ�λ�øı�
									temp[endI][endJ] = true;
									winkPiece.setPosition(new Point(endI, endJ));
									movePiece(winkPiece, startI, startJ, endI, endJ);
									needWink = false;winkPiece.setVisible(true);
									isSelected = false;ChessUtil.playSound("eat.wav");
								}
							}else{//֮ǰ��������Ӳ�Ϊ��ʱ����
								if(!winkPieceAtBoard){//֮ǰ��������Ӳ�����������
									canMove = ChessRule.partialRule1(winkPiece, startI,
											startJ, endI, endJ, chessPoints);
									if (canMove) {//���������ù��򣬽�����е����ӷ�����������
										winkPiece.setPosition(new Point(endI, endJ));
										chessPoints[endI][endJ].setPiece(winkPiece, board);
										needWink = false;isSelected = false;
										ChessUtil.playSound("eat.wav");
										boardOwner.piecesPanel.remove(winkPiece);
										boardOwner.piecesPanel.validate();
										winkPiece.setVisible(true);
										temp[endI][endJ]=true;//��֮ǰ��������ӱ��Ϊ��ʱ����
									}
								}
							}
							
						}else{//�����Ѿ�����
							if(winkPieceAtBoard){//֮ǰ�����������������
								canMove = ChessRule.partialRule(winkPiece, startI,
										startJ, endI, endJ, chessPoints, null);
								if(canMove){
									board.addChessRecord(winkPiece, startI,startJ, endI, endJ);
									movePiece(winkPiece, startI, startJ, endI,endJ);
									chessPoints[startI][startJ].setHasPiece(false);
									chessPoints[endI][endJ].setPiece(winkPiece, board);
									needWink = false;winkPiece.setVisible(true);
									isSelected = false;ChessUtil.playSound("eat.wav");
									boardOwner.curIndex++;changeSide();
								}
							}	
						}
					}
				}

				
				else if (e.getSource() instanceof ChessPiece) {// �ڶ��ε��������
					ChessPiece piece = (ChessPiece) e.getSource();
					boolean flag = false;// �ڶ��ε���ĶԷ������Ƿ���������
					for (int i = 1; i <= X; i++) {
						for (int j = 1; j <= Y; j++) {
							ChessPiece tempPiece = chessPoints[i][j].getPiece();
							if (chessPoints[i][j].hasPiece()&& tempPiece.equals(piece)) {
								endI = i;endJ = j;flag = true;break;
							}
						}
					}
					if (!flag) {// ��2�ε��������е�����
						if (!boardOwner.isLock) {
							startI = 0;startJ = 0;
							winkPiece.setVisible(true);
							ChessUtil.playSound("select.wav");
							winkPiece = piece;needWink = true;
							isSelected = true;winkPieceAtBoard = false;
						} 
					} else {//������������
						if(boardOwner.isLock){
							// �ڶ��ε����������ͬһ�������ӣ�����ѡ�е�����
							if (piece.getName().equals(winkPiece.getName())) {
								winkPiece.setVisible(true);winkPiece = piece;
								needWink = true;startI = endI;startJ = endJ;
								isSelected = true;winkPieceAtBoard = true;
								ChessUtil.playSound("select.wav");tipPoints.clear();
								board.initTipPoints(piece, startI, startJ);
							}else{//�ڶ��ε���������в�ͬ�������ӣ��Ե�ѡ�е�����
								canMove = ChessRule.partialRule(winkPiece, startI, startJ,
												endI, endJ, chessPoints, null);
								if(canMove){
									pieceRemoved = chessPoints[endI][endJ].getPiece();
									board.addChessRecord(winkPiece, startI,startJ, endI, endJ);
									movePiece(winkPiece, startI, startJ, endI,endJ);
									boardOwner.piecesPanel.add(pieceRemoved);//�����Ե����������·Ż������
									needWink = false;winkPiece.setVisible(true);
									isSelected = false;ChessUtil.playSound("eat.wav");
									tipPoints.clear();boardOwner.curIndex++;changeSide();
									if(pieceRemoved.getCategory().equals(PieceCategory.JIANG)){
										canPaly = false;boardOwner.gameOver(canPaly,PieceCategory.JIANG);
									}else if(pieceRemoved.getCategory().equals(PieceCategory.SHUAI)){
										canPaly = false;boardOwner.gameOver(canPaly,PieceCategory.SHUAI);
									}	
								}	
							}
						}
						else{//����δ����
							if(temp[endI][endJ]){//���������Ϊ��ʱ����
								//�����ǰ���������������У���ֹ����˸ʹ��֮ǰ�����Ӳ��ɼ�
								winkPiece.setVisible(true);
								ChessUtil.playSound("select.wav");
								winkPiece = piece;
								needWink = true;
								isSelected = true;
								winkPieceAtBoard = true;
							}
						}
					}
				}
			}
			validateAll();
		}
	}

	/**
	 * ��ڷ�����
	 * 
	 */
	public void changeSide() {
		if (playerName.equals(RED_NAME)) {
			playerName = BLACK_NAME;
		} else if (playerName.equals(BLACK_NAME)) {
			playerName = RED_NAME;
		}
	}

	public void validateAll() {
		System.out.println("ˢ�£�");
		validate();
		repaint();
		boardOwner.validate();
	}

	/**
	 * �ж�һ�������Ƿ���������,
	 * 
	 * @param piece
	 *            ����
	 * @return ����ڷ����������ڵ����꣬���򷵻�null
	 */
	private Point isPieceAtBoard(ChessPiece piece) {
		return getPiecePoint(piece);
	}

	@Override
	protected Color getBackgroundColor() {
		return ColorUtil.getDefaultBgcolor();
	}

	@Override
	protected MouseAdapter getMouseAdapter() {
		return new PartialMouseAdapter(this);
	}

	@Override
	protected BoardType getBoardType() {
		return BoardType.printPartial;
	}

}
