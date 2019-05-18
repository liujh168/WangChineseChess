
package cn.wang.chinesechess.load;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import cn.wang.chinesechess.ChessUtil;
import cn.wang.chinesechess.HelpDialog;
import cn.wang.chinesechess.config.NAME;
import cn.wang.chinesechess.config.PropertyReader;
import cn.wang.chinesechess.config.NAME.ManualType;
import cn.wang.chinesechess.core.ChessPiece;
import cn.wang.chinesechess.core.ManualItem;
import cn.wang.chinesechess.core.ManualUtil;
import cn.wang.chinesechess.core.MoveStep;
import cn.wang.chinesechess.core.PieceUtil;
import cn.wang.chinesechess.print.part.Position;
import cn.wang.chinesechess.save.GameRecord;
import cn.wang.chinesechess.save.ISaveManual;
import cn.wang.chinesechess.save.SaveAsDialog;
import cn.wang.chinesechess.save.SaveDialog;

/**
 * ��ʾ--����������ʾ���
 * 
 * @author wanghualiang
 */
public class ChessShowGUI extends JFrame implements ActionListener, NAME,
		ISaveManual {

	private static final long serialVersionUID = 266L;

	private JMenu fileMenu, manualMenu, settingMenu, helpMenu;

	private JMenuItem openManual, saveManual, saveManualAs, exitGame,
			openMainGUI;

	private JMenuItem firstMenuItem, prevMenuItem, autoMenuItem, nextMenuItem,
			lastMenuItem, manualListMenuItem;

	private JMenuItem setting, helpContent, aboutGame, welcome;

	private JCheckBoxMenuItem bgSound,gmSound;

	/** ��һ������һ�����Զ���ʾ����0�������һ��*/
	private JButton prev, next, auto, first, last;

	/** ���롢���桢��桢���á��˳�������*/
	private JButton read, save, saveAs, set, manualList;

	/** ��ʾ�����������ı���*/
	private JTextArea area;
	
	/**������*/
	private JPanel toolBar = new JPanel();

	/** ��ǰ����*/
	private int curIndex = -1;

	/** ����*/
	private LoadBoard board;

	/** ��ʾ���׵��б��*/
	private JList manual;

	/** �����б��Ĺ������*/
	private JScrollPane manualScroll;

	/** �Զ���ʾ��ʱ��ʱ����*/
	private int time = 1000;
	/**��Ϸ��¼����*/
	private GameRecord gameRecord;

	/**
	 * @param gameRecord
	 *            ��Ϸ��¼����
	 */
	public ChessShowGUI(GameRecord gameRecord) {
		this.gameRecord = gameRecord;
		board = new LoadBoard();
		initMenuBar();
		initButtons();
		initPanels();
		initPieces();
		initGui();
		
	}

	/**
	 * ���ý�������ԣ����С�����⡢ͼ���
	 */
	private void initGui() {
		setSize(660, 620);
		setTitle("�й�����---��ʾ����");
		setIconImage(ChessUtil.getAppIcon());
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

		openManual = new JMenuItem("����", ChessUtil.getImageIcon("open.gif"));
		saveManual = new JMenuItem("����", ChessUtil.getImageIcon("save.gif"));
		saveManualAs = new JMenuItem("���", ChessUtil.getImageIcon("saveas.gif"));
		exitGame = new JMenuItem("�˳�", ChessUtil.getImageIcon("exit.gif"));
		/*openMainGUI = new JMenuItem("������", ChessUtil
				.getImageIcon("mainGUI.gif"));*/
		// ���ļ��˵������Ӳ˵���
		fileMenu.add(openManual);
		fileMenu.add(saveManual);
		fileMenu.add(saveManualAs);
		fileMenu.add(exitGame);
		//fileMenu.add(openMainGUI);

		// �������ײ˵�
		manualMenu = new JMenu("����(M)");
		firstMenuItem = new JMenuItem("��һ��", ChessUtil
				.getImageIcon("first.gif"));
		prevMenuItem = new JMenuItem("��һ��", ChessUtil.getImageIcon("prev.gif"));
		autoMenuItem = new JMenuItem("�Զ���ʾ", ChessUtil.getImageIcon("auto.gif"));
		nextMenuItem = new JMenuItem("��һ��", ChessUtil.getImageIcon("next.gif"));
		lastMenuItem = new JMenuItem("���һ��", ChessUtil.getImageIcon("last.gif"));
		manualListMenuItem = new JMenuItem("�����б�", ChessUtil
				.getImageIcon("manualList.gif"));

		// �����ײ˵�����Ӳ˵���
		manualMenu.add(firstMenuItem);

		manualMenu.add(prevMenuItem);
		manualMenu.add(autoMenuItem);
		manualMenu.add(nextMenuItem);
		manualMenu.add(lastMenuItem);
		manualMenu.add(manualListMenuItem);

		// ��������˵�
		helpMenu = new JMenu("����(H)");
		welcome = new JMenuItem("��ӭ", ChessUtil.getImageIcon("welcome.gif"));
		helpContent = new JMenuItem("��������", ChessUtil.getImageIcon("help.gif"));
		aboutGame = new JMenuItem("������Ϸ", ChessUtil.getImageIcon("info.gif"));

		helpMenu.add(welcome);
		helpMenu.add(helpContent);
		helpMenu.add(aboutGame);

		// �������ò˵�
		settingMenu = new JMenu("����(S)");
		bgSound = new JCheckBoxMenuItem("��������");
		gmSound = new JCheckBoxMenuItem("��Ϸ��Ч");
		bgSound.setSelected(true);
		gmSound.setSelected(true);
		settingMenu.add(bgSound);
		settingMenu.add(gmSound);

		// ��˵�������Ӳ˵�
		bar.add(fileMenu);
		bar.add(manualMenu);
		bar.add(settingMenu);
		bar.add(helpMenu);

		setJMenuBar(bar);

		// ���ÿ�ݼ�
		openManual.setAccelerator(KeyStroke.getKeyStroke('N',
				InputEvent.CTRL_DOWN_MASK));
		saveManual.setAccelerator(KeyStroke.getKeyStroke('S',
				InputEvent.CTRL_DOWN_MASK));
		saveManualAs.setAccelerator(KeyStroke.getKeyStroke('U',
				InputEvent.CTRL_DOWN_MASK));

		exitGame.setAccelerator(KeyStroke.getKeyStroke('E',
				InputEvent.CTRL_DOWN_MASK));
		/*openMainGUI.setAccelerator(KeyStroke.getKeyStroke('M',
				InputEvent.CTRL_DOWN_MASK));*/

		firstMenuItem.setAccelerator(KeyStroke.getKeyStroke('Y',
				InputEvent.CTRL_DOWN_MASK));
		prevMenuItem.setAccelerator(KeyStroke.getKeyStroke('P',
				InputEvent.CTRL_DOWN_MASK));
		autoMenuItem.setAccelerator(KeyStroke.getKeyStroke('U',
				InputEvent.CTRL_DOWN_MASK));
		nextMenuItem.setAccelerator(KeyStroke.getKeyStroke('N',
				InputEvent.CTRL_DOWN_MASK));
		lastMenuItem.setAccelerator(KeyStroke.getKeyStroke('L',
				InputEvent.CTRL_DOWN_MASK));
		manualListMenuItem.setAccelerator(KeyStroke.getKeyStroke('M',
				InputEvent.CTRL_DOWN_MASK));

		welcome.setAccelerator(KeyStroke.getKeyStroke('W',
				InputEvent.CTRL_DOWN_MASK));
		helpContent.setAccelerator(KeyStroke.getKeyStroke('H',
				InputEvent.CTRL_DOWN_MASK));
		aboutGame.setAccelerator(KeyStroke.getKeyStroke('A',
				InputEvent.CTRL_DOWN_MASK));

		bgSound.setAccelerator(KeyStroke.getKeyStroke('B',
				InputEvent.CTRL_DOWN_MASK));
		gmSound.setAccelerator(KeyStroke.getKeyStroke('T',
				InputEvent.CTRL_DOWN_MASK));

		// �������Ƿ�
		fileMenu.setMnemonic(KeyEvent.VK_G);
		manualMenu.setMnemonic(KeyEvent.VK_M);
		settingMenu.setMnemonic(KeyEvent.VK_S);
		helpMenu.setMnemonic(KeyEvent.VK_H);

		// ע�������
		openManual.addActionListener(this);
		saveManual.addActionListener(this);
		saveManualAs.addActionListener(this);
		exitGame.addActionListener(this);
		//openMainGUI.addActionListener(this);
		
		firstMenuItem.addActionListener(this);
		prevMenuItem.addActionListener(this);
		nextMenuItem.addActionListener(this);
		lastMenuItem.addActionListener(this);
		autoMenuItem.addActionListener(this);
		manualListMenuItem.addActionListener(this);
	
		gmSound.addActionListener(this);
		bgSound.addActionListener(this);

		welcome.addActionListener(this);
		helpContent.addActionListener(this);
		aboutGame.addActionListener(this);

		this.setJMenuBar(bar);
	}

	/**
	 * ��ʼ�����
	 */
	private void initPanels() {
		/** JPanel �����ұߵ����*/
		JPanel rightPanel = new JPanel(new BorderLayout());

		/**JPanel �����Ϣ���*/
		JPanel recordsPanel = new JPanel(new BorderLayout());

		TitledBorder recordsBorder = new TitledBorder(PropertyReader
				.get("CHESS_MESSAGE_TOOLTIP"));
		recordsPanel.setBorder(recordsBorder);

		manual = new JList(gameRecord.getDescs());
		manual.setFont(new Font("����", Font.PLAIN, 16));
		manual.setToolTipText(PropertyReader.get("CHESS_MESSAGE_TOOLTIP"));
		manual.setVisibleRowCount(16);
		manual.setAutoscrolls(true);

		// �б����Ӧ������˫���¼�
		manual.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int count = e.getClickCount();
				if (count == 1 || count == 2) {
					int index = manual.getSelectedIndex();
					int n = Math.abs(index - curIndex);
					if (index > curIndex) {
						for (int i = 0; i < n; i++) {
							next();// ���޸�curIndex��ֵ
						}
					} else if (curIndex > index) {
						for (int i = 0; i < n; i++) {
							prev();// ���޸�curIndex��ֵ
						}
					}
				}
			}
		});

		manualScroll = new JScrollPane(manual);
		recordsPanel.setPreferredSize(new Dimension(240, 340));
		recordsPanel.add(BorderLayout.CENTER, manualScroll);

		// �������
		JPanel manualTools = new JPanel(new FlowLayout(FlowLayout.CENTER));
		manualTools.add(first);
		manualTools.add(prev);
		manualTools.add(auto);
		manualTools.add(next);
		manualTools.add(last);
		recordsPanel.add(BorderLayout.SOUTH, manualTools);

		// �������
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		toolBar.setLayout(layout);
		toolBar.add(read);
		toolBar.add(save);
		toolBar.add(saveAs);
		toolBar.add(set);
		toolBar.add(manualList);

		/**JPanel �����������*/
		JPanel descPanel = new JPanel();
		area = new JTextArea(gameRecord.getDateAndTime() + "\n"
				+ gameRecord.getDesc());

		area.setPreferredSize(new Dimension(170, 140));
		JScrollPane scroll = new JScrollPane(area);
		descPanel.setPreferredSize(new Dimension(200, 170));
		descPanel.add(scroll);
		rightPanel.add(BorderLayout.NORTH, recordsPanel);
		rightPanel.add(BorderLayout.CENTER, descPanel);

		/** JSplitPane �ָ����*/
		JSplitPane splitH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, board,
				rightPanel);
		splitH.setDividerSize(5);
		splitH.setDividerLocation(450);

		add(BorderLayout.CENTER, splitH);
		add(BorderLayout.NORTH, toolBar);
	}

	/**
	 * ��ʼ����ť
	 * 
	 */
	private void initButtons() {
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

		read = new JButton(ChessUtil.getImageIcon("open.gif"));
		read.setToolTipText("����");
		read.addActionListener(this);
		read.setCursor(new Cursor(Cursor.HAND_CURSOR));
		read.setMargin(insets);

		saveAs = new JButton(ChessUtil.getImageIcon("saveas.gif"));
		saveAs.setToolTipText("���");
		saveAs.addActionListener(this);
		saveAs.setCursor(new Cursor(Cursor.HAND_CURSOR));
		saveAs.setMargin(insets);

		save = new JButton(ChessUtil.getImageIcon("save.gif"));
		save.setToolTipText("����");
		save.addActionListener(this);
		save.setCursor(new Cursor(Cursor.HAND_CURSOR));
		save.setMargin(insets);

		set = new JButton(ChessUtil.getImageIcon("welcome.gif"));
		set.setToolTipText("����");
		set.addActionListener(this);
		set.setCursor(new Cursor(Cursor.HAND_CURSOR));
		set.setMargin(insets);

		manualList = new JButton(ChessUtil.getImageIcon("manualList.gif"));
		manualList.setToolTipText("���������б�");
		manualList.addActionListener(this);
		manualList.setCursor(new Cursor(Cursor.HAND_CURSOR));
		manualList.setMargin(insets);

	}

	/**
	 * ��Ӧ�¼�
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		// ��һ��
		if (source == next || source == nextMenuItem) {
			next();

		}
		// ǰһ��
		else if (source == prev || source == prevMenuItem) {
			prev();
		}
		// ��һ��
		else if (source == first || source == firstMenuItem) {
			while (curIndex != -1) {
				prev();
			}
		}
		// ���һ�� 
		else if (source == last || source == lastMenuItem) {
			while (curIndex != gameRecord.getDescs().size() - 1) {
				next();
			}
		}
		// �˳�
		else if (source == exitGame) {
			dispose();
		}
		// �Զ���ʾ
		else if (source == auto || source == autoMenuItem) {
			DemoThread auto = new DemoThread();
			auto.start();
		}
		// ����
		else if (source == helpContent) {
			ChessUtil.showHelpDialog();
		} 
		//��ӭ
		else if (source == welcome) {
			ChessUtil.showWelcomeDialog();
		}
		//������Ϸ
		else if (source == aboutGame) {
			ChessUtil.showAboutDialog();
		} 
		// ����
		else if (source == set) {

		}
		// ����
		else if (source == save || source == saveManual) {
			SaveDialog dialog = new SaveDialog(this);
			dialog.setVisible(true);
		}

		// ���Ϊ
		else if (source == saveAs || source == saveManualAs) {
			SaveAsDialog dialog = new SaveAsDialog(this);
			dialog.setVisible(true);
		}

		// ����
		else if (source == read || source == openManual) {
			read();
		} else if (source == manualList || source == manualListMenuItem) {
			ChessLoadingGUI loading = new ChessLoadingGUI();
			loading.setVisible(true);
		} else if (source == bgSound){
			ChessUtil.setLoop(bgSound.isSelected());
		} else if (source == gmSound){
			ChessUtil.setStart(gmSound.isSelected());
		}

	}

	/**
	 * ���������ļ�
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void read() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				EXTENSION_NAME2, EXTENSION_NAME2);
		fileChooser.setFileFilter(filter);

		int state = fileChooser.showOpenDialog(this);
		File openFile = fileChooser.getSelectedFile();
		if (openFile != null && state == JFileChooser.APPROVE_OPTION) {
			// ��ȡ��ǰĿ¼�µ������ļ�

			GameRecord gameRecord = ManualUtil.readManual(openFile);

			ChessShowGUI demo = new ChessShowGUI(gameRecord);

			demo.setVisible(true);
			dispose();

		}

	}

	/**
	 * ��ʾ��ǰ������һ��
	 * 
	 */
	private void next() {
		if (curIndex == gameRecord.getDescs().size() - 1) {
			return;
		}
		curIndex++;
		if (curIndex < gameRecord.getRecords().size()) {
			step(curIndex);
		}
		scrollToView();
	}

	/**
	 * ��ʾindexָ����һ��
	 * 
	 * @param index
	 */
	public void step(int index) {
		ManualItem moveRecord = gameRecord.getRecords().get(index);
		MoveStep step = moveRecord.getMoveStep();
		Point pStart = step.start;
		Point pEnd = step.end;
		int startI = pStart.x;
		int startJ = pStart.y;
		int endI = pEnd.x;
		int endJ = pEnd.y;

		// ����ƶ���Ӧ�û�2����ʾ��
		board.setMoveFlag(true);
		board.movePoints[0] = new Point(endI, endJ);
		board.movePoints[1] = new Point(startI, startJ);

		ChessPiece piece = board.chessPoints[startI][startJ].getPiece();
		if (board.chessPoints[endI][endJ].hasPiece()) {
			ChessPiece pieceRemoved = board.chessPoints[endI][endJ]
					.getPiece();
			board.remove(pieceRemoved);
			board.chessPoints[endI][endJ].setPiece(piece, board);
			board.chessPoints[startI][startJ].setHasPiece(false);
		} else {
			board.chessPoints[endI][endJ].setPiece(piece, board);
			board.chessPoints[startI][startJ].setHasPiece(false);
		}
		
		board.repaint();
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

		int size = gameRecord.getRecords().size();

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
			record = (ManualItem) gameRecord.getRecords().get(curIndex);
			eatedPiece = PieceUtil.createPiece(record.getEatedPieceId());

			moveStep = record.getMoveStep();
			startI = moveStep.start.x;
			startJ = moveStep.start.y;
			endI = moveStep.end.x;
			endJ = moveStep.end.y;

			// ��һ��û�г�����
			if (eatedPiece == null) {
				ChessPiece piece = board.chessPoints[endI][endJ].getPiece();
				board.chessPoints[startI][startJ].setPiece(piece, board);
				(board.chessPoints[endI][endJ]).setHasPiece(false);

			}
			// ��һ����������
			else {
				ChessPiece piece = board.chessPoints[endI][endJ].getPiece();
				board.chessPoints[startI][startJ].setPiece(piece, board);

				board.chessPoints[endI][endJ].setPiece(eatedPiece, board);
				(board.chessPoints[endI][endJ]).setHasPiece(true);

			}
		}

