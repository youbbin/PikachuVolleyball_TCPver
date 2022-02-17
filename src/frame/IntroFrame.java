package frame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import connect.*;


public class IntroFrame extends JFrame implements ActionListener {
	Container ct;
	JLabel jlTitle;
	public JButton jbServer;
	public JButton jbClient;
	public JButton jbRanking;
	public static boolean isServer = false;

	public IntroFrame() {
		ct = getContentPane();
		ct.setLayout(null);
		ct.setBackground(Color.GREEN);
		ImageIcon iiTitle = new ImageIcon("title.png"); // ���� Ÿ��Ʋ
		Image imgTitle = iiTitle.getImage().getScaledInstance(500, 100, Image.SCALE_SMOOTH); // ������ ����
		ImageIcon iiTitle_setSize = new ImageIcon(imgTitle);
		jlTitle = new JLabel();
		jlTitle.setIcon(iiTitle_setSize);
		jlTitle.setSize(500, 100);
		jlTitle.setLocation(250, 250); // ��ġ ����

		ImageIcon iiServer = new ImageIcon("server.png"); // ������ �ϱ� ��ư
		Image imgServer = iiServer.getImage().getScaledInstance(250, 70, Image.SCALE_SMOOTH); // ������ ����
		ImageIcon iiServer_setSize = new ImageIcon(imgServer);
		jbServer = new JButton();
		jbServer.setIcon(iiServer_setSize);
		jbServer.setBorderPainted(false); // ��ư �ܰ��� ���ֱ�
		jbServer.setContentAreaFilled(false); // ��ư ä��� ����
		jbServer.setSize(iiServer_setSize.getIconWidth(), iiServer_setSize.getIconHeight());
		jbServer.setLocation(380, 450); // ��ġ ����
		jbServer.addActionListener(this); // ��ư�� �׼� ������ �߰�

		ImageIcon iiClient = new ImageIcon("client.png"); // Ŭ���̾�Ʈ�� �ϱ� ��ư
		Image imgClient = iiClient.getImage().getScaledInstance(300, 70, Image.SCALE_SMOOTH); // ������ ����
		ImageIcon iiClient_setSize = new ImageIcon(imgClient);
		jbClient = new JButton();
		jbClient.setIcon(iiClient_setSize);
		jbClient.setBorderPainted(false); // ��ư �ܰ��� ���ֱ�
		jbClient.setContentAreaFilled(false); // ��ư ä��� ����
		jbClient.setSize(iiClient_setSize.getIconWidth(), iiClient_setSize.getIconHeight());
		jbClient.setLocation(360, 550); // ��ġ ����
		jbClient.addActionListener(this); // ��ư�� �׼� ������ �߰�

		ImageIcon iiRanking = new ImageIcon("ranking.png"); // ��ŷ ���� ��ư
		Image imgRanking = iiRanking.getImage().getScaledInstance(220, 60, Image.SCALE_SMOOTH);
		ImageIcon iiRanking_setSize = new ImageIcon(imgRanking);
		jbRanking = new JButton();
		jbRanking.setIcon(iiRanking_setSize);
		jbRanking.setBorderPainted(false); // ��ư �ܰ��� ���ֱ�
		jbRanking.setContentAreaFilled(false); // ��ư ä��� ����
		jbRanking.setSize(iiRanking_setSize.getIconWidth(), iiRanking_setSize.getIconHeight());
		jbRanking.setLocation(390, 650); // ��ġ ����
		jbRanking.addActionListener(this); // ��ư�� �׼� ������ �߰�

		ct.add(jlTitle);
		ct.add(jbServer);
		ct.add(jbClient);
		ct.add(jbRanking);
		setTitle("Pikachu Volleyball Intro");
		setSize(1000, 800);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		if (obj == jbServer) {
			isServer = true;
			ServerInput si = new ServerInput(this);
		}
		if (obj == jbClient) {
			isServer = false;
			ClientInput ci = new ClientInput(this);
		}
		if (obj == jbRanking) {
			RankingFrame rf = new RankingFrame();
		}
	}
}
