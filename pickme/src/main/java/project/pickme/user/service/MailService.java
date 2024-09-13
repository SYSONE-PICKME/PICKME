package project.pickme.user.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import project.pickme.bid.dto.response.SelectedBidDto;

import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class MailService {
	@Value("${spring.mail.username}")
	private String fromMail;

	private final JavaMailSender javaMailSender;
	private final SpringTemplateEngine templateEngine;

	public void sendSuccessfulBidMail(SelectedBidDto selectedBidDto, String toMail) throws MessagingException {
		MimeMessage mailForm = createBidMailForm(selectedBidDto, toMail);
		javaMailSender.send(mailForm);
	}

	private MimeMessage createBidMailForm(SelectedBidDto selectedBidDto, String toMail) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		message.addRecipients(MimeMessage.RecipientType.TO, toMail);
		message.setSubject("공매 낙찰 안내");
		message.setFrom(fromMail);
		message.setText(buildBidEmailContent(selectedBidDto),"utf-8", "html");

		return message;
	}

	private String buildBidEmailContent(SelectedBidDto selectedBidDto){
		Context context = new Context();
		context.setVariable("itemName", selectedBidDto.getItemName());
		context.setVariable("imageUrl", "https://pickmepickme.s3.ap-northeast-2.amazonaws.com/computer.jpg"); //TODO: selectedBidDto.getImageUrl로 수정하기
		context.setVariable("price", selectedBidDto.getPrice());

		return templateEngine.process("/user/bidMail", context);
	}
}
