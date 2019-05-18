
package cn.wang.chinesechess.load;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import cn.wang.chinesechess.ChessUtil;
import cn.wang.chinesechess.config.NAME;
import cn.wang.chinesechess.config.PropertyReader;
import cn.wang.chinesechess.core.ManualUtil;
import cn.wang.chinesechess.save.GameRecord;



/**
 * װ���б��� ��ʾ�����������Ϣ
 * 
 * @author wanghualinag
 */
public class ChessLoadingGUI extends JFrame implements ActionListener, NAME {
	private static final long serialVersionUID = 121L;

	/** װ����Ϸ���˳���Ϸ*/
	private JButton load, exit;

	/** �����б��*/
	private JList manualList;

	/** �б��ʹ�õ�ģ��*/
	private DefaultListModel listModel = new DefaultListModel();

	/** ���׼�¼�ļ���*/
	private ArrayList<GameRecord> chessRecords = new ArrayList<GameRecord>();

	/** ���������ı���*/
	private JTextArea descArea;

	/** ��ǰĿ¼*/
	private File curFile;

	/** ����Ŀ¼*/
	private File topFile;

	/**
	 * ���캯��
	 */
	public ChessLoadingGUI() {
		initList();// ���������ļ�
		initButtons();// ��ʼ����ť
		initPanels();// ��ʼ������
		showGUI();// //��ʾ����
	}

