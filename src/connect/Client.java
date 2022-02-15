package connect;

import java.net.*;

import play.Opponent;

import java.io.*;

public class Client extends Thread {
	Socket c;
	String serverIP;
	int serverPort;
	BufferedReader br;
	BufferedWriter bw;
	String read;
	public Opponent op;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		op =new Opponent(this);
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
		while (true) {
			try {
				read = br.readLine();
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
			}
		}
	}
}
