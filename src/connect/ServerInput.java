package connect;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import frame.*;

public class ServerInput extends JFrame implements ActionListener {
	IntroFrame introframe;
	JTextField jtfPort;
	JTextField jtfName;
	int port;
	public static String name;
	WaitingFrame waitingframe;

	public ServerInput(IntroFrame introframe) {
		this.introframe = introframe;
		Container ct = getContentPane();
		ct.setLayout(null);

		JLabel jlPort = new JLabel("포트번호를 입력하세요");
		jlPort.setSize(200, 50);
		jlPort.setLocation(80, 0);

		jtfPort = new JTextField(20); // 포트 번호 입력 JTextField
		jtfPort.setSize(220, 25);
		jtfPort.setLocation(80, 40);

		JLabel jlName = new JLabel("닉네임을 입력하세요");
		jlName.setSize(200, 50);
		jlName.setLocation(80, 60);

		jtfName = new JTextField(20); // 닉네임 입력 JTextField
		jtfName.setSize(220, 25);
		jtfName.setLocation(80, 100);

		JButton jbSet = new JButton("확인"); // 확인 버튼
		jbSet.setSize(60, 30);
		jbSet.setLocation(100, 140);
		jbSet.addActionListener(this);

		JButton jbCancel = new JButton("취소"); // 취소 버튼
		jbCancel.setSize(60, 30);
		jbCancel.setLocation(170, 140);
		jbCancel.addActionListener(this);

		JLabel jlIcon = new JLabel();
		ImageIcon pikachuR = new ImageIcon("pikachu_R.png");
		Image img = pikachuR.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon pikachuR_setSize = new ImageIcon(img);
		jlIcon.setIcon(pikachuR_setSize);
		jlIcon.setSize(pikachuR_setSize.getIconWidth(), pikachuR_setSize.getIconHeight());
		jlIcon.setLocation(15, 20);
		ct.add(jlIcon);
		ct.add(jlPort);
		ct.add(jtfPort);
		ct.add(jlName);
		ct.add(jtfName);
		ct.add(jbSet);
		ct.add(jbCancel);
		setVisible(true);
		setTitle("서버 연결 설정");
		setSize(330, 210);
		setResizable(false);
		setLocationRelativeTo(null);

	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand() == "취소") {
			setVisible(false);
		}
		if (ae.getActionCommand() == "확인") {
			port = Integer.parseInt(jtfPort.getText());
			name = jtfName.getText();
			setVisible(false);
			waitingframe = new WaitingFrame();
			Server s = new Server(port, waitingframe, introframe);
			s.start();
		}
	}
}

class WaitingFrame extends JFrame {
	Container ct;

	public WaitingFrame() {
		ct = getContentPane();
		ct.setLayout(null);
		JLabel jl = new JLabel("클라이언트 접속 대기중");
		jl.setSize(200, 50);
		jl.setLocation(80, 0);
		ct.add(jl);
		setSize(330, 100);
		setTitle("클라이언트 접속 대기");
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setAlwaysOnTop(true);
	}
}
