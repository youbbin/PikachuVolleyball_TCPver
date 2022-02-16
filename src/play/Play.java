package play;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import connect.ClientInput;
import frame.GameFrame;
import frame.IntroFrame;
import frame.ResultPanel;
import database.*;
import connect.ServerInput;

public class Play {
	boolean gameFinish = false;
	JPanel jp;
	GameFrame gameframe;
	static final int ROUND_MAX = 10; // 라운드 수
	public static String winner; // 우승자
	ImageIcon iiWin_setSize;
	static int round;
	Database database;

	public Play(GameFrame gameframe) {
		this.gameframe = gameframe;
		this.jp = gameframe.jp;
		ImageIcon iiWin = new ImageIcon("pikachu_win.png");
		Image imgWin = iiWin.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		iiWin_setSize = new ImageIcon(imgWin);

		if (!IntroFrame.isServer) { // 클라이언트일 경우
			jp.add(gameframe.client.pbc);
			jp.add(gameframe.client.jlRound);
			jp.add(gameframe.client.jlScore_ps);
			jp.add(gameframe.client.jlScore_pc);
		}
	}

	public void roundStart() {
		if (IntroFrame.isServer) { // 서버일 경우
			// 서버가 ROUND_MAX만큼 포켓볼 스레드 실행
			JLabel jlRound = new JLabel(); // 몇 라운드인지 표시하는 레이블
			jlRound.setSize(500, 100);
			jlRound.setLocation(230, 10);
			jlRound.setHorizontalAlignment(JLabel.CENTER);
			jlRound.setForeground(Color.red);
			jlRound.setFont(new Font("Cooper Black", Font.BOLD, 50));
			jp.add(jlRound);

			Pokeball ball;
			for (round = 1; round <= ROUND_MAX; round++) {
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
			database = new Database();
			if (Pokeball.psScore > Pokeball.pcScore) {
				jlRound.setText("Server Win!");
				try {
					database.updateWin(ServerInput.name); // 진 횟수 업데이트
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (Pokeball.pcScore > Pokeball.psScore) {
				jlRound.setText("Client Win!");
				try {
					database.updateLose(ServerInput.name); // 이긴 횟수 업데이트
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (Pokeball.psScore == Pokeball.pcScore) {
				jlRound.setText("Draw");
			}
			try {
				Thread.sleep(1000);
				ResultPanel resultpanel = new ResultPanel(ServerInput.name, gameframe.server.getClientName(),
						Pokeball.psScore, Pokeball.pcScore);
				System.out.println(ServerInput.name + " " + gameframe.server.getClientName() + " " + Pokeball.psScore
						+ " " + Pokeball.pcScore);
				jp.setVisible(false);
				gameframe.ct.add(resultpanel);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}