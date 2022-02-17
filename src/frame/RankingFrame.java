package frame;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import database.*;

public class RankingFrame extends JFrame {
	
	public RankingFrame() {
		Container ct = getContentPane();
		ct.setLayout(null);
		ct.setBackground(Color.cyan);
		JPanel jp=new JPanel();
		jp.setOpaque(false);
		jp.setLayout(new BorderLayout());
		jp.setSize(300,500);
		jp.setLocation(48,70);
		JLabel jl=new JLabel("RANKING",JLabel.CENTER);
		jl.setFont(new Font("Cooper Black", Font.BOLD, 50));
		
		JLabel jlCrown=new JLabel(); //¿Õ°ü ·¹ÀÌºí
		ImageIcon iiCrown = new ImageIcon("crown.png");
		Image imgCrown = iiCrown.getImage().getScaledInstance(100, 70, Image.SCALE_SMOOTH);
		ImageIcon iiCrown_setSize = new ImageIcon(imgCrown);
		jlCrown.setIcon(iiCrown_setSize);
		jlCrown.setSize(iiCrown_setSize.getIconWidth(),iiCrown_setSize.getIconHeight());
		jlCrown.setLocation(150, 0);
		
		Database database=new Database();
		try {
			jp.add(database.getRankingTable(),BorderLayout.CENTER);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		jp.add(jl,BorderLayout.NORTH);
		ct.add(jlCrown);
		ct.add(jp);
		setSize(400,630);
		setTitle("·©Å·");
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
}
