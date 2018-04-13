package org.sdr.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.sdr.dao.PictureDao;
import org.sdr.model.Picture;
import org.sdr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class PictureDaoImpl implements PictureDao {

	@Autowired
	private DataSource dataSource;
	
	@Override
	public void insertUserPicture (User user, Picture pic) {
		
		Connection conn = null;
		long maxPicId = 0; 	//max pic ID, 1 will be used for the first picture
		long userId = 0; 	//forced to init to 0, evn though no pic can be inserted 
							//w/o an existing user id
		try {
			conn = dataSource.getConnection();
			
			//Find the maximum pic id currently in table 'pictures'
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT MAX(p_id) from pictures");
	        if (rs.next()) {
	    	    maxPicId = rs.getLong(1);
	        }
	        stmt.close();
			
	        //Insert the new pic in 'pictures' with an id based 
	        //on the max one found above 
	        PreparedStatement ps = conn.prepareStatement(
	        			"INSERT INTO pictures (p_id, p_desc, p_name) " +
	   					"VALUES (?, ?, ?)");
	        ps.setLong(1, maxPicId + 1);
	        ps.setString(2, pic.getDescription());
	        ps.setString(3, pic.getName());
	        ps.executeUpdate();
	        ps.clearParameters();
	        
	        //find the user id who uploaded the pic
	        ps = conn.prepareStatement(
	        		"SELECT u_id FROM users " +
	        		"WHERE u_name = ?");
	        ps.setString(1, user.getName());
	        rs = ps.executeQuery();
	        if (rs.next()) {
	    	    userId = rs.getLong(1);
	        }
	        rs.close();
	        ps.clearParameters();
	  
	        //insert the user id and pic id into 'user_uploaded_pics'
	        ps = conn.prepareStatement(
	        		"INSERT INTO user_uploaded_pics(u_id, p_id) " +
	        		"VALUES (?, ?)");
	        ps.setLong(1, userId);
	        ps.setLong(2, maxPicId + 1);
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

	@Override
	public Picture findById(long id) {
		Connection conn = null;
		try {
		   conn = dataSource.getConnection();
	       PreparedStatement ps = conn.prepareStatement(
	    		"SELECT p_desc, p_name FROM pictures WHERE" +
	    		"p_id = ?");
	       
	       ps.setLong(1, id);
	       ResultSet rs = ps.executeQuery();
	       Picture pic = new Picture();

	       if (rs.next()) {
	    	   pic.setDescription(rs.getString(1));
	    	   pic.setDescription(rs.getString(2));
	       }
		
		   rs.close();
		   ps.close();
		   
		   return pic;
		
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

