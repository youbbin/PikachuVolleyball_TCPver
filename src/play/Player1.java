package play;

import java.awt.*;
import javax.swing.*;

import connect.Server;
import frame.GameFrame;

import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.swing.*;

import frame.GameFrame;

//플레이어1(왼쪽) 스레드
public class Player1 extends JLabel {
	ImageIcon pikachuR_setSize;
	ImageIcon pikachuL_setSize;
	JPanel jp;
	int netXPos; // 네트의 x 좌표
	static int xSize;
	static int ySize;
	int field;
	int yPos;
	boolean fall;
	boolean jump;
	Server server;
	Player2 player2;

	public Player1(JPanel jp, JLabel jlNet,Server server) {
		this.jp = jp;
		this.server=server;
		field = jp.getHeight() - GameFrame.groundHeight;
		netXPos = jlNet.getX(); // 네트의 x좌표
		ImageIcon pikachuR = new ImageIcon("pikachu_R.png");
		Image img = pikachuR.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		pikachuR_setSize = new ImageIcon(img);
		setIcon(pikachuR_setSize);
		xSize = pikachuR_setSize.getIconWidth();
		ySize = pikachuR_setSize.getIconHeight();
		setSize(xSize, ySize);
		ImageIcon pikachuL = new ImageIcon("pikachu_L.png");
		Image imgL = pikachuL.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		pikachuL_setSize = new ImageIcon(imgL);
	}

	public void setPikachu(int xPos, int yPos) { // 프레임 좌표 (x,y)에 피카츄 셋팅
		this.yPos = yPos;
		jp.add(this);
		setLocation(xPos, yPos);
		updateUI();
	}

	public void moveRight() {
		setIcon(pikachuR_setSize); // 오른쪽으로 움직이면 오른쪽 보는 피카츄로 셋팅
		if (getX() + getWidth() >= netXPos) {
			setLocation(getX(), yPos);
		} else {
			setLocation(getX() + 20, yPos);

		}
		sendLocation();  // 좌표 전송
		updateUI();
	}

	public void moveLeft() {
		setIcon(pikachuL_setSize); // 왼쪽으로 움직이면 왼쪽 보는 피카츄로 셋팅
		if (getX() <= 0) {
			setLocation(0, yPos);

		} else {
			setLocation(getX() - 20, yPos);

		}
		sendLocation(); // 좌표 전송
		updateUI();
	}

	public void jump() {
		Jump jump = new Jump();
	}

	public void sendLocation() {
		server.send(getX() + " " + getY()); // x좌표, y좌표 전송
	}

	public void setClient(int x, int y) { // 클라이언트 위치 설정
		player2.setLocation(x, y);
		player2.updateUI();
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
							sendLocation(); // 좌표 전송
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
								y = y + jumpY; // y좌표에 낙하량을 더한다
								foot = y + ySize; // 발바닥 위치 저장
								setLocation(getX(), y);
								
								sendLocation();// 좌표 전송
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
