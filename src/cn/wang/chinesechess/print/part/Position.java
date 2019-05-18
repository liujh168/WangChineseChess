
package cn.wang.chinesechess.print.part;

import java.awt.Point;
import java.io.Serializable;

import cn.wang.chinesechess.config.NAME;

/**
 * �оִ��ױ�������ʱ����Ҫ��¼���ӵĳ�ʼλ��
 * 
 * @author wanghualiang
 */
public class Position implements Serializable,NAME {

	private static final long serialVersionUID = 1L;

	private PieceId id;//���ӵ�id

	private Point point;//���ӵ�����

	public PieceId getId() {
		return id;
	}

	public void setId(PieceId id) {
		this.id = id;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

}
