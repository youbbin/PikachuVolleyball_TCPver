package frame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import play.GameStartLabel;
import play.*;
import connect.Client;
import connect.ClientInput;
import connect.Server;

public class GameFrame extends JFrame implements KeyListener {
	Container ct;
	public JLabel jlScore_p1;
	public JLabel jlScore_p2;
	public JPanel jp;
	public static boolean gameStart = false;
	public static int groundHeight;
	public JLabel jlNet;
	public JLabel jlWaiting;
	Server server;
	Client client;
	Player_Server ps;
	Player_Client pc;
	Opponent op;
	public GameFrame(Server server) {
		ct = getContentPane();
		this.server = server;
	}

	public GameFrame(Client client) {
		ct = getContentPane();
		this.client = client;
	}

	public void createGameFrame() {
		setLayout(null);
		jp = new JPanel(); // 모든 컴포넌트들이 추가될 JPanel
		jp.setSize(1000, 800);
		jp.setLayout(null);
		jp.setBackground(Color.cyan);

		jlScore_p1 = new JLabel("0", JLabel.CENTER); // 플레이어1의 점수 레이블
		jlScore_p1.setFont(new Font("Cooper Black", Font.BOLD, 70));
		jlScore_p1.setForeground(Color.red);
		jlScore_p1.setSize(100, 100);
		jlScore_p1.setLocation(50, 0);
		jp.add(jlScore_p1);

		jlScore_p2 = new JLabel("0", JLabel.CENTER); // 플레이어2의 점수 레이블
		jlScore_p2.setFont(new Font("Cooper Black", Font.BOLD, 70));
		jlScore_p2.setForeground(Color.red);
		jlScore_p2.setSize(100, 100);
		jlScore_p2.setLocation(jp.getWidth() - 150, 0);
		jp.add(jlScore_p2);

		createGround(); // 바닥 생성
		createNet(); // 네트 생성
		createPlayer(); // 플레이어 생성
		createOpponent(); //상대방 생성

		GameStartLabel gs = new GameStartLabel(this); // 시작 레이블 스레드 생성
		jp.add(gs);
		new Thread((Runnable) gs).start(); // 시작 레이블 스레드 시작

		ct.add(jp); // 컨테이너에 jp 추가
		setFocusable(true);
		addKeyListener(this); // 키리스너 추가
		setTitle("Pikachu Volleyball");
		setSize(1000, 810);
		setResizable(false); // 창 사이즈 조절 못하게 설정
		setLocationRelativeTo(null); // 창을 화면의 가운데에 띄움
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void createGround() { // 바닥 생성
		ImageIcon iiGround = new ImageIcon("ground.png");
		Image imgGround = iiGround.getImage().getScaledInstance(jp.getWidth(), 50, Image.SCALE_SMOOTH);
		ImageIcon iiGround_setSize = new ImageIcon(imgGround);
		JLabel jlGround = new JLabel();
		jlGround.setIcon(iiGround_setSize);
		jlGround.setSize(iiGround_setSize.getIconWidth(), iiGround_setSize.getIconHeight());
		jlGround.setLocation(0, jp.getHeight() - jlGround.getHeight() - 10);
		groundHeight = jlGround.getHeight();
		jp.add(jlGround);
	}

	public void createNet() { // 네트 생성
		ImageIcon iiNet = new ImageIcon("net.png");
		Image imgNet = iiNet.getImage().getScaledInstance(50, 300, Image.SCALE_SMOOTH);
		ImageIcon iiNet_setSize = new ImageIcon(imgNet);
		jlNet = new JLabel();
		jlNet.setIcon(iiNet_setSize);
		jlNet.setSize(iiNet_setSize.getIconWidth(), iiNet_setSize.getIconHeight());
		jlNet.setLocation(450, jp.getHeight() - jlNet.getHeight() - groundHeight);
		jp.add(jlNet);
	}

	public void createPlayer() { // 플레이어 생성
		if (IntroFrame.isServer) { // 서버이면 왼쪽 진영에 생성
			ps = new Player_Server(server, jp, jlNet);
			ps.setPlayer(50, jp.getHeight() - ps.getHeight() - groundHeight);
		} else { // 클라이언트이면 오른쪽 진영에 생성
			pc = new Player_Client(client, jp, jlNet);
			pc.setPlayer(jp.getWidth() - 200, jp.getHeight() - pc.getHeight() - groundHeight);
		}
	}
	
	public void createOpponent() {
		if(IntroFrame.isServer) {
			op=server.op;
			op.setOpponent(jp.getWidth() - 200, jp.getHeight() - ps.getHeight() - groundHeight);
			jp.add(op);
		}
		else {
			op=client.op;
			op.setOpponent(50, jp.getHeight() - pc.getHeight() - groundHeight);
			jp.add(op);
		}
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (gameStart) {
			// 방향키로 좌우 이동, 점프
			if (IntroFrame.isServer) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					ps.moveLeft();
				if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					ps.moveRight();
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					ps.jump();
				}
			}
			else {
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					pc.moveLeft();
				if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					pc.moveRight();
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					pc.jump();
				}
			}

		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
