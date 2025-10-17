import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A GUI-based Tic Tac Toe game.
 * <p>
 * Reuses the game logic from Lab 10 ([23]&#8203;:contentReference[oaicite:7]{index=7}) and modifies it to use
 * a 3x3 grid of JButtons instead of a text-based board.
 * Includes a status label and a "Game" menu with New and Quit options.
 * 
 * @author Your Name
 * @version Student Number: 12345678
 */
public class TicTacToeGUI extends JFrame implements ActionListener {
    public static final String PLAYER_X = "X";
    public static final String PLAYER_O = "O";
    public static final String EMPTY = " ";
    public static final String TIE = "T";
    
    private String currentPlayer;
    private String winner;
    private int numFreeSquares;
    
    private JButton[][] boardButtons;
    private JLabel statusLabel;
    
    public TicTacToeGUI() {
        super("Tic Tac Toe");
        currentPlayer = PLAYER_X;
        winner = EMPTY;
        numFreeSquares = 9;
        
        // Create board panel
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        boardButtons = new JButton[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                boardButtons[row][col] = new JButton(EMPTY);
                boardButtons[row][col].setFont(new Font("Arial", Font.BOLD, 60));
                boardButtons[row][col].setPreferredSize(new Dimension(100, 100)); // Ensures enough space for text
                boardButtons[row][col].addActionListener(this);
                boardPanel.add(boardButtons[row][col]);
            }
        }
        
        // Create status label
        statusLabel = new JLabel("It's " + currentPlayer + "'s turn.");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Create menu bar with "Game" menu
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        
        JMenuItem newItem = new JMenuItem("New");
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        newItem.addActionListener(e -> resetGame());
        gameMenu.add(newItem);
        
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        quitItem.addActionListener(e -> System.exit(0));
        gameMenu.add(quitItem);
        
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
        
        // Layout the frame
        add(boardPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!winner.equals(EMPTY)) {
            return; // Game over, ignore further moves.
        }
        
        JButton clicked = (JButton) e.getSource();
        if (!clicked.getText().equals(EMPTY)) {
            return; // Ignore if already marked.
        }
        
        // Mark the button with the current player's symbol.
        clicked.setText(currentPlayer);
        numFreeSquares--;
        
        // Determine which button was clicked.
        int row = -1, col = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boardButtons[i][j] == clicked) {
                    row = i;
                    col = j;
                    break;
                }
            }
            if (row != -1) break;
        }
        
        // Check if current move wins the game.
        if (haveWinner(row, col)) {
            winner = currentPlayer;
            statusLabel.setText("Game Over: " + currentPlayer + " wins!");
            disableAllButtons();
            return;
        } else if (numFreeSquares == 0) {
            winner = TIE;
            statusLabel.setText("Game Over: It's a tie!");
            return;
        }
        
        // Switch player.
        currentPlayer = currentPlayer.equals(PLAYER_X) ? PLAYER_O : PLAYER_X;
        statusLabel.setText("It's " + currentPlayer + "'s turn.");
    }
    
    /**
     * Checks whether the most recent move at (row, col) wins the game.
     *
     * @param row the row of the last move
     * @param col the column of the last move
     * @return true if the current player wins, false otherwise
     */
    private boolean haveWinner(int row, int col) {
        String player = currentPlayer;
        // Check row.
        if (boardButtons[row][0].getText().equals(player) &&
            boardButtons[row][1].getText().equals(player) &&
            boardButtons[row][2].getText().equals(player)) {
            return true;
        }
        // Check column.
        if (boardButtons[0][col].getText().equals(player) &&
            boardButtons[1][col].getText().equals(player) &&
            boardButtons[2][col].getText().equals(player)) {
            return true;
        }
        // Check main diagonal.
        if (row == col) {
            if (boardButtons[0][0].getText().equals(player) &&
                boardButtons[1][1].getText().equals(player) &&
                boardButtons[2][2].getText().equals(player)) {
                return true;
            }
        }
        // Check anti-diagonal.
        if (row + col == 2) {
            if (boardButtons[0][2].getText().equals(player) &&
                boardButtons[1][1].getText().equals(player) &&
                boardButtons[2][0].getText().equals(player)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Disables all board buttons to prevent further moves once the game is over.
     */
    private void disableAllButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardButtons[i][j].setEnabled(false);
            }
        }
    }
    
    /**
     * Resets the game state and clears the board for a new game.
     */
    private void resetGame() {
        currentPlayer = PLAYER_X;
        winner = EMPTY;
        numFreeSquares = 9;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardButtons[i][j].setText(EMPTY);
                boardButtons[i][j].setEnabled(true);
            }
        }
        statusLabel.setText("It's " + currentPlayer + "'s turn.");
    }
    
    public static void main(String[] args) {
        new TicTacToeGUI();
    }
}
