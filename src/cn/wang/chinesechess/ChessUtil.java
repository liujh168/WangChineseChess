
package cn.wang.chinesechess;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * �й����幤����
 * 
 * �������̳�
 * 
 * @author wanghualiang
 * 
 * @since 1.0
 */
public final class ChessUtil {
	/**private static �����Ƿ�ر�*/
	private static boolean isLoop = false;
	/**private static �����߳��Ƿ���*/
	private static boolean isStart = false;
	private static Thread backMusic = null;
	public static void setStart(boolean isStart) {
		ChessUtil.isStart = isStart;
	}

	public static void setLoop(boolean isLoop) {
		ChessUtil.isLoop = isLoop;
	}

	/**
	 * ������ʵ������
	 */
	private ChessUtil() {

	}

	/**
	 * ��ȡ��ǰ�����ں�ʱ��:������+ʱ����
	 * 
	 * @return ��ǰ�����ں�ʱ��
	 */
	public static String getDateAndTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * ��ȡ��ǰ��ʱ��:ʱ����
	 * 
	 * @return ��ǰʱ��
	 * 
	 */
	public static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * ��ý������ں�����:2010��3��4�� ������
	 * 
	 * @return ��ǰʱ��
	 */
	public static String getDateAndDay() {
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		String today = dateFormat.format(calendar.getTime());

		return today;
	}

	/**
	 * �������ֻ�ȡͼ�����
	 * 
	 * @param name
	 *            ͼƬ������
	 * @return ImageIcon�����null
	 */
	public static ImageIcon getImageIcon(String name) {
		String path = "img/" + name;
		URL imgURL = ChessUtil.class.getClassLoader().getResource(path);
		
		if (imgURL != null) {
			//System.out.println(imgURL.getPath());
			return new ImageIcon(imgURL);
		}else{
			System.out.println("**************************************113"+name);
		}
		return null;
	}

