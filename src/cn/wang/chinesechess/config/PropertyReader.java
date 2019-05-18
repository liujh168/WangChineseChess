
package cn.wang.chinesechess.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;

import cn.wang.chinesechess.ChessUtil;


/**
 * �����Լ������Զ�ȡ�ļ�
 * 
 * JDK�Դ���Properties���������ȡISO-8859-1������ַ��������༭���ֲ�����
 * 
 * @author wanghualiang
 */
public class PropertyReader {
	private static HashMap<String, String> properties;

	static {
		properties = new HashMap<String, String>();
		try {

			// FileReader reader = new FileReader(new
			// File("config/StringConstants.properties"));
			// ���ܺ�������·��-bug
			URL url = getConfigUrl("StringConstants.properties");
			FileReader reader = new FileReader(url.getFile());

			String oneLine = "";
			BufferedReader bufferedReader = new BufferedReader(reader);
			while ((oneLine = bufferedReader.readLine()) != null) {
				// System.out.println(oneLine);
				String[] str = oneLine.split("=");
				// System.out.println(str.length);
				if (str != null && str.length == 2) {
					// �������trim��ֹǰ�󶼿ո��ַ�
					properties.put(str[0].trim(), str[1].trim());
					// System.out.println("str[0]:" + str[0] + " str[1]:" +
					// str[1]);
				}
			}
			bufferedReader.close();// �ر���
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return properties.get(key);
	}

	public static URL getConfigUrl(String name) {
		String path = "config/" + name;
		URL url = null;
		try {
			//getClassLoaderֱ���Ǵ�classpath�²��ҵģ������ǡ�/����ͷ��
			url = ChessUtil.class.getClassLoader().getResource(path);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return url;
	}

	public static void main(String[] args) {
		System.out.println(PropertyReader.get("JU_TOOLTIP"));
		System.out.println(PropertyReader.get("MA_TOOLTIP"));
		/*
		 * URL path = PropertyReader.class.getResource("tooltip.properties");
		 * System.out.println(path); System.out.println(path.getPath());
		 * System.out.println(path.getFile());
		 */

	}
}