//		// ����ʱ��������������ƶ�ʱ��ͬ
//		if (curIndex >= 1) {
//			record = (ManualItem) gameRecord.getRecords().get(curIndex - 1);
//			moveStep = record.getMoveStep();
//			startI = moveStep.start.x;
//			startJ = moveStep.start.y;
//			endI = moveStep.end.x;
//			endJ = moveStep.end.y;
//		}
//		// ����ƶ���Ӧ�û�2����ʾ��
//		board.setMoveFlag(true);
//		board.movePoints[0] = new Point((int) (board.chessPoints[endI][endJ]
//				.getX()), (int) (board.chessPoints[endI][endJ].getY()));
//		board.movePoints[1] = new Point(
//				(int) (board.chessPoints[startI][startJ].getX()),
//				(int) (board.chessPoints[startI][startJ].getY()));

		curIndex--;

		scrollToView();
		if (curIndex == -1) {
			// �˻ص�û���κ������ƶ���״̬ʱ�����û��ƶ����
			board.setMoveFlag(false);
			/*
			 * ��curIndex == -1ʱ��Ӧ�ò�ѡ���κ�һ��,
			 * ��manual.setSelectedIndex(-1)����ʱ�б��ѡ���κ�һ��
			 * manual.setListData(descs);���Իָ���Ĭ��״̬��ѡ���κ�һ��
			 */
			manual.setListData(gameRecord.getDescs());
		}

		board.repaint();
	}

	private void scrollToView() {
		if (curIndex >= 0 && curIndex < gameRecord.getDescs().size()) {
			int lastIndex = curIndex;
			Rectangle rect = manual.getCellBounds(lastIndex, lastIndex);
			manualScroll.getViewport().scrollRectToVisible(rect);
			// ѡ�е�ǰ�У���ʾ���
			manual.setSelectedIndex(curIndex);
		}

	}

	/**
	 * �����������ͳ�ʼ�����ӵ�λ��
	 * 
	 */
	private void initPieces() {
		//ManualType type = gameRecord.getFlag();
		if (gameRecord.getInitLocations()==null) {
			board.initChess(gameRecord.getPalyerFirst());
		} else{
			int size = gameRecord.getInitLocations().size();
			System.out.println("��ʼ��ʱ���Ӹ�����" + size);
			board.setPlayerName("�췽");
			for (int i = 0; i < size; i++) {
				Position location = gameRecord.getInitLocations().get(i);
				if (location != null) {
					PieceId id = location.getId();
					int x = (int) location.getPoint().getX();
					int y = (int) location.getPoint().getY();

					ChessPiece piece = PieceUtil.createPiece(id);
					board.chessPoints[x][y].setPiece(piece, board);
				}

			}
		}

		board.validate();
		board.repaint();
	}

	/**
	 * �Զ���ʾ�߳�
	 */
	private class DemoThread extends Thread {

		/**
		 * ���캯��
		 */
		public DemoThread() {
			System.out.println("�Զ���ʾ�̹߳��������");
		}

		/*
		 * ���� Javadoc��
		 * 
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			System.out.println("�Զ���ʾ�̼߳�������");
			int size = gameRecord.getRecords().size();

			// �ӵ�ǰ����ʾ�����һ��
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

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.fans.chess.common.ISaveManual#getSavePaths()
	 */

	public ArrayList<String> getSavePaths() {
		ArrayList<String> paths = new ArrayList<String>();
		String path = "";
		String descsPath = "";

		URL url = ChessUtil.class.getResource("");
		if (url == null) {
			return null;

		}
		ManualType type = gameRecord.getType();
		if (type == ManualType.PRINT_WHOLE) {
			//path = url.getPath() + "/manual/whole/";
			//descsPath = url.getPath() + "/manualdesc/whole/";
			path = "src/manuals/whole/";
			descsPath = path;
		} else if (type == ManualType.PRINT_PARTIAL) {
			path = "src/manuals/partial/";
			descsPath = path;
		} else if (type == ManualType.MAN_MACHINE) {
			path = "src/manuals/ai/";
			descsPath = path;
		} else if (type == ManualType.TWO_MAN) {
			path = "src/manuals/palyer/";
			descsPath = path;
		}
		paths.add(path);
		paths.add(descsPath);
		return paths;
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.fans.chess.common.ISaveManual#getGameRecord()
	 */
	public GameRecord getGameRecord() {
		String palyerFirst;
		if(gameRecord.getPalyerFirst()==null){
			palyerFirst = PALYER_FIRST;
		}else{
			palyerFirst = COMPUTER_FIRST;
		}
		GameRecord gameRecord2 = new GameRecord(gameRecord.getType(),
				ChessUtil.getDateAndTime(), "",
				gameRecord.getRecords(), gameRecord.getDescs(),
				gameRecord.getInitLocations(),palyerFirst);
		return gameRecord2;
	}
}
