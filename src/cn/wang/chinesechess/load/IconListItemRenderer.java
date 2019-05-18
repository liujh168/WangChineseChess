
package cn.wang.chinesechess.load;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

/**
 * װ���б�����Ⱦ��
 * 
 * @author wanghualiang
 */
public class IconListItemRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 1L;

	/** ѡ�е��б���ʱ�ı߿�*/
	private Border selectedBorder = BorderFactory.createLineBorder(Color.blue,
			1);

	/** û��ѡ�е��б���ı߿�*/
	private Border emptyBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);

	public IconListItemRenderer() {
		setOpaque(true);

	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		IconListItem item = (IconListItem) value;
		this.setIcon(item.getIcon());
		this.setText(item.getText());

		Color background;

		if (isSelected) {
			setBorder(selectedBorder);
			background = new Color(212, 219, 238);
		} else {
			setBorder(emptyBorder);
			background = Color.WHITE;
		}

		// ���ñ���ɫ
		setBackground(background);
		return this;
	}
}
