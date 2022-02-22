package play;

import java.awt.*;
import java.util.Random;
import javax.swing.*;
import frame.GameFrame;
import connect.*;

public class Pokeball extends JLabel implements Runnable {
	JPanel jp;
	JLabel jlScore_p1;
	JLabel jlScore_p2;
	int xSize;
	int ySize;
	int xPos;
	int yPos;
	int xDir;
	int yDir;
	int xMax;
	int yMax;
	int xSpeed;
	int ySpeed;
	int netXPos;
	int netYPos;
	int netXSize;
	int netYSize;
	Random r = new Random();
	Player_Server ps;
	Opponent op;
	static boolean roundFinish = false;
	static int psScore;
	static int pcScore;
	Server server;

	public Pokeball(GameFrame gameframe) {
		jp = gameframe.jp;
		ps = gameframe.ps;
		op = gameframe.op;
		server = gameframe.server;

		jlScore_p1 = gameframe.jlScore_p1;
		jlScore_p2 = gameframe.jlScore_p2;
		ImageIcon iiBall = new ImageIcon("pokeball.png");
		Image imgBall = iiBall.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		ImageIcon iiBall_setSize = new ImageIcon(imgBall);
		setIcon(iiBall_setSize);
		netXPos = gameframe.jlNet.getX(); // 네트의 x좌표
		netYPos = gameframe.jlNet.getY(); // 네트의 y좌표
		netXSize = gameframe.jlNet.getWidth(); // 네트의 너비
		netYSize = gameframe.jlNet.getHeight(); // 네트의 높이
		xMax = jp.getWidth();
		yMax = jp.getHeight();
		xSize = iiBall_setSize.getIconWidth(); // 포켓볼의 너비
		ySize = iiBall_setSize.getIconHeight(); // 포켓볼의 높이
		setSize(xSize, ySize);
		xPos = r.nextInt(xMax - xSize); // 포켓볼의 x좌표
		yPos = 100; // 포켓볼의 y좌표
		xDir = 1; // 포켓볼의 x방향
		yDir = 1; // 포켓볼의 y방향
		xSpeed = 1; // x스피드
		ySpeed = 1; // y스피드
		setVisible(true);
	}

	public void move() {
		while (true) {
			// 좌우 벽에 닿았을 때
			if (xPos <= 0 || xPos + xSize >= xMax) 
				xDir *= -1;
			// 천장에 닿았을 때
			else if (yPos <= 0) 
				yDir *= -1;
			// 플레이어1 진영의 바닥에 닿았을 때
			else if (xPos + xSize <= netXPos && yPos + ySize >= yMax - GameFrame.groundHeight) {
				roundFinish = true;
				pcScore += 1;
				setScore();
				sendLocation();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setVisible(false);
				break;
			}
			// 플레이어2 진영의 바닥에 닿았을 때
			else if (xPos >= netXPos + netXSize && yPos + ySize >= yMax - GameFrame.groundHeight) {
				roundFinish = true;
				psScore += 1;
				setScore();
				sendLocation();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setVisible(false);
				break;
			}
			// 네트 왼쪽에 닿았을 때
			else if (xPos + xSize == netXPos && yPos + ySize >= netYPos) 
				xDir *= -1;			
			// 네트 오른쪽에 닿았을 때
			else if (xPos == netXPos + netXSize && yPos + ySize >= netYPos) 
				xDir *= -1;
			// 네트 위쪽에 닿았을 때
			else if (xPos + xSize > netXPos && xPos < netXPos + netXSize 
					&& yPos + ySize >= netYPos) 
				yDir *= -1;
			// 플레이어1 머리 반사
			else if (xPos + xSize >= ps.getX() && xPos <= ps.getX() + ps.xSize 
					&& yPos + ySize == ps.getY()) 
				yDir *= -1;			
			// 플레이어1 몸 왼쪽 반사
			else if (xPos + xSize == ps.getX() && yPos + ySize > ps.getY()) 
				xDir *= -1;			
			// 플레이어1 몸 오른쪽 반사
			else if (xPos == ps.getX() + ps.getWidth() && yPos + ySize > ps.getY()) 
				xDir *= -1;
			// 플레이어2 머리 반사
			else if (xPos + xSize >= op.getX() && xPos <= op.getX() + op.xSize 
					&& yPos + ySize == op.getY()) 
				yDir *= -1;
			// 플레이어2 몸 왼쪽 반사
			else if (xPos + xSize == op.getX() && yPos + ySize > op.getY()) 
				xDir *= -1;
			// 플레이어2 몸 오른쪽 반사
			else if (xPos == op.getX() + op.getWidth() && yPos + ySize > op.getY()) 
				yDir *= -1;
			xPos += (xDir * xSpeed); // x좌표 설정
			yPos += (yDir * ySpeed); // y좌표 설정
			setLocation(xPos, yPos); // 위치 셋팅
			sendLocation();
			updateUI();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void sendLocation() {
		// 서버의 방향과 위치, 공 위치를 전송
		server.send(ps.direction + " " + ps.getX() + " " + ps.getY() + " " + getX() + " " + getY() + " " + Play.round
				+ " " + psScore + " " + pcScore);
	}

	public void setScore() {
		jlScore_p1.setText(Integer.toString(psScore));
		jlScore_p2.setText(Integer.toString(pcScore));
	}

	public void run() {
		move();
	}
}
