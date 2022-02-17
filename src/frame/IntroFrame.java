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
		ImageIcon iiTitle = new ImageIcon("title.png"); // 게임 타이틀
		Image imgTitle = iiTitle.getImage().getScaledInstance(500, 100, Image.SCALE_SMOOTH); // 사이즈 조정
		ImageIcon iiTitle_setSize = new ImageIcon(imgTitle);
		jlTitle = new JLabel();
		jlTitle.setIcon(iiTitle_setSize);
		jlTitle.setSize(500, 100);
		jlTitle.setLocation(250, 250); // 위치 설정

		ImageIcon iiServer = new ImageIcon("server.png"); // 서버로 하기 버튼
		Image imgServer = iiServer.getImage().getScaledInstance(250, 70, Image.SCALE_SMOOTH); // 사이즈 조정
		ImageIcon iiServer_setSize = new ImageIcon(imgServer);
		jbServer = new JButton();
		jbServer.setIcon(iiServer_setSize);
		jbServer.setBorderPainted(false); // 버튼 외곽선 없애기
		jbServer.setContentAreaFilled(false); // 버튼 채우기 안함
		jbServer.setSize(iiServer_setSize.getIconWidth(), iiServer_setSize.getIconHeight());
		jbServer.setLocation(380, 450); // 위치 설정
		jbServer.addActionListener(this); // 버튼에 액션 리스너 추가

		ImageIcon iiClient = new ImageIcon("client.png"); // 클라이언트로 하기 버튼
		Image imgClient = iiClient.getImage().getScaledInstance(300, 70, Image.SCALE_SMOOTH); // 사이즈 조정
		ImageIcon iiClient_setSize = new ImageIcon(imgClient);
		jbClient = new JButton();
		jbClient.setIcon(iiClient_setSize);
		jbClient.setBorderPainted(false); // 버튼 외곽선 없애기
		jbClient.setContentAreaFilled(false); // 버튼 채우기 안함
		jbClient.setSize(iiClient_setSize.getIconWidth(), iiClient_setSize.getIconHeight());
		jbClient.setLocation(360, 550); // 위치 설정
		jbClient.addActionListener(this); // 버튼에 액션 리스너 추가

		ImageIcon iiRanking = new ImageIcon("ranking.png"); // 랭킹 보기 버튼
		Image imgRanking = iiRanking.getImage().getScaledInstance(220, 60, Image.SCALE_SMOOTH);
		ImageIcon iiRanking_setSize = new ImageIcon(imgRanking);
		jbRanking = new JButton();
		jbRanking.setIcon(iiRanking_setSize);
		jbRanking.setBorderPainted(false); // 버튼 외곽선 없애기
		jbRanking.setContentAreaFilled(false); // 버튼 채우기 안함
		jbRanking.setSize(iiRanking_setSize.getIconWidth(), iiRanking_setSize.getIconHeight());
		jbRanking.setLocation(390, 650); // 위치 설정
		jbRanking.addActionListener(this); // 버튼에 액션 리스너 추가

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
