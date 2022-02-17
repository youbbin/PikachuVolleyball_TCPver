package database;

import java.sql.*;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Database {
	Connection conn;
	String id;
	String pw;
	String url;
	Statement stmt;

	public Database() {
		id = "root";
		pw = "5478";
		url = "jdbc:mysql://210.102.142.15:3306/mydatabase?serverTimezone=UTC";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, pw);
			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQL ���� �ȵ�");
		}
	}

	public boolean checkPlayer(String name) throws SQLException { // �÷��̾ ���̺� ��ϵǾ��ִ��� Ȯ��
		String chk = "SELECT EXISTS (SELECT player_name FROM player where player_name = '" + name + "') as chk ";
		ResultSet rs = stmt.executeQuery(chk);
		boolean check = false;
		while (rs.next()) {
			if (rs.getBoolean("chk")) {
				check = true;
			} else
				check = false;
		}
		return check;
	}

	public void updateWin(String name) throws SQLException { // �̱� Ƚ�� ������Ʈ
		if (!checkPlayer(name)) { // �÷��̾ ���̺� ��ϵǾ����� ������ ���� ����Ѵ�
			String insert = "INSERT INTO player (player_name,win,lose) VALUES ('" + name + "',0,0)";
			stmt.executeUpdate(insert);
		}
		String search = "SELECT win FROM player WHERE player_name='" + name + "'";
		ResultSet rs = stmt.executeQuery(search);

		int num = 0;
		while (rs.next()) {
			num = rs.getInt("win"); // �̱� Ƚ�� ��������
		}
		num++; // �̱� Ƚ�� 1 ����
		String update = "UPDATE player SET win = " + num + " WHERE player_name = '" + name + "'";
		stmt.executeUpdate(update); // ������Ʈ
	}

	public void updateLose(String name) throws SQLException { // �� Ƚ�� ������Ʈ
		if (!checkPlayer(name)) { // �÷��̾ ���̺� ��ϵǾ����� ������ ���� ����Ѵ�
			String insert = "INSERT INTO player (player_name,win,lose) VALUES ('" + name + "',0,0)";
			stmt.executeUpdate(insert);
		}
		String search = "SELECT lose FROM player WHERE player_name='" + name + "'";
		ResultSet rs = stmt.executeQuery(search);

		int num = 0;
		while (rs.next()) {
			num = rs.getInt("lose"); // �� Ƚ�� ��������
		}
		num++;
		String update = "UPDATE player SET lose= " + num + " WHERE player_name = '" + name + "'";
		stmt.executeUpdate(update); // ������Ʈ
	}

	public int getWin(String name) throws SQLException {
		String search = "SELECT win FROM player WHERE player_name='" + name + "'";
		ResultSet rs = stmt.executeQuery(search);
		int num = 0;
		while (rs.next()) {
			num = rs.getInt("win"); // �̱� Ƚ�� ��������
		}
		return num;
	}

	public int getLose(String name) throws SQLException {
		String search = "SELECT lose FROM player WHERE player_name='" + name + "'";
		ResultSet rs = stmt.executeQuery(search);
		int num = 0;
		while (rs.next()) {
			num = rs.getInt("lose"); // �̱� Ƚ�� ��������
		}
		return num;
	}

	public int getRanking(String name) throws SQLException {
		String search = "select ranking "
				+ "from (select *, rank() over (order by win DESC, lose ASC) as ranking from player) c "
				+ "where player_name='" + name + "'";
		ResultSet rs = stmt.executeQuery(search);
		int ranking = 0;
		while (rs.next()) {
			ranking = rs.getInt("ranking");
		}
		return ranking;
	}

	public JScrollPane getRankingTable() throws SQLException {
		String[] header = { "����", "�̸�", "��� Ƚ��" };
		DefaultTableModel model = new DefaultTableModel(header, 0);
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);

		String search = "select *, rank() over (order by win DESC, lose ASC) as ranking from player";
		ResultSet rs = stmt.executeQuery(search);
		int ranking = 0;
		String name = "";
		int win = 0;
		while (rs.next()) {
			ranking = rs.getInt("ranking");
			name = rs.getString("player_name");
			win = rs.getInt("win");
			Object data[] = { ranking, name, win };
			model.addRow(data);
		}
		return scrollPane;
	}

}
