
package cn.wang.chinesechess.config;

/**
 * 
 * @author wanghualinag
 */
public interface NAME {

	public static final String RED_NAME = "�췽";

	public static final String BLACK_NAME = "�ڷ�";
	
	public static final String PALYER_FIRST = "���";
	
	public static final String COMPUTER_FIRST = "����";

	// �����ļ���չ��
	public static String EXTENSION_NAME = ".chs";

	public static String EXTENSION_NAME2 = "chs";
	/**
	 * ���ڱ���
	 */

	public static int RED_TURN_GAME_STATUS = 35668;

	public static int BLACK_TURN_GAME_STATUS = 35669;
	
	/** ���̵�Ԫ��Ŀ��*/
	public static int UNIT_WIDTH = 44;
	/** ���̵�Ԫ��ĸ߶�*/
	public static int UNIT_HEIGHT = 44;
	/** ���ӵĿ��*/
	public static int PIECE_WIDTH = 44;
	/** ���ӵĸ߶�*/
	public static int PIECE_HEIGHT = 44;
	/**��������*/
	public static enum PieceCategory {
		JU, MA, PAO, HONGXIANG, HEIXIANG, HONGSHI, HEISHI, JIANG, SHUAI, ZU, BING
	}// ���ӵ�����
	/**����ID*/
	public static enum PieceId {
		HONGJU1, HONGJU2, HONGMA1, HONGMA2, HONGXIANG1, HONGXIANG2, HONGSHI1, HONGSHI2, SHUAI, HONGPAO1, HONGPAO2, BING1, BING2, BING3, BING4, BING5, HEIJU1, HEIJU2, HEIMA1, HEIMA2, HEIXIANG1, HEIXIANG2, HEISHI1, HEISHI2, JIANG, HEIPAO1, HEIPAO2, ZU1, ZU2, ZU3, ZU4, ZU5;
	}
	/**
	 * ���̵�����
	 */
	public enum BoardType {
		printWhole, printPartial, twoman, manMachine
	}
	
	/**
	 * ��������
	 * 
	 */
	public enum ManualType {
		PRINT_PARTIAL, PRINT_WHOLE, MAN_MACHINE ,TWO_MAN
	};
	public String �� = "��";

	public String ܇ = "܇";

	public String �R = "�R";

	public String �� = "��";

	public String �� = "��";

	public String �� = "��";

	public String �� = "��";

	public String �� = "��";

	public String ʿ = "ʿ";

	public String �� = "��";

	public String �� = "��";

}
