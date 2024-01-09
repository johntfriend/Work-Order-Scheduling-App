package sru.edu.group1.workorders.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;
import sru.edu.group1.workorders.domain.Users;
import sru.edu.group1.workorders.repository.UserRepository;
import sru.edu.group1.workorders.services.UserServices;
import sru.edu.group1.workorders.services.UtilityServices;

/**
 *  This is the controller which will be called whenever a user forgets their password on the login page
 * @author Trevor Bell
 * @author Ellie Wurst
 * @author John Friend
 * @author Hunter Minteer
 */

@Controller
public class ForgotPasswordController {
	
	@Autowired
	private UserServices userService;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	/**
	 * Directs to the forgot password page
	 * @param model passes the 
	 * @return the forgot password page
	 */
	@GetMapping("/forgot_password")
    public String showForgotPasswordForm(Model model) {
	 model.addAttribute("pageTitle", "Forgot Password");
	 return "forgotPassword";
    }
	
	/**
	 * processes the forgot password request 
	 * @param request holds information for the email to send properly, for HTTP purposes
	 * @param model passes the message in the html
	 * @return sends a email and displays a message
	 */
	@PostMapping("/forgot_password")
	public String processForgotPasswordForm(HttpServletRequest request, Model model) {
		//gets inputed email and create token with random characters
		String email = request.getParameter("email");
		String token = RandomString.make(45);
		
		userService.updateResetPasswordToken(token, email);
		
		String resetPasswordLink = UtilityServices.getSiteURL(request) + "/reset_password?token=" + token;
		//try and catch statement if there was a problem sending the email
		try {
			sendEmail(email, resetPasswordLink);
			
			model.addAttribute("message", "We have sent you an email, please check");
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Error while sending email.");
		}
		model.addAttribute("pageTitle", "Forgot Password");
		return "forgotPassword";
	}
	
	/**
	 * send email controller which sends out the actual support ticket through email
	 * @param email is the email of the user who is reseting their password
	 * @param resetPasswordLink a link that the user can use to reset their password
	 * @throws UnsupportedEncodingException holds errors for encoding methods
	 * @throws MessagingException holds errors for basic messaging methods
	 */
	private void sendEmail(String email, String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
		//creates a mimemessage object
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		//tag for the email
		helper.setFrom("contact@SRUHelp.com", "SRU Support");
		helper.setTo(email);
		//code to that sets what the email says
		String subject = "Here's the link to reset your password";
		
		String content = "<p>Hello,</p>"
				+ "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>"
				+ "<p><b><a href=\"" + resetPasswordLink + "\">Change my password</a><b></p>"
				+ "<p>Ignore this email if you do remember you password, or have not made the request.</p>";
		
		helper.setSubject(subject);
		helper.setText(content,true);
		
		mailSender.send(message);
	}
	
	/**
	 * Getmapping for the reset password page
	 * @param token holds a generated token specific to that user
	 * @param model passes text and the token into the html
	 * @return a error message or the reset password page
	 */
	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
		//creates and instance of user with token
		Users user = userService.get(token);
		//if statement to catch it token is null mean has no token so not anyone can access
		if (user == null) {
			model.addAttribute("title", "Reset Your Password");
			model.addAttribute("message", "Invalid Token");
			return "message";
		}
		model.addAttribute("token", token);
		model.addAttribute("pageTitle", "Reset Your Password");
		
		return "resetPassword";
	}
	
	/**
	 * post mapping for reset password
	 * @param request holds information for the email to send properly, for HTTP purposes
	 * @param model passes the message into the html
	 * @return a error message or the login page
	 */
	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");
		
		Users user = userService.get(token);
		
		if (user == null) {
			model.addAttribute("title", "Reset Your Password");
			model.addAttribute("message", "Invalid Token");
			return "invalidToken";
		} else {
		userService.updatePassword(user, password);
		model.addAttribute("message", "you have successfully changed your password.");
		return "login";
		}
	}
	
	/**
	 * displays the change password page
	 * @param username Holds the name of the person logged in to display in the corner
	 * @param model passes the page title and username into the html
	 * @return displays the change password page
	 */
	@GetMapping("/group/changePassword")
	public String changePass(@CurrentSecurityContext(expression="authentication?.name") String username, Model model){
		model.addAttribute("pageTitle", "Change Password");
		model.addAttribute("username", username);
		return "changePassword";
	}
	
	/**
	 * Checks the entered password and compares it to the old one
	 * @param username Holds the name of the person logged in to display in the corner
	 * @param request holds information for the email to send properly, for HTTP purposes
	 * @param model passes the error message to the html
	 * @return to the login page or and error message
	 */
	@PostMapping("/group/changePassword")
	public String processChangePass(@CurrentSecurityContext(expression="authentication?.name") String username, HttpServletRequest request, Model model) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String oldPass = request.getParameter("oldPassword");
		String newPass = request.getParameter("password");
		
		Users user = userRepository.findByEmail(username);
		
		if (encoder.matches(oldPass, user.getPassword())) {
			userService.updatePassword(user, newPass);
			return "redirect:/";
		}
		else {
			model.addAttribute("message", "you have entered the wrong old Password");
			return "message";
		}
	}
}
