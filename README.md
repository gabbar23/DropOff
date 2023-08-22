# DropOff

DropOff is a web app for package delivery services that allows users to send packages from anywhere to anywhere and track their delivery in real-time. DropOff is a reliable and efficient solution for anyone who needs to send a package and wants to ensure that it arrives safely and on time.

## Getting Started

### Prerequisites
Before installing the DropOff web app, ensure that you have the following tools installed:

- Java Development Kit (JDK) version 11 or higher
- Apache Maven build tool version 3.6 or higher
- MySQL database server version 5.7 or higher

### Installation

To install the DropOff web app, follow these steps:

- Clone the DropOff app repository from GitHub

#### Back-end Installation

- Set up the MySQL database by creating a new database and a user with privileges to access it. You can use a tool like phpMyAdmin or the MySQL command line tool to do this.

- Configure the DropOff app to use the MySQL database by editing the application.properties file located in the src/main/resources directory. Replace the placeholders <database-name>, <username>, and <password> with your database name, username, and password respectively:


- Build the DropOff app using Maven by running the following command in your terminal or command prompt from the root directory of the project:

        mvn clean package

This will generate a JAR file in the target directory.

#### Front-end Installation

- Navigate to the project folder.

- Install Dependencies:

        npm install

- To run the app locally, use the following command:

        npm start

- This will start the development server, and you can view your app locally on your browser at http://localhost:3000.

- To build the app for production, use the following command:

        npm run build

### Deployment

To deploy the DropOff web app on a server, follow these steps:

- Ensure that the server has JDK/JRE version 11 or higher installed.

- Transfer the JAR file generated in the previous step to the web server. If you are using Linux or MacOS, you can transfer the file to the server using the scp command. After entering the command, it will ask for credentials for the user you are using to connect to the web server. Here, csci5308vm9 is the username and csci5308vm9.research.cs.dal.ca is the host_url, and backend/ is the path where we want to transfer the file:

        scp ./target/DropOff.jar csci5308vm9@csci5308vm9.research.cs.dal.ca:backend/

- Run the DropOff app on the server using the ssh command. Here, csci5308vm9 is the username, csci5308vm9.research.cs.dal.ca is the host_url, and java -jar ~/backend/DropOff.jar is the command to run the application on the server:

- After running these commands successfully, you can check the running application by going to: http://csci5308vm9.research.cs.dal.ca:8080. Here, replace csci5308vm9.research.cs.dal.ca with your server's URL.

Note: You may need to configure your server's firewall to allow incoming connections to port 8080 if necessary.

## Features

- Driver Verification while registration

- Search Dashboard (List view and Map View)

- Booking a ride with a user (Accepted Ride)

- Negotiation for a price (using chat-bot)

- Starting a ride (with otp)

- Ending a ride (with otp)

- Paying for a ride and getting a receipt

- Canceling the ride and Refund

- Tracking the ongoing Ride

- Giving Feedback to the driver

- Raising an issue once the ride is completed

### References

- [Online image hosting service](https://postimages.org/)
- [Stack Overflow](https://stackoverflow.com/)
- [GeeksForGeeks](https://www.geeksforgeeks.org/)
- [CSS and Styles](https://css-tricks.com/snippets/css/a-guide-to-flexbox/)
- [ReactJS](https://blog.logrocket.com/why-react-doesnt-update-state-immediately/)
- [API Testing](https://www.guru99.com/postman-tutorial.html)
- [Testing](https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockmvc-example/)
- [SpringBoot](https://spring.io/guides/gs/spring-boot/)
