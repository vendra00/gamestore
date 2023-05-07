Game Store
This is a sample project for a game store.

Table of Contents
Overview
Features
Getting Started
Usage
Contributing
License
Overview
This project is a simple game store that allows users to browse, search, and purchase games. The project is built using Java and Spring Boot, and uses a MySQL database to store game and platform information.

Features
Browse games by genre, platform, and release date
Search games by title
View detailed information about each game, including screenshots and reviews
Add games to a shopping cart and purchase them
Add new games and platforms to the store
Getting Started
To get started with this project, you will need to have Java, Maven, and MySQL installed on your machine. You can download Java from the Java website, Maven from the Maven website, and MySQL from the MySQL website.

Once you have these dependencies installed, you can clone the repository and build the project using Maven:

bash
Copy code
git clone https://github.com/vendra00/gamestore.git
cd gamestore
mvn package
You will also need to create a MySQL database and configure the application to use it. You can do this by creating a new database and user in MySQL, and then setting the following properties in the application.properties file:

bash
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/game_store
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
Usage
To run the application, you can use the following Maven command:

arduino
Copy code
mvn spring-boot:run
Once the application is running, you can access it in your web browser at http://localhost:8080. From there, you can browse, search, and purchase games.

Contributing
If you would like to contribute to this project, please fork the repository and submit a pull request.

License
This project is licensed under the MIT License - see the LICENSE file for details.