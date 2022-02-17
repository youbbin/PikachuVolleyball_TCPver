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
	static final int ROUND_MAX = 10; // ���� ��
	static int round;
	Database database;

	public Play(GameFrame gameframe) {
		this.gameframe = gameframe;
		this.jp = gameframe.jp;
		if (!IntroFrame.isServer) { // Ŭ���̾�Ʈ�� ���
			jp.add(gameframe.client.pbc);
			jp.add(gameframe.client.jlRound);
			jp.add(gameframe.client.jlScore_ps);
			jp.add(gameframe.client.jlScore_pc);
		}
	}

	public void roundStart() {
		if (IntroFrame.isServer) { // ������ ���
			// ������ ROUND_MAX��ŭ ���Ϻ� ������ ����
			JLabel jlRound = new JLabel(); // �� �������� ǥ���ϴ� ���̺�
			jlRound.setSize(500, 100);
			jlRound.setLocation(230, 10);
			jlRound.setHorizontalAlignment(JLabel.CENTER);
			jlRound.setForeground(Color.red);
			jlRound.setFont(new Font("Cooper Black", Font.BOLD, 50));
			jp.add(jlRound);

			Pokeball ball; //���� ���Ϻ� 
			for (round = 1; round <= ROUND_MAX; round++) {
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
			database = new Database();
			if (Pokeball.psScore > Pokeball.pcScore) {
				jlRound.setText("Server Win!");
				try {
					database.updateWin(ServerInput.name); // �� Ƚ�� ������Ʈ
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (Pokeball.pcScore > Pokeball.psScore) {
				jlRound.setText("Client Win!");
				try {
					database.updateLose(ServerInput.name); // �̱� Ƚ�� ������Ʈ
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (Pokeball.psScore == Pokeball.pcScore) {
				jlRound.setText("Draw");
			}
			try {
				Thread.sleep(500);
				ResultPanel resultpanel = new ResultPanel(ServerInput.name, gameframe.server.getClientName(),
						Pokeball.psScore, Pokeball.pcScore);
				jp.setVisible(false);
				gameframe.ct.add(resultpanel);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}