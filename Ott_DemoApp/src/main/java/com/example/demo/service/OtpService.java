package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class OtpService {
	@Autowired UserRepository userRepo;
	@Autowired JavaMailSender mailSender;
	@Autowired PasswordEncoder passwordEncoder;
	
	public String generateOneTimePassword(User user) throws UnsupportedEncodingException, MessagingException {
		String OTP = RandomString.make(8);
	    String encodedOTP = passwordEncoder.encode(OTP);
	     
	    user.setOneTimePassword(OTP);
	    user.setOtpRequestedTime(new Date());
	     
	    userRepo.save(user);
	     
	    sendOTPEmail(user, OTP);
     return (OTP);
	}
     
    public void sendOTPEmail(User user, String OTP) throws UnsupportedEncodingException, MessagingException {
    	MimeMessage message = mailSender.createMimeMessage();              
        MimeMessageHelper helper = new MimeMessageHelper(message);
         
        helper.setFrom("starnetspprt@gmail.com","Starnet+ Support");
        helper.setTo(user.getEmail());
         
        String subject = "Here's your One Time Password (OTP) - Expire in 5 minutes!";
         
        String content = "<p>Hello <b>" +  user.getUsername() + "</b></p>"
                + "<p>For security reason, you're required to use the following "
                + "One Time Password to login:</p>"
                + "<p><b>" + OTP + "</b></p>"
                + "<br>"
                + "<p>Note: this OTP is set to expire in 5 minutes.</p>"
                + "<br>"
                + "<p>Regards</p>"
                + "<img src = `https://res.cloudinary.com/zohoott/image/upload/v1653383302/logo/starnet__logo_ndtugj.png` width=100 height=75 />"
                ;
         
        helper.setSubject(subject);
         
        helper.setText(content, true);
         
        mailSender.send(message);       
    }
 
    public void clearOTP(User user) {
    	user.setOneTimePassword(null);
        user.setOtpRequestedTime(null);
        userRepo.save(user);
    } 
}
