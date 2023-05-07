# Game Store

Welcome to Game Store, a web application where you can browse and buy your favorite video games.

## Getting Started

To run the Game Store application, you'll need to do the following:

1. Clone the repository: `git clone https://github.com/vendra00/gamestore.git`
2. Navigate to the project root directory: `cd gamestore`
3. Install the dependencies: `mvn clean install`
4. Start the application: `mvn spring-boot:run`

The application will be running at `http://localhost:8080`.

## Usage

Once the application is running, you can access the following endpoints:

- `GET /games`: retrieves a list of all the games in the database.
- `GET /games/{id}`: retrieves a specific game by its ID.
- `POST /games`: adds a new game to the database.
- `PUT /games/{id}`: updates an existing game by its ID.
- `DELETE /games/{id}`: deletes a game by its ID.
- `GET /games/platform/{platformName}`: retrieves a list of all games for a specific platform.
- `GET /games/genre/{genre}`: retrieves a list of all games for a specific genre.
- `GET /games/year/{year}`: retrieves a list of all games released in a specific year.

All endpoints return data in JSON format.

## Contributing

We welcome contributions to the Game Store project! If you'd like to contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch: `git checkout -b my-feature-branch`.
3. Make your changes and commit them: `git commit -am 'Add some feature'`.
4. Push your changes to your fork: `git push origin my-feature-branch`.
5. Submit a pull request to the Game Store repository.

## License

The Game Store project is licensed under the MIT license. See the `LICENSE` file for more details.
