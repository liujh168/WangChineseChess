
package cn.wang.chinesechess.save;

import java.util.ArrayList;
import java.util.Vector;

import cn.wang.chinesechess.config.NAME;
import cn.wang.chinesechess.core.ManualItem;
import cn.wang.chinesechess.print.part.Position;


/**
 * ��Ϸ��¼��
 * 
 * @author wanghualiang
 */
public class GameRecord implements NAME{
	
	/**��ֵ�������Ϣ*/
	private String desc; 
	/**�ƶ���¼*/
	private ArrayList<ManualItem> records;
	/**��������*/
	private ManualType type;
	/**���ں�ʱ��*/
	private String dateAndTime; 
	/**��������*/
	private Vector<String> descs; 
	/**���ӵĳ�ʼλ��*/
	private ArrayList<Position> initLocations = null;
	/**��������private String*/
	private String palyerFirst = null;
	

	public GameRecord(ManualType type, String dateAndTime, String desc,
			ArrayList<ManualItem> records, Vector<String> descs,
			ArrayList<Position> initLocations, String palyerFisrt) {

		this.type = type;
		this.dateAndTime = dateAndTime;
		this.records = records;
		this.descs = descs;
		this.desc = desc;
		this.initLocations = initLocations;
		this.palyerFirst = palyerFisrt;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public String getDesc() {
		return desc;
	}

	public Vector<String> getDescs() {
		return descs;
	}

	public ManualType getType() {
		return type;
	}

	public ArrayList<Position> getInitLocations() {
		return initLocations;
	}

	public ArrayList<ManualItem> getRecords() {
		return records;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setDescs(Vector<String> descs) {
		this.descs = descs;
	}

	public void setType(ManualType type) {
		this.type = type;
	}

	public void setInitLocations(ArrayList<Position> initLocations) {
		this.initLocations = initLocations;
	}

	public void setRecords(ArrayList<ManualItem> records) {
		this.records = records;
	}

	public String getPalyerFirst() {
		return palyerFirst;
	}

	public void setPalyerFirst(String palyerFirst) {
		this.palyerFirst = palyerFirst;
	}
}