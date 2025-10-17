/**
 * This class models the tic-tac-toe game logic.
 * It maintains the board, current player, and win-checking logic.
 * 
 * @author Dumany Lombe
 * @version Student Number: 101316658
 * 
 */
public class TicTacToeGame {
    public static final String PLAYER_X = "X";
    public static final String PLAYER_O = "O";
    public static final String EMPTY = " ";
    public static final String TIE = "T";
    
    private String[][] board;
    private String currentPlayer;
    private String winner;
    private int numFreeSquares;
    
    /**
     * Constructs a new game with the specified starting player.
     * @param startingPlayer either PLAYER_X or PLAYER_O.
     */
    public TicTacToeGame(String startingPlayer) {
        board = new String[3][3];
        initializeGame(startingPlayer);
    }
    
    /**
     * Initializes or resets the board.
     * @param startingPlayer the player who should start.
     */
    public void initializeGame(String startingPlayer) {
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                board[i][j] = EMPTY;
            }
        }
        currentPlayer = startingPlayer;
        winner = EMPTY;
        numFreeSquares = 9;
    }
    
    /**
     * Attempts to make a move at the given row and column.
     * @param row the row index (0-2)
     * @param col the column index (0-2)
     * @return true if the move was made, false if the cell was not empty.
     */
    public boolean makeMove(int row, int col) {
        if (board[row][col].equals(EMPTY)) {
            board[row][col] = currentPlayer;
            numFreeSquares--;
            // Check if this move wins the game
            if (checkWinner(row, col)) {
                winner = currentPlayer;
            } else if (numFreeSquares == 0) {
                winner = TIE;
            }
            return true;
        }
        return false;
    }
    
    /**
     * Returns the value at the given cell.
     */
    public String getCell(int row, int col) {
        return board[row][col];
    }
    
    /**
     * Returns the current winner ("X", "O", "T" for tie, or EMPTY if game ongoing).
     */
    public String getWinner() {
        return winner;
    }
    
    /**
     * Checks if the game is over.
     */
    public boolean isGameOver() {
        return !winner.equals(EMPTY);
    }
    
    /**
     * Returns the number of free squares remaining.
     */
    public int getNumFreeSquares() {
        return numFreeSquares;
    }
    
    /**
     * Returns the current player.
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * Switches the current player.
     */
    public void switchPlayer() {
        currentPlayer = currentPlayer.equals(PLAYER_X) ? PLAYER_O : PLAYER_X;
    }
    
    /**
     * Checks whether the most recent move by the current player (at the specified row and column)
     * has produced a winning combination on the board. This method verifies the row, the column,
     * and (if applicable) both diagonals for three identical symbols (i.e., the current player's
     * marker).
     *
     * @param row the row index of the last move
     * @param col the column index of the last move
     * @return true if the current player's move completes a winning line, false otherwise
     */
    private boolean checkWinner(int row, int col) {
        String player = currentPlayer;
        // Check row
        if (board[row][0].equals(player) &&
            board[row][1].equals(player) &&
            board[row][2].equals(player))
            return true;
        // Check column
        if (board[0][col].equals(player) &&
            board[1][col].equals(player) &&
            board[2][col].equals(player))
            return true;
        // Check main diagonal
        if (row == col) {
            if (board[0][0].equals(player) &&
                board[1][1].equals(player) &&
                board[2][2].equals(player))
                return true;
        }
        // Check anti-diagonal
        if (row + col == 2) {
            if (board[0][2].equals(player) &&
                board[1][1].equals(player) &&
                board[2][0].equals(player))
                return true;
        }
        return false;
    }
    
    /**
     * If there is a winning combination, returns an array of coordinate pairs.
     * Otherwise, returns null.
     */
    public int[][] getWinningCombination() {
        String player = currentPlayer;
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(player) &&
                board[i][1].equals(player) &&
                board[i][2].equals(player)) {
                return new int[][] { {i, 0}, {i, 1}, {i, 2} };
            }
        }
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j].equals(player) &&
                board[1][j].equals(player) &&
                board[2][j].equals(player)) {
                return new int[][] { {0, j}, {1, j}, {2, j} };
            }
        }
        // Check main diagonal
        if (board[0][0].equals(player) &&
            board[1][1].equals(player) &&
            board[2][2].equals(player)) {
            return new int[][] { {0, 0}, {1, 1}, {2, 2} };
        }
        // Check anti-diagonal
        if (board[0][2].equals(player) &&
            board[1][1].equals(player) &&
            board[2][0].equals(player)) {
            return new int[][] { {0, 2}, {1, 1}, {2, 0} };
        }
        return null;
    }
}
