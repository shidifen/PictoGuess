package org.sdr.controller;

import javax.servlet.http.HttpSession;

import org.sdr.dao.impl.UserDaoImpl;
import org.sdr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PlayerHomeController {
	
	@Autowired
	UserDaoImpl userDaoImpl;
	
	@Value("${score.increase-amount-if-uploading}")
	int amountUpload;

	@Value("${score.increase-amount-if-guessing}")
	int amountGuess;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(@ModelAttribute("user") User user, 
    		Model model,
    		HttpSession session) {
		
		if (session == null) {
			return "home";
		}
		User usr = (User) session.getAttribute("user");
		
		model.addAttribute("dname", usr.getName());
		model.addAttribute("score", userDaoImpl.findByName(usr.getName()).getScore());
		model.addAttribute("amountUpload", amountUpload);
		model.addAttribute("amountGuess",  amountGuess);

		
		return "playerHome";
	}
}
