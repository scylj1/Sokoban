## Author

Lekang JIANG (scylj1), 20124840, Test on Windows Eclipse, Maven build, Java14, Javafx15

## How to run

In Eclipse, right click "pom.xml", run configuration, check name and base directory is right, use "javafx:run" in Goals and "Run"

## Javadocs

Javadoc is in javadocs folder in root directory

## New Features

-- Add a start screen, allow user to choose the color for wall, and click a "start" button to enter the game

-- Add background music

-- Use different sprites in the game, and the keeper can turn around when different keys are pressed

-- Add a information layout to show map name, level name, current moves and total moves and instructions constantly on the right side of the map

-- Add permanent high score list, and pop up first 3 highest score player and current player's score when a level is complete

-- Player can enter names to record the score when game is complete or click "save score"

-- Design some interesting levels in the resource folder "Mygame.skb", and very easy levels in "TEST.skb" for test the function of the program

-- Add save game function in the menu, it can save current game to another file, and when player load this saved game, it will go to that state with same position and moves. 

-- Add save score function in the menu, player can enter names to save the score

-- Add undo function in the menu, back to last step of the game

-- Add reset level function in the menu, back to the start of current level

-- Add toggle music function in the menu, control the playing of music

-- Add Junit test 

## Refactors
### Packages

Organize the project to 6 packages, to make it more understandable. The package "gameStart" contains the Main class of the program to start with, "gameView" controls how the application is look like, "gameController" contains how the player can interact with the game, "gameSettings" initialise the settings for the game, "gameRole" initialise resources such as sprite pictures, "gameProcessor" contains movement behavior and map loader

### Classes

-- Split class "Main" to classes. "Main" is the start of the game, "GameView" is the view of the game, "GameController" is the controller of the game, "Mapload" can read map file to the game model, "Dialog" can pop up messages, "Debug" is check if debug function is turn on, "GameComplete" is to check if the game is complete.

-- Split class "StartMeUp" to small classes. "GameModel" is the model of the game, "Movement" defines move functions, "Music" has function of playing background music

-- "Level", "GameGrid", GameObject", "GraphicObject" are modified with new features

-- Add class "StartController" to define interations in the start screen

-- Add class "Sprite" to initialise different character pictures of the game

-- Add class "WallColor" to set the wall color from the choice of player

-- Add class "GameRecord" to record scores of current player

### Design patterns

-- Use MVC pattern in the whole project

-- Use Singleton design pattern in WallColor class Debug class and GameComplete class, because only one instance is needed for the game

-- Use Iterator pattern in Level class, because it is easier to look through the level objects

-- Use Memento pattern in Movement class, because the past moves should be recorded to "undo" and "reset level"