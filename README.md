# DropOff

DropOff is a web application designed to streamline courier delivery services, enabling users to dispatch couriers from any location to any destination while tracking their delivery in real time. It stands as a dependable and effective solution for individuals seeking to send couriers, ensuring safe and timely arrivals.

## Getting Started

### Prerequisites

Before initiating the installation of the DropOff web app, ensure the availability of the following tools:

- Java Development Kit (JDK) version 11 or higher
- Apache Maven build tool version 3.6 or higher
- MySQL database server version 5.7 or higher

### Installation

To install the DropOff web app, adhere to these steps:

- Clone the DropOff app repository from GitHub

#### Back-end Installation

- Establish the MySQL database by developing a new database and a user with access privileges. Tools like phpMyAdmin or the MySQL command line tool are suitable for this purpose.

- Adapt the DropOff app to utilize the MySQL database by modifying the application.properties file located in the src/main/resources directory. Substitute the placeholders <database-name>, <username>, and <password> with your respective database name, username, and password:

- Construct the DropOff app using Maven by executing the following command in the root directory of the project:

        mvn clean package

This command will produce a JAR file in the target directory.

#### Front-end Installation

- Navigate to the project directory.

- Install Dependencies:

        npm install

- To operate the app locally, execute the following command:

        npm start

- The development server will commence, and the app can be accessed locally at http://localhost:3000.

- For production build, execute:

        npm run build

### Deployment

To deploy the DropOff web app on a server, perform the following:

- Ensure the server is equipped with JDK/JRE version 11 or higher.

- Transfer the JAR file, generated previously, to the web server. For Linux or MacOS, use the scp command, entering credentials when prompted:

        scp ./target/DropOff.jar username@host_url:path/

- Activate the DropOff app on the server using the ssh command:

        ssh username@host_url "java -jar ~/path/DropOff.jar"

- Verify the running application by navigating to: http://host_url:8080.

Note: Adjustments to your server's firewall may be required to allow incoming connections on port 8080.

## Features

- User Authentication
- Home Page / Dashboard / Filters
- Scheduling a DropOff with a user (Confirmed Delivery)
- Payment Module
- Inbox Chat (via chat-bot)
- Initiating and Concluding a delivery (with OTP)
- Feedback and Ratings
- Contact Admin for Raising issues.

### References

- [Online Image Hosting Service](https://postimages.org/)
- [Stack Overflow](https://stackoverflow.com/)
- [GeeksForGeeks](https://www.geeksforgeeks.org/)
- [CSS Tricks](https://css-tricks.com/snippets/css/a-guide-to-flexbox/)
- [ReactJS](https://blog.logrocket.com/why-react-doesnt-update-state-immediately/)
- [API Testing](https://www.guru99.com/postman-tutorial.html)
- [Testing](https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockmvc-example/)
- [SpringBoot](https://spring.io/guides/gs/spring-boot/)
