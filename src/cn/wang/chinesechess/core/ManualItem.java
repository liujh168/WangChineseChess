
package cn.wang.chinesechess.core;

import java.io.Serializable;

import cn.wang.chinesechess.config.NAME;


/**
 * �����߷� ��¼һ������
 * 
 * @author wanghualiang
 */
public class ManualItem implements Serializable,NAME{

	private static final long serialVersionUID = 259L;

	private PieceId eatedPieceId;// �������ӵ�ID������ʱʹ��

	private MoveStep moveStep;// �����ƶ�����

	private PieceId movePieceId;// �ƶ����ӵ�ID����ʱ��ʹ��

	public ManualItem() {
		moveStep = null;
		eatedPieceId = null;
	}

	public ManualItem(PieceId movePieceId, PieceId eatedPieceId,
			MoveStep moveStep) {
		this.movePieceId = movePieceId;
		this.eatedPieceId = eatedPieceId;
		this.moveStep = moveStep;
	}

	public MoveStep getMoveStep() {
		return moveStep;
	}

	public PieceId getEatedPieceId() {
		return eatedPieceId;
	}

	public void setEatedPieceId(PieceId eatedPieceId) {
		this.eatedPieceId = eatedPieceId;
	}

	public PieceId getMovePieceId() {
		return movePieceId;
	}

	public void setMovePieceId(PieceId movePieceId) {
		this.movePieceId = movePieceId;
	}

	public void setMoveStep(MoveStep moveStep) {
		this.moveStep = moveStep;
	}
}
