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
		jp = new JPanel(); // ��� ������Ʈ���� �߰��� JPanel
		jp.setSize(1000, 800);
		jp.setLayout(null);
		jp.setBackground(Color.cyan);

		jlScore_p1 = new JLabel("0", JLabel.CENTER); // �÷��̾�1�� ���� ���̺�
		jlScore_p1.setFont(new Font("Cooper Black", Font.BOLD, 70));
		jlScore_p1.setForeground(Color.red);
		jlScore_p1.setSize(100, 100);
		jlScore_p1.setLocation(50, 0);
		jp.add(jlScore_p1);

		jlScore_p2 = new JLabel("0", JLabel.CENTER); // �÷��̾�2�� ���� ���̺�
		jlScore_p2.setFont(new Font("Cooper Black", Font.BOLD, 70));
		jlScore_p2.setForeground(Color.red);
		jlScore_p2.setSize(100, 100);
		jlScore_p2.setLocation(jp.getWidth() - 150, 0);
		jp.add(jlScore_p2);

		createGround(); // �ٴ� ����
		createNet(); // ��Ʈ ����
		createPlayer(); // �÷��̾� ����
		createOpponent(); //���� ����

		GameStartLabel gs = new GameStartLabel(this); // ���� ���̺� ������ ����
		jp.add(gs);
		new Thread((Runnable) gs).start(); // ���� ���̺� ������ ����

		ct.add(jp); // �����̳ʿ� jp �߰�
		setFocusable(true);
		addKeyListener(this); // Ű������ �߰�
		setTitle("Pikachu Volleyball");
		setSize(1000, 810);
		setResizable(false); // â ������ ���� ���ϰ� ����
		setLocationRelativeTo(null); // â�� ȭ���� ����� ���
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void createGround() { // �ٴ� ����
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

	public void createNet() { // ��Ʈ ����
		ImageIcon iiNet = new ImageIcon("net.png");
		Image imgNet = iiNet.getImage().getScaledInstance(50, 300, Image.SCALE_SMOOTH);
		ImageIcon iiNet_setSize = new ImageIcon(imgNet);
		jlNet = new JLabel();
		jlNet.setIcon(iiNet_setSize);
		jlNet.setSize(iiNet_setSize.getIconWidth(), iiNet_setSize.getIconHeight());
		jlNet.setLocation(450, jp.getHeight() - jlNet.getHeight() - groundHeight);
		jp.add(jlNet);
	}

	public void createPlayer() { // �÷��̾� ����
		if (IntroFrame.isServer) { // �����̸� ���� ������ ����
			ps = new Player_Server(server, jp, jlNet);
			ps.setPlayer(50, jp.getHeight() - ps.getHeight() - groundHeight);
		} else { // Ŭ���̾�Ʈ�̸� ������ ������ ����
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
			// ����Ű�� �¿� �̵�, ����
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
