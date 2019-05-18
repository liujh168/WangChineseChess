
package cn.wang.chinesechess.manmachine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;

import cn.wang.chinesechess.ChessUtil;
import cn.wang.chinesechess.ColorUtil;
import cn.wang.chinesechess.core.ChessBoard;
import cn.wang.chinesechess.core.ChessPiece;
import cn.wang.chinesechess.core.ChessRule;

/**
 * 
 * �˻������õ�����
 * 
 * @author wanghualiang
 */
public class ManMachineBoard extends ChessBoard {

	private static final long serialVersionUID = 1L;

	public JList manualList;

	public JLabel gameStatusContent;

	public JTextArea msgArea;

	public ManMachineGUI boardOwner;// ����ʱ�����̵�ӵ����
	
	public ManMachineBoard() {
		
	}
	public ManMachineBoard(ManMachineGUI board) {
		boardOwner = board;
	}

	/**
	 * ��ʼ���췽���������ӵĿ�ȣ��߶Ⱥ�ǰ��ɫ
	 * 
	 */
	public void initChess(String palyerFirst) {
		playerName = RED_NAME;
		this.palyerFirst = palyerFirst;
		if (palyerFirst.equals(PALYER_FIRST)) {
			// ���������
			if (gameStatusContent != null) {
				gameStatusContent.setText("  " + "���������Ӵ��");
			}

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

		} else {
			if (gameStatusContent != null) {
				gameStatusContent.setText("   " + "�ȴ���������Ӵ��");
			}

			chessPoints[1][10].setPiece(��܇1, this);
			��܇1.setPosition(new Point(1, 10));
			chessPoints[2][10].setPiece(���R1, this);
			���R1.setPosition(new Point(2, 10));
			chessPoints[3][10].setPiece(����1, this);
			����1.setPosition(new Point(3, 10));
			chessPoints[4][10].setPiece(��ʿ1, this);
			��ʿ1.setPosition(new Point(4, 10));
			chessPoints[5][10].setPiece(�ڌ�, this);
			�ڌ�.setPosition(new Point(5, 10));
			chessPoints[6][10].setPiece(��ʿ2, this);
			��ʿ2.setPosition(new Point(6, 10));
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
			chessPoints[1][7].setPiece(����1, this);
			����1.setPosition(new Point(1, 7));
			chessPoints[3][7].setPiece(����2, this);
			����2.setPosition(new Point(3, 7));
			chessPoints[5][7].setPiece(����3, this);
			����3.setPosition(new Point(5, 7));
			chessPoints[7][7].setPiece(����4, this);
			����4.setPosition(new Point(7, 7));
			chessPoints[9][7].setPiece(����5, this);
			����5.setPosition(new Point(9, 7));

			chessPoints[1][1].setPiece(��܇1, this);
			��܇1.setPosition(new Point(1, 1));
			chessPoints[2][1].setPiece(���R1, this);
			���R1.setPosition(new Point(2, 1));
			chessPoints[3][1].setPiece(����1, this);
			����1.setPosition(new Point(3, 1));
			chessPoints[4][1].setPiece(����1, this);
			����1.setPosition(new Point(4, 1));
			chessPoints[5][1].setPiece(�쎛, this);
			�쎛.setPosition(new Point(5, 1));
			chessPoints[6][1].setPiece(����2, this);
			����2.setPosition(new Point(6, 1));
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
			chessPoints[1][4].setPiece(���1, this);
			���1.setPosition(new Point(1, 4));
			chessPoints[3][4].setPiece(���2, this);
			���2.setPosition(new Point(3, 4));
			chessPoints[5][4].setPiece(���3, this);
			���3.setPosition(new Point(5, 4));
			chessPoints[7][4].setPiece(���4, this);
			���4.setPosition(new Point(7, 4));
			chessPoints[9][4].setPiece(���5, this);
			���5.setPosition(new Point(9, 4));
		}

	}

