package frame;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import database.*;

public class ResultPanel extends JPanel {
	public ResultPanel(String serverName, String clientName, int serverScore, int clientScore) throws SQLException {
		setBackground(Color.green); // 패널 배경 : 초록색
		setLayout(null);
		setSize(1000, 800);
		JPanel jp = new JPanel(); // 레이블과 랭킹 테이블이 추가될 패널
		jp.setLayout(new BorderLayout());
		jp.setSize(700, 500);
		JPanel jpScore = new JPanel(); // 점수 패널
		jpScore.setLayout(new GridLayout(2, 3));

		JLabel jlServer = new JLabel(serverName, JLabel.CENTER); // 서버 이름 레이블
		jlServer.setFont(new Font("Cooper Black", Font.BOLD, 50));
		JLabel jlSpace = new JLabel("");
		JLabel jlClient = new JLabel(clientName, JLabel.CENTER); // 클라이언트 이름 레이블
		jlClient.setFont(new Font("Cooper Black", Font.BOLD, 50));
		JLabel jlServerScore = new JLabel(Integer.toString(serverScore), JLabel.CENTER); // 서버 점수
		jlServerScore.setFont(new Font("Cooper Black", Font.BOLD, 80));
		JLabel jlColon = new JLabel(":", JLabel.CENTER);
		jlColon.setFont(new Font("Cooper Black", Font.BOLD, 70));
		JLabel jlClientScore = new JLabel(Integer.toString(clientScore), JLabel.CENTER); // 클라이언트 점수
		jlClientScore.setFont(new Font("Cooper Black", Font.BOLD, 80));
		jpScore.add(jlServer);
		jpScore.add(jlSpace);
		jpScore.add(jlClient);
		jpScore.add(jlServerScore);
		jpScore.add(jlColon);
		jpScore.add(jlClientScore);
		jpScore.setOpaque(false);

		JLabel jlResult = new JLabel(); // 게임 결과 (win, lose, draw)
		jlResult.setHorizontalAlignment(JLabel.CENTER);
		jlResult.setFont(new Font("Cooper Black", Font.BOLD, 70));
		if (IntroFrame.isServer) {
			if (serverScore > clientScore) {
				jlResult.setText("Win");
			} else if (serverScore < clientScore) {
				jlResult.setText("Lose");
			} else
				jlResult.setText("Draw");
		} else {
			if (serverScore > clientScore) {
				jlResult.setText("Lose");
			} else if (serverScore < clientScore) {
				jlResult.setText("Win");
			} else
				jlResult.setText("Draw");
		}

		Database database = new Database(); // 데이터베이스 클래스 객체 생성

		JPanel jpRank = new JPanel(); // 나의 랭킹, 전적 출력 패널
		jpRank.setLayout(new GridLayout(2, 1));
		JLabel jlRecord;
		JLabel jlRank;
		if (IntroFrame.isServer) {
			jlRecord = new JLabel("나의 전적 : " + database.getWin(serverName) + "승 " + database.getLose(serverName) + "패");
			jlRank = new JLabel("나의 랭킹 : " + database.getRanking(serverName));
		} else {
			jlRecord = new JLabel("나의 전적 : " + database.getWin(clientName) + "승 " + database.getLose(clientName) + "패");
			jlRank = new JLabel("나의 랭킹 : " + database.getRanking(clientName) + "위");
		}
		jlRecord.setHorizontalAlignment(JLabel.CENTER);
		jlRecord.setFont(new Font("휴먼엑스포", Font.BOLD, 30));
		jlRank.setHorizontalAlignment(JLabel.CENTER);
		jlRank.setFont(new Font("휴먼엑스포", Font.BOLD, 30));
		jpRank.setOpaque(false);
		jpRank.add(jlRecord);
		jpRank.add(jlRank);
		jp.add(jpScore, BorderLayout.NORTH);
		jp.add(jlResult, BorderLayout.CENTER);
		jp.add(jpRank, BorderLayout.SOUTH);
		jp.setOpaque(false);
		jp.setLocation(150, 100);
		add(jp);
	}
}
