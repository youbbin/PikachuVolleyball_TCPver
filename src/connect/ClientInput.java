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
		JLabel jlServerIP = new JLabel("������ IP�� �Է��ϼ���");
		jlServerIP.setSize(200, 50);
		jlServerIP.setLocation(80, 0);

		jtfServerIP = new JTextField(20); //���� IP �Է� JTextField
		jtfServerIP.setSize(220, 25);
		jtfServerIP.setLocation(80, 40);

		JLabel jlPort = new JLabel("������ ��Ʈ��ȣ�� �Է��ϼ���");
		jlPort.setSize(200, 50);
		jlPort.setLocation(80, 60);

		jtfServerPort = new JTextField(20); // ��Ʈ ��ȣ �Է� JTextField
		jtfServerPort.setSize(220, 25);
		jtfServerPort.setLocation(80, 100);

		JLabel jlName = new JLabel("�г����� �Է��ϼ���");
		jlName.setSize(200, 50);
		jlName.setLocation(80, 120);

		jtfName = new JTextField(20); // �г��� �Է� JTextField
		jtfName.setSize(220, 25);
		jtfName.setLocation(80, 160);

		JButton jbSet = new JButton("����"); // Ȯ�� ��ư
		jbSet.setSize(60, 30);
		jbSet.setLocation(100, 200);
		jbSet.addActionListener(this);

		JButton jbCancel = new JButton("���"); // ��� ��ư
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
		setTitle("Ŭ���̾�Ʈ ���� ����");
		setSize(330, 270);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand() == "���") {
			setVisible(false);
		}
		if (ae.getActionCommand() == "����") {
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
