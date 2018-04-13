package org.sdr.controller;

import javax.servlet.http.HttpSession;

import org.sdr.dao.impl.UserDaoImpl;
import org.sdr.dao.impl.UserPictureDaoImpl;
import org.sdr.model.Picture;
import org.sdr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
/*import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
 */import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestMethod;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.servlet.mvc.support.RedirectAttributes;

 @Controller
 public class GameController {

	 @Autowired
	 private UserDaoImpl userDaoImpl;

	 @Autowired
	 private UserPictureDaoImpl userPictureDaoImpl;

	 @Value("${score.increase-amount-if-guessing}")
	 int amountGuess;

	 @Value("${score.increase-amount-if-uploading}")
	 int amountUpload;

	 @Value("${storage.location}")
	 String picFolder;

	 @Value("${gameplay.number-of-tries}")
	 int nrOfTriesAllowed;

	 @RequestMapping(value = "/play", method = RequestMethod.GET)
	 public String play(Model model,
			 HttpSession session,
			 RedirectAttributes redirAttr) {

		 if (session == null) {
			 return "home";
		 }

		 //determine who the user is
		 User user = (User) session.getAttribute("user");
		

		 //check if the session contains a previous picture
		 //the user is currently guessing
		 Picture pic = null;
		 if (session.getAttribute("pic") != null) {
			 pic = (Picture) session.getAttribute("pic");
		 }

		 //what picture to present the user:
		 //the same one or a new random one? A user has 3 tries.

		 Integer nrOfFailedTries = (Integer) session.getAttribute("nrOfFailedTries");

		 if (nrOfFailedTries == null) {
			 //get a new pic, the user has freshly arrived on the page
			 //(or has guessed correctly previously
			 pic = userPictureDaoImpl.getRandomPicForUser(user);
			 //no more pics to show if null
			 if (pic == null) {
				 model.addAttribute("outOfPics", "<b>Congrats! There are no more pictures " +
						 "for you to guess. This is such a rare event!</b>");

				 model.addAttribute("score", 
						 	userDaoImpl.findByName(user.getName()).getScore());

				 model.addAttribute("amountUpload", amountUpload);

				 session.removeAttribute("nrOfFailedTries");
				 session.removeAttribute("pic");

				 model.addAttribute("dname", user.getName());

				 return "playerHome";
			 }
			 //our new picture is added to the session
			 session.setAttribute("pic", pic);
		 }
		 else if ((int) nrOfFailedTries >= nrOfTriesAllowed ) {
			 //clean & prepare session for new picture
			 session.removeAttribute("nrOfFailedTries");
			 session.removeAttribute("pic");
			 pic = userPictureDaoImpl.getRandomPicForUser(user) ;
			 if (pic == null) {
				 model.addAttribute("outOfPics", "Congrats! There are no more pictures " +
						 "for you to guess. This is such a rare event!");
				 model.addAttribute("score", 
						 	userDaoImpl.findByName(user.getName()).getScore());
				 model.addAttribute("dname", user.getName());
				 return "playerHome";
			 }
			 
			 session.setAttribute("pic", pic);
		 }

		 //guaranteed to have a non-null pic
		 //used for the pic name in jsp
		 model.addAttribute("pic", pic);

		 //pic directory as per application.properties
		 model.addAttribute("picFolder", picFolder);

		 //display the correct number of tries left
		 model.addAttribute("nrOfTries", 
				 (nrOfFailedTries != null 
				 ? (((int) nrOfFailedTries >= nrOfTriesAllowed)
						 ? nrOfTriesAllowed 
						 : (nrOfTriesAllowed - (int) nrOfFailedTries))
				 : nrOfTriesAllowed));

		 model.addAttribute("score",        userDaoImpl.findByName(user.getName()).getScore());
		 model.addAttribute("nrGuessed",    userPictureDaoImpl.getNumberGuessedPicturesForUser(user));
		 model.addAttribute("nrNotGuessed", userPictureDaoImpl.getNumberUnseenPicturesForUser(user));

		 return "play";
	 }


	 @RequestMapping(value = "/play", method = RequestMethod.POST)
	 public String submitAnswer( 
			 Model model,
			 @RequestParam("desc") String desc,
			 RedirectAttributes redirAttr,
			 HttpSession session) {

		 if (session == null) {
			 return "home";
		 }
		 
		 //determine who the user is
		 User user = (User) session.getAttribute("user");
		 
		 //the picture the user was trying to guess
		 Picture pic = (Picture) session.getAttribute("pic");

		 //check if user is still allowed to guess this pic
		 //or has run out of chances
		 Integer nrOfFailedTries = (Integer) session.getAttribute("nrOfFailedTries");

		 //check if description entered by user
		 //equals the description from the db
		 if (!desc.toLowerCase().trim().equals(pic.getDescription())) {
			 //is this the first erroneous guess?
			 if (nrOfFailedTries != null) {
				 //this will overwrite the previous attribute
				 session.setAttribute("nrOfFailedTries", nrOfFailedTries + 1);
			 }
			 else session.setAttribute("nrOfFailedTries", 1);

			 return "redirect:/play";
		 }

		 //having reached here, the user guessed correctly

		 //make the user-pic  association to the db
		 userPictureDaoImpl.addPictureGuessed(user, pic);

		 //increase user score
		 userDaoImpl.updateScore(user, amountGuess);

		 //clear the session of previous errors
		 session.removeAttribute("nrOfFailedTries");
		 
		 //clear the pic from the session
		 //as we want a new random pic to take its place
		 session.removeAttribute("pic");

		 return "redirect:/play";
	 }
	 
	 @RequestMapping(value= {"/play"}, params = {"skip"})
	 public  String skip(HttpSession session) {
		 //remove from the session the current pic
		 //and the value associated (nrOfFailedTries)
		 session.removeAttribute("pic");
		 session.removeAttribute("nrOfFailedTries");
		 return "redirect:/play";
	 }
 }
