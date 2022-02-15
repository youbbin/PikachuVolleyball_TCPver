package play;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import frame.GameFrame;
import connect.*;

public class GameStartLabel extends JLabel implements Runnable { // 게임 시작 레이블이 위에서 중간까지 내려와 1초간 멈춘 후 게임이 시작됨
	JPanel jp;
	JLabel jlNet;

	GameFrame gameframe;
	public GameStartLabel(GameFrame gameframe) {
		this.gameframe=gameframe;
		this.jp = gameframe.jp;
		this.jlNet = gameframe.jlNet;
		ImageIcon iiStartLabel = new ImageIcon("start_label.png");
		Image imgStartLabel = iiStartLabel.getImage().getScaledInstance(500, 150, Image.SCALE_SMOOTH);
		ImageIcon iiStartLabel_setSize = new ImageIcon(imgStartLabel);
		setIcon(iiStartLabel_setSize);
		setSize(500, 150);
		setLocation(jp.getWidth() / 4, 0);
	}

	public void run() {
		while (true) {
			try {
				if (getY() >= jp.getHeight() * (0.4)) {
					Thread.sleep(1000);
					setVisible(false);
					GameFrame.gameStart = true; // 게임 시작
					Play play = new Play(gameframe);
					play.roundStart();
					break;
				}
				setLocation(jp.getWidth() / 4, getY() + 1);
				updateUI();
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}