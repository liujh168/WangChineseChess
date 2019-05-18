
package cn.wang.chinesechess.load;

import javax.swing.Icon;

/**
 * װ���б���
 * 
 * @author wanghualiang
 */
public class IconListItem {
	// �б����ͼ��
	private Icon icon;

	// �б�����ı�����
	private String text;

	/**
	 * @param icon
	 *            �б����ͼ��
	 * @param text
	 *            �б�����ı�
	 */
	public IconListItem(Icon icon, String text) {
		this.icon = icon;
		this.text = text;
	}

	/**
	 * @return �б����ͼ��
	 */
	public Icon getIcon() {
		return icon;
	}

	/**
	 * @return �б�����ı�
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param icon
	 *            �б����ͼ��
	 */
	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	/**
	 * @param text
	 *            �б�����ı�����
	 */
	public void setText(String text) {
		this.text = text;
	}
}