# Github Users App

<img src="https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png" alt="GitHub Logo" width="100">

The Github Users App is a simple Android application that allows users to search for GitHub users using the GitHub API. Users can input a username, and the application will display basic information about the user, such as full name, username, profile picture, number of followers, and number of following.

## Key Features

- **User Search:** Users can search for GitHub users based on their username.
- **User Profile Display:** The app displays detailed information about the user, including full name, username, profile picture, number of followers, and number of following.
- **Favorite Storage:** Users can add GitHub users to their favorites list for later access.
- **Dark Mode:** The app supports dark mode for a more comfortable user experience.

## Technologies Used

- **Kotlin/Java:** The application is developed using the Kotlin/Java programming language.
- **Room:** For local data storage, the application uses Room, a Jetpack library SQLite wrapper.
- **Picasso:** For loading and displaying user profile images from URLs.
- **Parcelize:** For easily sending data objects between application components (e.g., activity to fragment).
- **LiveData:** For observing changes in data directly from the local database and updating the user interface (UI) accordingly.
- **SharedPreferences and DataStore:** For storing user preferences, such as theme settings (light/dark mode).
- **DAO (Data Access Object):** For accessing and manipulating data in the local database.
- **ViewBinding:** For binding UI elements in XML layouts to Kotlin/Java source code.

## Author

This application is developed by muhfarian.

## License

Copyright © 2024 muhfarian.
