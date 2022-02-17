package connect;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import frame.GameFrame;
import frame.IntroFrame;
import play.*;

public class ClientInput extends JFrame implements ActionListener {
	IntroFrame introframe;
	JTextField jtfServerIP;
	JTextField jtfServerPort;
	JTextField jtfName;
	JButton jbStart;
	JButton jbServer;
	JButton jbClient;
	int port;
	String ip;
	static String name;
	Client client;
	public Opponent op;

	public ClientInput(IntroFrame introframe) {
		this.introframe = introframe;
		Container ct = getContentPane();
		ct.setLayout(null);
		JLabel jlServerIP = new JLabel("서버의 IP를 입력하세요");
		jlServerIP.setSize(200, 50);
		jlServerIP.setLocation(80, 0);

		jtfServerIP = new JTextField(20); //서버 IP 입력 JTextField
		jtfServerIP.setSize(220, 25);
		jtfServerIP.setLocation(80, 40);

		JLabel jlPort = new JLabel("서버의 포트번호를 입력하세요");
		jlPort.setSize(200, 50);
		jlPort.setLocation(80, 60);

		jtfServerPort = new JTextField(20); // 포트 번호 입력 JTextField
		jtfServerPort.setSize(220, 25);
		jtfServerPort.setLocation(80, 100);

		JLabel jlName = new JLabel("닉네임을 입력하세요");
		jlName.setSize(200, 50);
		jlName.setLocation(80, 120);

		jtfName = new JTextField(20); // 닉네임 입력 JTextField
		jtfName.setSize(220, 25);
		jtfName.setLocation(80, 160);

		JButton jbSet = new JButton("시작"); // 확인 버튼
		jbSet.setSize(60, 30);
		jbSet.setLocation(100, 200);
		jbSet.addActionListener(this);

		JButton jbCancel = new JButton("취소"); // 취소 버튼
		jbCancel.setSize(60, 30);
		jbCancel.setLocation(170, 200);
		jbCancel.addActionListener(this);

		JLabel jlIcon = new JLabel();
		ImageIcon pikachuR = new ImageIcon("pikachu_R.png");
		Image img = pikachuR.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon pikachuR_setSize = new ImageIcon(img);
		jlIcon.setIcon(pikachuR_setSize);
		jlIcon.setSize(pikachuR_setSize.getIconWidth(), pikachuR_setSize.getIconHeight());
		jlIcon.setLocation(15, 20);

		ct.add(jlIcon);
		ct.add(jlServerIP);
		ct.add(jtfServerIP);
		ct.add(jlPort);
		ct.add(jtfServerPort);
		ct.add(jbSet);
		ct.add(jlName);
		ct.add(jtfName);
		ct.add(jbCancel);
		setVisible(true);
		setTitle("클라이언트 연결 설정");
		setSize(330, 270);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand() == "취소") {
			setVisible(false);
		}
		if (ae.getActionCommand() == "시작") {
			ip = jtfServerIP.getText();
			port = Integer.parseInt(jtfServerPort.getText());
			name = jtfName.getText();
			setVisible(false);
			introframe.setVisible(false);
			client = new Client(ip, port);
			GameFrame gf = new GameFrame(client);
			gf.createGameFrame();
			client.send(name);
			client.start();
		}
	}
}
