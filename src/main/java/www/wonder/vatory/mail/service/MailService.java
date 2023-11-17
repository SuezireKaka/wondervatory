package www.wonder.vatory.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	@Autowired
	JavaMailSender mailSender;
		
	@Value("${spring.mail.username}")
	String sendFrom;
		
	public boolean sendMailToNaver() {
		String sendTo = "rwccm3241@naver.com";
		String mailTitle = "Oyastte!";
		String mailContent = "Tesseghrraσhin~?";
			
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
				
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true,"UTF-8");

				message.setTo(sendTo);
				message.setFrom(sendFrom);	//env.getProperty("spring.mail.username")
				message.setSubject(mailTitle);
				message.setText(mailContent, true); //ture : html 형식 사용
			}
		};
			
		try{
			mailSender.send(preparator);
		} catch (MailException e){
			return false;
		}
		return true;
	}
}
