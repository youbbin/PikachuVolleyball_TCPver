package play;

import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.swing.*;
import frame.GameFrame;
import play.Player1.Jump;

//플레이어2(오른쪽) 스레드
public class Player2 extends JLabel {
	ImageIcon pikachuL_setSize; // 왼쪽 보는 피카츄
	ImageIcon pikachuR_setSize; // 오른쪽 보는 피카츄
	JPanel jp;
	int netXPos; // 네트의 x 좌표
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
		netXPos = jlNet.getX(); // 네트의 x 좌표
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

	public void setPikachu(int xPos, int yPos) { // 프레임 좌표 (x,y)에 피카츄 셋팅
		this.yPos = yPos;
		jp.add(this);
		setLocation(xPos, yPos);
		updateUI();
	}

	public void moveRight() {
		setIcon(pikachuR_setSize); // 오른쪽으로 움직이면 오른쪽 보는 피카츄로 셋팅
		if (getX() + getWidth() >= jp.getWidth())
			setLocation(getX(), yPos);
		else
			setLocation(getX() + 20, yPos);
		updateUI();
	}

	public void moveLeft() {
		setIcon(pikachuL_setSize); // 왼쪽으로 움직이면 왼쪽 보는 피카츄로 셋팅
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

			Thread up = new Thread() { // 상승 스레드
				public void run() {
					int y = getY();
					int foot = y + ySize; // 발바닥 위치는 피카츄의 y좌표+피카츄의 높이
					if (fall == false) {
						System.out.println("점프 시작");
						jump = true; // 점프중으로 변경
						long t1 = getTime(); // 현재시간을 가져옴
						long t2;
						int set = 8; // 점프 계수 설정
						int jumpY = 8;
						while (jumpY > 0) {
							t2 = getTime() - t1; // 현재 시간에서 t1을 뺀다
							jumpY = set - (int) (t2 / 40);
							y = y - jumpY;
							foot = y + ySize; // 발바닥 위치 저장
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

			Thread down = new Thread() { // 하강 스레드
				public void run() {
					while (true) {
						int y = getY();
						int foot = y + ySize;

						if (jump == false && foot < field && fall == false) {
							fall = true;
							System.out.println("낙하 시작");
							long t1 = getTime(); // 현재시간을 가져옴
							long t2;
							int set = 1; // 낙하 계수 설정
							while (foot < field) { // 발이 땅에 닿기 전까지 반복
								t2 = getTime() - t1; // 현재 시간에서 t1을 뺀다
								int jumpY = set + (int) (t2 / 40);
								y = y+ jumpY; // y좌표에 낙하량을 더한다
								foot = y + ySize; // 발바닥 위치 저장
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
