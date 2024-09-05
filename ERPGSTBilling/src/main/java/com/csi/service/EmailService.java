package com.csi.service;

import com.csi.entity.GSTBill;
import com.csi.model.EmailModel;
import com.csi.repository.GSTBillRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {


    @Autowired
    private GSTBillRepository gstBillRepository;


    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendEmail(String toEmailId) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();


        GSTBill gstBill = gstBillRepository.findByCustEmailId(toEmailId);


        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(fromMail);
            mimeMessageHelper.setTo(toEmailId);
            mimeMessageHelper.setSubject("GST Bill Invoice");
            mimeMessageHelper.setText("\n GST Bill Invoice ID: " + gstBill.getInvoiceId() + "\n Customer Name: " + gstBill.getCustName() + "\n " + gstBill.getCustAddress() + "\n " + gstBill.getInvoiceDate() + "\n " + gstBill.getInvoiceAmount());


            javaMailSender.send(mimeMessage);

            log.info("Mail Sent Successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
