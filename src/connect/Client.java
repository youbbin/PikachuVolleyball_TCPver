package connect;

import java.net.*;
import java.sql.SQLException;

import javax.swing.JLabel;

import database.Database;
import play.Opponent;
import play.Pokeball_Client;
import java.awt.Color;
import java.awt.Font;
import java.io.*;
import frame.*;

public class Client extends Thread {
	Socket c;
	String serverIP;
	int serverPort;
	BufferedReader br;
	BufferedWriter bw;
	public Opponent op;
	public Pokeball_Client pbc;
	public JLabel jlRound;
	public JLabel jlScore_ps;
	public JLabel jlScore_pc;
	Database database;
	String serverName;

	public Client(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		try {
			c = new Socket(serverIP, serverPort);
			OutputStreamWriter os = new OutputStreamWriter(c.getOutputStream());
			bw = new BufferedWriter(os);
			InputStreamReader is = new InputStreamReader(c.getInputStream());
			br = new BufferedReader(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		jlRound = new JLabel(); // 현재의 라운드 표시
		jlRound.setSize(500, 100);
		jlRound.setLocation(230, 10);
		jlRound.setHorizontalAlignment(JLabel.CENTER);
		jlRound.setForeground(Color.red);
		jlRound.setFont(new Font("Cooper Black", Font.BOLD, 50));

		jlScore_ps = new JLabel("0", JLabel.CENTER); // 플레이어1의 점수 레이블
		jlScore_ps.setFont(new Font("Cooper Black", Font.BOLD, 70));
		jlScore_ps.setForeground(Color.red);
		jlScore_ps.setSize(100, 100);
		jlScore_ps.setLocation(50, 0);

		jlScore_pc = new JLabel("0", JLabel.CENTER); // 플레이어2의 점수 레이블
		jlScore_pc.setFont(new Font("Cooper Black", Font.BOLD, 70));
		jlScore_pc.setForeground(Color.red);
		jlScore_pc.setSize(100, 100);
		jlScore_pc.setLocation(850, 0);

		op = new Opponent(this);
		pbc = new Pokeball_Client();
	}

	public void send(String str) {
		try {
			bw.write(str + "\n");
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getServerName() {
		return serverName;
	}

	public void run() {
		while (true) {
			try {
				String read = br.readLine(); // 서버로부터 정보 수신
				String[] split = read.split(" "); // 수신한 좌표 정보를 배열에 저장
				// split={ 방향, 플레이어 x좌표, 플레이어 y좌표, 포켓볼 x좌표, 포켓볼 y좌표 , 라운드, 서버 점수, 클라이언트 점수}
				if (split.length == 1)
					serverName = split[0];
				else {
					if (split[0].equals("0")) { // 방향이 오른쪽이면 오른쪽 보는 아이콘으로 설정
						op.setIcon(Opponent.pikachuR_setSize);
					} else if (split[0].equals("1")) { // 방향이 왼쪽이면 왼쪽 보는 아이콘으로 설정
						op.setIcon(Opponent.pikachuL_setSize);
					}
					// 상대 플레이어와 포켓볼 셋팅
					op.setOpponent(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
					pbc.setPokeball(Integer.parseInt(split[3]), Integer.parseInt(split[4]));
					int round = Integer.parseInt(split[5]);
					jlRound.setText(round + " Round");
					jlScore_ps.setText(split[6]);
					jlScore_pc.setText(split[7]);
					int psScore = Integer.parseInt(split[6]);
					int pcScore = Integer.parseInt(split[7]);

					if (Integer.parseInt(split[5]) == 10 && psScore + pcScore == 10) { // 게임 종료 시
						database = new Database(); //데이터베이스 클래스 객체 생성
						if (psScore > pcScore) {
							jlRound.setText("Server Win!");
							try {
								database.updateLose(ClientInput.name); // 이긴 횟수 업데이트
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						if (psScore < pcScore) {
							jlRound.setText("Client Win!");
							try {
								database.updateWin(ClientInput.name); // 진 횟수 업데이트
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						if (psScore == pcScore)
							jlRound.setText("Draw");
						Thread.sleep(1000); // 게임 종료 1초 후 결과 패널 나옴
						ResultPanel resultPanel = new ResultPanel(serverName, ClientInput.name, psScore, pcScore);
						GameFrame.jp.setVisible(false);
						GameFrame.ct.add(resultPanel);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
