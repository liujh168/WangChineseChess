
package cn.wang.chinesechess.core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import javax.swing.JPanel;

import cn.wang.chinesechess.ChessUtil;
import cn.wang.chinesechess.ColorUtil;
import cn.wang.chinesechess.config.NAME;
import cn.wang.chinesechess.config.PropertyReader;



/**
 * 
 * �й�����__����������
 * 
 * �õ���ģ�淽�����ģʽ
 * 
 * ���������Ҫ�������߱�ǵĻ�������Ҫ����drawShuXianFlag����
 * 
 * ���������Ҫ�������̵ı���ͼƬ����Ҫ����getBackgroundImage������
 * 
 * �������ʵ��getMouseAdapter��getBoardType��getBackgroundColor����
 * 
 * 
 * ְ�𣺻�������
 * 
 * @author wanghualiang
 */
public abstract class ChessBoard extends JPanel implements NAME, Runnable {

	private static final long serialVersionUID = 1L;

	// ���̵�ˮƽ������������ӵĵ�ĸ���
	public static final int X = 9;

	// ���̵Ĵ�ֱ������������ӵĵ�ĸ���
	public static final int Y = 10;

	/** ArrayList<Point> �䡢�����ڵ�λ�õı��*/
	protected ArrayList<Point> sidePoints = new ArrayList<Point>(14);

	/**ArrayList<Point> ��ǰ���ӵĿ�ѡ�߷�*/
	protected ArrayList<Point> tipPoints = new ArrayList<Point>();

	/**ChessPoint ���ӵ㣬��90������9*��10*/
	public ChessPoint chessPoints[][];

	/**boolean �ƶ�����ʱ�Ƿ���Ҫ��ʾ*/
	protected boolean moveFlag = false;
	
	/**boolean �����Ƿ�������壬Ĭ�Ͽ���*/
	protected boolean canPaly = true;

	/**Point[] �ƶ�������ʾ��2���㣬���Լ�ΪMoveStep*/
	public Point[] movePoints = new Point[] { new Point(), new Point() };

	// �췽16������
	public ChessPiece ��܇1, ��܇2, ���R1, ���R2, ����1, ����2, �쎛, ����1, ����2, ���1, ���2,
			���3, ���4, ���5, ����1, ����2;

	// �ڷ�16������
	public ChessPiece ��܇1, ��܇2, ���R1, ���R2, ����1, ����2, �ڌ�, ��ʿ1, ��ʿ2, ����1, ����2,
			����3, ����4, ����5, ����1, ����2;

	/**Cursor ���ι��*/
	public static Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