	private void visibleOrNot() {
		if (winkPiece != null) {
			winkPiece.setVisible(true);
		}

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

	private class ManMachineMouseAdapter extends MouseAdapter {
		ManMachineBoard board;

		public ManMachineMouseAdapter(ManMachineBoard board) {
			this.board = board;
		}

		/**
		 * ����ƶ������̻���������
		 */
		public void mouseEntered(MouseEvent e) {
			ChessPiece piece = null;
			// �ֵ��������壬�ƶ���������������
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
			if(boardOwner.isLock||!canPaly){
				return;
			}
			// ��һ��ѡ������
			if (!isSelected) {
				ChessPiece piece = null;
				Rectangle rect = null;

				// ��һ�ε�������̣����Բ���
				if (e.getSource() == this) {
					isSelected = false;
					return;
				}

				// ��һ�ε��������
				if (e.getSource() instanceof ChessPiece) {
					piece = (ChessPiece) e.getSource();
					// ��һ�ε�����巽������
					if (piece.getName().equals(playerName)) {
						System.out.println("��һ��ѡ�����巽������ 282");
						isSelected = true;
						ChessUtil.playSound("select.wav");
						winkPiece = piece;
						needWink = true;
						Point p = getPiecePoint(piece);
						//�õ���������
						startI = p.x;
						startJ = p.y;
						//������ʾ
						board.initTipPoints(piece, startI, startJ);
					}else{
						return;
					}
				}
			}

			// �ڶ��ε��
			else {
				boolean canMove = false;
				int endI = 0, endJ = 0;
				// �ڶ��ε��������
				if (e.getSource() == board) {
					// ��������ת��ΪgamePoints[][]������
					double x1 = e.getX();
					double y1 = e.getY();
					for (int i = 1; i <= X; i++) {
						for (int j = 1; j <= Y; j++) {
							double x0 = chessPoints[i][j].getX();
							double y0 = chessPoints[i][j].getY();

							if ((Math.abs(x0 - x1) <= PIECE_WIDTH / 2)
									&& (Math.abs(y0 - y1) <= PIECE_HEIGHT / 2)) {
								// �յ�����
								endI = i;
								endJ = j;
								break;
							}
						}
					}
					System.out.println("��2�ε������ʱ,�յ�����Ϊ��(" + endI + "," + endJ
							+ ") 330");

					if (endI == 0 || endJ == 0) {
						// ��2�ε��ʱ,û�е�������̷�Χ��
						return;
					}else{
						canMove = ChessRule.allRule(winkPiece, startI, startJ, endI,
								endJ, chessPoints, palyerFirst);
						if(canMove){
							System.out.println("�ƶ����� 339");
							//���һ��������Ϣ��Ҫ���ƶ�֮ǰ��ӣ����򱻳Ե��������޷���¼
							board.addChessRecord(winkPiece, startI,
									startJ, endI, endJ);
							// �������������ƶ�
							movePiece(winkPiece, startI, startJ, endI,
									endJ);
							//�������ϣ���ʼλ��ȥ������
							chessPoints[startI][startJ].setHasPiece(false);
							//Ŀ��λ�ü�������
							chessPoints[endI][endJ].setPiece(winkPiece, board);
							needWink = false;
							winkPiece.setVisible(true);
							isSelected = false;
							ChessUtil.playSound("eat.wav");
							// �޸ĵ�ǰ����
							boardOwner.curIndex++;
							changeSide();
							boardOwner.computerThink();
						}else{
							System.out.println("��2�ε��ʱ,�����������ƶ�����351");
						}
					}
				}

				// �ڶ��ε��������
				else if (e.getSource() instanceof ChessPiece) {
					System.out.println("�ڶ��ε�������� 358");
					ChessPiece piece2 = (ChessPiece) e.getSource();
					Point point = getPiecePoint(piece2);
					endI = point.x;
					endJ = point.y;
					// �ڶ��ε���˱�������
					if (piece2.getName().equals(playerName)) {
						needWink = true;
						winkPiece.setVisible(true);
						winkPiece = piece2;
						startI = endI;
						startJ = endJ;
						isSelected = true;
						ChessUtil.playSound("select.wav");
						//��ʼ�����ӿ�����ʾ
						board.initTipPoints(piece2, startI, startJ);
					}else{//�ڶ��ε���˲�ͬ��������
						canMove = ChessRule.allRule(winkPiece, startI, startJ, endI,
								endJ, chessPoints, palyerFirst);
						if (canMove) {
							ChessPiece pieceRemoved = chessPoints[endI][endJ].getPiece();
							//���һ��������Ϣ��Ҫ���ƶ�֮ǰ��ӣ����򱻳Ե��������޷���¼
							board.addChessRecord(winkPiece, startI,
									startJ, endI, endJ);
							// �������������ƶ�
							movePiece(winkPiece, startI, startJ, endI,
									endJ);
							needWink = false;
							winkPiece.setVisible(true);
							isSelected = false;
							// �������ӱ�������
							ChessUtil.playSound("eat.wav");
							// �����൱ǰ����+1
							boardOwner.curIndex++;
							changeSide();
							if(pieceRemoved.getCategory().equals(PieceCategory.JIANG)){
								canPaly = false;
								boardOwner.gameOver(canPaly,PieceCategory.JIANG);
								ChessUtil.playSound("gamewin.wav");
								return;
							}else if(pieceRemoved.getCategory().equals(PieceCategory.SHUAI)){
								canPaly = false;
								boardOwner.gameOver(canPaly,PieceCategory.SHUAI);
								ChessUtil.playSound("gamewin.wav");
								return;
							}
							boardOwner.computerThink();
						} else {
							return;
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
	protected Color getBackgroundColor() {
		return ColorUtil.getManMachineBgcolor();
	}

	@Override
	protected void drawShuXianFlag(Graphics2D g2) {
		// ��ʾ������
		for (int i = 1; i <= X; i++) {
			g2.drawString("" + i, i * UNIT_WIDTH - 4, UNIT_HEIGHT / 2 - 4);
		}

		for (int i = 1; i <= X; i++) {
			g2.drawString("" + ChessUtil.numToZi(10 - i), i * UNIT_WIDTH - 4,
					10 * UNIT_HEIGHT + 34);
		}
	}

	@Override
	protected MouseAdapter getMouseAdapter() {
		return new ManMachineMouseAdapter(this);
	}

	@Override
	protected BoardType getBoardType() {
		return  BoardType.printWhole;
	}

}
