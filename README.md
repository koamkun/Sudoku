### Sudoku Game Project Report

#### **Project Overview**
The Sudoku Game is a Java-based implementation of the classic Sudoku puzzle. The program allows users to play Sudoku by generating puzzles of varying difficulty levels (easy, medium, hard), accepting user input to fill in the grid, and providing features like undo and restart. The game also tracks the time taken to solve the puzzle and checks for a valid solution upon completion.

---

#### **Design Choices**
1. **Difficulty Levels**:
   - The game offers three difficulty levels: easy, medium, and hard. Each level determines how many numbers are removed from the fully solved Sudoku board. For example:
     - Easy: 40 cells removed.
     - Medium: 50 cells removed.
     - Hard: 60 cells removed.
   - This approach ensures that the game remains challenging for players of all skill levels.

2. **Backtracking Algorithm**:
   - The `fillBoard` method uses a backtracking algorithm to generate a fully solved Sudoku board. This algorithm recursively tries numbers in empty cells and backtracks when it encounters a conflict, ensuring a valid solution.

3. **User Interaction**:
   - The game accepts user input in the form of row, column, and number to fill in the grid. It also supports commands like:
     - `u`: Undo the last move.
     - `n`: Start a new puzzle.
     - `-1`: Quit the game.
   - This interactive design makes the game user-friendly and engaging.

4. **Move History**:
   - A `Stack<int[]>` is used to store move history. Each move (row, column, and number) is pushed onto the stack, allowing the user to undo their last move.

5. **Validation**:
   - The game checks the validity of each move by ensuring the number does not already exist in the same row, column, or 3x3 block. This is done using the `isValid`, `isInRow`, `isInColumn`, and `isInBlock` methods.

6. **Time Tracking**:
   - The game tracks the time taken to solve the puzzle using `System.currentTimeMillis()`. The elapsed time is displayed when the player completes the puzzle or quits.

---

#### **Algorithms and Data Structures**
1. **Backtracking**:
   - The `fillBoard` method uses backtracking to generate a fully solved Sudoku board. It iterates through each cell, tries numbers from 1 to 9, and backtracks if a conflict arises.

2. **Random Removal**:
   - The `removeNumbers` method randomly removes numbers from the solved board to create the puzzle. The number of cells removed depends on the chosen difficulty level.

3. **Stack**:
   - A stack (`moveHistory`) is used to store move history. This allows the user to undo their last move by popping the most recent move from the stack.

4. **HashSet**:
   - Although not fully utilized in the current implementation, a `HashSet` was included in the `userInput` method for potential future enhancements, such as tracking unique moves.

---

#### **Challenges Encountered**
1. **Backtracking Efficiency**:
   - Initially, the backtracking algorithm was slow for generating large Sudoku boards. Optimizations were made to reduce redundant checks and improve performance.

2. **User Input Validation**:
   - Ensuring that user input (row, column, and number) was within valid bounds and did not conflict with existing numbers required careful validation logic.

3. **Undo Functionality**:
   - Implementing the undo feature using a stack required careful handling of the board state and move history to ensure consistency.

4. **Difficulty Balancing**:
   - Determining the appropriate number of cells to remove for each difficulty level was challenging. Too few removals made the puzzle too easy, while too many made it unsolvable.

---

#### **Improvements Made**
1. **Optimized Backtracking**:
   - The backtracking algorithm was optimized to reduce unnecessary checks and improve performance.

2. **Enhanced User Interaction**:
   - Additional commands (`u`, `n`, `-1`) were added to improve user experience and provide more control over the game.

3. **Time Tracking**:
   - A time-tracking feature was added to measure the player's performance and add a competitive element to the game.

4. **Error Handling**:
   - Improved error handling for invalid user input and edge cases (e.g., trying to undo when no moves are left).

---

#### **Input and Output**
- **Input**:
  - The game accepts user input via the console. Players can enter row, column, and number to fill in the grid or use commands like `u`, `n`, and `-1`.
  - Difficulty level is also input at the start of the game.

- **Output**:
  - The game outputs the Sudoku board to the console, displaying the current state of the grid with dividers for 3x3 blocks.
  - Messages are displayed for invalid moves, successful completions, and time taken.
    


**GAME STARTS**
![image](https://github.com/user-attachments/assets/4833fa35-033a-4fc3-94b8-2b6d3fe0dc06)

**ALREADY FILLED CELL**
![image](https://github.com/user-attachments/assets/3eff28dc-4053-41ba-841e-b313e0569690)

**OUT OF BOUNDS**
![image](https://github.com/user-attachments/assets/bbac8019-07fd-4743-9825-a8146ce7b291)

**GAME COMPLETE**
![image](https://github.com/user-attachments/assets/0698fd4c-c7ef-46fb-816b-a4bb703c4bb9)

**NEW PUZZLE**
![image](https://github.com/user-attachments/assets/357ce893-6243-4389-b308-88e2ccef59ee)


---

---

#### **Conclusion**
The Sudoku Game is a well-designed, interactive Java application that provides an engaging and challenging experience for players. By leveraging backtracking, stacks, and careful validation, the game ensures a smooth and enjoyable user experience. Future enhancements, such as file I/O and a GUI, could further elevate the game's appeal and functionality.
