# GIAI ĐOẠN 1: Build ứng dụng bằng Maven
# Sử dụng một image có sẵn Maven và Java 11
FROM maven:3.8-openjdk-11 AS build

# Đặt thư mục làm việc bên trong image
WORKDIR /app

# Copy file pom.xml trước để tận dụng Docker cache
# Nếu file pom.xml không đổi, Docker sẽ không cần tải lại thư viện
COPY pom.xml .

# Tải các thư viện cần thiết
RUN mvn dependency:go-offline

# Copy toàn bộ mã nguồn còn lại
COPY src ./src

# Build ứng dụng, bỏ qua việc chạy test để build nhanh hơn
RUN mvn package -DskipTests

# ---

# GIAI ĐOẠN 2: Tạo image để chạy ứng dụng
# Sử dụng một image Tomcat 9 chính thức chạy trên Java 11
FROM tomcat:9.0-jdk11-temurin

# Xóa các ứng dụng web mặc định của Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file .war đã được build từ giai đoạn 1 vào thư mục webapps của Tomcat
# Đổi tên thành ROOT.war để ứng dụng chạy ở đường dẫn gốc (vd: your-app.onrender.com/)
COPY --from=build /app/target/EmailListApp.war /usr/local/tomcat/webapps/ROOT.war

# Cổng 8080 được Tomcat sử dụng mặc định, Render sẽ tự động nhận diện
EXPOSE 8080

# Lệnh mặc định của image Tomcat sẽ là `catalina.sh run`, không cần ghi đè