	/**
	 * ���ý��������(��С�����⣬λ�ã�ͼ���)��Ȼ����ʾ��
	 */
	private void showGUI() {
		this.setSize(680, 650);
		this.setTitle("�й�����---�����б� ");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(ChessUtil.getAppIcon());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ��ӦĬ�ϵ��˳��¼�
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				handleExitGame();
			}
		});

	}

	/**
	 * ���������ļ�
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void initList() {
		manualList = new JList();
		// ��װ�����Զ���cellRenderer
		manualList.setCellRenderer(new IconListItemRenderer());
		manualList.setModel(listModel);

		curFile = new File("src/manuals");
		topFile = curFile;
		updateListData();

	}

	/**
	 * ��ʼ�����
	 */
	private void initPanels() {
		JPanel leftPanel = new JPanel();
		TitledBorder manualBorder = new TitledBorder("�����б�");
		leftPanel.setBorder(manualBorder);

		manualList.setFont(new Font("����", Font.PLAIN, 16));
		manualList.setToolTipText(PropertyReader.get("CHESS_MESSAGE_TOOLTIP"));
		manualList.setVisibleRowCount(10);
		manualList.setAutoscrolls(true);

		// �б����Ӧ������˫���¼�
		manualList.addMouseListener(new MouseAdapter() {
			// �б��������
			String name = "";

			// �б����Ƿ�ΪĿ¼
			boolean isDir = false;

			// �б����Ƿ�Ϊ�ļ�
			boolean isFile = false;

			public void mouseClicked(MouseEvent e) {
				int count = e.getClickCount();

				System.out.println("��ǰ·��Ϊ" + curFile.getAbsolutePath());
				// �����¼�
				if (count == 1) {
					int index = manualList.getSelectedIndex();
					if (index != -1) {
						IconListItem item = (IconListItem) manualList
								.getSelectedValue();
						if (item != null) {
							name = item.getText();
						}
						System.out.println("��ǰѡ�������Ϊ:" + name + "\n");
					}

					File dirFile = new File(curFile, name);
					System.out.println("����ô��" + dirFile.exists());
					if (dirFile.exists()) {

						if (dirFile.isDirectory()) {
							isDir = true;
							isFile = false;
							descArea.setText("����һ��Ŀ¼");
							System.out.println("������һ��Ŀ¼" + name);
						}
					} else {
						dirFile = new File(curFile, name + EXTENSION_NAME);
						if (dirFile.isFile()) {
							isFile = true;
							isDir = false;
							GameRecord chessRecord = chessRecords.get(index);
							descArea.setText(chessRecord.getDateAndTime()
									+ "\n" + chessRecord.getDesc());

							System.out.println("������һ���ļ�" + name);
						} else {
							isFile = false;
							isDir = false;
							System.out.println("������" + name);
							descArea.setText("");

						}

					}

					if (isDir) {
						System.out.println("������Ŀ¼�ļ�");
					} else if (isFile) {
						System.out.println("�������ļ�");

					} else if (name.equals("������һ��")) {
						System.out.println("�����˷�����һ��");
					}
				}
				// ˫���¼�
				if (count == 2) {
					int index = manualList.getSelectedIndex();
					if (index != -1) {
						IconListItem item = (IconListItem) manualList
								.getSelectedValue();
						if (item != null) {
							name = item.getText();
						}
						System.out.println("��ǰѡ�������Ϊ:" + name + "\n");
					}

					File dirFile = new File(curFile, name);
					System.out.println("����ô��" + dirFile.exists());
					if (dirFile.exists()) {

						if (dirFile.isDirectory()) {
							isDir = true;
							isFile = false;

							curFile = dirFile;
							System.out.println("��ǰѡ����һ��Ŀ¼" + name);
						}
					} else {
						dirFile = new File(curFile, name + EXTENSION_NAME);
						if (dirFile.isFile()) {
							isFile = true;
							isDir = false;

							System.out.println("��ǰѡ����һ���ļ�" + name);
						} else {
							isFile = false;
							isDir = false;
							System.out.println("��ǰѡ����ļ�������Ŀ¼" + name);

						}

					}
					// �����б���������˫���¼���δ���
					if (isDir) {
						System.out.println("��������Ŀ¼���ļ�");
						updateListData();
					} else if (isFile) {
						System.out.println("����װ����Ϸ");
						load();
					} else if (name.equals("������һ��")) {
						String p = curFile.getParent();
						System.out.println("���ص�Ŀ¼:" + p);
						chessRecords.clear();
						curFile = curFile.getParentFile();
						updateListData();
					}
				}
			}
		});

		JScrollPane listScroll = new JScrollPane(manualList);
		listScroll.setPreferredSize(new Dimension(400, 480));
		leftPanel.add(listScroll);

		// �ұߵ����
		JPanel rightPanel = new JPanel(new BorderLayout());

		// �����������
		JPanel descPanel = new JPanel();
		TitledBorder descBorder = new TitledBorder("��������");
		descPanel.setBorder(descBorder);

		descArea = new JTextArea("���ｫ��ʾ���׵�������Ϣ");
		descArea.setPreferredSize(new Dimension(170, 400));
		descPanel.add(descArea);
		rightPanel.add(BorderLayout.CENTER, descPanel);

		JPanel controlPanel = new JPanel(new FlowLayout());
		controlPanel.add(load);
		controlPanel.add(exit);

		rightPanel.add(BorderLayout.SOUTH, controlPanel);

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				leftPanel, rightPanel);
		split.setDividerSize(5);
		split.setDividerLocation(450);
		add(split, BorderLayout.CENTER);
	}

	/**
	 * ��ʼ����ť
	 */
	private void initButtons() {
		int width = ChessUtil.getImageIcon("load.png").getIconWidth();
		int height = ChessUtil.getImageIcon("load.png").getIconHeight();

		// װ�����װ�ť
		load = new JButton(ChessUtil.getImageIcon("load.png"));
		load.setPreferredSize(new Dimension(width, height));
		load.setToolTipText(PropertyReader.get("LOAD_GAME_JBUTTON_TOOLTIP"));
		load.setCursor(new Cursor(Cursor.HAND_CURSOR));
		load.addActionListener(this);

		// �˳���ť
		exit = new JButton(ChessUtil.getImageIcon("exit.png"));
		exit.setPreferredSize(new Dimension(width, height));
		exit.setToolTipText("�˳���Ϸ");
		exit.addActionListener(this);
		exit.setCursor(new Cursor(Cursor.HAND_CURSOR));

	}

	/**
	 * �¼���Ӧ
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		// װ����Ϸ
		if (source == load) {
			load();
		}
		// �˳�
		else if (source == exit) {
			handleExitGame();
		}

	}

	/**
	 * װ�����ײ���ʾ
	 * 
	 */
	private void load() {
		GameRecord chessRecord;

		if (manualList != null) {
			int index = manualList.getSelectedIndex();
			if (index != -1) {
				chessRecord = chessRecords.get(index);
				if (chessRecord == null) {
					JOptionPane.showMessageDialog(this, "ѡ�е��ļ������������ļ���");
					return;
				}
			} else {
				JOptionPane.showMessageDialog(this, "û��ѡ�����ף�");
				return;
			}

			ChessShowGUI demo = new ChessShowGUI(chessRecord);
			demo.setVisible(true);
			dispose();
			validate();
			repaint();
		}

	}

	private void handleExitGame() {
		dispose();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ChessLoadingGUI loading = new ChessLoadingGUI();
		loading.setVisible(true);
	}

	/**
	 * �����б���е�ѡ��
	 */
	@SuppressWarnings("unchecked")
	private void updateListData() {
		// ɾ�����е��б���
		listModel.removeAllElements();

		// ��ȡ��ǰĿ¼�µ��ļ�
		File[] listFile = curFile.listFiles(new FileFilter() {
			// ֻѡ��ǰĿ¼�µ���չ��ΪEXTENSION_ANME�ļ�
			public boolean accept(File file) {
				return file.getName().endsWith(EXTENSION_NAME);
			}
		});

		File[] listDir = curFile.listFiles(new FileFilter() {
			// ֻѡ��ǰĿ¼�µ�Ŀ¼�ļ�
			@Override
			public boolean accept(File file) {
				return file.isDirectory();
			}
		});

		// ������Ƕ���Ŀ¼����Ҫ��ӷ�����һ��Ŀ¼��ѡ��
		if (!curFile.equals(topFile)) {
			// ������һ��ѡ��
			IconListItem item = new IconListItem(ChessUtil
					.getImageIcon("open.gif"), "������һ��");
			listModel.addElement(item);

			// ������Ŀ¼����Ҫ���chessRecords���ԭ��Ŀ¼��������ļ�
			chessRecords.clear();
			chessRecords.add(null);
		}

		// ���Ŀ¼�б����������
		if (listDir != null) {
			for (int i = 0; i < listDir.length; i++) {
				File oneFile = listDir[i];
				if (oneFile.exists() && oneFile.canRead()) {
					String name = oneFile.getName();
					IconListItem item = new IconListItem(ChessUtil
							.getImageIcon("open.gif"), name);
					listModel.addElement(item);
					chessRecords.add(null);
				}
			}

		} else {
			System.out.println("û��Ŀ¼");
		}

		// ����ļ��б����������(������չ��)
		if (listFile != null) {
			for (int i = 0; i < listFile.length; i++) {
				File oneFile = listFile[i];
				if (oneFile.exists() && oneFile.canRead()) {
					String name = oneFile.getName();
					int index = name.indexOf('.');
					if (index != -1) {
						// �ļ�����Ҫ��ʾ��չ��
						name = name.substring(0, index);
					}

					// ��ȡ��ǰĿ¼�µ������ļ�
					try {
						GameRecord chessRecord = ManualUtil.readManual(oneFile);
						if (chessRecord != null) {
							chessRecords.add(chessRecord);
							IconListItem item = null;
							if(chessRecord.getPalyerFirst() == null || chessRecord.getPalyerFirst().equals(PALYER_FIRST)){
								 item = new IconListItem(ChessUtil
										.getImageIcon("hongshuai.gif"), name);
							}else{
								 item = new IconListItem(ChessUtil
										.getImageIcon("heijiang.gif"), name);
							}
							/*// ��������������ʾ��ͬ��ͼ��
							IconListItem item;
							if (chessRecord.getFlag() == ManualType.NETWORK_RED) {
								item = new IconListItem(ChessUtil
										.getImageIcon("heijiang.gif"), name);
							} else {
								item = new IconListItem(ChessUtil
										.getImageIcon("hongshuai.gif"), name);
							}*/
							// �б�ģ�����һ��
							listModel.addElement(item);
						}

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}

		} else {
			System.out.println("û���ļ�");
		}
		// ��ӡ���׼�¼�������Ƿ���ȷ
		printManualRecord();

	}

	/**
	 * ��ӡ�����ļ���¼������Ƿ���б��е���Ϣһ��
	 */
	private void printManualRecord() {
		printXing();
		int size = chessRecords.size();
		for (int i = 0; i < size; i++) {
			GameRecord record = chessRecords.get(i);
			System.out.print("��" + i + "����¼�Ƿ�Ϊ�գ�" + (record == null) + "\t");
			if (record == null) {
				break;
			}
			System.out.print("�������ڣ�" + record.getDateAndTime() + "\t");
			System.out.print("�������ͣ�" + record.getType() + "\t");
			System.out.print("��ֵ�������Ϣ��" + record.getDesc() + "\t");
		}
	}

	private void printXing() {
		System.out.println("**************************");
	}
}
