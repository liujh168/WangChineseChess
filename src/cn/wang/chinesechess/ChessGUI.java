
package cn.wang.chinesechess;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import cn.wang.chinesechess.config.NAME;
import cn.wang.chinesechess.core.ManualUtil;
import cn.wang.chinesechess.load.ChessShowGUI;
import cn.wang.chinesechess.load.ChessLoadingGUI;
import cn.wang.chinesechess.manmachine.ManMachineGUI;
import cn.wang.chinesechess.print.all.PrintAllGUI;
import cn.wang.chinesechess.print.part.ChessBoxPanel;
import cn.wang.chinesechess.print.part.PrintPartGUI;
import cn.wang.chinesechess.save.GameRecord;
import cn.wang.chinesechess.twoman.TwoManGUI;




/**
 *  ȫ�ִ��ף��оִ��ף�װ����Ϸ���߼�װ�أ�˫�˶�ս���˻����ģ�
 * 
 * @author wanghualiang
 */
public class ChessGUI extends JFrame implements ActionListener, NAME {

	private static final long serialVersionUID = -4285888351596327876L;

	private JPanel localPanel;

	private JButton load, loadSupper, partialManual, wholeManual, manMachine, twoMan;
	
	private ImageIcon backGround;

	public ChessGUI() {
		initButtons();
		initPanels();
		this.setTitle("�й�����");
		this.setIconImage(ChessUtil.getAppIcon());
		this.setSize(370, 340);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		
		addWindowListener(new WindowAdapter() {
			// ��ӦĬ�ϵ��˳��¼�
			public void windowClosing(WindowEvent e) {
				handleExitGame();
			}

		});
	}

	private void initButtons() {

		int width = ChessUtil.getImageIcon("load.png").getIconWidth()+10;
		int height = ChessUtil.getImageIcon("load.png").getIconHeight();

		load = new JButton("װ����Ϸ");
		load.addActionListener(this);
		load.setToolTipText("װ����Ϸ");
		load.setCursor(new Cursor(Cursor.HAND_CURSOR));
		load.setPreferredSize(new Dimension(width, height));

		loadSupper = new JButton("�߼�װ��");
		loadSupper.addActionListener(this);
		loadSupper.setToolTipText("�߼�װ��");
		loadSupper.setCursor(new Cursor(Cursor.HAND_CURSOR));
		loadSupper.setPreferredSize(new Dimension(width, height));

		partialManual = new JButton("�оִ���");
		partialManual.addActionListener(this);
		partialManual.setToolTipText("�оִ���");
		partialManual.setCursor(new Cursor(Cursor.HAND_CURSOR));
		partialManual.setPreferredSize(new Dimension(width, height));

		wholeManual = new JButton("ȫ�ִ���");
		wholeManual.addActionListener(this);
		wholeManual.setToolTipText("ȫ�ִ���");
		wholeManual.setCursor(new Cursor(Cursor.HAND_CURSOR));
		wholeManual.setPreferredSize(new Dimension(width, height));

		manMachine = new JButton("�˻�����");
		manMachine.addActionListener(this);
		manMachine.setToolTipText("�˻�����");
		manMachine.setCursor(new Cursor(Cursor.HAND_CURSOR));
		manMachine.setPreferredSize(new Dimension(width, height));
		
		twoMan = new JButton("˫�˶�ս");
		twoMan.addActionListener(this);
		twoMan.setToolTipText("˫�˶�ս");
		twoMan.setCursor(new Cursor(Cursor.HAND_CURSOR));
		twoMan.setPreferredSize(new Dimension(width, height));
		
	}
	
	private void initPanels() {
//		BackGroundPanel backGround = new BackGroundPanel("mainBack.jpg");
		/*backGround = ChessUtil.getImageIcon("mainBack.jpg");
		System.out.println(backGround);
		JLabel label = new JLabel(backGround);
		// �ѱ�ǩ�Ĵ�Сλ������ΪͼƬ�պ�����������
		label.setBounds(0, 0, this.getWidth(), this.getHeight());
		// �����ݴ���ת��ΪJPanel���������÷���setOpaque()��ʹ���ݴ���͸��
		JPanel imagePanel = (JPanel) this.getContentPane();
		imagePanel.setOpaque(false);
		// �ѱ���ͼƬ��ӵ��ֲ㴰�����ײ���Ϊ����
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		//���ÿɼ�
		setVisible(true);*/
//		this.add(backGround);
		TitledBorder oneBorder = new TitledBorder("�������");
		oneBorder.setTitleFont(new Font("����", Font.PLAIN, 16));
		oneBorder.setTitleColor(new Color(0, 0, 255));

		TitledBorder twoBorder = new TitledBorder("װ������");
		twoBorder.setTitleFont(new Font("����", Font.PLAIN, 16));
		twoBorder.setTitleColor(new Color(26, 151, 34));

		TitledBorder threeBorder = new TitledBorder("�˹�����");
		threeBorder.setTitleFont(new Font("����", Font.PLAIN, 16));
		threeBorder.setTitleColor(new Color(255, 0, 0));
		
		TitledBorder fourBorder = new TitledBorder("˫����Ϸ");
		fourBorder.setTitleFont(new Font("����", Font.PLAIN, 16));
		fourBorder.setTitleColor(new Color(128, 128, 0));

		// �����������
		FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
		JPanel one = new JPanel(flow);
		one.setOpaque(false);
		one.setBorder(oneBorder);
		JPanel two = new JPanel(flow);
		two.setOpaque(false);
		two.setBorder(twoBorder);
		JPanel three = new JPanel(flow);
		three.setOpaque(false);
		three.setBorder(threeBorder);
		JPanel four = new JPanel(flow);
		four.setOpaque(false);
		four.setBorder(fourBorder);

		one.add(wholeManual);
		one.add(partialManual);

		two.add(load);
		two.add(loadSupper);
		
		three.add(manMachine);
		
		four.add(twoMan);

		localPanel = new ChessBoxPanel("mainBack.jpg",new GridLayout(4, 3));
		localPanel.setOpaque(false);//�������͸��
		localPanel.add(one);
		localPanel.add(two);
		localPanel.add(three);
		localPanel.add(four);
		this.add(localPanel);

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == load) {
			ChessLoadingGUI test = new ChessLoadingGUI();
			test.setVisible(true);
		} else if (source == loadSupper) {
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
				// dispose();

			}
		} else if (source == twoMan) {
			TwoManGUI twoMan = new TwoManGUI();
			twoMan.setVisible(true);
		
		} else if (source == manMachine) {
			ManMachineGUI ai = new ManMachineGUI();
			ai.setVisible(true);
			
		} else if (source == partialManual) {
			PrintPartGUI partialManual = new PrintPartGUI();
			partialManual.setVisible(true);

		} else if (source == wholeManual) {
			PrintAllGUI wholeManual = new PrintAllGUI();
			wholeManual.setVisible(true);
		} 
	}

	/**
	 * ����ر��¼�
	 * 
	 */
	private void handleExitGame() {
		int result = JOptionPane.showConfirmDialog(this, "��ȷ��Ҫ�˳�ô��", "ȷ���˳�",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			dispose();
		}
	}
	
	/**
	 * �ͻ���Ӧ�ó������
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		createAndShowGUI();
		//ChessUtil.palyBacksound("backSound.wav");
		
		
	}

	protected static void createAndShowGUI() {
		/* ��������GUI�����ʽ������ϵͳ��ʽ */
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ChessGUI chineseChess = new ChessGUI();
		chineseChess.setVisible(true);
		
	}
}