	/**
	 * ��ȡImage����
	 * 
	 * @param name
	 *            ͼƬ������
	 * @return Image �����null
	 */
	public static Image getImage(String name) {
		String path = "img/" + name;
		URL imgURL = ChessUtil.class.getClassLoader().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL).getImage();
		}
		return null;
	}

	/**
	 * ����Ӧ�ó���ͼ�����õ���ͼ��
	 * 
	 * @return ͼ�����
	 */
	public static Image getAppIcon() {
		return getImage("piece/shuai.png");
	}

	/**
	 * ����Ӧ�ó���ͼ�����õ���ͼ��
	 * 
	 * @return ͼ�����
	 */
	public static Image getAppIcon2() {
		String path = "img/piece/" + "jiang.png";
		URL imgURL = ChessUtil.class.getClassLoader().getResource(path);
		System.out.println("url"+imgURL.getPath());
		if (imgURL != null) {
			return new ImageIcon(imgURL).getImage();
		}
		return null;
	}

	/**
	 * �������֣���������Ŀ¼�µ������ļ�
	 * 
	 * @param name
	 *            �����ļ�������(����׺)
	 */
	public static void playSound(String name) {
		if(isStart){
			String path = "src/sounds/" + name;
			//System.out.println("path="+path);
			//path = ChessUtil.class.getClassLoader().getResource(path).getPath();
			File file = new File(path);
			//System.out.println("File="+file.getPath());
			InputStream is;
			try {
				is = new FileInputStream(file);
				AudioStream as = new AudioStream(is);
				AudioPlayer.player.start(as);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ��ʼ���������߳� ����ģʽ
	 * @param name
	 */
	public static void palyBacksound(final String name){
		if(backMusic==null){
			backMusic = new Thread(){
				String path = "src/sounds/"+name;
				/*String path = new Object(){
					public String getPath() {
						return this.getClass().getClassLoader().getResource(filename).getPath();}
					}.getPath();*/
				@Override
				public void run(){
					System.out.println("��ʼ�����߳�");
					File soundFile = new File(path);
					//System.out.println("soundFile1="+soundFile.getPath());
					//System.out.println("soundFile2="+soundFile.getAbsolutePath());
					AudioInputStream audioInputStream = null;
					try {
						audioInputStream = AudioSystem.getAudioInputStream(soundFile);
					} catch (Exception e1) {
						e1.printStackTrace();
						return;
					}
					AudioFormat format = audioInputStream.getFormat();
					SourceDataLine auline = null;
					DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
					try {
						auline = (SourceDataLine) AudioSystem.getLine(info);
						auline.open(format);
						} catch (Exception e) {
							e.printStackTrace();
							return;
						}
					
					auline.start();
					int nBytesRead = 0;
					audioInputStream.mark(0);
					//System.out.println(audioInputStream.markSupported());
					//���ǻ���
					byte[] abData = new byte[512];
					try {
						while(true){
							System.out.println("---------------------");
							while (isLoop) {
								//System.out.printf("���ڲ�������");
								nBytesRead = audioInputStream.read(abData, 0, abData.length);
								if (nBytesRead > 0){
									auline.write(abData, 0, nBytesRead);
								}
								if(nBytesRead == -1){
									try {
										audioInputStream=AudioSystem.getAudioInputStream(soundFile);
									} catch (UnsupportedAudioFileException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
						return;
					} finally {
						auline.drain();
						auline.close();
					}	
				};
			};
			backMusic.start();
		}
	}
	/**
	 * ���������߳�
	 */
	private static void endBackMusic(){
		if(backMusic!=null){
			backMusic.stop();
			backMusic = null;
		}
	}
	/**
	 * �������ַ���һ��URL����
	 * 
	 * @param name
	 *            �����ļ�������
	 * @return URL�����null
	 */
	public static URL getHelpsUrl(String name) {
		String path = "helps/" + name;
		URL url = null;
		try {
			url = ChessUtil.class.getClassLoader().getResource(path);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return url;
	}

	/**
	 * ��ʾ�й���������Ի���
	 * 
	 */
	public static void showHelpDialog() {
		HelpDialog help = new HelpDialog();
		help.setVisible(true);
	}

	/**
	 * ������������ת���ɺ���
	 * 
	 * @param num
	 *            ���������� 1��9
	 * @return ת��֮��ĺ���
	 */
	public static String numToZi(int num) {
		if (num <= 0 || num >= 10) {
			System.out.println("�������Ϸ���");
			return "";
		}
		String zi = "";

		switch (num) {
		case 1:
			zi = "һ";
			break;
		case 2:
			zi = "��";
			break;
		case 3:
			zi = "��";
			break;
		case 4:
			zi = "��";
			break;
		case 5:
			zi = "��";
			break;
		case 6:
			zi = "��";
			break;
		case 7:
			zi = "��";
			break;
		case 8:
			zi = "��";
			break;
		case 9:
			zi = "��";
			break;
		default:
			System.out.println("�������Ϸ���");
			break;
		}

		return zi;
	}

	/**
	 * �����ֺ���ת���ɶ�Ӧ�İ���������
	 * 
	 * @param zi
	 * @return
	 */
	public static int ziToNum(String zi) {
		int num = 0;

		if (zi.equals("һ") || zi.equals("1")) {
			num = 1;
		} else if (zi.equals("��") || zi.equals("2")) {
			num = 2;
		} else if (zi.equals("��") || zi.equals("3")) {
			num = 3;
		} else if (zi.equals("��") || zi.equals("4")) {
			num = 4;
		} else if (zi.equals("��") || zi.equals("5")) {
			num = 5;
		} else if (zi.equals("��") || zi.equals("6")) {
			num = 6;
		} else if (zi.equals("��") || zi.equals("7")) {
			num = 7;
		} else if (zi.equals("��") || zi.equals("8")) {
			num = 8;
		} else if (zi.equals("��") || zi.equals("9")) {
			num = 9;
		}

		return num;
	}

	/**
	 * ���ı�����д�뵽�ı��ļ���
	 * 
	 * @param path
	 *            �ļ���·��
	 * @param content
	 *            �ı�����
	 */
	public static void writeStringToFile(String path, String content) {
		File file = new File(path);

		try {
			FileWriter writer = new FileWriter(file);
			writer.write(content);
			// ���û�е���flush(),Ҳû�йر�����������Ὣ�ַ���д���ļ���
			writer.close();// �ر���

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ʾ���ڶԻ���
	 * 
	 */
	public static void showAboutDialog() {
		JOptionPane
				.showMessageDialog(
						null,
						"����:������\nQQ:1094035769\n\n����:2019��5��",
						"������Ϸ", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * ��ʾ��ӭ�Ի���
	 * 
	 */
	public static void showWelcomeDialog() {
		JOptionPane.showMessageDialog(null, "ף����Ϸ��죡", "��ӭʹ��",
				JOptionPane.INFORMATION_MESSAGE);

	}

	/**
	 * ���Թ�����
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// ��������
		URL url = ChessUtil.class.getResource("/sounds/back.mid");
		ChessUtil.playSound("back.mid");
		System.out.println(url);
		// AudioClip bgSound = JApplet.newAudioClip(url);

	}

}
