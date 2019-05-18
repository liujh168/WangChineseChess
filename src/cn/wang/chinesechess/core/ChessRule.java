
package cn.wang.chinesechess.core;

import java.awt.Point;

import cn.wang.chinesechess.config.NAME;

/**
 * ��Ϸ���򹤾���
 * 
 * @author wanghualiang
 */
public final class ChessRule implements NAME {

	private ChessRule() {

	}
	/**
	 * ��ȫ��ʱ�õ������ƶ�����
	 * 
	 * @param piece
	 *            ��Ҫ�ƶ�������
	 * @param startX
	 *            ��ѧ�����ϵ� ��������
	 * @param startY
	 *            ��ѧ�����ϵ� ���������
	 * @param endX
	 *            ��ѧ�����ϵ� �յ������
	 * @param endY
	 *            ��ѧ�����ϵ� �յ�������
	 * @param chessPoints
	 *            �����������̵����ӵ�
	 * @return �Ϸ�����true�����Ϸ�����false
	 */
	public static boolean allRule(ChessPiece piece, int startX, int startY,
			int endX, int endY, ChessPoint[][] chessPoints, String palyFirst) {
		return partialRule(piece, startX, startY, endX, endY, chessPoints, palyFirst);
	}

	/**
	 * �߼�����ʱ�õ������ƶ�����
	 * 
	 * @param piece
	 *            ��Ҫ�ƶ�������
	 * @param startX
	 *            ��ѧ�����ϵ� ��������
	 * @param startY
	 *            ��ѧ�����ϵ� ���������
	 * @param endX
	 *            ��ѧ�����ϵ� �յ������
	 * @param endY
	 *            ��ѧ�����ϵ� �յ�������
	 * @param chessPoints
	 *            �����������̵����ӵ�
	 * @param palyFirst
	 * 			�������ַ�����һ����
	 * @return �Ϸ�����true�����Ϸ�����false
	 */
	public static boolean partialRule(ChessPiece piece, int startX, int startY,
			int endX, int endY, ChessPoint[][] chessPoints, String palyFirst) {

		// �����յ�Ϊͬһ�����ӣ������ƶ�
		if (isSameColor(chessPoints, startX, startY, endX, endY)) {
			return false;
		}
		// �Ƿ�����ƶ�
		boolean canMove = false;

		// ���ӵ����
		PieceCategory category = piece.getCategory();
		// ����܇���R���ڵ��ƶ�����
		if (category.equals(PieceCategory.JU)
				|| category.equals(PieceCategory.MA)
				|| category.equals(PieceCategory.PAO)) {
			if(jmpRule(category, startX, startY, endX, endY, chessPoints)){
				canMove = true;
			}
			//System.out.println("�ƶ�����܇���R���� 335");
		}

		// ��Ĺ��� �����������������ñ仯����ֵ������2
		else if (category.equals(PieceCategory.HEIXIANG)
				|| category.equals(PieceCategory.HONGXIANG)) {
			//System.out.println("�ƶ������� 341");
			int centerI = (startX + endX) / 2;
			int centerJ = (startY + endY) / 2;
			int xAxle = Math.abs(startX - endX);
			int yAxle = Math.abs(startY - endY);

			if (xAxle == 2 && yAxle == 2) {
				// ������
				if (!chessPoints[centerI][centerJ].hasPiece()) {
					if((startY<=5&&endY<=5)||(startY>=6&&endY>=6)){
						canMove = true;
					}
				}
			} 
		}

		// �����ƶ�����
		else if (category.equals(PieceCategory.BING)) {
			boolean flag;
			boolean flag2;
			if(palyFirst == null || palyFirst.equals(PALYER_FIRST)){
				flag = endY >= 6? true: false;
				flag2 = startY - endY == 1? true: false;
			}else{
				flag = endY <= 5? true: false;
				flag2 = endY - startY == 1? true: false;
			}
			int xAxle = Math.abs(startX - endX);
			// ��û�й��� ֻ��ǰ��
			if (flag) {
				if (flag2 && xAxle == 0) {
					canMove = true;
				}
			}
			// �������ˣ�����ǰ����ƽ��
			else {
				if (flag2 && (xAxle == 0)) {
					canMove = true;
				} else if ((startY - endY == 0) && (xAxle == 1)) {
					canMove = true;
				} 
			}
			
//			System.out.println("�ƶ����Ǳ� 341");
		}
		//����ƶ�����
		else if(category.equals(PieceCategory.ZU)){
			boolean flag;
			boolean flag2;
			if(palyFirst == null || palyFirst.equals(PALYER_FIRST)){
				flag = endY <= 5? true: false;
				flag2 = endY - startY == 1? true: false;
			}else{
				flag = endY >= 6? true: false;
				flag2 = startY - endY == 1? true: false;
			}
			int xAxle = Math.abs(startX - endX);
			
			// ��û�й��ӣ�ֻ��ǰ��
			if(flag){
				if(flag2 && (xAxle == 0)){
					canMove = true;
				}
			}
			// ������ˣ�����ǰ����ƽ��
			else{
				if (flag2 && (xAxle == 0)) {
					canMove = true;
				}else if ((endY - startY == 0) && (xAxle == 1)) {
					canMove = true;
				} 
			}
//			System.out.println("�ƶ����Ǳ� 367");
		}
		/*
		 * ʿ�Ĺ�����һ����Χ�ڣ�������������
		 * 
		 * ʿ��б
		 */
		else if (category.equals(PieceCategory.HEISHI)
				|| category.equals(PieceCategory.HONGSHI)) {
			int xAxle = Math.abs(startX - endX);
			int yAxle = Math.abs(startY - endY);
			if(xAxle == 1 && yAxle == 1){
				if(endX>=4&&endX<=6){
					if((endY>=1&&endY<=3)||(endY>=8&&endY<=10)){
						canMove = true;
					}
				}
			}
			//System.out.println("�ƶ�����ʿ 394");
		}

		/*
		 * ˧�ͽ��Ĺ�����һ����Χ�ڣ�������������
		 * 
		 * 4<=x<=6,8<=y<=10 һ��һ����ֱ��
		 */
		else if ((category.equals(PieceCategory.SHUAI))
				|| (category.equals(PieceCategory.JIANG))) {
			int xAxle = Math.abs(startX - endX);
			int yAxle = Math.abs(startY - endY);
			// ���������������
			if (endX <= 6 && endX >= 4) {
				if((endY>=1&&endY<=3)||(endY>=8&&endY<=10)){
					// һ��ֻ���ƶ�һ��
					if ((xAxle == 1 && yAxle == 0) || (xAxle == 0 && yAxle == 1)){
						canMove = true;
					}
				}
			}
			//System.out.println("�ƶ����ǽ�˧  413");
		}
		
		//��⽫˧�Ƿ����
		if(palyFirst == null || palyFirst.equals(PALYER_FIRST)){
			if(ChessRule.isMeeting(piece, startX, startY, endX, endY, chessPoints)){
				canMove = false; 
			}
		}else{
			if(ChessRule.isMeeting2(piece, startX, startY, endX, endY, chessPoints)){
				canMove = false; 
			}
		}
		
		return canMove;
	}
	/**
	 * �оִ�������δ����ʱ���ӷ��õĹ���
	 * 1.˧�����������ˣ���ʿ�������ࣨ���󣩣���������䣩���õ�λ����Ҫ��
	 * 2.܇���R���ڿ�����������ڿ��е�λ����
	 * @param piece ���������Ӷ���
	 * @param startX ��ʼλ�ú�����
	 * @param startY ��ʼλ��������
	 * @param endX Ŀ��λ�ú�����
	 * @param endY Ŀ��λ��������
	 * @param chessPoints ���������е����Ӽ���
	 * @return
	 */
	public static boolean partialRule1(ChessPiece piece, int startX, int startY,
			int endX, int endY, ChessPoint[][] chessPoints) {
		// �����յ�Ϊͬһ�����ӣ������ƶ�
				if (isSameColor(chessPoints, startX, startY, endX, endY)) {
					System.out.println("�����յ�Ϊͬһ�����ӣ������ƶ� 478");
					return false;
				}

				System.out.println("startX=" + startX + " startY= " + startY
						+ " endX= " + endX + " endY= " + endY);
				PieceCategory category = piece.getCategory();

				/*// �ӱ�����������ѡ�����ӵ�������,ֻ�ܽ��ˡ��ࡢ�����ںϷ���λ��
				if (startX == 0 && startY == 0) {
*/
					// �ˡ��ࡢ�����յ�λ��������
					if (category.equals(PieceCategory.HONGSHI)) {
						if (endX == 4 || endX == 6) {
							if (endY == 8 || endY == 10) {
								return true;
							}
						} else if (endX == 5 && endY == 9) {
							return true;
						}
						return false;
					}

					else if (category.equals(PieceCategory.HEISHI)) {
						if (endX == 4 || endX == 6) {
							if (endY == 1 || endY == 3) {
								return true;
							}
						} else if (endX == 5 && endY == 2) {
							return true;
						}
						return false;
					}

					else if (category.equals(PieceCategory.HEIXIANG)) {
						if (endX == 1 || endX == 5 || endX == 9) {
							if (endY == 3) {
								return true;
							}
						} else if (endX == 3 || endX == 7) {
							if (endY == 1 || endY == 5) {
								return true;
							}
						}
						return false;
					}

					else if (category.equals(PieceCategory.HONGXIANG)) {
						if (endX == 1 || endX == 5 || endX == 9) {
							if (endY == 8) {
								return true;
							}
						} else if (endX == 3 || endX == 7) {
							if (endY == 6 || endY == 10) {
								return true;
							}
						}
						return false;
					}

					else if (category.equals(PieceCategory.JIANG)) {
						if (endX >= 4 && endX <= 6) {
							if (endY >= 1 && endY <= 3) {
								// �Ͻ�����
								if (ChessRule.isDuiLian(piece, endX, endY, chessPoints)) {
									return false;
								}
								return true;
							}
						}
						return false;
					}

					else if (category.equals(PieceCategory.SHUAI)) {
						if (endX >= 4 && endX <= 6) {
							if (endY >= 8 && endY <= 10) {
								// �Ͻ�����
								if (ChessRule.isDuiLian(piece, endX, endY, chessPoints)) {
									return false;
								}
								return true;
							}
						}
						return false;
					}

					// �����յ�λ��������
					else if (category.equals(PieceCategory.BING)) {
						if (endY == 6 || endY == 7) {
							if (endX == 1 || endX == 3 || endX == 5 || endX == 7
									|| endX == 9) {
								return true;
							}
							return false;
						} else if (endY < 6) {
							return true;
						} else {
							return false;
						}

					}
					// ����յ�λ��������
					else if (category.equals(PieceCategory.ZU)) {
						if (endY == 4 || endY == 5) {
							if (endX == 1 || endX == 3 || endX == 5 || endX == 7
									|| endX == 9) {
								return true;
							}
							return false;
						} else if (endY > 5) {
							return true;
						} else {
							return false;
						}

					}

					else {
						// ܇���R���ڿ��Է����κ�λ��
						return true;
					}
				
	}
	/**
	 * ܇���R���ڵ��ƶ������������ƶ�ʱͨ�õĹ���
	 * 
	 * ����3��
	 * 
	 * @param startX
	 *            ��ѧ�����ϵ� ��������
	 * @param startY
	 *            ��ѧ�����ϵ� ���������
	 * @param endX
	 *            ��ѧ�����ϵ� �յ������
	 * @param endY
	 *            ��ѧ�����ϵ� �յ�������
	 * @param chessPoints
	 *            �����������̵����ӵ�
	 * @return �Ϸ�����true�����Ϸ�����false
	 */
	public static boolean jmpRule(PieceCategory category, int startX,
			int startY, int endX, int endY, ChessPoint[][] chessPoints) {
		/*
		 * System.out.println("܇�R���Ƿ񽫾���(" + startX + "," + startY + "),(" + endX
		 * + "," + endY + ")");
		 */
		int minX = Math.min(startX, endX);
		int maxX = Math.max(startX, endX);
		int minY = Math.min(startY, endY);
		int maxY = Math.max(startY, endY);

		// ܇�Ĺ���܇��ֱ
		if (category.equals(PieceCategory.JU)) {
			// ������
			if (startX == endX) {
				int j = 0;
				for (j = minY + 1; j <= maxY - 1; j++) {
					// �м䲻��������
					if (chessPoints[startX][j].hasPiece()) {
						return false;
					}
				}
				if (j == maxY) {
					return true;
				}
			}
			// ������
			else if (startY == endY) {
				int i = 0;
				for (i = minX + 1; i <= maxX - 1; i++) {
					if (chessPoints[i][startY].hasPiece()) {
						return false;
					}
				}
				if (i == maxX) {
					return true;
				}
			} else {
				return false;
			}
		}

		// ��Ĺ�����̤��,��������4�����
		else if (category.equals(PieceCategory.MA)) {
			int xAxle = Math.abs(startX - endX);
			int yAxle = Math.abs(startY - endY);

			// X�����ƶ�2����Y�����ƶ�1��
			if (xAxle == 2 && yAxle == 1) {
				if (endX > startX) {
					// ������
					if (chessPoints[startX + 1][startY].hasPiece()) {
						return false;
					}
					return true;
				}
				if (endX < startX) {
					// ������
					if (chessPoints[startX - 1][startY].hasPiece()) {
						return false;
					}
					return true;
				}

			}

			// X�����ƶ�1����Y�����ƶ�2��
			else if (xAxle == 1 && yAxle == 2) {
				if (endY > startY) {
					// ������
					if (chessPoints[startX][startY + 1].hasPiece()) {
						return false;
					}
					return true;
				}
				if (endY < startY) {
					// ������
					if (chessPoints[startX][startY - 1].hasPiece()) {
						return false;
					}
					return true;
				}

			} else {
				return false;
			}
		}

		// �ڵĹ���
		else if (category.equals(PieceCategory.PAO)) {
			// ������ӣ��м�ֻ�ܸ���һ������
			int number = 0;
			// ��ֱ�����ƶ�
			if (startX == endX) {
				int j = 0;
				for (j = minY + 1; j <= maxY - 1; j++) {
					if (chessPoints[startX][j].hasPiece()) {
						number++;
					}
				}

				// �м�û�����ӣ����ܳ���
				if (number == 0 && !chessPoints[endX][endY].hasPiece()) {
					return true;
				}

				// �м���һ�����ӣ������һ����
				else if (number == 1) {
					if (chessPoints[endX][endY].hasPiece()) {
						return true;
					}
				}

				// �м����һ�����ӣ������ƶ�
				else if (number > 1) {
					return false;
				}

			}

			// ˮƽ�����ƶ�
			else if (startY == endY) {
				int i = 0;
				for (i = minX + 1; i <= maxX - 1; i++) {
					if (chessPoints[i][startY].hasPiece()) {
						number++;
					}
				}

				if (number > 1) {
					return false;
				} else if (number == 1) {
					if (chessPoints[endX][endY].hasPiece()) {
						return true;
					}
				}

				else if (number == 0 && !chessPoints[endX][endY].hasPiece()) {
					return true;
				}
			} else {
				return false;
			}
		}
		return false;

	}



