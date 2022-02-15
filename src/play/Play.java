package play;

import java.awt.*;
import javax.swing.*;
import frame.GameFrame;
import frame.IntroFrame;

public class Play {
	boolean gameFinish = false;
	JPanel jp;
	GameFrame gameframe;
	static final int ROUND_MAX = 10; // 라운드 수
	public static String winner; // 우승자
	ImageIcon iiWin_setSize;

	public Play(GameFrame gameframe) {
		this.gameframe = gameframe;
		this.jp = gameframe.jp;
		ImageIcon iiWin = new ImageIcon("pikachu_win.png");
		Image imgWin = iiWin.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		iiWin_setSize = new ImageIcon(imgWin);
	}

	public void roundStart() {
		JLabel jlRound = new JLabel(); // 몇 라운드인지 표시하는 레이블
		jlRound.setSize(500, 100);
		jlRound.setLocation(230, 10);
		jlRound.setHorizontalAlignment(JLabel.CENTER);
		jlRound.setForeground(Color.red);
		jlRound.setFont(new Font("Cooper Black", Font.BOLD, 50));
		jp.add(jlRound);

		Pokeball ball;
		// ROUND_MAX만큼 포켓볼 스레드 실행
		for (int round = 1; round <= ROUND_MAX; round++) {
			jlRound.setText(round + " Round");
			ball = new Pokeball(gameframe); // 포켓볼 스레드 생성
			jp.add(ball);
			Thread ballThread = new Thread((Runnable) ball);
			ballThread.start(); // 포켓볼 스레드 시작
			try {
				ballThread.join(); // 현재 스레드 종료 후 다음 스레드 실행
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
			winner = null; // 비기면 우승자 없음
		}
	}
}
