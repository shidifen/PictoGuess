package org.sdr.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sdr.dao.impl.UserDaoImpl;
import org.sdr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@Autowired
	private UserDaoImpl userDaoImpl;

	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public ModelAndView login() {

		return new ModelAndView("login", "user", new User(null,null,0));
	} 


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView validate(@ModelAttribute("user") User user, 
			Model model,
			RedirectAttributes redirAttr, 
			HttpServletResponse response,
			HttpSession session) {
		//here we check server-side only as a learning experience
		//on the other screens javascript is used for this
		if (user.getName().isEmpty() || user.getPassword().isEmpty()) {
			model.addAttribute("userPasswordEmpty", 
					"Your name or password are empty. This is not allowed. Please try again.");
			return new ModelAndView("login", "user", user);
		}
		
		//retrieve the user from database
		User dbUser = userDaoImpl.findByName(user.getName());

		if (dbUser == null) {
			redirAttr.addFlashAttribute("userInexistent", "Unfortunately, you do not have an account. Please register below:");

			return new ModelAndView("redirect:/register");		
		}

		//check if password is correct
		if (dbUser.getPassword().equals(user.getPassword())) {
			//for jsp
			redirAttr.addFlashAttribute("dname", dbUser.getName());
			session.setAttribute("user", dbUser);
			return new ModelAndView ("redirect:/home");
		}
		
		//something wrong by ths point
		model.addAttribute("userPasswordIncorrect", "The password is incorrect. " +
				"Please try again or register a new name.");

		return new ModelAndView ("login");

	}




}
