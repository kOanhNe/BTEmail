package murach.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class MailUtilGmail {

    public static void sendMail(String to, String from,
            String subject, String body, boolean bodyIsHTML)
            throws MessagingException {
        
        // 1 - get a mail session
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp"); // <-- Thay đổi thành "smtp"
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // <-- Thay đổi thành cổng "587"
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // <-- Thêm dòng này
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // Đọc thông tin từ Biến Môi Trường
                final String emailUser = System.getenv("GMAIL_USER");
                final String emailPassword = System.getenv("GMAIL_PASSWORD");
                return new PasswordAuthentication(emailUser, emailPassword);
            }
        });
        

        // 2 - create a message
        Message message = new MimeMessage(session);
        message.setSubject(subject);
        if (bodyIsHTML) {
            message.setContent(body, "text/html"); // Gửi email với định dạng text/html
        } else {
            message.setText(body);
        }

        // 3 - address the message
        Address fromAddress = new InternetAddress(from);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        // 4 - send the message
        Transport transport = session.getTransport();
        transport.connect("k.oanhlh@gmail.com", "bzmj rspq tvik hvvw");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
