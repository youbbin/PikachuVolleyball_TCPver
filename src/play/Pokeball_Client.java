package play;

import javax.swing.*;
import java.awt.*;

public class Pokeball_Client extends JLabel { // �������� �� ��ġ�� �޾Ƽ� ���� ����
	public Pokeball_Client() {
		ImageIcon iiBall = new ImageIcon("pokeball.png");
		Image imgBall = iiBall.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		ImageIcon iiBall_setSize = new ImageIcon(imgBall);
		setIcon(iiBall_setSize);
		setSize(iiBall_setSize.getIconWidth(),iiBall_setSize.getIconHeight());
	}

	public void setPokeball(int x,int y) {
		setLocation(x,y);
		updateUI();
	}
}
