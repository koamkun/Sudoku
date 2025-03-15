import java.util.Random;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    private static final int SIZE = 9;
    private static Stack<int[]> moveHistory = new Stack<>();
    private static long startTime;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose difficulty level: easy, medium, or hard");
        String difficulty = scanner.next().toLowerCase();
        int[][] board = new int[SIZE][SIZE];
        startTime = System.currentTimeMillis();
        initializeBoard(board, difficulty);
        printBoard(board);
        userInput(board, difficulty);
    }

    // Initialize the Sudoku board with random numbers between 1 and 9 and empty spaces
    public static void initializeBoard(int[][] board, String difficulty) {
        Random random = new Random();
        double emptyProbability = switch (difficulty) {
            case "easy" -> 0.4;
            case "medium" -> 0.6;
            case "hard" -> 0.8;
            default -> {
                System.out.println("Invalid difficulty. Defaulting to medium.");
                yield 0.6;
            }
        };

        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
                HashSet<Integer> usedNumbers = new HashSet<>();

                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 3; col++) {
                        if (random.nextDouble() < 0.6) { // 60% chance of an empty space
                            board[blockRow * 3 + row][blockCol * 3 + col] = 0; // Representing an empty space with 0
                        } else {
                            int num;
                            do {
                                num = random.nextInt(9) + 1;
                            } while (usedNumbers.contains(num));
                            usedNumbers.add(num);
                            board[blockRow * 3 + row][blockCol * 3 + col] = num;
                        }
                    }
                }
            }
        }
    }

    // Print the Sudoku board
    public static void printBoard(int[][] board) {
        System.out.println("Current Sudoku Board:");
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    System.out.print(". "); // Representing empty space with '.'
                } else {
                    System.out.print(board[row][col] + " ");
                }
                if ((col + 1) % 3 == 0 && col != SIZE - 1) {
                    System.out.print("| ");
                }

            }
            System.out.println();
            if ((row + 1) % 3 == 0 && row != SIZE - 1) {
                System.out.println("---------------------"); // Horizontal separator between 3x3 blocks
            }

        }
    }

    // Allow the user to input numbers into the board
    public static void userInput(int[][] board, String difficulty) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter row (0-8), column (0-8), and number (1-9) to insert, 'u' to undo last move, 'n' for new puzzle, or '-1' to quit:");
            if (scanner.hasNextInt()) {
                int row = scanner.nextInt();
                if (row == -1) {
                    displayTime();
                    break;
                }
                int col = scanner.nextInt();
                int num = scanner.nextInt();

                if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || num < 1 || num > 9) {
                    System.out.println("Out of bounds. Try again.");
                    continue;
                }

                if (board[row][col] != 0) {
                    System.out.println("Cell is already filled. Try again.");
                    continue;
                }

                if (isInRow(board, row, num)) {
                    System.out.println("Number already exists in the row. Try again.");
                    board[row][col] = 0;
                } else if (isInColumn(board, col, num)) {
                    System.out.println("Number already exists in the column. Try again.");
                    board[row][col] = 0;
                } else if (isInBlock(board, row, col, num)) {
                    System.out.println("Number already exists in the 3x3 block. Try again.");
                    board[row][col] = 0;
                } else {
                    board[row][col] = num;
                    moveHistory.push(new int[]{row, col, num});
                    printBoard(board);
                }
            } else if (scanner.hasNext()) {
                String command = scanner.next();
                if (command.equals("u")) {
                    undoMove(board);
                    printBoard(board);
                } else if (command.equals("n")) {
                    System.out.println("Starting a new puzzle...");
                    board = new int[SIZE][SIZE];
                    initializeBoard(board, difficulty);
                    moveHistory.clear();
                    startTime = System.currentTimeMillis();
                    printBoard(board);
                } else {
                    System.out.println("Invalid input. Try again.");
                }
            }
        }
        scanner.close();
    }

    // Undo the last move
    public static void undoMove(int[][] board) {
        if (!moveHistory.isEmpty()) {
            int[] lastMove = moveHistory.pop();
            board[lastMove[0]][lastMove[1]] = 0;
            System.out.println("Last move undone.");
        } else {
            System.out.println("No moves to undo.");
        }
    }

    // Display time taken to solve the puzzle
    public static void displayTime() {
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        System.out.println("Time taken: " + elapsedTime + " seconds");
    }

    // Check if the number exists in the row
    public static boolean isInRow(int[][] board, int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    // Check if the number exists in the column
    public static boolean isInColumn(int[][] board, int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    // Check if the number exists in the 3x3 block
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
