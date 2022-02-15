package play;

import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.swing.*;
import frame.GameFrame;
import play.Player1.Jump;

//�÷��̾�2(������) ������
public class Player2 extends JLabel {
	ImageIcon pikachuL_setSize; // ���� ���� ��ī��
	ImageIcon pikachuR_setSize; // ������ ���� ��ī��
	JPanel jp;
	int netXPos; // ��Ʈ�� x ��ǥ
	int netXSize;
	static int xSize;
	static int ySize;
	int field;
	int yPos;
	boolean fall;
	boolean jump;
	
	public Player2(JPanel jp, JLabel jlNet) {
		this.jp = jp;
		field=jp.getHeight()-GameFrame.groundHeight;
		netXPos = jlNet.getX(); // ��Ʈ�� x ��ǥ
		netXSize = jlNet.getWidth();
		ImageIcon pikachuL = new ImageIcon("pikachu_L.png");
		Image imgL = pikachuL.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		pikachuL_setSize = new ImageIcon(imgL);
		setIcon(pikachuL_setSize);

		xSize = pikachuL_setSize.getIconWidth();
		ySize = pikachuL_setSize.getIconHeight();
		setSize(xSize, ySize);

		ImageIcon pikachuR = new ImageIcon("pikachu_R.png");
		Image imgR = pikachuR.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		pikachuR_setSize = new ImageIcon(imgR);
	}

	public void setPikachu(int xPos, int yPos) { // ������ ��ǥ (x,y)�� ��ī�� ����
		this.yPos = yPos;
		jp.add(this);
		setLocation(xPos, yPos);
		updateUI();
	}

	public void moveRight() {
		setIcon(pikachuR_setSize); // ���������� �����̸� ������ ���� ��ī��� ����
		if (getX() + getWidth() >= jp.getWidth())
			setLocation(getX(), yPos);
		else
			setLocation(getX() + 20, yPos);
		updateUI();
	}

	public void moveLeft() {
		setIcon(pikachuL_setSize); // �������� �����̸� ���� ���� ��ī��� ����
		if (getX() <= netXPos + netXSize)
			setLocation(getX(), yPos);
		else
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
						System.out.println("���� ����");
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
							System.out.println("���� ����");
							long t1 = getTime(); // ����ð��� ������
							long t2;
							int set = 1; // ���� ��� ����
							while (foot < field) { // ���� ���� ��� ������ �ݺ�
								t2 = getTime() - t1; // ���� �ð����� t1�� ����
								int jumpY = set + (int) (t2 / 40);
								y = y+ jumpY; // y��ǥ�� ���Ϸ��� ���Ѵ�
								foot = y + ySize; // �߹ٴ� ��ġ ����
								setLocation(getX(), y);
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