	/**
	 * �ж������յ㴦��2�������Ƿ�Ϊͬһ����
	 * 
	 * @param chessPoints
	 *            ���ӵ㼯
	 * @param startX
	 *            ��������
	 * @param startY
	 *            ���������
	 * @param endX
	 *            �յ������
	 * @param endY
	 *            ���������
	 * @return ͬһ���򷵻�true�����򷵻�false��
	 */
	private static boolean isSameColor(ChessPoint[][] chessPoints, int startX,
			int startY, int endX, int endY) {

		if (startX < 1 || startX > 9 || startY < 1 || startY > 10) {
			return false;
		}

		if (endX < 1 || endX > 10 || endY < 1 || endY > 10) {
			return false;
		}
		
		 /* System.out.println("�Ƿ�Ϊͬһ������:" + startX + "," + startY + "," + endX +
		  "," + endY+"  1051");*/
		ChessPiece startPiece = chessPoints[startX][startY].getPiece();
		ChessPiece endPiece = chessPoints[endX][endY].getPiece();

		if (startPiece != null && endPiece != null) {
			String startPlayerName = startPiece.getName();
			String endPlayerName = endPiece.getName();
			if (startPlayerName.equals(endPlayerName)) {
				/*
				 * System.out.println("�Ƿ�Ϊͬһ�����ӣ�" + startPiece.getCategory() +
				 * "" + startX + "," + startY + "," + endX + "," + endY);
				 */
				//System.out.println("��ͬһ������: 1063");
				return true;
			}
		}
		//System.out.println("����ͬһ������: 1067");
		return false;
	}


