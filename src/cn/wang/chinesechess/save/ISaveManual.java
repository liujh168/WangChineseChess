
package cn.wang.chinesechess.save;

import java.util.ArrayList;

/**
 * �������׽ӿ�,�������׵Ľ�����Ҫʵ�ָýӿ�
 * 
 * @author wanghualiang
 */
public interface ISaveManual {
	/**
	 * ������׵���Ϸ��¼��Ϣ
	 * 
	 * @return ��Ϸ��¼
	 */
	public GameRecord getGameRecord();

	/**
	 * ������׵ı���·��
	 * 
	 * @return ����·���б�
	 */
	public ArrayList<String> getSavePaths();
}
