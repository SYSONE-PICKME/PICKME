package project.pickme.bid.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import project.pickme.bid.dto.reqeust.SelectedBidDto;

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

	public void sendSuccessfulBidMail(SelectedBidDto selectedBidDto, String toMail, long price) throws MessagingException {
		MimeMessage mailForm = createBidMailForm(selectedBidDto, toMail, price);
		javaMailSender.send(mailForm);
	}

	private MimeMessage createBidMailForm(SelectedBidDto selectedBidDto, String toMail, long price) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		message.addRecipients(MimeMessage.RecipientType.TO, toMail);
		message.setSubject("공매 낙찰 안내");
		message.setFrom(fromMail);
		message.setText(buildBidEmailContent(selectedBidDto, price),"utf-8", "html");

		return message;
	}

	private String buildBidEmailContent(SelectedBidDto selectedBidDto, long price){
		Context context = new Context();
		context.setVariable("itemName", selectedBidDto.getItemName());
		context.setVariable("imageUrl", selectedBidDto.getItemImage());
		context.setVariable("price", price);

		return templateEngine.process("/user/bidMail", context);
	}
}