	/**
	 * ��x�̶�������£�y1---y2֮����û�����ӣ����ڽ�˧�������
	 * @param x �̶��ĺ�����
	 * @param y1 ������
	 * @param y2 ������
	 * @param chessPoints ���ӵ��ά����
	 * @return �����ӷ���true �����򷵻�false
	 */
	public static boolean sectionHavePiece(int x,int y1,int y2,ChessPoint[][] chessPoints){
		for(int i=y1;i<=y2;i++){
			if(chessPoints[x][i].getPiece()!=null){
				return true;
			}
		}
		return false;
	}
	/**
	 * �����ߺ��жϽ�˧�Ƿ���� �췽�������°벿��
	 * @param piece �ƶ�������
	 * @param startX ��ʼ������
	 * @param startY ��ʼ������
	 * @param endX  �յ������
	 * @param endY �յ�������
	 * @param chessPoints ���ӵ��ά����
	 * @return �����˧����������true�����򣬷���false��
	 */
	public static boolean isMeeting(ChessPiece piece,int startX ,int startY, int endX, int endY,
			ChessPoint[][] chessPoints){
		boolean canMove = true;
		PieceCategory pc = piece.getCategory();
		if(pc.equals(PieceCategory.JIANG)){//�ƶ��������ǽ�
			for(int j=8;j<=10;j++){
				if(chessPoints[endX][j].getPiece()!=null&&
						chessPoints[endX][j].getPiece().getCategory().equals(PieceCategory.SHUAI)){
					canMove = sectionHavePiece(endX,endY+1,j-1,chessPoints);
					//System.out.println("�ƶ��������ǽ�����˧������ 1143 canMove="+canMove);
					break;
				}
			}
		}else if(pc.equals(PieceCategory.SHUAI)){//�ƶ���������˧
			for(int j=1;j<=3;j++){
				if(chessPoints[endX][j].getPiece()!=null&&
						chessPoints[endX][j].getPiece().getCategory().equals(PieceCategory.JIANG)){
					canMove = sectionHavePiece(endX,j+1,endY-1,chessPoints);
					//System.out.println("�ƶ�������˧���뽫������ 1151 canMove="+canMove);
					break;
				}
			}
		}else{//�ƶ�����������������
			int JIANG_Y = 0;
			int SHUAI_Y = 0;
			//��x=startX�ĺ������ϣ�����1---(startY-1)֮����û�н�
			for(int j=1;j<startY;j++){
				if(chessPoints[startX][j].getPiece()!=null
						&&chessPoints[startX][j].getPiece().getCategory().equals(PieceCategory.JIANG)){
					JIANG_Y = j;
					//System.out.println("��x=startX�ĺ������ϣ�����1---(startY-1)֮���н� 1160 canMove="+canMove);
					break;
				}
			}
			//��x=startX�ĺ������ϣ�����(startY+1)---10֮����û��˧
			for(int j=startY+1;j<=10;j++){
				if(chessPoints[startX][j].getPiece()!=null&&
						chessPoints[startX][j].getPiece().getCategory().equals(PieceCategory.SHUAI)){
					SHUAI_Y = j;
					//System.out.println("��x=startX�ĺ������ϣ�����(startY+1)---10֮����˧ 1168");
					break;
				}
			}
			//�ƶ��������ڽ�˧֮��
			if(JIANG_Y!=0&&SHUAI_Y!=0){
				if(endX == startX){//���������귽�����ƶ�
					canMove = sectionHavePiece(startX, JIANG_Y+1, SHUAI_Y-1, chessPoints);
					//System.out.println("���������귽�����ƶ�  1167 canMove="+canMove);
				}else{//���Ӻ����귽�����ƶ�
					canMove = sectionHavePiece(startX,JIANG_Y+1,startY-1,chessPoints)||
							sectionHavePiece(startX,startY+1,SHUAI_Y-1,chessPoints);
					//System.out.println("���Ӻ����귽�����ƶ�  1171 canMove="+canMove);
				}
			}
		}
		return !canMove;
	}
	/**
	 * �����ߺ��жϽ�˧�Ƿ�������췽�������ϰ벿��
	 * @param piece �ƶ�������
	 * @param startX ��ʼ������
	 * @param startY ��ʼ������
	 * @param endX  �յ������
	 * @param endY �յ�������
	 * @param chessPoints ���ӵ��ά����
	 * @return �����˧����������true�����򣬷���false��
	 */
	public static boolean isMeeting2(ChessPiece piece,int startX ,int startY, int endX, int endY,
			ChessPoint[][] chessPoints){
		boolean canMove = true;
		PieceCategory pc = piece.getCategory();
		/*PieceCategory category1 = null;
		PieceCategory category2 = null;
		if(palyFirst.equals(PALYER_FIRST) || palyFirst == null){
			category1 = PieceCategory.JIANG;
			category2 = PieceCategory.SHUAI;
		}else{
			category1 = PieceCategory.SHUAI;
			category2 = PieceCategory.JIANG;
		}*/
		if(pc.equals(PieceCategory.SHUAI)){//�ƶ���������˧
			for(int j=8;j<=10;j++){
				if(chessPoints[endX][j].getPiece()!=null&&
						chessPoints[endX][j].getPiece().getCategory().equals(PieceCategory.JIANG)){
					canMove = sectionHavePiece(endX,endY+1,j-1,chessPoints);
					//System.out.println("�ƶ��������ǽ�����˧������ 1143 canMove="+canMove);
					break;
				}
			}
		}else if(pc.equals(PieceCategory.JIANG)){//�ƶ��������ǽ�
			for(int j=1;j<=3;j++){
				if(chessPoints[endX][j].getPiece()!=null&&
						chessPoints[endX][j].getPiece().getCategory().equals(PieceCategory.SHUAI)){
					canMove = sectionHavePiece(endX,j+1,endY-1,chessPoints);
					//System.out.println("�ƶ�������˧���뽫������ 1151 canMove="+canMove);
					break;
				}
			}
		}else{//�ƶ�����������������
			int JIANG_Y = 0;
			int SHUAI_Y = 0;
			//��x=startX�ĺ������ϣ�����1---(startY-1)֮����û��˧
			for(int j=1;j<startY;j++){
				if(chessPoints[startX][j].getPiece()!=null
						&&chessPoints[startX][j].getPiece().getCategory().equals(PieceCategory.SHUAI)){
					SHUAI_Y = j;
					break;
				}
			}
			//��x=startX�ĺ������ϣ�����(startY+1)---10֮����û�н�
			for(int j=startY+1;j<=10;j++){
				if(chessPoints[startX][j].getPiece()!=null&&
						chessPoints[startX][j].getPiece().getCategory().equals(PieceCategory.JIANG)){
					JIANG_Y = j;
					break;
				}
			}
			//�ƶ��������ڽ�˧֮��
			if(JIANG_Y!=0&&SHUAI_Y!=0){
				if(endX == startX){//���������귽�����ƶ�
					canMove = sectionHavePiece(startX, SHUAI_Y+1, JIANG_Y-1, chessPoints);
				}else{//���Ӻ����귽�����ƶ�
					canMove = sectionHavePiece(startX,SHUAI_Y+1,startY-1,chessPoints)||
							sectionHavePiece(startX,startY+1,JIANG_Y-1,chessPoints);
				}
			}
		}
		return !canMove;
	}
	/**
	 * �Ƿ����
	 * 
	 * @param piece
	 *            �ƶ�������
	 * @param endX
	 *            �յ������
	 * @param endY
	 *            �յ�������
	 * @param chessPoints
	 *            ���ӵ��ά����
	 * @return �������������true�����򣬷���false��
	 */
	public static boolean isDuiLian(ChessPiece piece, int endX, int endY,
			ChessPoint[][] chessPoints) {

		int shuaiI = 0, shuaiJ = 0;
		int jiangI = 0, jiangJ = 0;
		PieceCategory pc = piece.getCategory();

		// �����кڌ���λ��
		for (int i = 4; i <= 6; i++) {
			for (int j = 1; j <= 3; j++) {
				if (chessPoints[i][j].hasPiece()) {
					if (chessPoints[i][j].getPiece().getCategory()
							.equals(PieceCategory.JIANG)) {
						jiangI = i;
						jiangJ = j;
						break;
					}
				}
			}
		}

		// �����к쎛��λ��
		for (int i = 4; i <= 6; i++) {
			for (int j = 8; j <= 10; j++) {
				if (chessPoints[i][j].hasPiece()) {
					if (chessPoints[i][j].getPiece().getCategory()
							.equals(PieceCategory.SHUAI)) {
						shuaiI = i;
						shuaiJ = j;
						break;
					}
				}
			}
		}
		// �ƶ��������ǎ���
		if (pc == PieceCategory.SHUAI || pc == PieceCategory.JIANG) {
			int startX = 0;
			int startY = 0;
			if (pc == PieceCategory.SHUAI) {
				startX = jiangI;
				startY = jiangJ;
			} else {
				startX = shuaiI;
				startY = shuaiJ;
			}
			boolean flag = false;
			// ��������ͬ���п��ܶ���
			if (startX == endX) {
				int y2 = Math.max(endY, startY);
				int y1 = Math.min(endY, startY);
				for (int j = y1 + 1; j < y2; j++) {
					if (chessPoints[startX][j].hasPiece()) {
						flag = true;
					}
				}
				if (!flag) {
					return true;
				}
			}
		}
		// �ƶ������Ӳ��ǌ��͎�
		else {
			// ��������ͬ���п��ܶ���
			if (shuaiI == jiangI) {
				boolean flag = false;
				int y2 = Math.max(shuaiJ, jiangJ);
				int y1 = Math.min(shuaiJ, jiangJ);
				for (int j = y1 + 1; j < y2; j++) {
					// ���͎�֮���Ƿ������ӣ��������ƶ������ӣ���Ϊ������ӽ�Ҫ�뿪
					if (j != endY && chessPoints[shuaiI][j].hasPiece()) {
						flag = true;
					}
				}
				if (!flag) {
					return true;
				}
			}
		}
		// System.out.println(piece.getCategory()+"Test" + shuaiI + shuaiJ + ","
		// + endX + endY);

		return false;

	}
}
