
package cn.wang.chinesechess.print.part;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import cn.wang.chinesechess.ChessGUI;
import cn.wang.chinesechess.ChessUtil;
import cn.wang.chinesechess.config.NAME;
import cn.wang.chinesechess.config.PropertyReader;
import cn.wang.chinesechess.core.ChessManual;
import cn.wang.chinesechess.core.ChessPiece;
import cn.wang.chinesechess.core.ChessPoint;
import cn.wang.chinesechess.core.ChessRule;
import cn.wang.chinesechess.core.ManualItem;
import cn.wang.chinesechess.core.MoveStep;
import cn.wang.chinesechess.core.PieceUtil;
import cn.wang.chinesechess.save.GameRecord;
import cn.wang.chinesechess.save.ISaveManual;
import cn.wang.chinesechess.save.SaveAsDialog;
import cn.wang.chinesechess.save.SaveDialog;


/**
 * ������ --�Զ�������λ�ã���Ҫ������о�����
 * 
 * @author wanghualiang
 */
public class PrintPartGUI extends JFrame implements ActionListener, NAME,
		ISaveManual {

	private static final long serialVersionUID = 266L;

	private JMenu fileMenu, manualMenu, settingMenu, helpMenu;

	private JMenuItem newManual, saveManual, saveManualAs, exitGame;
	
	private JMenuItem reprintManual, inputManual, undoManual;

	private JMenuItem setting, helpContent, aboutGame, welcome;

	private JCheckBoxMenuItem bgSound,gmSound;

	private JButton newButton, reprint, save, saveAs, undo, lock, set,
			inputManualButton;
	
	private JButton prev, next, auto, first, last;

	private JPanel toolBar = new JPanel();

	private JPanel manualTools;

	private ArrayList<ManualItem> records;

	private ArrayList<Position> initPositions;

	private PrintPartBoard board;

	public ChessManual chessManual;

	private JPanel gameStatusPanel;

	public ChessBoxPanel piecesPanel;

	public int curIndex = -1;

	// ��Ϸ״̬
	JLabel gameStatusContent, gameStatusIcon;

	public JScrollPane manualScroll;

	private Vector descs;

	// ��ť���õ�����ͼ��
	private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

	/**boolean Ĭ��û���������*/
	public boolean isLock = false;
	
	/**boolean �����Ƿ�������壬Ĭ�Ͽ���*/
	protected boolean canPaly = true;

	private int time = 1000;

	/*// �������֮���ķ�����
	public boolean redFirst = true;*/

	/**boolean[][] temp[i][j]=true��Ϊ����[i][j]λ���ϵ���������ʱ��*/
	public boolean[][] temp = new boolean[11][11];
	public PrintPartGUI() {

		board = new PrintPartBoard(this);

		chessManual = board.chessManual;
		manualScroll = chessManual.manualScroll;
		// manual = chessManual.manual;
		descs = chessManual.descs;
		records = chessManual.getManualItems();
		initMenuBar();
		initButtons();
		initToolBar();
		initPanels();
		handleKeyEvent();
		ChessUtil.setLoop(true);
		ChessUtil.setStart(true);

		setSize(670, 760);
		setTitle("�й�����---�оִ���");
		setResizable(false);
		setIconImage(ChessUtil.getAppIcon());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//��Ҫ���ȷ���˳����˳�
		addWindowListener(new WindowAdapter() {
			// ��ӦĬ�ϵ��˳��¼�
			public void windowClosing(WindowEvent e) {
				handleExitGame();
			}

		});
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
					}

				}
			}
		}, AWTEvent.KEY_EVENT_MASK);

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
		recordsPanel.setPreferredSize(new Dimension(240, 350));

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

		// ˮƽ�ָ���
		JSplitPane splitH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, board,
				rightPanel);
		splitH.setDividerSize(5);
		splitH.setDividerLocation(450);

		initPiecesPanel();

		// ��Ϸ״̬���
		gameStatusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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

		// add(BorderLayout.SOUTH, gameStatusPanel);
		add(BorderLayout.CENTER, splitH);
		add(BorderLayout.NORTH, toolBar);
	}

	/**
	 * ��ʼ���������
	 * 
	 */
	private void initPiecesPanel() {

		GridLayout gridLayout = new GridLayout(4, 8);
		gridLayout.setHgap(5);
		gridLayout.setVgap(2);
		piecesPanel = new ChessBoxPanel("chessBoxBack.jpg");
		piecesPanel.setLayout(gridLayout);
		piecesPanel.add(board.��܇1);
		piecesPanel.add(board.���R1);
		piecesPanel.add(board.����1);
		piecesPanel.add(board.����1);

		piecesPanel.add(board.����2);
		piecesPanel.add(board.����2);
		piecesPanel.add(board.���R2);
		piecesPanel.add(board.��܇2);

		piecesPanel.add(board.�쎛);
		piecesPanel.add(board.����1);
		piecesPanel.add(board.����2);
		piecesPanel.add(board.���1);
		piecesPanel.add(board.���2);
		piecesPanel.add(board.���3);
		piecesPanel.add(board.���4);
		piecesPanel.add(board.���5);

		piecesPanel.add(board.��܇1);
		piecesPanel.add(board.���R1);
		piecesPanel.add(board.����1);
		piecesPanel.add(board.��ʿ1);
		piecesPanel.add(board.��ʿ2);
		piecesPanel.add(board.����2);
		piecesPanel.add(board.���R2);
		piecesPanel.add(board.��܇2);

		piecesPanel.add(board.�ڌ�);
		piecesPanel.add(board.����1);
		piecesPanel.add(board.����2);
		piecesPanel.add(board.����1);
		piecesPanel.add(board.����2);
		piecesPanel.add(board.����3);
		piecesPanel.add(board.����4);
		piecesPanel.add(board.����5);

		add(BorderLayout.SOUTH, piecesPanel);

		TitledBorder piecesPanelBorder = new TitledBorder("��������");
		piecesPanelBorder.setTitleColor(Color.RED);
		piecesPanelBorder.setTitleFont(new Font("����", Font.PLAIN, 14));
		piecesPanel.setBorder(piecesPanelBorder);
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
		save.setEnabled(false);
		save.setMargin(insets);

		saveAs = new JButton(ChessUtil.getImageIcon("saveas.gif"));
		saveAs.addActionListener(this);
		saveAs.setToolTipText("�������");
		// saveAs.setPreferredSize(dimension);
		saveAs.setCursor(handCursor);
		saveAs.setEnabled(false);
		saveAs.setMargin(insets);

		lock = new JButton(ChessUtil.getImageIcon("unLock.gif"));
		lock.addActionListener(this);
		lock.setToolTipText("����");
		// saveAs.setPreferredSize(dimension);
		lock.setCursor(handCursor);
		lock.setMargin(insets);

		undo = new JButton(ChessUtil.getImageIcon("undo.gif"));
		undo.addActionListener(this);
		undo.setToolTipText("����");
		undo.setCursor(handCursor);
		undo.setEnabled(false);
		undo.setMargin(insets);

		set = new JButton(ChessUtil.getImageIcon("welcome.gif"));
		set.setToolTipText("����");
		set.addActionListener(this);
		set.setCursor(handCursor);
		set.setMargin(insets);

		newButton = new JButton(ChessUtil.getImageIcon("newManual.gif"));
		newButton.setToolTipText("�½�����");
		newButton.addActionListener(this);
		newButton.setCursor(handCursor);
		newButton.setMargin(insets);

		inputManualButton = new JButton(
				ChessUtil.getImageIcon("inputManual.gif"));
		inputManualButton.setToolTipText("��������");
		inputManualButton.addActionListener(this);
		inputManualButton.setCursor(handCursor);
		inputManualButton.setEnabled(false);
		inputManualButton.setMargin(insets);

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
		saveManual.setEnabled(false);
		saveManualAs = new JMenuItem("���", ChessUtil.getImageIcon("saveas.gif"));
		saveManualAs.setEnabled(false);
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
		inputManual.setEnabled(false);
		undoManual = new JMenuItem("����", ChessUtil.getImageIcon("undo.gif"));
		undoManual.setEnabled(false);
		// �����ײ˵�����Ӳ˵���
		manualMenu.add(reprintManual);
		manualMenu.add(inputManual);
		manualMenu.add(undoManual);

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

		saveManual.setAccelerator(KeyStroke.getKeyStroke('S',
				InputEvent.CTRL_DOWN_MASK));
		saveManualAs.setAccelerator(KeyStroke.getKeyStroke('U',
				InputEvent.CTRL_DOWN_MASK));
		reprintManual.setAccelerator(KeyStroke.getKeyStroke('P',
				InputEvent.CTRL_DOWN_MASK));
		exitGame.setAccelerator(KeyStroke.getKeyStroke('E',
				InputEvent.CTRL_DOWN_MASK));

		newManual.setAccelerator(KeyStroke.getKeyStroke('N',
				InputEvent.CTRL_DOWN_MASK));
		inputManual.setAccelerator(KeyStroke.getKeyStroke('I',
				InputEvent.CTRL_DOWN_MASK));
		undoManual.setAccelerator(KeyStroke.getKeyStroke('U',
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

		setJMenuBar(bar);
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
		toolBar.add(lock);
		toolBar.add(set);

	}

	/**
	 * ��Ӧ�¼�
	 */
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
			DemoThread auto = new DemoThread();
			auto.start();
			System.out.println("�ո�д�����߳�");
		} else if (source == helpContent) {
			ChessUtil.showHelpDialog();
		} 

		// ���´���
		else if (source == reprint) {
			int result = JOptionPane.showConfirmDialog(this, "��ȷ����Ҫ���´���ô��",
					"��ȷ����Ҫ���´���ô��", JOptionPane.YES_NO_OPTION);

			if (result == JOptionPane.YES_OPTION) {
				// ���پɵĴ��״��ڣ�Ȼ���½�һ�����״���
				dispose();
				PrintPartGUI newPrint = new PrintPartGUI();
				newPrint.setVisible(true);
			}

		} else if (source == lock) {
			if(!isLock){
				int result = JOptionPane
						.showConfirmDialog(
								this,
								"  �������֮�󣬲����ٽ��������ӷŵ�������,\nҲ���ܽ������е����ӷŻر���������\n֮�󣬿��Ա������ף������´���ʾ",
								"��ȷ��Ҫ�������ô��", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					int lockResult = canLock();
					if (lockResult == 2) {
						lockManual();
					} else if (lockResult == 1) {
						JOptionPane.showMessageDialog(this, "��û��λ�ھŹ���", "�������ʧ��",
								JOptionPane.ERROR_MESSAGE);
					} else if (lockResult == -1) {
						JOptionPane.showMessageDialog(this, "��û��λ�ھŹ���", "�������ʧ��",
								JOptionPane.ERROR_MESSAGE);
					} else if (lockResult == 0) {
						JOptionPane.showMessageDialog(this, "���͎�û��λ�ھŹ���", "�������ʧ��",
								JOptionPane.ERROR_MESSAGE);
					} else if (lockResult == 11) {
						JOptionPane.showMessageDialog(this, "���͎����ܶ���", "�������ʧ��",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}else{
				int result = JOptionPane
						.showConfirmDialog(
								this,
								"���������֮�󣬿����޸��·������ʱ����",
								"��ȷ��Ҫ�������ô��", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					unlockManual();
				}
			}
			
		}

		else if (source == save) {
			if (!isLock) {
				JOptionPane.showMessageDialog(this, "����֮����ܱ���");
				return;
			}
			SaveDialog dialog = new SaveDialog(this);
			dialog.setVisible(true);

		} else if (source == saveAs) {
			if (!isLock) {
				JOptionPane.showMessageDialog(this, "����֮��������");
				return;
			}
			SaveAsDialog dialog = new SaveAsDialog(this);
			dialog.setVisible(true);
		} else if (source == exitGame) {
			handleExitGame();
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
		} else if (source == inputManualButton || source == inputManual) {
			Icon icon = null;
			if(board.getPlayerName().equals(RED_NAME)){
				 icon = ChessUtil.getImageIcon("hongshuai.gif");
			}else{
				icon = ChessUtil.getImageIcon("heijiang.gif");
			}
			
			String manual = (String) JOptionPane.showInputDialog(this, "����������", "����������",
					JOptionPane.PLAIN_MESSAGE,icon,null,null);
			if(!canPaly){
				JOptionPane.showConfirmDialog(this, "��Ϸ�Ѿ�����", "��ʾ",
						JOptionPane.YES_OPTION);
				return;
			}
			if (manual != null) {
				manual = manual.trim();// ȥ��ǰ���հ׺�β���հ�
			}
			movePieceByManual(manual);

		} else if (source == newButton || source == newManual) {
			PrintPartGUI newPrint = new PrintPartGUI();
			newPrint.setVisible(true);
		}  else if (source == bgSound){
			ChessUtil.setLoop(bgSound.isSelected());
		} else if (source == gmSound){
			ChessUtil.setStart(gmSound.isSelected());
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
				/*// һ��Ҫ��ɾ�����ӣ��������µ����ӵ�λ��
				board.movePiece(movePiece, startX, startY, endX, endY);
				ManualItem record = new ManualItem();
				MoveStep moveStep = new MoveStep(new Point(startX, startY),
						new Point(endX, endY));
				record.setMoveStep(moveStep);
				if (removedPiece != null) {
					record.setEatedPieceId(removedPiece.getId());
				} else {
					record.setEatedPieceId(null);
				}
				record.setMovePieceId(movePiece.getId());
				board.chessManual.addManualItem(record);*/
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

	/**
	 * 
	 */
	private void undo() {
		canPaly = true;
		boolean flag = chessManual.removeManualItem();
		if (flag) {
			if (board.getPlayerName().equals(RED_NAME)) {
				board.setPlayerName(BLACK_NAME);
			} else {
				board.setPlayerName(RED_NAME);
			}
			curIndex--;
			
		}
	}
	
	/**
	 * �������
	 */
	private void unlockManual(){
		if(!canPaly){
			JOptionPane.showConfirmDialog(this, "��Ϸ�Ѿ�����", "��ʾ",
					JOptionPane.YES_OPTION);
			return;
		}
		isLock = false;
		
		saveManual.setEnabled(false);
		saveManualAs.setEnabled(false);
		inputManual.setEnabled(false);
		undoManual.setEnabled(false);
		
		board.setSelected(true);
		board.setNeedWink(true);
		//lock.setEnabled(true);
		save.setEnabled(false);
		saveAs.setEnabled(false);
		undo.setEnabled(false);
		inputManualButton.setEnabled(false);
		//����ͼ����ʾ
		lock.setIcon(ChessUtil.getImageIcon("unLock.gif"));
		lock.setToolTipText("����");
		
	}
	/**
	 * �������
	 * 
	 */
	private void lockManual() {
		if(!canPaly){
			JOptionPane.showConfirmDialog(this, "��Ϸ�Ѿ�����", "ȷ��",
					JOptionPane.YES_OPTION);
			return;
		}
		//��PrintPartBoard��Ҫ�õ���temp��Ϊfalse,��ʾ�����ϵ�����ȫ��������ʱ����
		for(int i=1;i<=9;i++)
			for(int j=1;j<=10;j++){
				temp[i][j]=false;
			}
		//����Ǹտ�ʼ���壬��ѡ��˭����
		if(descs.size()==0){
			int result = JOptionPane.showConfirmDialog(this,
					"�췽����ѡ�ǣ��ڷ�����ѡ��ȡ����������", "�ķ������أ�",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (result == JOptionPane.CANCEL_OPTION) {
				return;
			} else if (result == JOptionPane.YES_OPTION) {
				board.setPlayerName(RED_NAME);
				board.setPalyerFirst(RED_NAME);
			} else if (result == JOptionPane.NO_OPTION) {
				board.setPlayerName(BLACK_NAME);
				board.setPalyerFirst(BLACK_NAME);
			}
		}

		// ���������
		isLock = true;
		//����ͼ����ʾ
		lock.setIcon(ChessUtil.getImageIcon("lock.gif"));
		lock.setToolTipText("����");
		saveManual.setEnabled(true);
		saveManualAs.setEnabled(true);
		inputManual.setEnabled(true);
		undoManual.setEnabled(true);
		
		board.setSelected(false);
		board.setNeedWink(false);

		//lock.setEnabled(false);
		save.setEnabled(true);
		saveAs.setEnabled(true);
		undo.setEnabled(true);
		inputManualButton.setEnabled(true);

		// �������ӵĳ�ʼλ��
		initPositions = new ArrayList<Position>();
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 10; j++) {
				ChessPiece piece = board.chessPoints[i][j].getPiece();
				Position position = new Position();
				if (piece != null) {
					position.setId(piece.getId());
					position.setPoint(piece.getPosition());
					initPositions.add(position);
				}
			}
		}
	}

	public void scrollToView() {
		if (curIndex >= 0 && curIndex < descs.size()) {
			int lastIndex = curIndex;
			Rectangle rect = chessManual.manualList.getCellBounds(lastIndex,
					lastIndex);
			if (rect != null) {
				manualScroll.getViewport().scrollRectToVisible(rect);
			}
			// ѡ�е�ǰ�У���ʾ���
			System.out.println("Ӧ��ѡ�е�N�У�" + curIndex);
			chessManual.manualList.setSelectedIndex(curIndex);
		}

	}

	/**
	 * �����˳��¼� ���ȹر���˸�߳�
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
	 * ��һ��
	 * 
	 */

	private void prev() {
		System.out.println("��ǰ����Ϊ��" + curIndex);
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
			record = (ManualItem) records.get(curIndex);
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
//			record = (ManualItem) records.get(curIndex - 1);
//			moveStep = record.getMoveStep();
//			startI = moveStep.start.x;
//			startJ = moveStep.start.y;
//			endI = moveStep.end.x;
//			endJ = moveStep.end.y;
//		}
//
//		// ����ƶ���Ӧ�û�2����ʾ��
//		board.setMoveFlag(true);
//		board.movePoints[0] = new Point(
//				(int) (board.chessPoints[endI][endJ].getX()),
//				(int) (board.chessPoints[endI][endJ].getY()));
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
			chessManual.manualList.setListData(descs);
		}

		repaint();
		validate();
	}

	/**
	 * ��ʾindexָ����һ��
	 * 
	 * @param index
	 */
	public void step(int index) {
		ManualItem moveRecord = records.get(index);
		MoveStep step = moveRecord.getMoveStep();
		Point pStart = step.start;
		Point pEnd = step.end;
		int startI = pStart.x;
		int startJ = pStart.y;
		int endI = pEnd.x;
		int endJ = pEnd.y;

		ChessPiece piece = (board.chessPoints)[startI][startJ].getPiece();
		board.movePiece(piece, startI, startJ, endI, endJ);

		/*
		 * // ����ƶ���Ӧ�û�2����ʾ�� board.setMoveFlag(true); board.movePoints[0] = new
		 * Point((int) (board.chessPoints[endI][endJ] .getX()), (int)
		 * (board.chessPoints[endI][endJ].getY())); board.movePoints[1] = new
		 * Point( (int) (board.chessPoints[startI][startJ].getX()), (int)
		 * (board.chessPoints[startI][startJ].getY()));
		 * 
		 * 
		 * if ((board.chessPoints)[endI][endJ].hasPiece()) { ChessPiece
		 * pieceRemoved = (board.chessPoints)[endI][endJ] .getPiece();
		 * board.remove(pieceRemoved);
		 * (board.chessPoints)[endI][endJ].setPiece(piece, board);
		 * (board.chessPoints)[startI][startJ].setHasPiece(false); } else {
		 * (board.chessPoints)[endI][endJ].setPiece(piece, board);
		 * (board.chessPoints)[startI][startJ].setHasPiece(false); }
		 */
		// ��ʾʱ�����ƶ����ӵ�
		repaint();
		validate();
	}

	/**
	 * ��ʾ��ǰ������һ��
	 * 
	 */
	public void next() {
		System.out.println("��ǰ����Ϊ��" + curIndex);

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
	 * �ƶ������һ��
	 * 
	 */
	public void last() {
		int size = descs.size();
		System.out.println(size + "aa" + curIndex);
		while (curIndex < size - 1) {
			next();
		}
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
	 * �ж��Ƿ�����������
	 * 
	 * �������к쎛�ͺڌ�ʱ��������
	 * 
	 * @return
	 */
	private int canLock() {

		boolean hasShuai = false;
		boolean hasJiang = false;

		ChessPoint[][] chessPoints = board.chessPoints;

		int shuaiX = 0, shuaiY = 0, jiangX = 0, jiangY = 0;

		// �������Ƿ��кڌ�
		for (int i = 4; i <= 6; i++) {
			for (int j = 1; j <= 3; j++) {
				if (chessPoints[i][j].hasPiece()) {
					if (chessPoints[i][j].getPiece().getCategory()
							.equals(PieceCategory.JIANG)) {
						hasJiang = true;
						jiangX = i;
						jiangY = j;
						break;
					}
				}
			}
		}
		// �������Ƿ��к쎛
		for (int i = 4; i <= 6; i++) {
			for (int j = 8; j <= 10; j++) {
				if (chessPoints[i][j].hasPiece()) {
					if (chessPoints[i][j].getPiece().getCategory()
							.equals(PieceCategory.SHUAI)) {
						hasShuai = true;
						shuaiX = i;
						shuaiY = j;
						break;
					}
				}
			}
		}

		if (hasShuai) {
			if (hasJiang) {
				boolean flag = false;
				// �����Ƿ����
				if (shuaiX == jiangX) {

					for (int j = jiangY + 1; j <= shuaiY - 1; j++) {
						if (chessPoints[shuaiX][j].hasPiece()) {
							flag = true;
						}
					}
				} else {
					flag = true;
				}

				if (flag) {
					return 2;
				}
				return 11;

			}
			return -1;
		}
		if (hasJiang) {
			return 1;
		}
		return 0;

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

	public static void main(String[] args) {
		/* ��������GUI�����ʽ������ϵͳ��ʽ */
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		PrintPartGUI printEndManual = new PrintPartGUI();
		printEndManual.setVisible(true);

	}

	@Override
	public ArrayList<String> getSavePaths() {
		ArrayList<String> paths = new ArrayList<String>();
		String path = "src/manuals/partial/";
		String path2 = "src/manuals/partial/";

		paths.add(path);
		paths.add(path2);
		return paths;
	}

	@Override
	public GameRecord getGameRecord() {
		GameRecord gameRecord = new GameRecord(ManualType.PRINT_PARTIAL,
				ChessUtil.getDateAndTime(), "",
				board.chessManual.getManualItems(), board.chessManual.descs,
				initPositions, PALYER_FIRST);
		return gameRecord;
	}

}
