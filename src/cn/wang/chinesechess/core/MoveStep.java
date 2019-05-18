
package cn.wang.chinesechess.core;

import java.awt.Point;
import java.io.Serializable;

/**
 * �ƶ�һ��
 * 
 * @author wanghualiang
 */
public class MoveStep implements Serializable {

	private static final long serialVersionUID = 260L;

	// �ƶ������
	public Point start;

	// �ƶ����յ�
	public Point end;

	public MoveStep(Point start, Point end) {
		this.start = start;
		this.end = end;
	}

	public MoveStep() {
	}

	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}

	public Point getStart() {
		return start;
	}

	public void setStart(Point start) {
		this.start = start;
	}
}
