package org.sdr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.sdr.dao.UserDao;
import org.sdr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private DataSource dataSource;

	@Override
	public void insert(User user) {
		String sql = "INSERT INTO users (u_id, u_name, u_passwd) " +
				"VALUES (?, ?, ?)";
		String findMaxIdSql = "SELECT MAX(u_id) from users";

		Connection conn = null;
		long maxId = 0; //max user ID, 1 will be used for the first user

		try {
			conn = dataSource.getConnection();

			//Find the maximum user id currently in db
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(findMaxIdSql);
			if (rs.next()) {
				maxId = rs.getLong(1);
			}
			stmt.close();

			//Insert the new user, with an id based 
			//on the max one found above
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, maxId + 1);
			ps.setString(2, user.getName());
			ps.setString(3, user.getPassword());
			ps.executeUpdate();
			ps.close();		

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	@Override
	public User findByName(String name) {
		String sql = "SELECT u_name, u_passwd, u_score FROM users " +
				"where u_name = ?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			User user = null;
			if (rs.next()) {
				user = new User(rs.getString(1),
						rs.getString(2),
						rs.getLong(3));
			}

			rs.close();
			ps.close();
			return user;

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	public void updateScore(User user, int amount) {
		String sql = "UPDATE users " +
				"SET u_score = u_score + ? " +
				"WHERE u_name = ?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, amount);
			ps.setString(2, user.getName());
			ps.executeUpdate();
			ps.close();		
		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}
}