	/**Cursor Ĭ�Ϲ��*/
	public static Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);

	/**ChessPiece ��˸������*/
	protected ChessPiece winkPiece;

	/**boolean Ĭ��û��ѡ������*/
	protected boolean isSelected = false;

	/**boolean �Ƿ���Ҫ��˸*/
	protected boolean needWink = false;

	/**boolean �Ƿ�����ƶ�����*/
	protected boolean move = false;

	/**Thread ��˸���߳�*/
	protected Thread winkThread;

	/**int ��˸���ӵ�����*/
	protected int startI, startJ;
	
	public ChessManual chessManual = null;

	/**protected String ս���������֣��췽or�ڷ�*/
	protected String playerName;
	/**protected String �������ַ� ���or����,Ĭ�����*/
	protected String palyerFirst = null;

	/**
	 * ���캯��
	 * 
	 */
	public ChessBoard() {
		super();
		this.setLayout(null);// ���ɺ���
		this.setBackground(getBackgroundColor());// �������̵ı���ɫ
		this.addMouseListener(getMouseAdapter());// ���������

		// ��ʼ�����ӵ�
		chessPoints = new ChessPoint[X + 1][Y + 1];
		for (int i = 1; i <= X; i++) {
			for (int j = 1; j <= Y; j++) {
				chessPoints[i][j] = new ChessPoint(i * UNIT_WIDTH, j
						* UNIT_HEIGHT);
			}
		}
		// Ϊ32�����ӷ���ռ�
		init32Pieces();
		// ��ʼ��32�����ӵĹ�����ʾ
		init32PiecesTooltip();

		// ������
		chessManual = new ChessManual(this);

		// ��ʼ���ںͱ���λ�ã��Ա㻭�����
		initPBFlag();

		winkThread = new Thread(this);
		winkThread.start();
	}

	// Ĭ��ʹ��Ĭ�ϱ���ɫ
	protected Color getBackgroundColor() {
		return ColorUtil.getDefaultBgcolor();
	}
	
	/**
	 * ��ʼ��32�����ӵĹ�����ʾ
	 * 
	 */
	private void init32PiecesTooltip() {
		��܇1.setToolTipText(PropertyReader.get("JU_TOOLTIP"));
		��܇2.setToolTipText(PropertyReader.get("JU_TOOLTIP"));
		��܇1.setToolTipText(PropertyReader.get("JU_TOOLTIP"));
		��܇2.setToolTipText(PropertyReader.get("JU_TOOLTIP"));

		����1.setToolTipText(PropertyReader.get("PAO_TOOLTIP"));
		����2.setToolTipText(PropertyReader.get("PAO_TOOLTIP"));
		����1.setToolTipText(PropertyReader.get("PAO_TOOLTIP"));
		����2.setToolTipText(PropertyReader.get("PAO_TOOLTIP"));

		���R1.setToolTipText(PropertyReader.get("MA_TOOLTIP"));
		���R2.setToolTipText(PropertyReader.get("MA_TOOLTIP"));
		���R1.setToolTipText(PropertyReader.get("MA_TOOLTIP"));
		���R2.setToolTipText(PropertyReader.get("MA_TOOLTIP"));

		���1.setToolTipText(PropertyReader.get("BING_TOOLTIP"));
		���2.setToolTipText(PropertyReader.get("BING_TOOLTIP"));
		���3.setToolTipText(PropertyReader.get("BING_TOOLTIP"));
		���4.setToolTipText(PropertyReader.get("BING_TOOLTIP"));
		���5.setToolTipText(PropertyReader.get("BING_TOOLTIP"));

		����1.setToolTipText(PropertyReader.get("ZU_TOOLTIP"));
		����2.setToolTipText(PropertyReader.get("ZU_TOOLTIP"));
		����3.setToolTipText(PropertyReader.get("ZU_TOOLTIP"));
		����4.setToolTipText(PropertyReader.get("ZU_TOOLTIP"));
		����5.setToolTipText(PropertyReader.get("ZU_TOOLTIP"));

		����1.setToolTipText(PropertyReader.get("HONGSHI_TOOLTIP"));
		����2.setToolTipText(PropertyReader.get("HONGSHI_TOOLTIP"));

		��ʿ1.setToolTipText(PropertyReader.get("HEISHI_TOOLTIP"));
		��ʿ2.setToolTipText(PropertyReader.get("HEISHI_TOOLTIP"));

		����1.setToolTipText(PropertyReader.get("HEIXIANG_TOOLTIP"));
		����2.setToolTipText(PropertyReader.get("HEIXIANG_TOOLTIP"));

		����1.setToolTipText(PropertyReader.get("HONGXIANG_TOOLTIP"));

		����2.setToolTipText(PropertyReader.get("HONGXIANG_TOOLTIP"));

		�쎛.setToolTipText(PropertyReader.get("SHUAI_TOOLTIP"));
		�ڌ�.setToolTipText(PropertyReader.get("JIANG_TOOLTIP"));

	}

	/**
	 * �����Ƕ�ս���̻��Ǵ������̣�����Ҫ��ʼ��32�����ӣ�
	 * 
	 * ��ʼ��32������
	 * 
	 */
	private void init32Pieces() {
		��܇1 = PieceUtil.createPiece(PieceId.HONGJU1);
		��܇2 = PieceUtil.createPiece(PieceId.HONGJU2);
		���R1 = PieceUtil.createPiece(PieceId.HONGMA1);
		���R2 = PieceUtil.createPiece(PieceId.HONGMA2);
		����1 = PieceUtil.createPiece(PieceId.HONGPAO1);
		����2 = PieceUtil.createPiece(PieceId.HONGPAO2);
		����1 = PieceUtil.createPiece(PieceId.HONGXIANG1);
		����2 = PieceUtil.createPiece(PieceId.HONGXIANG2);
		����1 = PieceUtil.createPiece(PieceId.HONGSHI1);
		����2 = PieceUtil.createPiece(PieceId.HONGSHI2);
		�쎛 = PieceUtil.createPiece(PieceId.SHUAI);
		���1 = PieceUtil.createPiece(PieceId.BING1);
		���2 = PieceUtil.createPiece(PieceId.BING2);
		���3 = PieceUtil.createPiece(PieceId.BING3);
		���4 = PieceUtil.createPiece(PieceId.BING4);
		���5 = PieceUtil.createPiece(PieceId.BING5);

		�ڌ� = PieceUtil.createPiece(PieceId.JIANG);
		��ʿ1 = PieceUtil.createPiece(PieceId.HEISHI1);
		��ʿ2 = PieceUtil.createPiece(PieceId.HEISHI2);
		��܇1 = PieceUtil.createPiece(PieceId.HEIJU1);
		��܇2 = PieceUtil.createPiece(PieceId.HEIJU2);
		����1 = PieceUtil.createPiece(PieceId.HEIPAO1);
		����2 = PieceUtil.createPiece(PieceId.HEIPAO2);
		����1 = PieceUtil.createPiece(PieceId.HEIXIANG1);
		����2 = PieceUtil.createPiece(PieceId.HEIXIANG2);
		���R1 = PieceUtil.createPiece(PieceId.HEIMA1);
		���R2 = PieceUtil.createPiece(PieceId.HEIMA2);
		����1 = PieceUtil.createPiece(PieceId.ZU1);
		����2 = PieceUtil.createPiece(PieceId.ZU2);
		����3 = PieceUtil.createPiece(PieceId.ZU3);
		����4 = PieceUtil.createPiece(PieceId.ZU4);
		����5 = PieceUtil.createPiece(PieceId.ZU5);

		�쎛.addMouseListener(getMouseAdapter());
		����1.addMouseListener(getMouseAdapter());
		����2.addMouseListener(getMouseAdapter());
		����1.addMouseListener(getMouseAdapter());
		����2.addMouseListener(getMouseAdapter());
		��܇1.addMouseListener(getMouseAdapter());
		��܇2.addMouseListener(getMouseAdapter());
		���R1.addMouseListener(getMouseAdapter());
		���R2.addMouseListener(getMouseAdapter());
		����1.addMouseListener(getMouseAdapter());
		����2.addMouseListener(getMouseAdapter());
		���1.addMouseListener(getMouseAdapter());
		���2.addMouseListener(getMouseAdapter());
		���3.addMouseListener(getMouseAdapter());
		���4.addMouseListener(getMouseAdapter());
		���5.addMouseListener(getMouseAdapter());

		�ڌ�.addMouseListener(getMouseAdapter());
		��ʿ1.addMouseListener(getMouseAdapter());
		��ʿ2.addMouseListener(getMouseAdapter());
		����1.addMouseListener(getMouseAdapter());
		����2.addMouseListener(getMouseAdapter());
		����1.addMouseListener(getMouseAdapter());
		����2.addMouseListener(getMouseAdapter());
		��܇1.addMouseListener(getMouseAdapter());
		��܇2.addMouseListener(getMouseAdapter());
		���R1.addMouseListener(getMouseAdapter());
		���R2.addMouseListener(getMouseAdapter());
		����1.addMouseListener(getMouseAdapter());
		����2.addMouseListener(getMouseAdapter());
		����3.addMouseListener(getMouseAdapter());
		����4.addMouseListener(getMouseAdapter());
		����5.addMouseListener(getMouseAdapter());
	}

	/**
	 * �������̣�10�����ߡ�9�����ߡ��ڱ���14����ǡ��Ź��񡢳��ӝh��
	 * 
	 * ������Ҫ�����������ƶ��ı��
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBackgroundImage(g);

		Graphics2D g2 = (Graphics2D) g;
		// �����䡢�ڱ�Ǳʻ�
		BasicStroke bsFlag = new BasicStroke(2);
		// ���Ӻ��硢���̱߿�ʻ�
		BasicStroke bsLine = new BasicStroke(2);

		// �����߱ʻ�
		BasicStroke bs1 = new BasicStroke(1);

		//����ֱ��
		drawLines(g2, bsLine, bs1);
		//���ƾŹ���
		drawJiuGongLines(g2, bs1);
		//���Ƴ��ӝh��
		drawChuheHanjieString(g2);
		//�����ںͱ����
		drawPaoBingFlag(g2, bsFlag);

		// ����������ƶ�������2����ʾ��ÿ����ʾ����8�������
		drawMoveFlag(g2);
		drawWillMoveFlag(g2);

		// ����������߿�Ϊ��������׼��
		BasicStroke bsOld = new BasicStroke(1);
		g2.setStroke(bsOld);
		g2.setFont(new Font("����", Font.PLAIN, 14));
		g2.setColor(new Color(0, 0, 0));
		//�������߱��
		drawShuXianFlag(g2);
	}
	/**
	 * ���Ʊ���ͼƬ
	 * @param g
	 */
	private void drawBackgroundImage(Graphics g) {
		// Ĭ�ϲ����Ʊ���ͼƬ
		Image image = getBackgroundImage();
		if (image != null) {
			Dimension size = new Dimension(super.getWidth(), super.getHeight());
			g.drawImage(image, 0, 0, size.width, size.height, null);
		}

	}

	protected Image getBackgroundImage() {
		return null;
	}

	/**
	 * Ĭ��ʹ��
	 * ���߱��1��9��һ���ţ��ǰ��պ췽���£��ڷ����ϻ��Ƶ�
	 * ����ǰ��պڷ����£��췽���ϻ��Ƶģ�������дdrawShuXianFlag���ڷ������ڵ���drawShuXianFlag2
	 * @param g2
	 */
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
	/**
	 * ���߱��һ���ţ�1��9���ǰ��պڷ����£��췽���ϻ��Ƶ�
	 * @param g2
	 */
	protected void drawShuXianFlag2(Graphics2D g2) {
		// ��ʾ������
		for (int i = 1; i <= X; i++) {
			g2.drawString("" + ChessUtil.numToZi(10 - i), i * UNIT_WIDTH - 4, UNIT_HEIGHT / 2 - 4);
		}

		for (int i = 1; i <= X; i++) {
			g2.drawString("" + i, i * UNIT_WIDTH - 4,
					10 * UNIT_HEIGHT + 34);
		}
	}

	/**
	 * ��������ӽ�Ҫ�ƶ������ߵ�λ�ô����Ʊ��
	 * @param g2
	 */
	private void drawWillMoveFlag(Graphics2D g2) {
		int tipSize = tipPoints.size();
		int distance = PIECE_WIDTH - 6;

		for (int i = 0; i < tipSize; i++) {
			int a = (int) tipPoints.get(i).getX();
			int b = (int) tipPoints.get(i).getY();

			// ����ת��
			int boardX = chessPoints[a][b].getX();
			int boardY = chessPoints[a][b].getY();

			Color color;

			color = new Color(82, 72, 255);
			BasicStroke bs = new BasicStroke(2);
			g2.setStroke(bs);
			ChessPiece piece = chessPoints[a][b].getPiece();
			if (piece != null && !piece.getName().equals(playerName)) {
				if (playerName.equals(RED_NAME)) {
					// �췽�Ժڷ�����������ɫ
					color = new Color(255, 0, 0);
				} else {
					// �ڷ��Ժ췽����������ɫ
					color = new Color(0, 0, 0);
				}
				drawMoveTips2(g2, boardX, boardY, color);
			} else {
				// ��λ��û����
				drawMoveTips(g2, distance, boardX, boardY, color);
			}
		}
	}

	/**
	 * �ƶ���ı�ǻ���
	 * @param g2
	 */
	private void drawMoveFlag(Graphics2D g2) {
		if (moveFlag) {
			int d = PIECE_WIDTH - 6;
			int a = 0, b = 0;
			for (int i = 0; i < movePoints.length; i++) {
				// �����ƶ���ǣ�8������
				a = (int) movePoints[i].getX();
				b = (int) movePoints[i].getY();
				// ����ת��
				int boardX = chessPoints[a][b].getX();
				int boardY = chessPoints[a][b].getY();
				Color c = new Color(0, 128, 0);
				BasicStroke bs = new BasicStroke(2);
				g2.setStroke(bs);
				drawMoveTips(g2, d, boardX, boardY, c);
			}
		}
	}

	/**
	 * ���ںͱ���λ�õı��
	 * @param g2
	 * @param bsFlag
	 */
	private void drawPaoBingFlag(Graphics2D g2, BasicStroke bsFlag) { 
		int size = sidePoints.size();
		double x = PIECE_WIDTH / 9;// �������ĵ㵽���ֱ�Ǳ߽����ˮƽ����
		double side = PIECE_WIDTH / 6;// ��ǵĳ���
		for (int i = 0; i < size; i++) {
			double a = sidePoints.get(i).getX();
			double b = sidePoints.get(i).getY();
			g2.setStroke(bsFlag);
			if (i >= 0 && i <= 9) {
				drawPBMiddle(g2, x, side, a, b);
			} else if (i == 10 || i == 11) {
				drawPBRight(g2, x, side, a, b);
			} else if (i == 12 || i == 13) {
				drawPBLeft(g2, x, side, a, b);
			}

		}
	}

	/**
	 * ���Ƴ��ӡ�����
	 * @param g2
	 */
	private void drawChuheHanjieString(Graphics2D g2) { 
		g2.setFont(new Font("����", Font.PLAIN, 32));
		g2.drawString("�h ��", chessPoints[2][5].getX(), chessPoints[2][5].getY()
				+ 2 * UNIT_HEIGHT / 3 + 2);
		g2.drawString("�� ��", chessPoints[6][5].getX(), chessPoints[2][5].getY()
				+ 2 * UNIT_HEIGHT / 3 + 2);
	}

	/**
	 * ���ƾŹ���
	 * @param g2
	 * @param bs1
	 */
	private void drawJiuGongLines(Graphics2D g2, BasicStroke bs1) {
		g2.setStroke(bs1);
		g2.drawLine(chessPoints[4][1].getX(), chessPoints[4][1].getY(),
				chessPoints[6][3].getX(), chessPoints[6][3].getY());
		g2.drawLine(chessPoints[6][1].getX(), chessPoints[6][1].getY(),
				chessPoints[4][3].getX(), chessPoints[4][3].getY());
		g2.drawLine(chessPoints[4][8].getX(), chessPoints[4][8].getY(),
				chessPoints[6][Y].getX(), chessPoints[6][Y].getY());
		g2.drawLine(chessPoints[4][Y].getX(), chessPoints[4][Y].getY(),
				chessPoints[6][8].getX(), chessPoints[6][8].getY());
	}

	/**
	 * ���ƺ��ߺ�����
	 * @param g2
	 * @param bsLine
	 * @param bs1
	 */
	private void drawLines(Graphics2D g2, BasicStroke bsLine, BasicStroke bs1) {
		// 10������
		for (int j = 1; j <= Y; j++) {
			if (j == 1 || j == 5 || j == 6 || j == 10) {
				g2.setStroke(bsLine);
				g2.drawLine(chessPoints[1][j].getX(), chessPoints[1][j].getY(),
						chessPoints[X][j].getX(), chessPoints[X][j].getY());
			} else {
				g2.setStroke(bs1);
				g2.drawLine(chessPoints[1][j].getX(), chessPoints[1][j].getY(),
						chessPoints[X][j].getX(), chessPoints[X][j].getY());
			}
		}

		// 9������
		for (int i = 1; i <= X; i++) {
			if (i != 1 && i != X) {
				// �м������
				g2.setStroke(bs1);
				g2.drawLine(chessPoints[i][1].getX(), chessPoints[i][1].getY(),
						chessPoints[i][Y - 5].getX(),
						chessPoints[i][Y - 5].getY());
				g2.drawLine(chessPoints[i][Y - 4].getX(),
						chessPoints[i][Y - 4].getY(), chessPoints[i][Y].getX(),
						chessPoints[i][Y].getY());
			} else {
				// ���ߵ�����
				g2.setStroke(bsLine);
				g2.drawLine(chessPoints[i][1].getX(), chessPoints[i][1].getY(),
						chessPoints[i][Y].getX(), chessPoints[i][Y].getY());
			}
		}
	}
	/**
	 * �����ӻ����ԳԵ���ʾ��־
	 * @param g2
	 * @param boardX
	 * @param boardY
	 * @param color
	 */
	private void drawMoveTips2(Graphics2D g2, int boardX, int boardY,
			Color color) {
		BasicStroke bs = new BasicStroke(2);
		g2.setStroke(bs);
		g2.setColor(color);
		
		g2.drawLine(boardX - PIECE_WIDTH / 2, boardY - PIECE_HEIGHT / 2, boardX
				+ PIECE_WIDTH / 2, boardY + PIECE_HEIGHT / 2);
	
		g2.drawLine(boardX - PIECE_WIDTH / 2, boardY + PIECE_HEIGHT / 2, boardX
				+ PIECE_WIDTH / 2, boardY - PIECE_HEIGHT / 2);

	}

	/**
	 * ��ʼ��һ�����ӿ����ߵ�λ��
	 * @param piece
	 *            ��Ҫ�ƶ�������
	 * @param startX
	 *            ��������
	 * @param startY
	 *            ���������
	 */
	protected void initTipPoints(ChessPiece piece, int startX, int startY) {
		BoardType boardType = getBoardType();
		if (boardType == null) {
			return;
		}
		boolean rule = false;
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 10; j++) {
				if (boardType == BoardType.printWhole
						|| boardType == BoardType.manMachine
						|| boardType == BoardType.twoman
						) {
					rule = ChessRule.allRule(piece, startX, startY, i, j,
							chessPoints, palyerFirst);
				} else if (boardType == BoardType.printPartial) {
					rule = ChessRule.partialRule(piece, startX, startY, i, j,
							chessPoints, palyerFirst);
				} 
				if(rule){
					tipPoints.add(new Point(i, j));
				}
			}
		}

		repaint();
		validate();
	}

	protected abstract BoardType getBoardType();

	
	/**
	 * �������ϵ�λ�û������ӿ����ߵ���ʾ��־
	 * 
	 * @param g2
	 * @param d
	 * @param a
	 * @param b
	 */
	private void drawMoveTips(Graphics2D g2, int d, int a, int b,
			Color color) {
		g2.setColor(color);
		// ���Ͻǵ�2����
		g2.drawLine((a - d / 2),  (b - d / 2),  (a - d / 2),
				(b - d / 4));
		g2.drawLine((a - d / 2),  (b - d / 2),  (a - d / 4),
				 (b - d / 2));
		// ���Ͻǵ�2����
		g2.drawLine((a + d / 2),  (b - d / 2),  (a + d / 4),
				  (b - d / 2));
		g2.drawLine( (a + d / 2),  (b - d / 2),  (a + d / 2),
				 (b - d / 4));
		// ���½ǵ�2����
		g2.drawLine(  (a - d / 2),   (b + d / 2),   (a - d / 2),
				 (b + d / 4));
		g2.drawLine( (a - d / 2),  (b + d / 2),  (a - d / 4),
				 (b + d / 2));
		// ���½ǵ�2����
		g2.drawLine( (a + d / 2),  (b + d / 2),  (a + d / 4),
				 (b + d / 2));
		g2.drawLine( (a + d / 2),  (b + d / 2),  (a + d / 2),
				 (b + d / 4));
	}

	private void drawPBLeft(Graphics2D g2, double x, double side, double a,
			double b) {
		// ���Ͻ�
		g2.drawLine((int) (a - x), (int) (b - x), (int) (a - x),
				(int) (b - x - side));
		g2.drawLine((int) (a - x), (int) (b - x), (int) (a - x - side),
				(int) (b - x));
		// ���½�
		g2.drawLine((int) (a - x), (int) (b + x), (int) (a - x),
				(int) (b + x + side));
		g2.drawLine((int) (a - x), (int) (b + x), (int) (a - x - side),
				(int) (b + x));
	}

	private void drawPBRight(Graphics2D g2, double x, double side, double a,
			double b) {
		// ���Ͻ�
		g2.drawLine((int) (a + x), (int) (b - x), (int) (a + x),
				(int) (b - x - side));
		g2.drawLine((int) (a + x), (int) (b - x), (int) (a + x + side),
				(int) (b - x));
		// ���½�
		g2.drawLine((int) (a + x), (int) (b + x), (int) (a + x),
				(int) (b + x + side));
		g2.drawLine((int) (a + x), (int) (b + x), (int) (a + x + side),
				(int) (b + x));
	}

	private void drawPBMiddle(Graphics2D g2, double x, double side, double a,
			double b) {
		// ���Ͻ�
		g2.drawLine((int) (a - x), (int) (b - x), (int) (a - x),
				(int) (b - x - side));
		g2.drawLine((int) (a - x), (int) (b - x), (int) (a - x - side),
				(int) (b - x));
		// ���½�
		g2.drawLine((int) (a - x), (int) (b + x), (int) (a - x),
				(int) (b + x + side));
		g2.drawLine((int) (a - x), (int) (b + x), (int) (a - x - side),
				(int) (b + x));

		// ���Ͻ�
		g2.drawLine((int) (a + x), (int) (b - x), (int) (a + x),
				(int) (b - x - side));
		g2.drawLine((int) (a + x), (int) (b - x), (int) (a + x + side),
				(int) (b - x));
		// ���½�
		g2.drawLine((int) (a + x), (int) (b + x), (int) (a + x),
				(int) (b + x + side));
		g2.drawLine((int) (a + x), (int) (b + x), (int) (a + x + side),
				(int) (b + x));
	}

	/**
	 * ��ʼ���ںͱ�����ı�ǵ�λ��
	 * 
	 */
	private void initPBFlag() {
		// 4���ڵ�λ��
		sidePoints.add(new Point(chessPoints[2][3].getX(), chessPoints[2][3]
				.getY()));
		sidePoints.add(new Point(chessPoints[8][3].getX(), chessPoints[8][3]
				.getY()));
		sidePoints.add(new Point(chessPoints[2][8].getX(), chessPoints[2][8]
				.getY()));
		sidePoints.add(new Point(chessPoints[8][8].getX(), chessPoints[8][8]
				.getY()));

		// 3������3����
		sidePoints.add(new Point(chessPoints[3][4].getX(), chessPoints[3][4]
				.getY()));
		sidePoints.add(new Point(chessPoints[5][4].getX(), chessPoints[5][4]
				.getY()));
		sidePoints.add(new Point(chessPoints[7][4].getX(), chessPoints[7][4]
				.getY()));
		sidePoints.add(new Point(chessPoints[3][7].getX(), chessPoints[3][7]
				.getY()));
		sidePoints.add(new Point(chessPoints[5][7].getX(), chessPoints[5][7]
				.getY()));
		sidePoints.add(new Point(chessPoints[7][7].getX(), chessPoints[7][7]
				.getY()));

		// ��ߣ�1����+ 1���䣻�ұߣ�1����+ 1����
		sidePoints.add(new Point(chessPoints[1][4].getX(), chessPoints[1][4]
				.getY()));
		sidePoints.add(new Point(chessPoints[1][7].getX(), chessPoints[1][7]
				.getY()));
		sidePoints.add(new Point(chessPoints[9][4].getX(), chessPoints[9][4]
				.getY()));
		sidePoints.add(new Point(chessPoints[9][7].getX(), chessPoints[9][7]
				.getY()));

	}

	/**
	 * �������ӵ�������
	 * 
	 * @param x
	 *            ���ӵĺ�����
	 * @param pc
	 *            ���ӵ����
	 * @return �������ӵ������꣬���򷵻� 0
	 */
	protected int getPieceYByCategory(int x, PieceCategory pc) {
		if (pc == null) {
			System.out.println("getPieceYByCategory:pc == null");
			return 0;
		}
		int y = 0;
		if (x < 1 || x > 9) {
			return y;
		}

		for (int j = 1; j <= Y; j++) {
			ChessPiece piece = chessPoints[x][j].getPiece();
			if (piece != null && pc.equals(piece.getCategory())
					&& piece.getName().equals(playerName)) {
				System.out.println(playerName + piece.getCategory() + x + ","
						+ +j);
				y = j;
				break;
			}
		}
		return y;

	}

	/**
	 * �������ף����R�˽��ߣ�����ƶ�������
	 * 
	 * @param manual
	 *            ���ף����R�˽���
	 * @return �ƶ������ӡ�
	 */
	public ChessPiece getMovePiece(String manual) {
		if (manual == null || manual.length() != 4) {
			return null;
		}

		Point point = getStartPosition(manual);
		int startX = (int) point.getX();
		int startY = (int) point.getY();

		if (startX < 1 || startX > 9 || startY < 1 || startY > 10) {
			return null;
		}
		ChessPiece piece = chessPoints[startX][startY].getPiece();
		System.out.println("�ƶ������ӣ�" + piece.getName() + " "
				+ piece.getCategory());
		return piece;

	}
	
	
	/**
	 * �������ף����R�˽��ߣ�����ƶ����ӵ���ʼ����
	 * 
	 * @param manual����
	 *            �����R�˽���
	 * @return �ƶ����ӵ���ʼ���ꡣ
	 */
	public Point getStartPosition(String manual) {
		char name = manual.charAt(0);PieceCategory pc = getPieceCategory(manual);
		if (name == 'ǰ' || name == '��' || name == '��'
				|| name == '��' || name == '��' || name == '2'
				|| name == '3' || name == '4' ) {
			ArrayList<Point> list = new ArrayList<Point>(5);
			for (int i = 1; i <= X; i++) {
				for (int j = 1; j <= Y; j++) {
					ChessPiece piece = chessPoints[i][j].getPiece();
					if (piece != null && piece.getCategory().equals(pc)
							&& piece.getName().equals(playerName)) {
						list.add(new Point(i, j));
					}
				}
			}
			if(playerName.equals(RED_NAME)){
				if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
					if(name == 'ǰ'){return list.get(0);}
					else if(name == '��'){return list.get(1);}
					else if(name == '��'){return list.get(2);}
					else if(name == '��'){return list.get(3);}
					else if(name == '��'){return list.get(4);}
				}else{
					if(name == 'ǰ'){return list.get(4);}
					else if(name == '��'){return list.get(3);}
					else if(name == '��'){return list.get(2);}
					else if(name == '��'){return list.get(1);}
					else if(name == '��'){return list.get(0);}
				}
			}else{
				if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
					if(name == 'ǰ'){return list.get(4);}
					else if(name == '2'){return list.get(3);}
					else if(name == '3'){return list.get(2);}
					else if(name == '4'){return list.get(1);}
					else if(name == '��'){return list.get(0);}
				}else{
					if(name == 'ǰ'){return list.get(0);}
					else if(name == '2'){return list.get(1);}
					else if(name == '3'){return list.get(2);}
					else if(name == '4'){return list.get(3);}
					else if(name == '��'){return list.get(4);}
				}
			}

		}
		int startX = ChessUtil.ziToNum(manual.substring(1, 2));
		if (playerName.equals(RED_NAME)) {
			startX = 10 - startX;
		}
		int startY = getPieceYByCategory(startX, pc);
		return new Point(startX, startY);
	}
	/**
	 * �������ף����R�˽��ߣ�����ƶ����ӵ��յ�����
	 * 
	 * @param manual
	 *            ���ף����R�˽���
	 * @return �ƶ����ӵ��յ����ꡣ
	 */
	public Point getEndPosition(String manual) {
		Point pStart = getStartPosition(manual);
		String third = manual.substring(2, 3);// ��3����
		String fourth = String.valueOf((manual.charAt(3)));// ��4����
		PieceCategory pc = getPieceCategory(manual);
		int endX = 0;int endY = 0;
		int jbzpjs = ChessUtil.ziToNum(fourth);
		int msx = jbzpjs;
		if (playerName.equals(RED_NAME)) {
			msx = 10 - msx;
		}
		if (third.equals("��")) {
			switch (pc) {
			case JU:
			case BING:
			case ZU:
			case PAO:
			case JIANG:
			case SHUAI:
				endX = (int) pStart.getX();
				if (playerName.equals(RED_NAME)) {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						endY = (int) (pStart.getY() - jbzpjs);
					}else{
						endY = (int) (pStart.getY() + jbzpjs);
					}
					
				} else {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						endY = (int) (pStart.getY() + jbzpjs);
					}else{
						endY = (int) (pStart.getY() - jbzpjs);
					}
				}
				break;
			case MA:
				endX = msx;
				int startX = (int) pStart.getX();
				int xDistance = Math.abs(startX - endX);
				if (playerName.equals(RED_NAME)) {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						if (xDistance == 1) {
							endY = (int) (pStart.getY() - 2);
						} else {
							endY = (int) (pStart.getY() - 1);
						}
					}else{
						if (xDistance == 1) {
							endY = (int) (pStart.getY() + 2);
						} else {
							endY = (int) (pStart.getY() + 1);
						}
					}
					
				} else {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						if (xDistance == 1) {
							endY = (int) (pStart.getY() + 2);
						} else {
							endY = (int) (pStart.getY() + 1);
						}
					}else{
						if (xDistance == 1) {
							endY = (int) (pStart.getY() - 2);
						} else {
							endY = (int) (pStart.getY() - 1);
						}
					}
				}
				break;
			case HONGSHI:
				endX = msx;
				if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
					endY = (int) (pStart.getY() - 1);
				}else{
					endY = (int) (pStart.getY() + 1);
				}
				break;
			case HEISHI:
				endX = msx;
				if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
					endY = (int) (pStart.getY() + 1);
				}else{
					endY = (int) (pStart.getY() - 1);
				}
				break;
			case HEIXIANG:
				endX = msx;
				if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
					endY = (int) (pStart.getY() + 2);
				}else{
					endY = (int) (pStart.getY() - 2);
				}
				break;
			case HONGXIANG:
				endX = msx;
				if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
					endY = (int) (pStart.getY() - 2);
				}else{
					endY = (int) (pStart.getY() + 2);
				}
				break;

			}
		} else if (third.equals("��")) {
			switch (pc) {
			case JU:
			case ZU:
			case BING:
			case PAO:
			case JIANG:
			case SHUAI:
				if (playerName.equals(RED_NAME)) {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						endY = (int) (pStart.getY() + jbzpjs);
					}else{
						endY = (int) (pStart.getY() - jbzpjs);
					}
					
				} else {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						endY = (int) (pStart.getY() - jbzpjs);
					}else{
						endY = (int) (pStart.getY() + jbzpjs);
					}
				}
				break;
			case MA:
				endX = msx;
				int startX = (int) pStart.getX();
				int xDistance = Math.abs(startX - endX);
				if (playerName.equals(RED_NAME)) {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						if (xDistance == 1) {
							endY = (int) (pStart.getY() + 2);
						} else {
							endY = (int) (pStart.getY() + 1);
						}
					}else{
						if (xDistance == 1) {
							endY = (int) (pStart.getY() - 2);
						} else {
							endY = (int) (pStart.getY() - 1);
						}
					}
				} else {
					if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
						if (xDistance == 1) {
							endY = (int) (pStart.getY() - 2);
						} else {
							endY = (int) (pStart.getY() - 1);
						}
					}else{
						if (xDistance == 1) {
							endY = (int) (pStart.getY() + 2);
						} else {
							endY = (int) (pStart.getY() + 1);
						}
					}
				}
				break;
			case HONGSHI:
				endX = msx;
				if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
					endY = (int) (pStart.getY() + 1);
				}else{
					endY = (int) (pStart.getY() - 1);
				}
				break;
			case HEISHI:
				endX = msx;
				if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
					endY = (int) (pStart.getY() - 1);
				}else{
					endY = (int) (pStart.getY() + 1);
				}
				break;
			case HEIXIANG:
				endX = msx;
				if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
					endY = (int) (pStart.getY() - 2);
				}else{
					endY = (int) (pStart.getY() + 2);
				}
				break;
			case HONGXIANG:
				endX = msx;
				if(palyerFirst == null || palyerFirst.equals(PALYER_FIRST)){
					endY = (int) (pStart.getY() + 2);
				}else{
					endY = (int) (pStart.getY() - 2);
				}
				break;
			}
		} else if (third.equals("ƽ")) {
			endX = msx;
			endY = (int) pStart.getY();
		}
		return new Point(endX, endY);
	}

	/**
	 * �������ף����R�˽��ߣ���ȡ�ƶ����ӵ�����
	 * 
	 * @param manual
	 *            ���ף����R�˽���
	 * @return �ƶ����ӵ�����
	 */
	private PieceCategory getPieceCategory(String manual) {
		String name = manual.substring(0, 1);
		if (name.equals("ǰ") || name.equals("��") ||
				name.equals("��") || name.equals("��") || name.equals("��") || 
				name.equals("2") || name.equals("3") || name.equals("4")) {
			name = manual.substring(1, 2);
		}

		return PieceUtil.getPieceCategory(name);
	}
	
	/**
	 * �õ�������ѡ�����ӵ���������
	 * @param piece ѡ�е�����
	 * @return ������Ӳ�������,����null,�ڷ�����������
	 */
	public Point getPiecePoint(ChessPiece piece){
		for (int i = 1; i <= X; i++) {
			for (int j = 1; j <= Y; j++) {
				ChessPiece p = chessPoints[i][j].getPiece();
				if (p != null && chessPoints[i][j].hasPiece()
						&& p.equals(piece)) {
					
					return new Point(i, j);
				}
			}
		}
		return null;
	}
	/**
	 * ��������̵�����ת��Ϊ�����ϵ����꣬�����Է����ӵ�����
	 * @param x �����ĺ�����
	 * @param y ������������
	 * @return �������ĵ����ת�����������꣬���򷵻�null
	 */
	public Point getBoardPoint(double x,double y){
		for (int i = 1; i <= X; i++) {
			for (int j = 1; j <= Y; j++) {
				double x0 = chessPoints[i][j].getX();
				double y0 = chessPoints[i][j].getY();

				if ((Math.abs(x0 - x) <= PIECE_WIDTH / 2)
						&& (Math.abs(y0 - y) <= PIECE_HEIGHT / 2)) {
					// �յ�����
					return new Point(i,j);
				}
			}
		}
		return null;
	}
	/**
	 * ������˸�߳�
	 */
	@Override
	public void run() {
		while (true) {
			try {
				if (needWink) {
					//System.out.println("needWink:"+needWink);
					winkPiece.setVisible(false);
					Thread.sleep(600);
					winkPiece.setVisible(true);
					Thread.sleep(600);
				} else {
					//����˸ʱ�����߳�
					//System.out.println("needWink:"+needWink);
					Thread.sleep(400);
				}
			} catch (InterruptedException ex) {
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void clearTipPoints() {
		tipPoints.clear();
	}

	public String getPlayerName() {
		return playerName;
	}

	public Thread getWinkThread() {
		return winkThread;
	}

	public ChessPoint[][] getChessPoints() {
		return chessPoints;
	}
	public void setChessPoints(ChessPoint[][] chessPoints) {
		this.chessPoints = chessPoints;
	}
	public void addPiece(ChessPiece piece) {
		if (piece == null) {
			return;
		}
		add(piece);
	}

	public void removePiece(ChessPiece piece) {
		if (piece == null) {
			return;
		}
		remove(piece);

	}

	public void setMoveFlag(boolean moveFlag) {
		this.moveFlag = moveFlag;

	}

	public void setMoveFlagPoints(Point start, Point end) {
		movePoints[0] = start;
		movePoints[1] = end;
	}

	protected abstract MouseAdapter getMouseAdapter();

	/**
	 * �ƶ�����
	 * 
	 * @param movePiece
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 */
	public void movePiece(ChessPiece movePiece, int startX, int startY,
			int endX, int endY) {
		System.out.println("��Ҫ�ƶ����ӣ����꣺(" + startX + "," + startY + "),(" + endX
				+ "," + endY + ")");
		ChessPiece pieceRemoved = chessPoints[endX][endY].getPiece();
		if (pieceRemoved != null) {
			chessPoints[endX][endY].removePiece(pieceRemoved, this);
		}
		chessPoints[endX][endY].setPiece(movePiece, this);
		chessPoints[startX][startY].setHasPiece(false);
		System.out.println("1129�ƶ���" + movePiece.getCategory());

		setMoveFlag(true);
		movePoints[0] = new Point(endX, endY);
		movePoints[1] = new Point(startX, startY);
		clearTipPoints();

		validate();
		repaint();

	}

	/**
	 * ����һ�����׼�¼
	 * 
	 * @param movePiece
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 */
	public void addChessRecord(ChessPiece movePiece, int startX, int startY,
			int endX, int endY) {

		if (movePiece == null) {
			System.out.println("movePiece == null");
			return;
		}
		ManualItem moveRecord = new ManualItem();
		ChessPiece pieceRemoved = chessPoints[endX][endY].getPiece();

		moveRecord.setMovePieceId(movePiece.getId());
		if (pieceRemoved != null) {
			moveRecord.setEatedPieceId(pieceRemoved.getId());
		} else {
			moveRecord.setEatedPieceId(null);
		}

		moveRecord.setMoveStep(new MoveStep(new Point(startX, startY),
				new Point(endX, endY)));
		chessManual.addManualItem(moveRecord,palyerFirst);

	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setNeedWink(boolean needWink) {
		this.needWink = needWink;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPalyerFirst() {
		return palyerFirst;
	}

	public void setPalyerFirst(String palyerFirst) {
		this.palyerFirst = palyerFirst;
	}
}
