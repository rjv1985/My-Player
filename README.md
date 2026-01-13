# Nothing Player 🚗🎵

Nothing Player is a unique, retro-styled multimedia player built using **Java 21**, **JavaFX**, and **Swing**. It features a "car player" aesthetic, providing a nostalgic yet functional experience for playing audio and video files. The UI is enhanced with **FlatLaf** to maintain a classic feel with modern performance and high-DPI support.

## ✨ Features

- **Multimedia Playback**: Support for various audio and video formats via JavaFX Media.
- **Retro Car Themes**: Integrated "car themes" that change the visual aesthetic of the player.
- **Hybrid UI**: Combines the robustness of Java Swing with the modern multimedia capabilities of JavaFX.
- **Modern Look & Feel**: Uses [FlatLaf](https://www.formdev.com/flatlaf/) for a sleek, consistent cross-platform appearance.
- **Visual Effects**: Built-in support for animated GIFs and custom icons for a dynamic user experience.

## 🛠️ Tech Stack

- **Language**: Java 21
- **Multimedia Framework**: JavaFX 21
- **UI Framework**: Swing (with FlatLaf 3.4.1)
- **Build Tool**: Maven

## 📋 Prerequisites

Before you begin, ensure you have the following installed:
- **Java Development Kit (JDK) 21** or higher.
- **Apache Maven** (for building and dependency management).

## 🚀 Installation & Setup

1. **Navigate to the Project Directory**
   ```bash
   cd Nothing
   ```

2. **Build the Project**
   Use Maven to download dependencies and compile the project:
   ```bash
   mvn clean install
   ```

3. **Run the Application**
   You can run the player directly using the JavaFX Maven plugin:
   ```bash
   mvn javafx:run
   ```
   
   Alternatively, run the generated "fat JAR" from the `target` directory:
   ```bash
   java -jar target/nothing-player-2.0-SNAPSHOT.jar
   ```

## 📁 Project Structure

- `src/main/java/Nothing`: Core application logic and UI components.
- `Images/`: Graphical assets, icons, and car themes.
- `SampleMedia/`: Included media files for testing.
- `pom.xml`: Project configuration and dependency list.

## 📦 Dependencies

The project relies on the following key libraries (managed via Maven):
- `javafx-controls`: Standard JavaFX UI controls.
- `javafx-media`: Multimedia playback support.
- `javafx-swing`: Integration between Swing and JavaFX.
- `flatlaf`: Modern Look and Feel for Swing components.

## 🤝 Contributing

Contributions are welcome! Feel free to open issues or submit pull requests to improve the player.

---
*Created by Rajeev as part of the Nothing Player project.*
