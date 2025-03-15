import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.HashSet;

public class Main {
    private static final int SIZE = 9; // Size of the board
    private static final int EMPTY = 0; // empty cell
    private static Stack<int[]> moveHistory = new Stack<>(); // Stack move history
    private static long startTime; // Start time

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        boolean playAgain = true; // replay

        while (playAgain) {
            System.out.println("Choose difficulty level: easy, medium, or hard");
            String difficulty = input.next().toLowerCase();
            int[][] board = new int[SIZE][SIZE]; // Initialize  board
            startTime = System.currentTimeMillis(); //  start time
            generateSudoku(board, difficulty); // Generate a new  puzzle
            printBoard(board); // Print  board
            userInput(board, difficulty); //  user input

            // Ask the player if they want to play again
            System.out.println("Do you want to play again? (yes/no)");
            String response = input.next().toLowerCase();
            if (!response.equals("yes")) {
                playAgain = false; // End the game loop
                System.out.println("Thank you for playing! Goodbye!");
            } else {
                moveHistory.clear(); // Clear move history for a new game
            }
        }
    }

    // Generate a board
    public static void generateSudoku(int[][] board, String difficulty) {
        fillBoard(board); //  backtracking

        // Determine difficulty
        int cellsToRemove = switch (difficulty) {
            case "easy" -> 40;
            case "medium" -> 50;
            case "hard" -> 60;
            default -> {
                System.out.println("Invalid difficulty. Defaulting to medium.");
                yield 50;
            }
        };

        removeNumbers(board, cellsToRemove);
    }

    //BACKTRACKING
    private static boolean fillBoard(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num; // Place the number if valid

                            if (fillBoard(board)) {
                                return true; // Continue filling the board
                            }

                            board[row][col] = EMPTY; // Backtrack if no valid number is found
                        }
                    }
                    return false; // No valid number
                }
            }
        }
        return true; // Board is full
    }

    // Remove numbers to create the puzzle
    private static void removeNumbers(int[][] board, int cellsToRemove) {
        Random random = new Random();
        while (cellsToRemove > 0) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);

            if (board[row][col] != EMPTY) {
                board[row][col] = EMPTY; // Remove the number
                cellsToRemove--;
            }
        }
    }

    // Check if a number is valid in the given cell
    private static boolean isValid(int[][] board, int row, int col, int num) {
        return !isInRow(board, row, num) &&
                !isInColumn(board, col, num) &&
                !isInBlock(board, row, col, num);
    }

    // Print \ board
    public static void printBoard(int[][] board) {
        System.out.println("Current Sudoku Board:");
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    System.out.print("_ "); //  empty place
                } else {
                    System.out.print(board[row][col] + " "); // Print the number
                }
                if ((col + 1) % 3 == 0 && col != SIZE - 1) {
                    System.out.print("| "); //  vertical dividers
                }
            }
            System.out.println();
            if ((row + 1) % 3 == 0 && row != SIZE - 1) {
                System.out.println("---------------------"); //  horizontal dividers
            }
        }
    }

    //Player input
    public static void userInput(int[][] board, String difficulty) {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("Enter row (0-8), column (0-8), and number (1-9) to insert, 'u' to undo last move, 'n' for new puzzle, or '-1' to quit:");

            if (input.hasNextInt()) {
                int row = input.nextInt();

                if (row == -1) {
                    displayTime(); // Display time taken and exit
                    break;
                }

                int col = input.nextInt();
                int num = input.nextInt();

                // Bounds
                if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || num < 1 || num > 9) {
                    System.out.println("Out of bounds. Try again.");
                    continue;
                }

                // Check if the cell is already filled
                if (board[row][col] != EMPTY) {
                    System.out.println("Cell is already filled. Try again.");
                    continue;
                }

                //Checking move valid
                if (!isValid(board, row, col, num)) {
                    System.out.println("Invalid move. Number already exists in row, column, or block.");
                    continue;
                }

                //Place number and record move :)
                board[row][col] = num;
                HashSet set = new HashSet();
                moveHistory.push(new int[]{row, col, num});
                printBoard(board);

                // Check the board is complete
                if (isBoardComplete(board)) {
                    System.out.println("Congratulations! You've solved the Sudoku!");
                    displayTime();
                    break;
                }
            } else if (input.hasNext()) {
                String command = input.next();
                if (command.equals("u")) {
                    undoMove(board); // Undo
                    printBoard(board);
                } else if (command.equals("n")) {
                    System.out.println("Starting a new puzzle...");
                    board = new int[SIZE][SIZE];
                    generateSudoku(board, difficulty); // Generate a new puzzle
                    moveHistory.clear(); // Clear move history
                    startTime = System.currentTimeMillis(); // Reset the timer
                    printBoard(board);
                } else {
                    System.out.println("Invalid input. Try again.");
                }
            }
        }
    }

    // Check the board
    private static boolean isBoardComplete(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY || !isValid(board, row, col, board[row][col])) {
                    return false; // Board is not complete or invalid
                }
            }
        }
        return true; // Board is complete and valid
    }

    // Undo
    public static void undoMove(int[][] board) {
        if (!moveHistory.isEmpty()) {
            int[] lastMove = moveHistory.pop();
            board[lastMove[0]][lastMove[1]] = EMPTY; // Clear the cell
            System.out.println("Last move undone.");
        } else {
            System.out.println("No moves to undo.");
        }
    }

    // Time
    public static void displayTime() {
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        System.out.println("Time taken: " + elapsedTime + " seconds");
    }

    // Check a number in the row
    public static boolean isInRow(int[][] board, int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    // Check number in the column
    public static boolean isInColumn(int[][] board, int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    // Check a number in the 3x3 block
    public static boolean isInBlock(int[][] board, int row, int col, int num) {
        int blockRowStart = (row / 3) * 3;
        int blockColStart = (col / 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[blockRowStart + i][blockColStart + j] == num) {
                    return true;
                }
            }
        }
        return false;
    }
}