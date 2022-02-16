package connect;

import java.io.*;
import java.net.*;

import frame.GameFrame;
import frame.IntroFrame;
import play.GameStartLabel;
import play.Opponent;
import play.Play;

public class Server extends Thread {
	ServerSocket ss;
	Socket s;
	int port;
	BufferedWriter bw;
	BufferedReader br;
	WaitingFrame waitingframe;
	IntroFrame introframe;
	public Opponent op;
	GameFrame gf;
	String clientName;

	public Server(int port, WaitingFrame waitingframe, IntroFrame introframe) {
		this.waitingframe = waitingframe;
		this.introframe = introframe;
		this.port = port;
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public String getClientName() {
		return clientName;
	}

	public void run() {
		try {
			s = ss.accept(); // 클라이언트와 연결
			waitingframe.setVisible(false); // 접속대기 창 보이지 않게 함
			introframe.setVisible(false); // 인트로 화면 보이지 않게 함
			gf = new GameFrame(this);
			op = new Opponent(this); // 상대방 객체 생성
			gf.createGameFrame(); // 게임 창 생성 및 시작
			OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
			bw = new BufferedWriter(os);
			InputStreamReader is = new InputStreamReader(s.getInputStream());
			br = new BufferedReader(is);
			send(ServerInput.name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String read = "";
		while (true) {
			try {
				read = br.readLine(); // 클라이언트의 좌표 수신
				String[] split = read.split(" "); // 수신한 좌표 정보를 배열에 저장
				if (split.length == 1) {
					clientName = split[0];
					System.out.println("서버가 받은 클라이언트 이름:" + clientName);
				}
				if (split[0].equals("0")) { // 방향이 오른쪽이면 오른쪽 보는 아이콘으로 설정
					op.setIcon(Opponent.pikachuR_setSize);
				} else if (split[0].equals("1")) { // 방향이 왼쪽이면 왼쪽 보는 아이콘으로 설정
					op.setIcon(Opponent.pikachuL_setSize);
				}
				op.setOpponent(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}
}
