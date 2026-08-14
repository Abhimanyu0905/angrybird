# 🚀 Angry Birds (Java Edition)

A 2D physics-based arcade game inspired by **Angry Birds**, implemented in **Java** using Object-Oriented Programming (OOP) principles and GUI rendering.

---

## 📌 Features

* **Physics & Motion**: Real-time trajectory modeling, projectile motion, launch velocity, and collision detection.
* **Object-Oriented Design**: Clean hierarchy using Inheritance, Polymorphism, and Design Patterns (e.g., Factory/Singleton for game states and entity creation).
* **Game Mechanics**:
  * Slingshot launch mechanism with adjustable drag distance and angles.
  * Multiple bird types with distinct behaviors and abilities.
  * Destructible structures (wood, ice, stone) with varying hit points/durability.
  * Enemy pigs with health and score tracking.
* **UI & Audio**: Complete game loop with Main Menu, Level Selection, Pause overlay, Win/Loss conditions, and sound effects.

---

## 🛠️ Tech Stack & Prerequisites

* **Language**: Java 17+ (or JDK 11+)
* **Graphics/GUI**: JavaFX / LibGDX / Swing
* **Build Tool**: Maven / Gradle
* **IDE**: IntelliJ IDEA / Eclipse / VS Code

---

## 📂 Project Structure

```text
angrybird/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/angrybirds/
│   │   │       ├── main/          # Main application & Game Loop entry point
│   │   │       ├── entities/      # Birds, Pigs, Obstacles, Slingshot
│   │   │       ├── physics/       # Collision detection & trajectory math
│   │   │       ├── scenes/        # Menu, Level, Pause, Game Over screens
│   │   │       └── utils/         # Asset loaders and sound managers
│   │   └── resources/
│   │       ├── assets/            # Sprites, textures, and background images
│   │       ├── audio/             # Sound effects and background music
│   │       └── fxml/              # JavaFX layout files (if applicable)
├── pom.xml / build.gradle         # Build & Dependency configurations
└── README.md
⚙️ Building & Running the Game
Using Maven
Clone the repository:
Bashgit clone [https://github.com/Abhimanyu0905/AngryBirds.git](https://github.com/Abhimanyu0905/AngryBirds.git)
cd AngryBirds
Build the project:
Bashmvn clean compile
Run the application:
Bashmvn exec:java -Dexec.mainClass="com.angrybirds.main.Main"
# Or if using JavaFX:
mvn javafx:run
Using Gradle
Bash./gradlew run
🤝 Contributing
Contributions, issues, and feature requests are welcome! Feel free to check out the issues page or submit a Pull Request.

Fork the Project

Create your Feature Branch (git checkout -b feature/NewFeature)

Commit your Changes (git commit -m 'Add NewFeature')

Push to the Branch (git push origin feature/NewFeature)

Open a Pull Request

📜 License
Distributed under the MIT License. See LICENSE for more information.
