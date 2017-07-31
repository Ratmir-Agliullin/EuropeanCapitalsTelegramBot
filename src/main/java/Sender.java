import org.telegram.telegrambots.api.objects.Message;



import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static javax.mail.internet.MimeMessage.*;

public class Sender {


    public void sendEMail(String mess, String subj, String toEmail) {
        Properties props = new Properties();


        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.host", "smtp.gmail.com");
        props.put("mail.defaultEncoding", "UTF-8");
        props.put("mail.mime.charset", "UTF-8");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("agliullin.ratmir@gmail.com", "Kbctyrjgbljh48");
                    }
                });

        try {

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress("agliullin.ratmir@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subj, "UTF-8");
            message.setText(mess, "UTF-8");

            Transport.send(message);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}

