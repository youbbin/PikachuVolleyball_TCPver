package play;

import connect.*;
import frame.*;
import java.awt.*;
import javax.swing.*;

public class Opponent extends JLabel {
	static ImageIcon pikachuR = new ImageIcon("pikachu_R.png");
	static Image img = pikachuR.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
	public static ImageIcon pikachuR_setSize = new ImageIcon(img);
	static ImageIcon pikachuL = new ImageIcon("pikachu_L.png");
	static Image imgL = pikachuL.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
	public static ImageIcon pikachuL_setSize = new ImageIcon(imgL);
	int yPos;
	Server server;
	Client client;
	public int xSize;

	public Opponent(Server server) { // ������ ���� : Ŭ���̾�Ʈ
		this.server = server;
		setIcon(pikachuL_setSize);
		xSize = pikachuL_setSize.getIconWidth();
		int ySize = pikachuL_setSize.getIconHeight();
		setSize(xSize, ySize);
	}

	public Opponent(Client client) { // Ŭ���̾�Ʈ�� ���� : ����
		this.client = client;
		setIcon(pikachuR_setSize);
		int xSize = pikachuR_setSize.getIconWidth();
		int ySize = pikachuR_setSize.getIconHeight();
		setSize(xSize, ySize);
	}

	public void setOpponent(int xPos, int yPos) {
		this.yPos = yPos;
		setLocation(xPos, yPos);
		updateUI();
	}
}
