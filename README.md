# Crafting the Way
This Java-implemented board game is inspired by the rules of a well-known board game, applying an object-oriented design based on the MVC architecture.

## Introduction
This project was created after learning how to design a program.
A team of three developed the project, drawing inspiration from the rules of a well-known board game.
The board game "Crafting the Way" was implemented in Java, applying an object-oriented design based on the MVC architecture.
Additionally, the components of the program are visualized through a Class Diagram to make the structure easier to understand.


## Game UI & Rules

![Game UI](https://github.com/highlees/crafting-the-way/blob/main/game-ui.jpg)
1. Players can place a wall at any desired location. However, walls must not completely block any piece, including their own. In other words, all players must always have a valid path to their goal.
2. Players can move their piece one space in any of the four directions: up, down, left, or right. Diagonal movement is generally not allowed. However, if an opponent's piece is in the chosen direction, the player may either jump over it or move diagonally.


## Development Process

### Planning
Although we could have proceeded with the project by improving the design or adding features to an existing Java-based board game, 
our team decided to start from scratch, believing that this approach would allow us to truly apply software development methodologies without relying on any base code.

### Implementation
![Class Diagram](https://github.com/highlees/crafting-the-way/blob/main/class-diagram.png)
The image above visualizes the program structure through a class diagram.  
The BGLogic checks whether the player’s movement is possible and whether a wall can be placed. If these actions are valid, the changes are reflected in the game UI through BGFrame.

### Testing & Debugging
Various cases were examined to ensure that the wall is only displayed on the game board when it is possible to place it. 
By using Depth-First Search (DFS), the validity of wall placement was checked, 
ensuring that there are no issues in most normal gameplay scenarios.  
Also, It was ensured that the buttons for pawn movement and wall placement are disabled when it is not the player's turn.


## Review

### Achievements
By developing the board game from scratch without any base code, 
I gained a clearer understanding of the MVC architecture and the concepts of Object-Oriented Programming (OOP). 

During the project, I had the opportunity to present and demo the game in front of other teams. 
After my presentation, a member of another team mentioned that she found it interesting and impressive. 
She also asked if she could share the source code. After obtaining permission from my teammates, I shared the source code with her.

### Challenges & Improvements
We spent the most time working on the algorithm that checks whether a wall can be placed. 
Although this wasn’t the part I was responsible for developing, 
I was involved in identifying edge cases and discussing and improving the solution. 
While the algorithm works correctly in most normal gameplay scenarios, there are still errors in a few specific cases.


## Additional Information
  
This project was completed during my first year of university. In order to release it, I recently modified the names of a few files and variables in the source code, but the code logic remains unchanged.  
Additionally, I would like to clarify that I obtained permission from my teammates to distribute the project.
