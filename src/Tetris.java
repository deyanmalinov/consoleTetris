import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Tetris {

    private static final int ROWS = 20;
    private static final int COLS = 10;
    private static final char EMPTY = ' ';
    private static final char BLOCK = '#';

    private char[][] grid;
    private int currentRow;
    private int currentCol;
    private int[][] currentPiece;

    public Tetris() {
        grid = new char[ROWS][COLS];
        currentPiece = generateRandomPiece();
    }

    private int[][] generateRandomPiece() {
        // Define different Tetris piece shapes
        int[][][] pieces = {
                { { 1, 1 }, { 1, 1 } },
                { { 1, 1, 1, 1 } },
                { { 1, 1, 0 }, { 0, 1, 1 } },
                { { 0, 1, 1 }, { 1, 1, 0 } },
                { { 1, 0, 0 }, { 1, 1, 1 } },
                { { 0, 0, 1 }, { 1, 1, 1 } },
                { { 1, 1, 1 }, { 0, 0, 1 } }
        };

        Random random = new Random();
        int[][] piece = pieces[random.nextInt(pieces.length)];

        currentRow = 0;
        currentCol = COLS / 2 - piece[0].length / 2;

        return piece;
    }

    private void clearGrid() {
        for (char[] row : grid) {
            Arrays.fill(row, EMPTY);
        }
    }

    private void placePiece() {
        for (int i = 0; i < currentPiece.length; i++) {
            for (int j = 0; j < currentPiece[i].length; j++) {
                if (currentPiece[i][j] == 1) {
                    grid[currentRow + i][currentCol + j] = BLOCK;
                }
            }
        }
    }

    private void printGrid() {
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }

    private void movePieceDown() {
        currentRow++;
        if (collisionDetected()) {
            currentRow--;
            placePiece();
            clearLines();
            currentPiece = generateRandomPiece();
        }
    }

    private void movePieceLeft() {
        currentCol--;
        if (collisionDetected()) {
            currentCol++;
        }
    }

    private void movePieceRight() {
        currentCol++;
        if (collisionDetected()) {
            currentCol--;
        }
    }

    private boolean collisionDetected() {
        for (int i = 0; i < currentPiece.length; i++) {
            for (int j = 0; j < currentPiece[i].length; j++) {
                if (currentPiece[i][j] == 1) {
                    int newRow = currentRow + i;
                    int newCol = currentCol + j;
                    if (newRow >= ROWS || newCol < 0 || newCol >= COLS || grid[newRow][newCol] == BLOCK) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void clearLines() {
        for (int i = ROWS - 1; i >= 0; i--) {
            boolean lineIsFull = true;
            for (int j = 0; j < COLS; j++) {
                if (grid[i][j] != BLOCK) {
                    lineIsFull = false;
                    break;
                }
            }
            if (lineIsFull) {
                for (int k = i; k > 0; k--) {
                    System.arraycopy(grid[k - 1], 0, grid[k], 0, COLS);
                }
                Arrays.fill(grid[0], EMPTY);
            }
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            clearGrid();
            placePiece();
            printGrid();

            System.out.print("Enter a command (a: left, d: right, s: down, q: quit): ");
            String command = scanner.nextLine().toLowerCase();

            if (command.equals("q")) {
                break;
            } else if (command.equals("a")) {
                movePieceLeft();
            } else if (command.equals("d")) {
                movePieceRight();
            } else if (command.equals("s")) {
                movePieceDown();
            }
        }

        System.out.println("Game over!");
        scanner.close();
    }

    public static void main(String[] args) {
        Tetris tetris = new Tetris();
        tetris.play();
    }
}