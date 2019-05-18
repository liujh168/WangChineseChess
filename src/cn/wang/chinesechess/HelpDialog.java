
package cn.wang.chinesechess;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.EditorKit;
import javax.swing.text.html.FormSubmitEvent;
import javax.swing.text.html.HTMLEditorKit;

import cn.wang.chinesechess.config.PropertyReader;



/**
 * ��JEditorPane��������򵥵������ SimpleWebBrowser��ʵ����һ���򵥵������������չʾHTML�ĵ��������ܴ����û�
 * ѡ�񳬼������¼����ύ���¼���
 * 
 * @author wanghualiang
 */
public class HelpDialog extends JFrame implements HyperlinkListener {

	private static final long serialVersionUID = 1659L;

	private JEditorPane jep = new JEditorPane();

	public static final String GAISHU;

	public static final String JIANSHI;

	public static final String QIPAN;

	public static final String ZOUFA;

	public static final String JILUFANGFA;

	public static final String ZILIJIAZHI;

	public static final String SHAWANGFA;

	public static final String SHUYU;

	public static final String WENHUA;

	static {
		GAISHU = PropertyReader.get("GAISHU");
		JIANSHI = PropertyReader.get("JIANSHI");
		QIPAN = PropertyReader.get("QIPAN");
		ZOUFA = PropertyReader.get("ZOUFA");
		JILUFANGFA = PropertyReader.get("JILUFANGFA");
		ZILIJIAZHI = PropertyReader.get("ZILIJIAZHI");
		SHAWANGFA = PropertyReader.get("SHAWANGFA");
		SHUYU = PropertyReader.get("SHUYU");
		WENHUA = PropertyReader.get("WENHUA");
	}

	private String[] data = { GAISHU, JIANSHI, QIPAN, ZOUFA, JILUFANGFA,
			ZILIJIAZHI, SHAWANGFA, SHUYU, WENHUA };

	JList list = new JList(data);

	public HelpDialog() {
		setTitle("�й�����");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setSize(730, 650);
		setIconImage(ChessUtil.getAppIcon2());
		setResizable(false);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			// ��ӦĬ�ϵ��˳��¼�
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		jep.setEditable(false);
		jep.addHyperlinkListener(this);
		//JPanel leftPanel = new JPanel();
		JScrollPane leftPanel =new JScrollPane(list);
		JPanel rightPanel = new JPanel();

		list.setSelectedIndex(0);
		list.setFont(new Font("����", Font.PLAIN, 16));

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int count = e.getClickCount();
				if (count == 1 || count == 2) {
					int index = list.getSelectedIndex();
					String item = data[index];
					try {
						if (item == GAISHU) {
							jep.setPage(ChessUtil.getHelpsUrl("gaishu.html"));
						} else if (item == JIANSHI) {
							jep.setPage(ChessUtil.getHelpsUrl("jianshi.html"));
						} else if (item == QIPAN) {
							jep.setPage(ChessUtil.getHelpsUrl("qipan.html"));
						} else if (item == ZOUFA) {
							jep.setPage(ChessUtil.getHelpsUrl("zoufa.html"));
						} else if (item == JILUFANGFA) {
							jep.setPage(ChessUtil.getHelpsUrl("jilufangfa.html"));
						} else if (item == ZILIJIAZHI) {
							jep.setPage(ChessUtil.getHelpsUrl("zilijiazhi.html"));
						} else if (item == SHAWANGFA) {
							jep.setPage(ChessUtil.getHelpsUrl("shawangfa.html"));
						} else if (item == SHUYU) {
							jep.setPage(ChessUtil.getHelpsUrl("shuyu.html"));
						} else if (item == WENHUA) {
							jep.setPage(ChessUtil.getHelpsUrl("wenhua.html"));
						}
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}
		};
		list.addMouseListener(mouseListener);
		
		jep.setPreferredSize(new Dimension(580, 640));

		rightPanel.setPreferredSize(new Dimension(600, 700));
		JScrollPane scroll = new JScrollPane(jep);
		scroll.setPreferredSize(new Dimension(590, 670));
		rightPanel.add(scroll);

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
				leftPanel, rightPanel);
		split.setDividerSize(5);
		split.setDividerLocation(120);

		add(split, BorderLayout.CENTER);

		// ����editorKit���Ա��������õ��¼�
		jep.addPropertyChangeListener("editorKit",
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						EditorKit kit = jep.getEditorKit();
						if (kit.getClass() == HTMLEditorKit.class) {
							((HTMLEditorKit) kit).setAutoFormSubmission(false);
						}
					}
				});

		try {
			jep.setPage(ChessUtil.getHelpsUrl("gaishu.html"));
		} catch (IOException e) {
			showError("gaishu.html");
		}

	}

	private void showError(String url) {
		jep.setContentType("text/html");
		jep.setText("<html>�޷�����ҳ:" + url + ",�����URL���Ϸ���������ҳ�����ڡ�</html>");
	}

	public static void main(String[] args) {
		HelpDialog dialog = new HelpDialog();
		dialog.setVisible(true);
	}

	/** �����û�ѡ�񳬼����ӻ����ύ���¼� */
	@Override
	public void hyperlinkUpdate(HyperlinkEvent evt) {
		/*try {
			if (evt.getClass() == FormSubmitEvent.class) { // �����ύ���¼�
				FormSubmitEvent fevt = (FormSubmitEvent) evt;
				URL url = fevt.getURL(); // ���URL
				String method = fevt.getMethod().toString(); // �������ʽ
				String data = fevt.getData(); // ��ñ�����

				if (method.equals("GET")) { // ���ΪGET����ʽ
					jep.setPage(url.toString() + "?" + data);

				} else if (method.equals("POST")) { // ���ΪPOST����ʽ
					URLConnection uc = url.openConnection();
					// ����HTTP��Ӧ����
					uc.setDoOutput(true);
					OutputStreamWriter out = new OutputStreamWriter(uc
							.getOutputStream());
					out.write(data);
					out.close();

					// ����HTTP��Ӧ����
					InputStream in = uc.getInputStream();
					ByteArrayOutputStream buffer = new ByteArrayOutputStream();
					byte[] buff = new byte[1024];
					int len = -1;

					while ((len = in.read(buff)) != -1) {
						buffer.write(buff, 0, len);
					}
					in.close();

					jep.setText(new String(buffer.toByteArray()));

				}

			} else if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				// �����û�ѡ��ĳ�������
				jep.setPage(evt.getURL());
			}

		} catch (Exception e) {
			showError(evt.getURL().toString());
		}*/
	}

}
