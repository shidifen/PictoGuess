package org.sdr.controller;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.sdr.dao.impl.PictureDaoImpl;
import org.sdr.dao.impl.UserDaoImpl;
import org.sdr.model.Picture;
import org.sdr.model.User;
import org.sdr.storage.*;
import org.sdr.util.RandomStringUtil;

@Controller
public class UploadController {

    private final Storage storageService;
    
	@Value("${score.increase-amount-if-uploading}")
	int amountUpload;
    
	@Autowired
	private PictureDaoImpl pictureDaoImpl;
	
	@Autowired
	private UserDaoImpl userDaoImpl;

    @Autowired
    public UploadController(Storage storageService) {
        this.storageService = storageService;
    }
    
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String uploadForm(
			@ModelAttribute("pic") Picture pic, 
			ModelMap model, 
			HttpServletResponse response, 
			HttpSession session,
			RedirectAttributes redirAttr) {

		if (session == null) {
			return "home";
		}
		
		//determine who the user is
		User user = (User) session.getAttribute("user");
		
		model.addAttribute("amountUpload", amountUpload);
    	model.addAttribute("score", userDaoImpl.findByName(user.getName()).getScore());

		return "upload";
	}

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
    		@RequestParam("desc") String description,
    		Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
    	if (session == null) {
			return "home";
		}
    	
		//determine who the user is
		User user = (User) session.getAttribute("user");
		
    	//determine what kind of file it is
    	String origName = file.getOriginalFilename();
    	
    	//check if file has an extension and whether it is .jpg/.jpeg, 
    	//else ask the user to try again
    	//I had to choose between duplicating code after 'if' 
    	//and writing this monster
    	if ((origName.lastIndexOf(".") != -1) &&
    		!(origName.substring(origName.lastIndexOf(".")).toLowerCase().
    					equals(".jpg") ||
    		  origName.substring(origName.lastIndexOf(".")).toLowerCase().
    					equals(".jpeg"))) {
    	//then...
    		model.addAttribute("fileError", "Please upload a file with the " +
					"extension <em><i>.jpg/.jpeg</i></em>!");
        	model.addAttribute("score", userDaoImpl.findByName(user.getName()).getScore());
    		
    		return "upload";
    	}
    
    	//get a new name of the picture to be stored on the hard drive
    	//jpg and jpeg are identical as regards the file,
    	//we will stick to '.jpg'
    	String newName = RandomStringUtil.get() + ".jpg";
    	
    	//store it
    	try {
            storageService.store(file, newName);
    	}
    	catch (StorageException e) {
    		model.addAttribute("fileError", "We are sorry, a rare error occurred. " +
    						"Please try uploading the same file again");
    		return "/upload";
    	}
    	  	
    	//prepare to insert the picture information into the db
        pictureDaoImpl.insertUserPicture(user, new Picture(newName, description.toLowerCase().trim()));
    
        //increase user score
        userDaoImpl.updateScore(user, amountUpload);
        
        //reflect the new score in the result to the user. A new db connection required
    	model.addAttribute("score", userDaoImpl.findByName(user.getName()).getScore());
    	
        model.addAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!<br>" +
        		"Your score increased with the following amount: " + amountUpload + " points!");
        return "upload";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
