
package cn.wang.chinesechess.save;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.wang.chinesechess.ChessUtil;
import cn.wang.chinesechess.config.NAME;
import cn.wang.chinesechess.core.ManualUtil;


/**
 * ��������ʱ�õĶԻ���
 * 
 * @author wanghualiang
 * @since 2.0
 */
public class SaveDialog extends JDialog implements ActionListener, NAME {

	private static final long serialVersionUID = 11L;

	private JButton ok, cancel;

	JLabel name = new JLabel("��������:");

	JLabel desc = new JLabel("�������:");

	JTextField nameField = new JTextField(20);

	JTextArea area = new JTextArea();

	// �Ի����ӵ���ߣ�����ʵ�ֱ������׽ӿ�
	private ISaveManual owner;

	public SaveDialog(ISaveManual owner) {
		this.owner = owner;
		initButtons();
		this.setModal(true);
		GridLayout layout = new GridLayout(3, 2);
		JPanel panel = new JPanel(layout);

		panel.add(name);
		panel.add(nameField);
		panel.add(desc);
		panel.add(area);
		panel.add(ok);
		panel.add(cancel);

		add(panel);
		setSize(200, 200);
		setTitle("�й�����---��������");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setIconImage(ChessUtil.getAppIcon());

		// ��ӦĬ�ϵ��˳��¼�
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}

	public void initButtons() {
		int width = ChessUtil.getImageIcon("ok.png").getIconWidth();
		int height = ChessUtil.getImageIcon("ok.png").getIconHeight();
		ok = new JButton(ChessUtil.getImageIcon("ok.png"));
		ok.addActionListener(this);
		ok.setToolTipText("ȷ��");
		ok.setCursor(new Cursor(Cursor.HAND_CURSOR));
		ok.setPreferredSize(new Dimension(width, height));

		cancel = new JButton(ChessUtil.getImageIcon("cancel.png"));
		cancel.addActionListener(this);
		cancel.setToolTipText("ȡ��");
		cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cancel.setPreferredSize(new Dimension(width, height));
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == ok) {
			String name = nameField.getText();
			String desc = area.getText();
			if (name == null || name.equals("")) {
				JOptionPane.showMessageDialog(this, "��������������", "��������������",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			} else if (desc == null || desc.equals("")) {
				JOptionPane.showMessageDialog(this, "�������������", "�������������",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			GameRecord gameRecord = owner.getGameRecord();
			gameRecord.setDesc(desc);
			ArrayList<String> paths = owner.getSavePaths();

			boolean flag = ManualUtil.saveManual(paths.get(0) + name
					+ EXTENSION_NAME, paths.get(1) + name + ".txt", gameRecord);

			if (flag) {
				JOptionPane.showMessageDialog(this, "���ױ���ɹ�ඣ�", "���ױ���ɹ�ඣ�",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "���ױ���ʧ�ܣ�", "���ױ���ʧ�ܣ�",
						JOptionPane.ERROR_MESSAGE);
			}
			dispose();
		} else if (source == cancel) {
			dispose();
		}

	}

}