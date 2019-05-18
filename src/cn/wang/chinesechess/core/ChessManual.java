
package cn.wang.chinesechess.core;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import cn.wang.chinesechess.ChessUtil;
import cn.wang.chinesechess.config.NAME;
import cn.wang.chinesechess.config.PropertyReader;
import cn.wang.chinesechess.print.part.PrintPartBoard;


/**
 * ������
 * 
 * ְ�𣺱������ס�ɾ�����ס���ȡ���ס���������
 * 
 * @author ecjtu---WangHualiang
 */
public class ChessManual extends JPanel implements NAME {

	private static final long serialVersionUID = 1L;

	public JList manualList = null;// �����б�

	public JScrollPane manualScroll = null;// ���׹�����

	private ChessBoard board = null;// ����

	private ChessPoint[][] chessPoints;// ���ӵ��ά����

	private ArrayList<ManualItem> manualItems;// �ƶ���¼

	public Vector<String> descs;// ���׶�Ӧ����������

	/**
	 * ���캯������ʼ������
	 */
	public ChessManual(ChessBoard board) {
		this.board = board;
		this.chessPoints = board.getChessPoints();
		manualItems = new ArrayList<ManualItem>();
		descs = new Vector<String>();

		manualList = new JList();
		manualList.setFont(new Font("����", Font.PLAIN, 14));
		manualList.setToolTipText(PropertyReader.get("CHESS_MESSAGE_TOOLTIP"));
		// manual.setPreferredSize(new Dimension(160,180));
		manualList.setVisibleRowCount(12);
		manualList.setAutoscrolls(true);

		manualScroll = new JScrollPane(manualList);
		// manualScroll.setPreferredSize(new Dimension(200,210));
		setLayout(new BorderLayout());
		setSize(220, 260);
		add(manualScroll, BorderLayout.CENTER);
	}

