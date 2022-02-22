package play;

import frame.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import connect.*;
import frame.GameFrame;

public class Player_Client extends JLabel {
	Client client;
	ImageIcon pikachuR_setSize;
	ImageIcon pikachuL_setSize;
	JPanel jp;
	int netXPos; // ��Ʈ�� x ��ǥ
	int netXSize;
	static int xSize;
	static int ySize;
	int field;
	int yPos;
	boolean fall;
	boolean jump;
	int direction; // 0: ������, 1: ����

	public Player_Client(Client client, JPanel jp, JLabel jlNet) { // ���� �������� �� player
		this.client = client;
		this.jp = jp;
		field = jp.getHeight() - GameFrame.groundHeight;
		netXPos = jlNet.getX(); // ��Ʈ�� x��ǥ
		netXSize = jlNet.getWidth();
		ImageIcon pikachuR = new ImageIcon("pikachu_R.png");
		Image img = pikachuR.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
		pikachuR_setSize = new ImageIcon(img);
		setIcon(pikachuR_setSize);
		xSize = pikachuR_setSize.getIconWidth();
		ySize = pikachuR_setSize.getIconHeight();
		setSize(xSize, ySize);
		ImageIcon pikachuL = new ImageIcon("pikachu_L.png");
		Image imgL = pikachuL.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
		pikachuL_setSize = new ImageIcon(imgL);
	}

	public void setPlayer(int xPos, int yPos) {
		this.yPos = yPos;
		jp.add(this);
		setLocation(xPos, yPos);
		updateUI();
	}

	public void moveRight() {
		direction = 0;
		setIcon(pikachuR_setSize); // ���������� �����̸� ������ ���� ��ī��� ����
		if (getX() + getWidth() >= jp.getWidth()) {
			setLocation(getX(), yPos);
		} else
			setLocation(getX() + 20, yPos);
		sendLocation();
		updateUI();
	}

	public void moveLeft() {
		direction = 1;
		setIcon(pikachuL_setSize); // �������� �����̸� ���� ���� ��ī��� ����
		if (getX() <= netXPos + netXSize) {
			setLocation(getX(), yPos);
		} else {
			setLocation(getX() - 20, yPos);
		}
		sendLocation();
		updateUI();
	}

	public void jump() {
		Jump jump = new Jump();
	}

	public void sendLocation() {
		client.send(direction + " " + getX() + " " + getY()); // x��ǥ, y��ǥ ����
	}

	class Jump {
		public Jump() {
			Thread up = new Thread() { // ��� ������
				public void run() {
					int y = getY();
					int foot = y + ySize; // �߹ٴ� ��ġ�� ��ī���� y��ǥ+��ī���� ����
					if (fall == false) {
						jump = true; // ���������� ����
						long t1 = getTime(); // ����ð��� ������
						long t2;
						int set = 8; // ���� ��� ����
						int jumpY = 8;
						while (jumpY > 0) {
							t2 = getTime() - t1; // ���� �ð����� t1�� ����
							jumpY = set - (int) (t2 / 40);
							y = y - jumpY;
							foot = y + ySize; // �߹ٴ� ��ġ ����
							setLocation(getX(), y);
							sendLocation(); // ��ǥ ����
							updateUI();
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						jump = false;
					}
				}
			};
			up.start();

			Thread down = new Thread() { // �ϰ� ������
				public void run() {
					while (true) {
						int y = getY();
						int foot = y + ySize;

						if (jump == false && foot < field && fall == false) {
							fall = true;
							long t1 = getTime(); // ����ð��� ������
							long t2;
							int set = 1; // ���� ��� ����
							while (foot < field) { // ���� ���� ��� ������ �ݺ�
								t2 = getTime() - t1; // ���� �ð����� t1�� ����
								int jumpY = set + (int) (t2 / 40);
								y = y + jumpY; // y��ǥ�� ���Ϸ��� ���Ѵ�
								foot = y + ySize; // �߹ٴ� ��ġ ����
								setLocation(getX(), y);
								sendLocation();// ��ǥ ����
								updateUI();
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							fall = false;
							break;
						}
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			};
			down.start();
		}

		public long getTime() {
			return Timestamp.valueOf(LocalDateTime.now()).getTime();
		}
	}
}
