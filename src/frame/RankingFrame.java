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
		jp.setLocation(45,30);
		JLabel jl=new JLabel("RANKING",JLabel.CENTER);
		jl.setFont(new Font("Cooper Black", Font.BOLD, 50));
		
		Database database=new Database();
		try {
			jp.add(database.getRankingTable(),BorderLayout.CENTER);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jp.add(jl,BorderLayout.NORTH);
		
		ct.add(jp);
		setSize(400,600);
		setTitle("·©Å·");
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
}
