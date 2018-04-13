package org.sdr.controller;



import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sdr.dao.impl.UserDaoImpl;
import org.sdr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

	@Autowired
	private UserDaoImpl userDaoImpl;
	

	@RequestMapping(value = {"/register"}, method = RequestMethod.GET)
	public ModelAndView showForm() {
		
		return new ModelAndView("register", "user", new User());
	} 

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute("user") User user, 
			ModelMap model, 
			HttpServletResponse response, 
			HttpSession session,
			RedirectAttributes redirAttr) {

		if (user.getName().isEmpty() || user.getPassword().isEmpty()) {
			model.addAttribute("userPasswordEmpty", 
					"Your name or password is empty. This is not allowed. Please try again.");
			return "register";
		}

		if (userDaoImpl.findByName(user.getName()) != null) {
			model.addAttribute("userExists", "Unfortunately, your desired name already exists. Please pick another one.");
			return "register";
		}

		userDaoImpl.insert(user);

		model.addAttribute("user", user);
		redirAttr.addFlashAttribute("dname", user.getName() );
		session.setAttribute("user", user);
	
		return "redirect:/home";
	}
}
