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

	public Server(int port, WaitingFrame waitingframe,IntroFrame introframe) {
		this.waitingframe = waitingframe;
		this.introframe=introframe;
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

	public void run() {
		try {
			s = ss.accept(); // Ŭ���̾�Ʈ�� ����
			System.out.println("Ŭ���̾�Ʈ �����");
			waitingframe.setVisible(false); // ���Ӵ�� â ������ �ʰ� ��
			introframe.setVisible(false); //��Ʈ�� ȭ�� ������ �ʰ� ��
			gf = new GameFrame(this);
			op = new Opponent(this); // ���� ��ü ����
			gf.createGameFrame(); // ���� â ���� �� ����
			OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
			bw = new BufferedWriter(os);
			InputStreamReader is = new InputStreamReader(s.getInputStream());
			br = new BufferedReader(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String read = "";
		while (true) {
			try {
				read = br.readLine(); // Ŭ���̾�Ʈ�� ��ǥ ����
				System.out.println(read);
				String[] split = read.split(" "); // ������ ��ǥ ������ �迭�� ����
				if(split[0].equals("0")) { //������ �������̸� ������ ���� ���������� ����
					op.setIcon(Opponent.pikachuR_setSize);
				}else if(split[0].equals("1")) { //������ �����̸� ���� ���� ���������� ����
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
