
package cn.wang.chinesechess.print.part;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

import cn.wang.chinesechess.ChessUtil;


/**
 * �߼��������ɱ������ӵ����
 * 
 * @author wanghualiang
 */
public class ChessBoxPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private String imgName = "";

	public ChessBoxPanel(String imgName) {
		this.imgName = imgName;
	}

	public ChessBoxPanel(GridLayout layout) {
		super(layout);
	}
	
	public ChessBoxPanel(String imgName,GridLayout layout) {
		super(layout);
		this.imgName = imgName;
	}
	@Override
	protected void paintComponent(Graphics g) { // ��д�˷������ɼ����Լ���ͼƬ
		super.paintComponent(g);
		Dimension size = new Dimension(super.getWidth(), super.getHeight());
		g.drawImage(ChessUtil.getImage(imgName), 0, 0, size.width,
				size.height, null);
	}

}
