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
		netXPos = gameframe.jlNet.getX(); // ��Ʈ�� x��ǥ
		netYPos = gameframe.jlNet.getY(); // ��Ʈ�� y��ǥ
		netXSize = gameframe.jlNet.getWidth(); // ��Ʈ�� �ʺ�
		netYSize = gameframe.jlNet.getHeight(); // ��Ʈ�� ����
		xMax = jp.getWidth();
		yMax = jp.getHeight();
		xSize = iiBall_setSize.getIconWidth(); // ���Ϻ��� �ʺ�
		ySize = iiBall_setSize.getIconHeight(); // ���Ϻ��� ����
		setSize(xSize, ySize);
		xPos = r.nextInt(xMax - xSize); // ���Ϻ��� x��ǥ
		yPos = 100; // ���Ϻ��� y��ǥ
		xDir = 1; // ���Ϻ��� x����
		yDir = 1; // ���Ϻ��� y����
		xSpeed = 1; // x���ǵ�
		ySpeed = 1; // y���ǵ�
		setVisible(true);
	}

	public void move() {
		while (true) {
			// �¿� ���� ����� ��
			if (xPos <= 0 || xPos + xSize >= xMax) 
				xDir *= -1;
			// õ�忡 ����� ��
			else if (yPos <= 0) 
				yDir *= -1;
			// �÷��̾�1 ������ �ٴڿ� ����� ��
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
			// �÷��̾�2 ������ �ٴڿ� ����� ��
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
			// ��Ʈ ���ʿ� ����� ��
			else if (xPos + xSize == netXPos && yPos + ySize >= netYPos) 
				xDir *= -1;			
			// ��Ʈ �����ʿ� ����� ��
			else if (xPos == netXPos + netXSize && yPos + ySize >= netYPos) 
				xDir *= -1;
			// ��Ʈ ���ʿ� ����� ��
			else if (xPos + xSize > netXPos && xPos < netXPos + netXSize 
					&& yPos + ySize >= netYPos) 
				yDir *= -1;
			// �÷��̾�1 �Ӹ� �ݻ�
			else if (xPos + xSize >= ps.getX() && xPos <= ps.getX() + ps.xSize 
					&& yPos + ySize == ps.getY()) 
				yDir *= -1;			
			// �÷��̾�1 �� ���� �ݻ�
			else if (xPos + xSize == ps.getX() && yPos + ySize > ps.getY()) 
				xDir *= -1;			
			// �÷��̾�1 �� ������ �ݻ�
			else if (xPos == ps.getX() + ps.getWidth() && yPos + ySize > ps.getY()) 
				xDir *= -1;
			// �÷��̾�2 �Ӹ� �ݻ�
			else if (xPos + xSize >= op.getX() && xPos <= op.getX() + op.xSize 
					&& yPos + ySize == op.getY()) 
				yDir *= -1;
			// �÷��̾�2 �� ���� �ݻ�
			else if (xPos + xSize == op.getX() && yPos + ySize > op.getY()) 
				xDir *= -1;
			// �÷��̾�2 �� ������ �ݻ�
			else if (xPos == op.getX() + op.getWidth() && yPos + ySize > op.getY()) 
				yDir *= -1;
			xPos += (xDir * xSpeed); // x��ǥ ����
			yPos += (yDir * ySpeed); // y��ǥ ����
			setLocation(xPos, yPos); // ��ġ ����
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
		// ������ ����� ��ġ, �� ��ġ�� ����
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
