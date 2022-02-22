package play;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import connect.*;
import frame.GameFrame;

public class Player_Server extends JLabel {
	Server server;
	ImageIcon pikachuR_setSize;
	ImageIcon pikachuL_setSize;
	JPanel jp;
	int netXPos; // ��Ʈ�� x ��ǥ
	static int xSize;
	static int ySize;
	int field;
	int yPos;
	boolean fall; // �ϰ� ������ ǥ��
	boolean jump; // ���� ������ ǥ��
	public int direction; // 0: ������, 1: ����

	public Player_Server(Server server, JPanel jp, JLabel jlNet) { // ���� �������� �� player
		this.server = server;
		this.jp = jp;
		field = jp.getHeight() - GameFrame.groundHeight;
		netXPos = jlNet.getX(); // ��Ʈ�� x��ǥ
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
		if (getX() + getWidth() >= netXPos) {
			setLocation(getX(), yPos);
		} else
			setLocation(getX() + 20, yPos);
		updateUI();
	}

	public void moveLeft() {
		direction = 1;
		setIcon(pikachuL_setSize); // �������� �����̸� ���� ���� ��ī��� ����
		if (getX() <= 0) {
			setLocation(0, yPos);
		} else
			setLocation(getX() - 20, yPos);
		updateUI();
	}

	public void jump() {
		Jump jump = new Jump();
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
							updateUI();
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
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
								updateUI();
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							fall = false;
							break;
						}
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
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
