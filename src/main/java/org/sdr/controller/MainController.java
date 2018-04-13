package org.sdr.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	@Autowired 
	private DataSource dataSource;
			
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage(Model model) {

		//a one-off connection to the db, hence I don't provide
		//much neater DAO's here

		String SQL = "SELECT " +
				"(SELECT count(*) FROM pictures), " +
				"(SELECT count(*) FROM users)";
		long picNr = 0;
		long userNr = 0;

		try (Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) {
			if (rs.next()){
				picNr  = (long) rs.getObject(1);
				userNr = (long) rs.getObject(2);
			};

			model.addAttribute("picNr", picNr);
			model.addAttribute("userNr", userNr);

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		return "home";
	}
}
