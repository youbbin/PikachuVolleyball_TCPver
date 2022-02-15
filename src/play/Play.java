package play;

import java.awt.*;
import javax.swing.*;
import frame.GameFrame;
import frame.IntroFrame;

public class Play {
	boolean gameFinish = false;
	JPanel jp;
	GameFrame gameframe;
	static final int ROUND_MAX = 10; // ���� ��
	public static String winner; // �����
	ImageIcon iiWin_setSize;

	public Play(GameFrame gameframe) {
		this.gameframe = gameframe;
		this.jp = gameframe.jp;
		ImageIcon iiWin = new ImageIcon("pikachu_win.png");
		Image imgWin = iiWin.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		iiWin_setSize = new ImageIcon(imgWin);
	}

	public void roundStart() {
		JLabel jlRound = new JLabel(); // �� �������� ǥ���ϴ� ���̺�
		jlRound.setSize(500, 100);
		jlRound.setLocation(230, 10);
		jlRound.setHorizontalAlignment(JLabel.CENTER);
		jlRound.setForeground(Color.red);
		jlRound.setFont(new Font("Cooper Black", Font.BOLD, 50));
		jp.add(jlRound);

		Pokeball ball;
		// ROUND_MAX��ŭ ���Ϻ� ������ ����
		for (int round = 1; round <= ROUND_MAX; round++) {
			jlRound.setText(round + " Round");
			ball = new Pokeball(gameframe); // ���Ϻ� ������ ����
			jp.add(ball);
			Thread ballThread = new Thread((Runnable) ball);
			ballThread.start(); // ���Ϻ� ������ ����
			try {
				ballThread.join(); // ���� ������ ���� �� ���� ������ ����
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (Pokeball.p1Score > Pokeball.p2Score) {
			jlRound.setText("1P Win!");

			
		}
		if (Pokeball.p1Score < Pokeball.p2Score) {
			jlRound.setText("2P Win!");

		}
		if (Pokeball.p1Score == Pokeball.p2Score) {
			jlRound.setText("Draw");
			winner = null; // ���� ����� ����
		}
	}
}
