
package cn.wang.chinesechess.print.all;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import cn.wang.chinesechess.ChessGUI;
import cn.wang.chinesechess.ChessUtil;
import cn.wang.chinesechess.config.NAME;
import cn.wang.chinesechess.config.PropertyReader;
import cn.wang.chinesechess.core.ChessManual;
import cn.wang.chinesechess.core.ChessPiece;
import cn.wang.chinesechess.core.ChessRule;
import cn.wang.chinesechess.core.ManualItem;
import cn.wang.chinesechess.core.MoveStep;
import cn.wang.chinesechess.core.PieceUtil;
import cn.wang.chinesechess.save.GameRecord;
import cn.wang.chinesechess.save.ISaveManual;
import cn.wang.chinesechess.save.SaveAsDialog;
import cn.wang.chinesechess.save.SaveDialog;

/**
 * ȫ�ִ���������
 * 
 * @author wanghualiang
 */
public class PrintAllGUI extends JFrame implements ActionListener, ISaveManual,
		NAME {

	private static final long serialVersionUID = 266L;
	/**private JMenu �˵���*/
	private JMenu fileMenu, manualMenu, settingMenu, helpMenu;
	/**private JMenuItem �ļ��˵�ѡ��*/
	private JMenuItem newManual, saveManual, saveManualAs, exitGame;
	/**private JMenuItem ���ײ˵�ѡ��*/
	private JMenuItem reprintManual, inputManual, undoManual;
	/**private JMenuItem �����˵�ѡ��*/
	private JMenuItem setting, helpContent, aboutGame, welcome;
	/**private JMenuItem ���ò˵�ѡ��*/
	private JCheckBoxMenuItem bgSound,gmSound;
	/**private JButton ������ʾ��ť*/
	private JButton prev, next, auto, first, last;
	/**private JButton ���ܰ�ť*/
	private JButton newButton, save, saveAs, reprint, inputManualButton, undo, set;
	/**private ArrayList<ManualItem> ���׼�¼*/
	private ArrayList<ManualItem> records;
	
	/**PrintAllBoard board ����*/
	private PrintAllBoard board;
	/**ChessManual chessManual ����*/
	private ChessManual chessManual;
	/**JPanel gameStatusPanel ��Ϸ״̬���*/
	private JPanel gameStatusPanel;
	/**private JPanel ���������ʾ��ť�Ĺ�����*/
	private JPanel manualTools;
	
	/**boolean �����Ƿ�������壬Ĭ�Ͽ���*/
	protected boolean canPaly = true;
	/**private JLabel ��Ϸ״̬*/ 
	private JLabel gameStatusContent, gameStatusIcon;
	/**private JPanel ��Ź��ܰ�ť�Ĺ�����*/
	private JPanel toolBar = new JPanel();

	/**public int ��ǰ������ */
	public int curIndex = -1;
	/**private JScrollPane ������ʾ������� */
	private JScrollPane manualScroll;
	/**public int �����Զ���ʾ��ʱ���� */
	private int time = 1000;
	/**private JList �����б� */
	private JList manual;
	/**private Vector ���׶�Ӧ���������� */
	private Vector descs;

	/**Cursor handCursor ��ť���õ�����ͼ��*/
	private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

	/**
	 * ���캯��
	 */
	public PrintAllGUI() {

		board = new PrintAllBoard(this);
		board.initChess();

		chessManual = board.chessManual;
		manualScroll = chessManual.manualScroll;
		manual = chessManual.manualList;
		descs = chessManual.descs;
		records = chessManual.getManualItems();
		initMenuBar();
		initButtons();
		initPanels();
		handleKeyEvent();
		ChessUtil.setLoop(true);
		ChessUtil.setStart(true);

		setSize(670, 660);
		setTitle("�й�����---ȫ�ִ���");
		setResizable(false);
		setIconImage(ChessUtil.getAppIcon());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			// ��ӦĬ�ϵ��˳��¼�
			public void windowClosing(WindowEvent e) {
				handleExitGame();
			}

		});
	}

	/**
	 * ��ʼ���˵�
	 * 
	 */
	private void initMenuBar() {
		// ����˵�
		JMenuBar bar = new JMenuBar();

		// �����ļ��˵�
		fileMenu = new JMenu("�ļ�(G)");

		newManual = new JMenuItem("�½�", ChessUtil.getImageIcon("newManual.gif"));
		saveManual = new JMenuItem("����", ChessUtil.getImageIcon("save.gif"));
		saveManualAs = new JMenuItem("���", ChessUtil.getImageIcon("saveas.gif"));
		exitGame = new JMenuItem("�˳�", ChessUtil.getImageIcon("exit.gif"));
		// ���ļ��˵������Ӳ˵���
		fileMenu.add(newManual);
		fileMenu.add(saveManual);
		fileMenu.add(saveManualAs);
		fileMenu.add(exitGame);

		// �������ײ˵�
		manualMenu = new JMenu("����(M)");
		reprintManual = new JMenuItem("���´���",ChessUtil.getImageIcon("reprint.gif"));
		inputManual = new JMenuItem("��������",ChessUtil.getImageIcon("inputManual.gif"));
		undoManual = new JMenuItem("����", ChessUtil.getImageIcon("undo.gif"));
		// �����ײ˵�����Ӳ˵���
		manualMenu.add(reprintManual);
		manualMenu.add(inputManual);
		manualMenu.add(undoManual);
		
		// �������ò˵�
		settingMenu = new JMenu("����(S)");
		bgSound = new JCheckBoxMenuItem("��������");
		gmSound = new JCheckBoxMenuItem("��Ϸ��Ч");
		bgSound.setSelected(true);
		gmSound.setSelected(true);
		// �����ò˵�����Ӳ˵���
		settingMenu.add(bgSound);
		settingMenu.add(gmSound);

		// ��������˵�
		helpMenu = new JMenu("����(H)");
		welcome = new JMenuItem("��ӭ", ChessUtil.getImageIcon("welcome.gif"));
		helpContent = new JMenuItem("��������", ChessUtil.getImageIcon("help.gif"));
		aboutGame = new JMenuItem("������Ϸ", ChessUtil.getImageIcon("info.gif"));
		// ������˵�����Ӳ˵���
		helpMenu.add(welcome);
		helpMenu.add(helpContent);
		helpMenu.add(aboutGame);
		// ��˵�������Ӳ˵�
		bar.add(fileMenu);
		bar.add(manualMenu);
		bar.add(settingMenu);
		bar.add(helpMenu);

		setJMenuBar(bar);

		// ���ÿ�ݼ�
		newManual.setAccelerator(KeyStroke.getKeyStroke('N',InputEvent.CTRL_DOWN_MASK));
		saveManual.setAccelerator(KeyStroke.getKeyStroke('S',InputEvent.CTRL_DOWN_MASK));
		saveManualAs.setAccelerator(KeyStroke.getKeyStroke('U',InputEvent.CTRL_DOWN_MASK));
		exitGame.setAccelerator(KeyStroke.getKeyStroke('E',InputEvent.CTRL_DOWN_MASK));

		reprintManual.setAccelerator(KeyStroke.getKeyStroke('P',InputEvent.CTRL_DOWN_MASK));
		inputManual.setAccelerator(KeyStroke.getKeyStroke('I',InputEvent.CTRL_DOWN_MASK));
		undoManual.setAccelerator(KeyStroke.getKeyStroke('U',InputEvent.CTRL_DOWN_MASK));
		
		welcome.setAccelerator(KeyStroke.getKeyStroke('W',InputEvent.CTRL_DOWN_MASK));
		helpContent.setAccelerator(KeyStroke.getKeyStroke('H',InputEvent.CTRL_DOWN_MASK));
		aboutGame.setAccelerator(KeyStroke.getKeyStroke('A',InputEvent.CTRL_DOWN_MASK));

		bgSound.setAccelerator(KeyStroke.getKeyStroke('B',InputEvent.CTRL_DOWN_MASK));
		gmSound.setAccelerator(KeyStroke.getKeyStroke('T',InputEvent.CTRL_DOWN_MASK));

		// �������Ƿ�
		fileMenu.setMnemonic(KeyEvent.VK_G);
		manualMenu.setMnemonic(KeyEvent.VK_M);
		settingMenu.setMnemonic(KeyEvent.VK_S);
		helpMenu.setMnemonic(KeyEvent.VK_H);

		// ע�������
		newManual.addActionListener(this);
		saveManual.addActionListener(this);
		saveManualAs.addActionListener(this);
		exitGame.addActionListener(this);

		inputManual.addActionListener(this);
		reprintManual.addActionListener(this);
		undoManual.addActionListener(this);

		gmSound.addActionListener(this);
		bgSound.addActionListener(this);

		welcome.addActionListener(this);
		helpContent.addActionListener(this);
		aboutGame.addActionListener(this);

	}

	/**
	 * ��ʼ�����
	 * 
	 */
	private void initPanels() {
		// �����ұߵ����
		JPanel rightPanel = new JPanel(new BorderLayout());
		// �����������
		JPanel recordsPanel = new JPanel(new BorderLayout());

		TitledBorder recordsBorder = new TitledBorder(
				PropertyReader.get("CHESS_MESSAGE_TOOLTIP"));
		recordsPanel.setBorder(recordsBorder);
		recordsPanel.setPreferredSize(new Dimension(240, 330));
		recordsPanel.add(BorderLayout.CENTER, chessManual);

		// ������
		manualTools = new JPanel(new FlowLayout(FlowLayout.CENTER));
		// manualTools.setPreferredSize(new Dimension(220, 20));
		manualTools.add(first);
		manualTools.add(prev);
		manualTools.add(auto);
		manualTools.add(next);
		manualTools.add(last);

		recordsPanel.add(BorderLayout.SOUTH, manualTools);
		rightPanel.add(BorderLayout.CENTER, recordsPanel);

		JSplitPane splitH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, board,
				rightPanel);
		splitH.setDividerSize(5);
		splitH.setDividerLocation(450);

		// ��Ϸ״̬���
		gameStatusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		gameStatusPanel.setPreferredSize(new Dimension(660, 80));
		// gameStatusPanel.setBackground(new Color(122, 146, 170));
		gameStatusIcon = new JLabel(ChessUtil.getImageIcon("hongshuai.gif"));
		// ��Ϸ״̬��
		gameStatusContent = new JLabel("�췽������");
		gameStatusContent.setFont(new Font("����", Font.PLAIN, 16));

		TitledBorder gameStatusBorder = new TitledBorder("��Ϸ״̬");
		gameStatusBorder.setTitleColor(Color.RED);
		gameStatusBorder.setTitleFont(new Font("����", Font.PLAIN, 16));
		gameStatusPanel.setToolTipText("��Ϸ״̬");
		gameStatusPanel.setBorder(gameStatusBorder);
		gameStatusPanel.add(gameStatusIcon);
		gameStatusPanel.add(gameStatusContent);

		initToolBar();

		add(BorderLayout.NORTH, toolBar);
		add(BorderLayout.CENTER, splitH);
		add(BorderLayout.SOUTH, gameStatusPanel);

	}

	/**
	 * ��ʼ��������
	 * 
	 */
	private void initToolBar() {
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		toolBar.setLayout(flowLayout);

		toolBar.add(newButton);
		toolBar.add(save);
		toolBar.add(saveAs);

		toolBar.add(reprint);
		toolBar.add(undo);
		toolBar.add(inputManualButton);

		toolBar.add(set);

	}

	/**
	 * ��ʼ����ť
	 * 
	 */
	private void initButtons(){
		Dimension iconSize = new Dimension(16, 16);
		prev = new JButton(ChessUtil.getImageIcon("prev.gif"));
		prev.addActionListener(this);
		prev.setToolTipText("��һ��");
		prev.setCursor(new Cursor(Cursor.HAND_CURSOR));
		prev.setPreferredSize(iconSize);

		next = new JButton(ChessUtil.getImageIcon("next.gif"));
		next.addActionListener(this);
		next.setToolTipText("��һ��");
		next.setCursor(new Cursor(Cursor.HAND_CURSOR));
		next.setPreferredSize(iconSize);

		first = new JButton(ChessUtil.getImageIcon("first.gif"));
		first.addActionListener(this);
		first.setToolTipText("��һ��");
		first.setCursor(new Cursor(Cursor.HAND_CURSOR));
		first.setPreferredSize(iconSize);

		last = new JButton(ChessUtil.getImageIcon("last.gif"));
		last.addActionListener(this);
		last.setToolTipText("���һ��");
		last.setCursor(new Cursor(Cursor.HAND_CURSOR));
		last.setPreferredSize(iconSize);

		auto = new JButton(ChessUtil.getImageIcon("auto.gif"));
		auto.addActionListener(this);
		auto.setToolTipText("�Զ���ʾ");
		auto.setPreferredSize(iconSize);
		auto.setCursor(new Cursor(Cursor.HAND_CURSOR));

		Insets insets = new Insets(1, 1, 1, 1);
		
		newButton = new JButton(ChessUtil.getImageIcon("newManual.gif"));
		newButton.setToolTipText("�½�����");
		newButton.addActionListener(this);
		newButton.setCursor(handCursor);
		newButton.setMargin(insets);
		
		reprint = new JButton(ChessUtil.getImageIcon("reprint.gif"));
		reprint.setToolTipText("���´���");
		reprint.addActionListener(this);
		// reprint.setPreferredSize(dimension);
		reprint.setCursor(handCursor);
		reprint.setMargin(insets);

		save = new JButton(ChessUtil.getImageIcon("save.gif"));
		save.addActionListener(this);
		save.setToolTipText("��������");
		// save.setPreferredSize(dimension);
		save.setCursor(handCursor);
		save.setMargin(insets);
		save.setOpaque(true);

		saveAs = new JButton(ChessUtil.getImageIcon("saveas.gif"));
		saveAs.addActionListener(this);
		saveAs.setToolTipText("�������");
		// saveAs.setPreferredSize(dimension);
		saveAs.setCursor(handCursor);
		saveAs.setMargin(insets);

		undo = new JButton(ChessUtil.getImageIcon("undo.gif"));
		undo.addActionListener(this);
		undo.setToolTipText("����");
		undo.setCursor(handCursor);
		undo.setMargin(insets);
		
		inputManualButton = new JButton(ChessUtil.getImageIcon("inputManual.gif"));
		inputManualButton.setToolTipText("��������");
		inputManualButton.addActionListener(this);
		inputManualButton.setCursor(handCursor);
		inputManualButton.setMargin(insets);

		set = new JButton(ChessUtil.getImageIcon("welcome.gif"));
		set.setToolTipText("����");
		set.addActionListener(this);
		set.setCursor(handCursor);
		set.setMargin(insets);

		

	}

	/**
	 * ��Ӧ�¼�
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		// ��һ��
		if (source == next) {
			next();
		} else if (source == prev) {
			prev();
		} else if (source == first) {
			first();
		} else if (source == last) {
			last();
		} else if (source == auto) {
			auto();
		} else if (source == helpContent) {
			ChessUtil.showHelpDialog();
		} else if (source == welcome) {
			ChessUtil.showWelcomeDialog();
		} else if (source == aboutGame) {
			ChessUtil.showAboutDialog();
		}

		// ���´���
		else if (source == reprint || source == reprintManual) {
			int result = JOptionPane.showConfirmDialog(this, "����Ҫ���浱ǰ��������",
					"����Ҫ���浱ǰ��������", JOptionPane.YES_NO_OPTION);

			if (result == JOptionPane.YES_OPTION) {
				SaveDialog dialog = new SaveDialog(this);
				dialog.setVisible(true);
			}
			int size = chessManual.getManualItems().size();
			for (int index = 0; index < size; index++) {
				undo();
			}
		}

		// ����
		else if (source == undo || source == undoManual) {
			last();
			undo();

		} else if (source == save || source == saveManual) {
			SaveDialog dialog = new SaveDialog(this);
			dialog.setVisible(true);

		} else if (source == saveAs || source == saveManualAs) {
			SaveAsDialog dialog = new SaveAsDialog(this);
			dialog.setVisible(true);
		} else if (source == exitGame) {
			handleExitGame();

		} else if (source == inputManualButton || source == inputManual) {
			Icon icon = null;
			if(board.getPlayerName().equals(RED_NAME)){
				 icon = ChessUtil.getImageIcon("hongshuai.gif");
			}else{
				icon = ChessUtil.getImageIcon("heijiang.gif");
			}
			
			String manual = (String) JOptionPane.showInputDialog(this, "����������", "����������",
					JOptionPane.PLAIN_MESSAGE,icon,null,null);
			if (manual != null) {
				manual = manual.trim();// ȥ��ǰ���հ׺�β���հ�
			}
			movePieceByManual(manual);

		} else if (source == newButton || source == newManual) {
			PrintAllGUI newPrint = new PrintAllGUI();
			newPrint.setVisible(true);
		} else if (source == bgSound){
			ChessUtil.setLoop(bgSound.isSelected());
		} else if (source == gmSound){
			ChessUtil.setStart(gmSound.isSelected());
		}

	}

	/**
	 * ���壬����һ��
	 */
	private void undo() {
		canPaly = true;
		boolean flag = chessManual.removeManualItem();
		if (flag) {
			if (board.getPlayerName().equals(RED_NAME)) {
				board.changeSide();
				updateGameStatus(2, "�ֵ��ڷ���ඣ�");
			} else {
				board.changeSide();
				updateGameStatus(1, "�ֵ��췽��ඣ�");
			}
			curIndex--;
		}
	}

	/**
	 * �������ף����R�˽��ߣ��ƶ�����
	 * 
	 * @param manual
	 */
	private void movePieceByManual(String manual) {
		board.setNeedWink(false);
		if(!canPaly){
			JOptionPane.showConfirmDialog(this, "��Ϸ�Ѿ�����", "��ʾ",
					JOptionPane.YES_OPTION);
			return;
		}
		if (manual != null && manual.length() == 4) {
			ChessPiece movePiece = board.getMovePiece(manual);
			if (movePiece == null || !movePiece.getName().equals(board.getPlayerName())) {
				return;
			}
			//ChessPiece removedPiece = board.getRemovedPiece(manual);
			Point pStart = board.getStartPosition(manual);
			Point pEnd = board.getEndPosition(manual);

			int startX = (int) pStart.getX();
			int startY = (int) pStart.getY();
			int endX = (int) pEnd.getX();
			int endY = (int) pEnd.getY();
			System.out.println(movePiece.getCategory() + " :" + startX + startY
					+ endX + endY);
			boolean rule = ChessRule.allRule(movePiece, startX, startY, endX,
					endY, board.chessPoints, null);
			System.out.println("�ܷ��ƶ����ӣ�" + rule);
			if (rule) {
				//���һ��������Ϣ��Ҫ���ƶ�֮ǰ��ӣ����򱻳Ե��������޷���¼
				board.addChessRecord(movePiece, startX, startY, endX, endY);
				//�ƶ�����
				board.movePiece(movePiece, startX, startY, endX, endY);
				// �����൱ǰ����+1
				curIndex++;

				// �������ӱ�������
				ChessUtil.playSound("eat.wav");

				validate();
				repaint();

				// �����������֣�ʵ��һ�������ߺ��2��������
				board.changeSide();

			}
		}
	}

	private void auto() {
		first();
		DemoThread auto = new DemoThread();
		auto.start();
	}

	/**
	 * �ƶ�����һ��
	 * 
	 */
	private void first() {
		while (curIndex != -1) {
			prev();
		}
	}

	/**
	 * ��ʾindexָ����һ��
	 * 
	 * @param index
	 */
	private void step(int index) {
		if (index < 0) {
			return;
		}

		ManualItem moveRecord = records.get(index);
		MoveStep step = moveRecord.getMoveStep();
		Point pStart = step.start;
		Point pEnd = step.end;
		int startI = pStart.x;
		int startJ = pStart.y;
		int endI = pEnd.x;
		int endJ = pEnd.y;

		ChessPiece piece = board.chessPoints[startI][startJ].getPiece();
		board.movePiece(piece, startI, startJ, endI, endJ);

		// ��ʾʱ�����ƶ����ӵ�
		repaint();
		validate();
	}

	/**
	 * ��һ��
	 * 
	 */

	private void prev() {
		if (curIndex == -1) {
			return;
		}

		int size = records.size();

		// �������̽���
		ManualItem record = new ManualItem();
		MoveStep moveStep;

		ChessPiece eatedPiece;
		int startI = 0;
		int startJ = 0;
		int endI = 0;
		int endJ = 0;

		if (size > 0 && curIndex < size && curIndex >= 0) {
			// ���ָ����Ԫ��
			record = records.get(curIndex);
			eatedPiece = PieceUtil.createPiece(record.getEatedPieceId());

			moveStep = record.getMoveStep();
			startI = moveStep.start.x;
			startJ = moveStep.start.y;
			endI = moveStep.end.x;
			endJ = moveStep.end.y;

			// ��һ��û�г�����
			if (eatedPiece == null) {
				System.out.println("û������");

				ChessPiece piece = board.chessPoints[endI][endJ].getPiece();
				board.chessPoints[startI][startJ].setPiece(piece, board);
				board.chessPoints[endI][endJ].setHasPiece(false);

			}
			// ��һ����������
			else {
				ChessPiece piece = board.chessPoints[endI][endJ].getPiece();
				board.chessPoints[startI][startJ].setPiece(piece, board);
				board.chessPoints[endI][endJ].setPiece(eatedPiece, board);

			}
		}

		// ����ʱ��������������ƶ�ʱ��ͬ
		if (curIndex >= 1) {
			record = (ManualItem) records.get(curIndex - 1);
			moveStep = record.getMoveStep();
			startI = moveStep.start.x;
			startJ = moveStep.start.y;
			endI = moveStep.end.x;
			endJ = moveStep.end.y;
		}

		// ����ƶ���Ӧ�û�2����ʾ��
		board.setMoveFlag(true);
		board.movePoints[0] = new Point(endI, endJ);
		board.movePoints[1] = new Point(startI, startJ);

		curIndex--;
		scrollToView();

		repaint();
		validate();
	}

	/**
	 * �������б��������ǰ��
	 */
	private void scrollToView() {
		if (curIndex >= 0 && curIndex < descs.size()) {
			// ѡ�е�ǰ�У���ʾ���
			System.out.println("Ӧ��ѡ�е�N�У�" + curIndex);
			manual.setSelectedIndex(curIndex);

			int lastIndex = curIndex;
			Rectangle rect = manual.getCellBounds(lastIndex, lastIndex);
			manualScroll.getViewport().scrollRectToVisible(rect);

		}
		if (curIndex == -1) {
			// �˻ص�û���κ������ƶ���״̬ʱ�����û��ƶ����
			board.setMoveFlag(false);
			/*
			 * ��curIndex == -1ʱ��Ӧ�ò�ѡ���κ�һ��,
			 * ��manual.setSelectedIndex(-1)����ʱ�б��ѡ���κ�һ��
			 * manual.setListData(descs);���Իָ���Ĭ��״̬��ѡ���κ�һ��
			 */
			System.out.println("û��ѡ���κ�һ�У�" + curIndex);
			manual.setListData(descs);
		}
	}

	/**
	 * ��Ӧ�����¼�
	 * 
	 */
	private void handleKeyEvent() {
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			public void eventDispatched(AWTEvent event) {
				if (((KeyEvent) event).getID() == KeyEvent.KEY_PRESSED) {
					int code = ((KeyEvent) event).getKeyCode();
					if (code == KeyEvent.VK_F1) {
						ChessUtil.showHelpDialog();
					} else if (code == KeyEvent.VK_DOWN) {
						next();
						System.out.println("next");
					} else if (code == KeyEvent.VK_UP) {
						prev();
						System.out.println("prev");
					} else if (code == KeyEvent.VK_HOME) {
						first();
						System.out.println("first");
					} else if (code == KeyEvent.VK_END) {
						last();
						System.out.println("last");
					} else if (code == KeyEvent.VK_ENTER) {
						auto();
					}

				}
			}
		}, AWTEvent.KEY_EVENT_MASK);

	}

	/**
	 * ����ر��¼�
	 * 
	 */
	private void handleExitGame() {
		int result = JOptionPane.showConfirmDialog(this, "��ȷ��Ҫ�˳�ô��", "ȷ���˳�",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			if (board.getWinkThread() != null) {
				board.getWinkThread().interrupt();
				System.out.println("�ر���...");
			}
			dispose();
		}
	}

	/**
	 * ������Ϸ״̬
	 * 
	 * @param state
	 *            ͼ���ʶ
	 * @param content
	 *            ��Ϸ״̬��������
	 */
	protected void updateGameStatus(int state, String content) {

		switch (state) {
		case 1:
			gameStatusIcon.setIcon(ChessUtil.getImageIcon("hongshuai.gif"));
			gameStatusIcon.setToolTipText("�ֵ��췽��ඣ�");
			break;
		case 2:
			gameStatusIcon.setIcon(ChessUtil.getImageIcon("heijiang.gif"));
			gameStatusIcon.setToolTipText("�ֵ��ڷ���ඣ�");
			break;
		default:
			break;
		}

		if (content != null && !content.equals("")) {
			gameStatusContent.setText(content);
		}
	}

	private class DemoThread extends Thread {

		public DemoThread() {
			System.out.println("�ո�д�����̹߳��������");
		}

		public void run() {
			System.out.println("�ո�д�����߳�������");
			int size = records.size();

			while (curIndex < size - 1) {
				try {
					Thread.sleep(time);

					next();
					if (curIndex >= size - 1) {
						this.join(100);
						break;
					}
				} catch (InterruptedException ie) {
					break;
				}
			}
		}

	}

	/**
	 * �ƶ������һ��
	 * 
	 */
	protected  void last() {
		while (curIndex != descs.size() - 1) {
			next();
		}
	}

	/**
	 * ��ʾ��ǰ������һ��
	 * 
	 */
	protected void next() {
		if (curIndex == descs.size() - 1) {
			return;
		}
		curIndex++;
		if (curIndex < records.size()) {
			step(curIndex);
		}
		scrollToView();
	}
	
	/**
	 * ��ʾ��Ϸ�Ѿ�����
	 * @param canPaly �����Ϸ�Ƿ���� trueδ���� ��false����
	 * @param category ���������������࣬˧��
	 */
	protected void gameOver(boolean canPaly,PieceCategory category){
		if(!canPaly){
			this.canPaly = canPaly;
			if(category.equals(PieceCategory.JIANG)){
				JOptionPane.showConfirmDialog(this, "��Ϸ����,�췽��ʤ", "��ʾ",
						JOptionPane.YES_OPTION);
			}else{
				JOptionPane.showConfirmDialog(this, "��Ϸ����,�ڷ���ʤ", "��ʾ",
						JOptionPane.YES_OPTION);
			}			
		}
	}

	/**
	 * ȫ�ִ��ײ��Գ������
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PrintAllGUI printManual = new PrintAllGUI();
		printManual.setVisible(true);

	}

	/**
	 * ���� Javadoc��
	 */
	@Override
	public ArrayList<String> getSavePaths() {
		ArrayList<String> paths = new ArrayList<String>();
		String path = "src/manuals/whole/";
		String path2 = "src/manuals/whole/";

		paths.add(path);
		paths.add(path2);
		return paths;
	}

	@Override
	public GameRecord getGameRecord() {
		GameRecord gameRecord = new GameRecord(ManualType.PRINT_WHOLE,
				ChessUtil.getDateAndTime(), "",
				board.chessManual.getManualItems(), board.chessManual.descs,
				null, PALYER_FIRST);
		return gameRecord;
	}

}
