
package cn.wang.chinesechess.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;

import cn.wang.chinesechess.ChessUtil;
import cn.wang.chinesechess.config.NAME;
import cn.wang.chinesechess.print.part.Position;
import cn.wang.chinesechess.save.GameRecord;


/**
 * ���׹�����
 * 
 * @author ecjtu-WangHualiang
 */
public final class ManualUtil implements NAME {
	private ManualUtil() {

	}

	/**
	 * �������ף�Ҳ�����˵�ǰ��ʱ��
	 * 
	 * @param filePath
	 *            �����ļ���·��
	 * @param descPath
	 *            �����ı��Ĵ洢·��
	 * @param gameRecord
	 *            ��Ϸ��¼
	 * @return ����ɹ�����true��ʧ�ܷ���false
	 */
	public static boolean saveManual(String filePath, String descPath,
			GameRecord gameRecord) {

		/*
		 * ��������--�ı���ʽ--���� Windows�»��з���\r\n
		 */
		String allDescs = "";
		for (int i = 0; i < gameRecord.getDescs().size(); i++) {
			allDescs += gameRecord.getDescs().get(i) + "\r\n\r\n";
		}
		ChessUtil.writeStringToFile(descPath, allDescs);

		// ���������ļ�
		try {
			FileOutputStream outOne = new FileOutputStream(filePath);
			ObjectOutputStream outTwo = new ObjectOutputStream(outOne);

			outTwo.writeObject(gameRecord.getType());// ��������
			outTwo.writeObject(gameRecord.getDateAndTime());// ��ǰ���ں�ʱ��
			outTwo.writeObject(gameRecord.getDesc());// ��������
			outTwo.writeObject(gameRecord.getRecords());// ���׼�¼
			outTwo.writeObject(gameRecord.getDescs());// ����
			outTwo.writeObject(gameRecord.getInitLocations());// ���ӳ�ʼλ��
			outTwo.writeObject(gameRecord.getPalyerFirst().toString());// ���ַ� ����or���

			outOne.close();
			outTwo.close();

		} catch (NotSerializableException nse) {
			nse.printStackTrace();
			return false;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * ��ָ�����ļ��ж�ȡ��Ϸ��¼
	 * 
	 * @param file
	 *            ��Ҫ��ȡ���ļ�
	 * @return ���ļ��ж�ȡ�Ķ���
	 */
	@SuppressWarnings("unchecked")
	/**
	 * 
	 */
	public static GameRecord readManual(File file) {
		GameRecord record = null;
		try {
			FileInputStream inOne = new FileInputStream(file);
			ObjectInputStream inTwo = new ObjectInputStream(inOne);
			ManualType type = null;
			// ���ݱ������׵ĸ�ʽ������
			type = (ManualType) inTwo.readObject();
			String dateAndTime = (String) inTwo.readObject();
			String desc = (String) inTwo.readObject();
			ArrayList<ManualItem> records = (ArrayList<ManualItem>) inTwo.readObject();
			Vector<String> descs = (Vector<String>) inTwo.readObject();	
			ArrayList<Position> initLocations = null;
			if (type == ManualType.PRINT_PARTIAL) {
				initLocations = (ArrayList<Position>) inTwo.readObject();
			}
			String palyerFirst = (String) inTwo.readObject();
			record = new GameRecord(type, dateAndTime, desc, records, descs,
					initLocations, palyerFirst);

			// ��ʱ�ر���
			inOne.close();
			inTwo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return record;

	}
}
