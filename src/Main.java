import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int gridSize = 10;
        int mineCount = 20;

        while (true) {
            int choice = showMenu(scanner);

            switch (choice) {
                case 1:
                    playMinesweeper(gridSize, mineCount, scanner);
                    break;
                case 2:
                    int gridSizeTemp = changeSettings("grid size", scanner);
                    int mineCountTemp = changeSettings("mine count", scanner);
                    if (gridSizeTemp * gridSizeTemp <= mineCountTemp) System.out.println("can't have more mines than spaces.");
                    else {
                        gridSize = gridSizeTemp;
                        mineCount = mineCountTemp;
                    }
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please choose 1, 2, or 3.");
            }
        }
    }

    public static int showMenu(Scanner scanner) {
        System.out.println("===== Minesweeper Menu =====");
        System.out.println("1. Start the game");
        System.out.println("2. Change settings");
        System.out.println("3. Quit");
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }

    public static int changeSettings(String settingName, Scanner scanner) {
        System.out.print("Enter the new " + settingName + ": ");
        return scanner.nextInt();
    }

    public static void playMinesweeper(int gridSize, int mineCount, Scanner scanner) {
        int[][] cell = new int[gridSize + 2][gridSize + 2];
        boolean[][] revealed = new boolean[gridSize + 2][gridSize + 2];

        initializeGame(gridSize, cell, revealed);
        placeMines(gridSize, mineCount, cell);
        calculateNumbers(gridSize, cell);

        boolean gameOver = false;
        int revealedCount = 0;

        while (!gameOver) {
            displayGrid(gridSize, cell, revealed);

            System.out.print("Enter row and column (e.g., '2 3'): ");
            int selectedRow = scanner.nextInt();
            int selectedCol = scanner.nextInt();

            if (selectedCol > gridSize || selectedRow > gridSize) {
                System.out.println("Don't go out of bounds!!");
            }
            else if(revealed[selectedRow][selectedCol]) System.out.println("You have already picked this space.");
            else if (cell[selectedRow][selectedCol] == 9) {
                gameOver = true;
            } else {
                revealed[selectedRow][selectedCol] = true;
                revealedCount++;

                if (revealedCount == gridSize * gridSize - mineCount) {
                    gameOver = true;
                }
            }
        }

        //displayGrid(gridSize, cell, revealed);

        if (revealedCount == gridSize * gridSize - mineCount) {
            System.out.println("Congratulations! You win!");
            revealAll(gridSize, revealed);
            displayGrid(gridSize, cell, revealed);
        } else {
            System.out.println("Game over. You hit a mine!");
            revealAll(gridSize, revealed);
            displayGrid(gridSize, cell, revealed);
        }


    }

    public static void initializeGame(int gridSize, int[][] cell, boolean[][] revealed) {
        for (int row = 1; row <= gridSize; row++) {
            for (int col = 1; col <= gridSize; col++) {
                cell[row][col] = 0;
                revealed[row][col] = false;
            }
        }
    }

    public static void placeMines(int gridSize, int mineCount, int[][] cell) {
        int z = 0;
        while (z < mineCount) {
            Random rand = new Random();
            int randomValue = rand.nextInt(gridSize) + 1;
            Random rand1 = new Random();
            int randomValue1 = rand1.nextInt(gridSize) + 1;
            if (cell[randomValue][randomValue1] != 9) {
                cell[randomValue][randomValue1] = 9;
                z++;
            }
        }
    }

    public static void calculateNumbers(int gridSize, int[][] cell) {
        for (int row = 1; row <= gridSize; row++) {
            for (int col = 1; col <= gridSize; col++) {
                int k = 0;

                if (cell[row][col] != 9) {
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            if (i == 0 && j == 0) continue;
                            if (cell[row + i][col + j] == 9) k++;
                        }
                    }
                    cell[row][col] = k;
                }
            }
        }
    }

    public static void displayGrid(int gridSize, int[][] cell, boolean[][] revealed) {
        System.out.print("   ");
        for (int col = 1; col <= gridSize; col++) {
            System.out.printf("%2d ", col);
        }
        System.out.println();

        for (int row = 1; row <= gridSize; row++) {
            System.out.printf("%2d ", row);
            for (int col = 1; col <= gridSize; col++) {
                if (revealed[row][col]) {
                    if (cell[row][col] == 9) {
                        System.out.print("[X]");
                    } else {
                        System.out.printf("[" + cell[row][col] +  "]", cell[row][col]);
                    }
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println();
        }
    }
    public static void revealAll(int gridSize, boolean[][] revealed) {
        for (int row = 1; row <= gridSize; row++) {
            for (int col = 1; col <= gridSize; col++) {
                revealed[row][col] = true;
            }
        }
    }
}