	/**
	 * ��¼�ƶ����Ӻͱ������ӵ���Ϣ���Լ��ƶ���Ϣ
	 */
	public void addManualItem(ManualItem manualItem,String palyerFirst) {
		manualItems.add(manualItem);
		ChessPiece piece = PieceUtil.searchPieceById(manualItem
				.getMovePieceId(), chessPoints);
		MoveStep moveStep = manualItem.getMoveStep();
		Point pStart = moveStep.getStart();
		Point pEnd = moveStep.getEnd();
		String name = piece.getName();
		PieceCategory category = piece.getCategory();
		int index = 1;
		if (name.equals(RED_NAME)) {
			index = manualItems.size() / 2 + 1;
		} else {
			index = manualItems.size() / 2;
		}	
		if (board instanceof PrintPartBoard) {// ֻ�воִ��ף��ڷ�����ʱ������Ҫ�޸�
			PrintPartBoard board2 = (PrintPartBoard) (board);
			if (board2.getPalyerFirst().equals(BLACK_NAME)) {
				if (name.equals(BLACK_NAME)) {
					index = manualItems.size() / 2 + 1;
				} else {
					index = manualItems.size() / 2;
				}
			}
		}
		int startX = (int) pStart.getX();int startY = (int) pStart.getY();
		int endX = (int) pEnd.getX();int endY = (int) pEnd.getY();
		int count = 0;String desc = name;
		// ��������4����Ҫ����ģ�������Ϊ������û�ж���
		if (index < 10) {
			desc += "  " + index + ": ";
		} else {
			desc += " " + index + ": ";
		}
		for (int j = 1; j <= 10; j++){//��ѡ�е�������ͬһ�����ϵ�ͬ������һ���ж��ٸ�
			ChessPiece tempPiece = chessPoints[startX][j].getPiece();
			if (tempPiece!=null&&tempPiece.getCategory().equals(category)
					&&tempPiece.getName().equals(name)){
				count++;
			}
		}
		if(count==1){//ֻ��ѡ�е�����
			desc += PieceUtil.catetoryToZi(category);//��һ����
			if (name.equals(RED_NAME)) {//�ڶ�����
				desc += ChessUtil.numToZi(10 - startX);
			} else {
				desc += (" " + startX);
			}
			if (name.equals(RED_NAME)) {//��������
				if (startY == endY) {
					desc += "ƽ";
				} else if (startY > endY) {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						desc += "��";
					}else{
						desc += "��";
					}
				} else {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						desc += "��";
					}else{
						desc += "��";
					}
				}
			} else if (name.equals(BLACK_NAME)) {
				if (startY == endY) {
					desc += "ƽ";
				} else if (startY > endY) {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						desc += "��";
					}else{
						desc += "��";
					}
				} else {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						desc += "��";
					}else{
						desc += "��";
					}
				}
			}
			if (startX == endX) {// ��4����
				if (name.equals(RED_NAME)) {
					desc += ChessUtil.numToZi(Math.abs(startY - endY));
				} else {
					desc += " " + Math.abs(startY - endY);
				}
			} else {
				if (name.equals(RED_NAME)) {
					desc += ChessUtil.numToZi(10 - endX);
				} else {
					desc += " " + endX;
				}
			}
		}
		else if(count==2){//������ͬ���������
			int num = 0;
			for (int j = 1; j <= 10; j++) {
				if (j == startY) {break;}
				ChessPiece tempPiece = chessPoints[startX][j].getPiece();
				if(tempPiece!=null && tempPiece.getCategory().equals(category)
						&& tempPiece.getName().equals(name)){num++;}
			}
			if(piece.getName().equals(RED_NAME)){//ѡ�е�����Ϊ��ɫ
				if(num==0){//������֣���ʱѡ�е�������ǰ
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						desc += "ǰ";
					}else{
						desc += "��";
					}
				}else{//������֣���ʱѡ�е������ں�
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						desc += "��";
					}else{
						desc += "ǰ";
					}
				}
				desc += PieceUtil.catetoryToZi(category);
				if (startY == endY) {
					desc += "ƽ";
				} else if (startY > endY) {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						desc += "��";
					}else{
						desc += "��";
					}
				} else {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						desc += "��";
					}else{
						desc += "��";
					}
				}
				if(startX == endX){
					desc += ChessUtil.numToZi(Math.abs(startY - endY));
				}else{
					desc += ChessUtil.numToZi(10 - endX);
				}
			}else{//ѡ�е�����Ϊ��ɫ
				if(num==0){//������֣���ʱѡ�е������ں�
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						desc += "��";
					}else{
						desc += "ǰ";
					}
				}else{//������֣���ʱѡ�е�������ǰ
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						desc += "ǰ";
					}else{
						desc += "��";
					}
				}
				desc += PieceUtil.catetoryToZi(category);
				if (startY == endY) {
					desc += "ƽ";
				} else if (startY > endY) {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						desc += "��";
					}else{
						desc += "��";
					}
				} else {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						desc += "��";
					}else{
						desc += "��";
					}
				}
				if(startX == endX){
					desc += " " + Math.abs(startY - endY);
				}else{
					desc += " " + endX;
				}
			}
		
		}
		else if(count > 2){//������������ͬ��������ӣ�Ϊ������
			System.out.println("������������ͬ��������ӣ�Ϊ������  234");
			int num = 0;
			for(int j = 1; j <= 10; j++){
				if (j == startY) {break;}
				ChessPiece tempPiece = chessPoints[startX][j].getPiece();
				if(tempPiece!=null && tempPiece.getCategory().equals(category)
						&& tempPiece.getName().equals(name)){num++;}
			}
			num++;
			if(category.equals(PieceCategory.BING)){
				if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){//�������
					if(num == 1){desc += "ǰ��";}
					else if(num == 2){desc += "����";}
					else if(num == 3){desc += "����";}
					else if(num == 4){desc += "�ı�";}
					else if(num == 5){desc += "���";}
				}else{
					if(num == 1){desc += "���";}
					else if(num == 2){desc += "�ı�";}
					else if(num == 3){desc += "����";}
					else if(num == 4){desc += "����";}
					else if(num == 5){desc += "ǰ��";}
				}
				if (startY == endY) {
					desc += "ƽ";
				} else if (startY > endY) {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						desc += "��";
					}else{
						desc += "��";
					}
				} else {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						desc += "��";
					}else{
						desc += "��";
					}
				}
				if(startX == endX){
					desc += ChessUtil.numToZi(Math.abs(startY - endY));
				}else{
					desc += ChessUtil.numToZi(10 - endX);
				}
			}else{
				if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){//�������
					if(num == 1){desc += "����";}
					else if(num == 2){desc += "4��";}
					else if(num == 3){desc += "3��";}
					else if(num == 4){desc += "2��";}
					else if(num == 5){desc += "ǰ��";}
				}else{
					if(num == 1){desc += "ǰ��";}
					else if(num == 2){desc += "2��";}
					else if(num == 3){desc += "3��";}
					else if(num == 4){desc += "4��";}
					else if(num == 5){desc += "����";}
				}
				if (startY == endY) {
					desc += "ƽ";
				} else if (startY > endY) {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						desc += "��";
					}else{
						desc += "��";
					}	
				} else {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						desc += "��";
					}else{
						desc += "��";
					}
				}
				if(startX == endX){
					desc += " " + Math.abs(startY - endY);
				}else{
					desc += " " + endX;
				}
			}
		}
		descs.add(desc);// �����������
		manualList.setListData(descs);
		scrollToView();// �����б�������������
	}

	/**
	 * ������е������ƶ���¼
	 * 
	 * @return ���������ƶ���¼�ļ���
	 */
	public ArrayList<ManualItem> getManualItems() {
		return manualItems;
	}

	public void setManualItems(ArrayList<ManualItem> manualItems) {
		this.manualItems = manualItems;
	}

	/**
	 * ���壬������������ƶ�ʱ��ͬ
	 * 
	 */
	public boolean removeManualItem() {
		int size = manualItems.size();
		if (size <= 0) {return false;}
		descs.remove(size - 1);// �����б���Ϣ
		manualList.setListData(descs);
		ManualItem record = new ManualItem();
		MoveStep moveStep;ChessPiece eatedPiece;
		int startI = 0;int startJ = 0;
		int endI = 0;int endJ = 0;
		if (size > 0) {
			record = (ManualItem) manualItems.remove(manualItems.size() - 1);
			eatedPiece = PieceUtil.createPiece(record.getEatedPieceId());
			moveStep = record.getMoveStep();
			startI = moveStep.start.x;startJ = moveStep.start.y;
			endI = moveStep.end.x;endJ = moveStep.end.y;
			ChessPiece piece = chessPoints[endI][endJ].getPiece();
			chessPoints[startI][startJ].setPiece(piece, board);
			if (eatedPiece == null) { // ��һ��û�г�����
				chessPoints[endI][endJ].setHasPiece(false);
			} else {// ��һ����������
				chessPoints[endI][endJ].setPiece(eatedPiece, board);
				eatedPiece.addMouseListener(board.getMouseAdapter());
			}
		}
		if (descs.size() >= 1) {
			record = (ManualItem) manualItems.get(descs.size() - 1);
			moveStep = record.getMoveStep();
			startI = moveStep.start.x;startJ = moveStep.start.y;
			endI = moveStep.end.x;endJ = moveStep.end.y;
		}
		Point start = new Point(endI, endJ);Point end = new Point(startI, startJ);
		if (descs.size() > 0) {
			board.setMoveFlag(true);board.setMoveFlagPoints(start, end);
		} else {
			board.setMoveFlag(false);
		}
		scrollToView();
		board.validate();
		board.repaint();
		return true;
	}

	private void scrollToView() {
		int lastIndex = descs.size();
		// ѡ�����һ�У���ʾ���
		manualList.setSelectedIndex(lastIndex - 1);

		Rectangle rect = manualList.getCellBounds(lastIndex - 1, lastIndex - 1);
		if (rect == null) {
			return;
		}
		System.out.println(rect == null);
		if ((manualScroll != null) && (manualScroll.getViewport() != null)
				&& (rect != null)) {
			// ���rect==null������ֿ�ָ���쳣
			manualScroll.getViewport().scrollRectToVisible(rect);
		}
	}
}
