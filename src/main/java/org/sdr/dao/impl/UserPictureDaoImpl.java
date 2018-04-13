package org.sdr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.sql.DataSource;

import org.sdr.model.Picture;
import org.sdr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserPictureDaoImpl {
	@Autowired
	private DataSource dataSource;

	public void addPictureGuessed(User user, Picture picture) {

		Connection conn = null;
		long userId = 0; 	
		long picId = 0;

		try {
			conn = dataSource.getConnection();

			//find the user id who uploaded the pic
			PreparedStatement ps = conn.prepareStatement(
					"SELECT u_id FROM users " +
					"WHERE u_name = ?");
			ps.setString(1, user.getName());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				userId = rs.getLong(1);
			}
			rs.close();
			ps.clearParameters();

			//find the picture id 
			ps = conn.prepareStatement(
					"SELECT p_id FROM pictures " +
					"WHERE p_name = ?");
			ps.setString(1, picture.getName());
			rs = ps.executeQuery();
			if (rs.next()) {
				picId = rs.getLong(1);
			}
			rs.close();
			ps.clearParameters();

			//insert the user id and pic id into 'user_uploaded_pics'
			ps = conn.prepareStatement(
					"INSERT INTO user_seen_pics(u_id, p_id) " +
					"VALUES (?, ?)");
			ps.setLong(1, userId);
			ps.setLong(2,  picId);
			ps.executeUpdate();

			//we are done!
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

	public Picture getRandomPicForUser(User user) {
		//this will be returned to the user to guess
		Picture newRandomPic = new Picture();

		//this will hold all the pic ids that should NOT be displayed
		//to the user (already seen or self-uploaded)
		Set<Long> picsSeenOrUploadedByUserSet = new HashSet<>();

		Connection conn = null;
		long maxPicId = 1; 	//equiv to total number of pics in db; at least one by default
		long picsSeenOrUploadedNr = 0; //the number of pics already seen/uploaded by the user

		//if the user has run out of pictures to see, then
		//maxPicID <= picsSeenOrUploaded
		try {
			conn = dataSource.getConnection();

			//find the maximum pic id currently in table 'pictures'
			//this is the same as the total number of pictures (id starts at 1)
			PreparedStatement ps = conn.prepareStatement(
					"SELECT MAX(p_id) from pictures");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				maxPicId = rs.getLong(1);
			}

			rs.close();
			ps.clearParameters();

			//find the sum of uploaded pics and seen pics 
			//this will tell us further how many pictures haven't been seen yet
			ps = conn.prepareStatement(
					"SELECT " + 			
						"(SELECT COUNT(*) FROM user_uploaded_pics " +
						"WHERE u_id = ( " +
							"SELECT u_id " +
							"FROM users " +
							"WHERE u_name = ?)) " +
					" + " +
						"(SELECT COUNT(*) FROM user_seen_pics " +
						"WHERE u_id = ( " +
							"SELECT u_id " + 
							"FROM users " + 
							"WHERE u_name = ?))");						

			ps.setString(1, user.getName());
			ps.setString(2, user.getName());

			rs = ps.executeQuery();
			if(rs.next()) {
				picsSeenOrUploadedNr = rs.getLong(1);

				ps.clearParameters();

				//if true, game over, no more pics to see
				if(maxPicId <= picsSeenOrUploadedNr) {
					rs.close();
					ps.close();
					//will be checked in the calling function
					return null;
				}

				//find all the specific pics uploaded or seen by the user
				//along with their number
				ps = conn.prepareStatement(

						"SELECT p_id FROM user_uploaded_pics " +
						"WHERE u_id = ( " +
							"SELECT u_id " +
								"FROM users " +
								"WHERE u_name = ?) " +
						"UNION " +
						"SELECT p_id FROM user_seen_pics " +
						"WHERE u_id = ( " +
								"SELECT u_id " + 
								"FROM users " + 
								"WHERE u_name = ?)");

				ps.setString(1, user.getName());
				ps.setString(2, user.getName());

				rs = ps.executeQuery();
				ps.clearParameters();

				//add the pictures (their ids) discovered above to the exclusion set       
				while (rs.next()) {
					picsSeenOrUploadedByUserSet.add(rs.getLong(1));
				}

				//find a random id which is not in the set
				long randomPicId;

				do {
					//select a random pic id to be searched (and compared with
					randomPicId = ThreadLocalRandom.current().nextLong(1, maxPicId + 1);
				}
				while (picsSeenOrUploadedByUserSet.contains(randomPicId));

				//finally, retrieve the never-seen-before pic id from the db
				ps = conn.prepareStatement(
						"SELECT p_name, p_desc " +
								"FROM pictures " + 
						"WHERE p_id = ?");
				ps.setLong(1, randomPicId);
				rs = ps.executeQuery();

				//and populate the pic with data
				if (rs.next()){
					newRandomPic.setName(rs.getString(1));
					newRandomPic.setDescription(rs.getString(2));
					rs.close();
					ps.close();

					return newRandomPic;
				}				
			}
			rs.close();
			ps.close();
			throw new SQLException("Db error");

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

	public long getNumberUnseenPicturesForUser(User user) {

		Connection conn = null;
		long maxPicId = 1; 	//equiv to total number of pics in db; at least one by default
		long picsSeenOrUploadedNr = 0; //the number of pics already seen/uploaded by the user


		try {
			conn = dataSource.getConnection();

			//find the maximum pic id currently in table 'pictures'
			//this is the same as the total number of pictures (id starts at 1)
			PreparedStatement ps = conn.prepareStatement(
					"SELECT MAX(p_id) from pictures");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				maxPicId = rs.getLong(1);
			}

			rs.close();
			ps.clearParameters();

			//find the sum of uploaded pics and seen pics 
			//this will tell us further how many pictures haven't been seen yet
			ps = conn.prepareStatement(
					"SELECT " + 			
							"(SELECT COUNT(*) FROM user_uploaded_pics " +
							"WHERE u_id = ( " +
							"SELECT u_id " +
							"FROM users " +
							"WHERE u_name = ?)) " +
							" + " +
							"(SELECT COUNT(*) FROM user_seen_pics " +
							"WHERE u_id = ( " +
							"SELECT u_id " + 
							"FROM users " + 
					"WHERE u_name = ?))");						

			ps.setString(1, user.getName());
			ps.setString(2, user.getName());

			rs = ps.executeQuery();
			if(rs.next()) {
				picsSeenOrUploadedNr = rs.getLong(1);
				ps.clearParameters();

				return maxPicId - picsSeenOrUploadedNr;
			}
			rs.close();
			ps.close();
			return maxPicId - picsSeenOrUploadedNr;

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


	public long  getNumberGuessedPicturesForUser(User user) {

		Connection conn = null;
		long numberGuessed = 0;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					//inner join not mandatory here
					"SELECT COUNT(*) FROM user_seen_pics up " +
							"INNER JOIN users u ON " +
							"up.u_id = u.u_id " +
							"WHERE up.u_id = (" +
							"SELECT u_id " +
							"FROM users " +
					"WHERE u_name = ?)"); 

			ps.setString(1, user.getName());

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				numberGuessed  = rs.getLong(1);
			}
			rs.close();
			ps.close();
			return numberGuessed;

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
