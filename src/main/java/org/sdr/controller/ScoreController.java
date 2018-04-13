package org.sdr.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.sdr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//responsible for displaying a high score list
@Controller
public class ScoreController {

	@Autowired 
	private DataSource dataSource;

	@Value("${score.increase-amount-if-uploading}")
	int amountUpload;

	@Value("${score.increase-amount-if-guessing}")
	int amountGuess;

	@RequestMapping(value = "/scores", method = RequestMethod.GET)
	public String showScore(Model model, HttpSession session){
		
		if (session == null) {
			return "home";
		}
		
		User user = (User) session.getAttribute("user");
		
		model.addAttribute("amountUpload", amountUpload);
		model.addAttribute("amountGuess",  amountGuess);


		try ( 	Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(
						"SELECT u_name, u_score FROM users " +
						"ORDER BY u_score DESC");
				ResultSet rs = ps.executeQuery(); ) {

			List<User> users = new ArrayList<>();
			while (rs.next()) {

				if (!rs.getString(1).equals("admin")) {
					users.add(new User(rs.getString(1),
							null,
							rs.getLong(2)));
				}
				else {
					//if we are admin, we still want to see our score
					//in the scores list :)
					if(user.getName().equals("admin")) {
						users.add(new User(rs.getString(1),
								null,
								rs.getLong(2)));
					}
				}
			}

			model.addAttribute("users", users);
			model.addAttribute("ourUser", user);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return "scores";
	}
}