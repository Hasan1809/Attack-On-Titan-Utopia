# Attack on Titan: Utopia

## 🏰 Overview

Welcome to the last bastion of humanity. Attack on Titan: Utopia is a turn-based tower defense game where you command the defense of humanity's walls against waves of incoming titans. As a member of the Military Police, your strategic decisions will determine humanity's survival.

## ⚔️ Features

- **Turn-Based Combat System**: Strategically place and upgrade defenses during your turn while titans advance in waves
- **Dynamic Titan System**: Face various titan types including Pure, Abnormal, Armored, and Colossal titans
- **Weapon Arsenal**: Deploy different defensive weapons including:
  - Piercing Cannons
  - Sniper Cannons
  - Volley Spread Cannons
  - Wall Traps
- **Data-Driven Design**: Titan and weapon configurations loaded from CSV files for easy modding
- **Immersive GUI**: Built with JavaFX featuring:
  - Animated battles
  - Strategic placement grid
  - Resource management interface
- **Original Soundtrack**: Atmospheric background music enhancing the gaming experience

## 🔧 Installation

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- JavaFX SDK
- JUnit 4 (for running tests)

### Setup

1. Clone the repository:

```bash
git clone https://github.com/yourusername/Attack-On-Titan-Utopoia.git
```

2. Navigate to the project directory:

```bash
cd Attack-On-Titan-Utopia
```

3. Ensure JavaFX is properly configured in your IDE

4. Run the game through the main class:

```bash
java game.gui.Main
```

## 📁 Project Structure

```
aot-utopia/
├── src/
│   ├── game/
│   │   ├── engine/       # Game logic and mechanics
│   │   └── gui/         # JavaFX UI components
├── images/              # Game assets and sprites
├── background_music/    # Game soundtrack files
└── data/               # CSV configuration files
    ├── titans.csv      # Titan specifications
    └── weapons.csv     # Weapon specifications
```

## 🎮 How to Play

1. **Start Game**: Launch the application through Main.java
2. **Setup Phase**:
   - Place defensive weapons along the wall
   - Manage your resources wisely
3. **Battle Phase**:
   - Titans approach in waves
   - Weapons automatically target approaching titans
   - Upgrade or repair defenses between waves
4. **Victory/Defeat**:
   - Win by surviving all waves
   - Lose if titans breach the wall

## 🙏 Acknowledgments

- Inspired by Attack on Titan created by Hajime Isayama
- Built with JavaFX
