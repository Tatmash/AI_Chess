### What does the program do?
It's a single player game that allows users to play chess against the machine.
### How to run the code?

You could "checkout" the project with your preferred IDEA or alternatively:

1. Download this project onto your PC
2. Install java JDK onto your PC from here:
https://docs.oracle.com/javase/10/install/toc.htm
3. Open a command prompt window and navigate to the directory where you saved the java program.
4. Compile the code using the javac command plus java file name such as: 
"javac Main.java" 
5. Once the project has been compiled, type 
"java Main" 
to run the program.
6. Have fun :)

### The engine.
The engine consists of a data-structure that represents the game and a decision making mechanism.
Decision making happens by searching a decision tree using the MiniMax and Alpha-Beta Pruning algorithms.
### The GUI.
The GUI was built in Java Swing which is a lightweight GUI toolkit that has a wide variety of widgets for building optimized window based applications.
This is how the GUI looks at this stage.:   
  
  
![gui gif](images/chess.gif)

### Further development:
There is plenty of room for improvement. A few of the ideas I have in mind:
* Building a test suite before moving on and continue adding features in a Test Driven Developement approach.
* Adding a two players feature: human vs human.
* Displaying the "taken pieces" on a new, separate section of the window.
* Implementing other search algorithms/ decision making mechanisms and adding a machine vs machine feature.
* Implementing features that help analyse the game, such as: recording all moves.
* Implementing a "give me a hint" feature when the machine suggests a move to the user if needed.

If you would like to contribute to this project you are very welcome to do so. Please open an issue so that we can discuss your idea.
