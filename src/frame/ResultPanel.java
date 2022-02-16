package frame;

import java.awt.*;
import java.sql.SQLException;

import javax.swing.*;
import database.*;

public class ResultPanel extends JPanel {

	public ResultPanel(String serverName, String clientName, int serverScore, int clientScore) throws SQLException {
		setBackground(Color.green);
		setLayout(null);
		setSize(1000, 800);
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		jp.setSize(700, 500);
		JPanel jpScore = new JPanel();
		jpScore.setLayout(new GridLayout(2, 3));

		JLabel jlServer = new JLabel(serverName, JLabel.CENTER);
		jlServer.setFont(new Font("Cooper Black", Font.BOLD, 50));
		JLabel jlSpace = new JLabel("");
		JLabel jlClient = new JLabel(clientName, JLabel.CENTER);
		jlClient.setFont(new Font("Cooper Black", Font.BOLD, 50));
		JLabel jlServerScore = new JLabel(Integer.toString(serverScore), JLabel.CENTER);
		jlServerScore.setFont(new Font("Cooper Black", Font.BOLD, 80));
		JLabel jlColon = new JLabel(":", JLabel.CENTER);
		jlColon.setFont(new Font("Cooper Black", Font.BOLD, 70));
		JLabel jlClientScore = new JLabel(Integer.toString(clientScore), JLabel.CENTER);
		jlClientScore.setFont(new Font("Cooper Black", Font.BOLD, 80));
		jpScore.add(jlServer);
		jpScore.add(jlSpace);
		jpScore.add(jlClient);
		jpScore.add(jlServerScore);
		jpScore.add(jlColon);
		jpScore.add(jlClientScore);
		jpScore.setOpaque(false);

		JLabel jlResult = new JLabel();
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

		Database database = new Database();

		JPanel jpRank = new JPanel();
		jpRank.setLayout(new GridLayout(2, 1));
		JLabel jlRecord;
		JLabel jlRank;
		if (IntroFrame.isServer) {
			jlRecord = new JLabel("³ªÀÇ ÀüÀû : " + database.getWin(serverName) + "½Â " + database.getLose(serverName) + "ÆÐ");
			jlRank = new JLabel("³ªÀÇ ·©Å· : " + database.getRanking(serverName));
		} else {
			jlRecord = new JLabel("³ªÀÇ ÀüÀû : " + database.getWin(clientName) + "½Â " + database.getLose(clientName) + "ÆÐ");
			jlRank = new JLabel("³ªÀÇ ·©Å· : " + database.getRanking(clientName) + "À§");
		}
		jlRecord.setHorizontalAlignment(JLabel.CENTER);
		jlRecord.setFont(new Font("ÈÞ¸Õ¿¢½ºÆ÷", Font.BOLD, 30));
		jlRank.setHorizontalAlignment(JLabel.CENTER);
		jlRank.setFont(new Font("ÈÞ¸Õ¿¢½ºÆ÷", Font.BOLD, 30));
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
