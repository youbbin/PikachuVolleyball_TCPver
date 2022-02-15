package play;


import java.awt.*;
import java.util.Random;
import javax.swing.*;
import frame.GameFrame;

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
	Player1 p1;
	Player2 p2;
	static boolean roundFinish = false;
	static int p1Score;
	static int p2Score;

	public Pokeball(GameFrame gameframe) {
		jp = gameframe.jp;
		p1 = gameframe.p1;
		p2 = gameframe.p2;
		jlScore_p1 = gameframe.jlScore_p1;
		jlScore_p2 = gameframe.jlScore_p2;
		ImageIcon iiBall = new ImageIcon("pokeball.png");
		Image imgBall = iiBall.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
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
			if (xPos <= 0 || xPos + xSize >= xMax) {
				xDir *= -1;
			}
			// õ�忡 ����� ��
			else if (yPos <= 0) {
				yDir *= -1;
			}
			// �÷��̾�1 ������ �ٴڿ� ����� ��
			else if (xPos + xSize <= netXPos && yPos + ySize >= yMax - GameFrame.groundHeight) {
				roundFinish = true;
				p2Score++;
				setScore();
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
				p1Score++;
				setScore();
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
			else if (xPos + xSize == netXPos && yPos + ySize >= netYPos) {
				xDir *= -1;
			}
			// ��Ʈ �����ʿ� ����� ��
			else if (xPos == netXPos + netXSize && yPos + ySize >= netYPos) {
				xDir *= -1;
			}

			// ��Ʈ ���ʿ� ����� ��
			else if (xPos + xSize > netXPos && xPos < netXPos + netXSize && yPos + ySize >= netYPos) {
				yDir *= -1;
			}

			// �÷��̾�1 �Ӹ� �ݻ�
			else if (xPos + xSize >= p1.getX() && xPos <= p1.getX() + Player1.xSize && yPos + ySize == p1.getY()) {
				yDir *= -1;
			}
			// �÷��̾�1 �� ���� �ݻ�
			else if (xPos + xSize == p1.getX() && yPos + ySize > p1.getY()) {
				xDir *= -1;
			}
			// �÷��̾�1 �� ������ �ݻ�
			else if (xPos == p1.getX() + p1.getWidth() && yPos + ySize > p1.getY()) {
				xDir *= -1;
			}
			// �÷��̾�2 �Ӹ� �ݻ�
			else if (xPos+ xSize >= p2.getX() && xPos <= p2.getX() + Player2.xSize && yPos + ySize == p2.getY()) {
				yDir *= -1;
			}
			// �÷��̾�2 �� ���� �ݻ�
			else if (xPos + xSize == p2.getX() && yPos + ySize > p2.getY()) {
				xDir *= -1;
			}
			// �÷��̾�2 �� ������ �ݻ�
			else if (xPos == p2.getX() + p2.getWidth() && yPos + ySize > p2.getY()) {
				yDir *= -1;
			}
			xPos += (xDir * xSpeed); // x��ǥ ����
			yPos += (yDir * ySpeed); // y��ǥ ����
			setLocation(xPos, yPos); // ��ġ ����
			updateUI();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setScore() {
		jlScore_p1.setText(Integer.toString(p1Score));
		jlScore_p2.setText(Integer.toString(p2Score));
	}

	public void run() {
		move();
	}
}
