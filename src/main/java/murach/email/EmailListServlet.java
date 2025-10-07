package murach.email;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import murach.business.User;
import murach.data.UserDB;
import murach.util.MailUtilGmail;

public class EmailListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        // Luôn đặt encoding ở đầu để xử lý tiếng Việt
        request.setCharacterEncoding("UTF-8");

        // Lấy action từ request
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";  // action mặc định
        }

        // Thực hiện action và gán URL tới trang thích hợp
        String url = "/index.jsp";        
        if (action.equals("join")) {
            url = "/index.jsp";    // Trang "join"
        } 
        else if (action.equals("add")) {
            // Lấy các tham số từ request
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            // Lưu dữ liệu vào đối tượng User
            User user = new User(firstName, lastName, email);
            UserDB.insert(user);
            request.setAttribute("user", user);
            
            // --- BẮT ĐẦU PHẦN GỬI EMAIL ---
            
            // Khai báo các biến cần thiết cho email
            String to = email;
            String from = "your-email@gmail.com"; // Email người gửi, phải khớp với cấu hình
            String subject = "Welcome to our email list";
            
            // 1. Lấy thông tin server để tạo URL động cho ảnh
            String scheme = request.getScheme();             // http
            String serverName = request.getServerName();     // localhost
            int serverPort = request.getServerPort();        // 8080
            String contextPath = request.getContextPath();   // /EmailListApp (hoặc tên project của bạn)

            // 2. Tạo URL đầy đủ đến file ảnh
            String imageUrl = scheme + "://" + serverName + ":" + serverPort + contextPath + "/images/thanks.jpg";

            // 3. Đặt cờ isBodyHTML thành true
            boolean isBodyHTML = true;

            // 4. Xây dựng nội dung email bằng HTML, sử dụng URL động
            String body = "<!DOCTYPE html><html><head><meta charset='UTF-8'></head>" +
                          "<body>" +
                          "<h1>Thank you for joining!</h1>" +
                          "<p>Dear <b>" + firstName + "</b>,</p>" +
                          "<p>Thanks for joining our email list. We'll make sure to send " +
                          "you announcements about new products and promotions.</p>" +
                          "<p>Here is a special thank you image from us:</p>" +
                          "<img src='" + imageUrl + "' alt='Thank You Image' width='400'>" + 
                          "<p>Have a great day!</p>" +
                          "</body></html>";
            
            // Gửi email và xử lý lỗi
            try {
                MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
            }
            catch (MessagingException e) {
                String errorMessage = 
                    "ERROR: Unable to send email. " + 
                        "Check Tomcat logs for details.<br>" +
                    "NOTE: You may need to configure your system " + 
                        "as described in chapter 14.<br>" +
                    "ERROR MESSAGE: " + e.getMessage();
                request.setAttribute("errorMessage", errorMessage);
                this.log(
                    "Unable to send email. \n" +
                    "Here is the email you tried to send: \n" +
                    "=====================================\n" +
                    "TO: " + email + "\n" +
                    "FROM: " + from + "\n" +
                    "SUBJECT: " + subject + "\n" +
                    "\n" +
                    body + "\n\n");
            }            
            url = "/thanks.jsp";
        }
        
        // Chuyển hướng đến trang JSP tương ứng
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }    
}