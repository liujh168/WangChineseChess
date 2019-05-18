package cn.wang.chinesechess.load;

import java.awt.Point;
import java.awt.event.MouseAdapter;

import cn.wang.chinesechess.core.ChessBoard;

public class LoadBoard extends ChessBoard{
	/**boolean �Ƿ�������� */
	public boolean canPaly = false;
	/**String ս���������֣��췽or�ڷ�*/
	protected String playerName;
	/**
	 * �����̳�ʼ�����ӵ�λ��
	 */
	public void initChess(String palyerFirst){
		if(palyerFirst == null || palyerFirst.equals(RED_NAME)){
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

		}else{
			
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
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	protected BoardType getBoardType() {
		// TODO Auto-generated method stub
		return BoardType.printWhole;
	}

	@Override
	protected MouseAdapter getMouseAdapter() {
		// TODO Auto-generated method stub
		return null;
	}

}
