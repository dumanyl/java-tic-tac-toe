import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The main GUI class that displays the tic-tac-toe board, 
 * scoreboard, and status.
 * It interacts with the game model (TicTacToeGame), 
 * Scoreboard, and SoundManager.
 * 
 * @author Dumany Lombe
 * @version Student Number: 101316658
 * 
 */
public class TicTacToeGUI extends JFrame implements ActionListener {
    
    private TicTacToeGame game;
    private Scoreboard scoreboard;
    private SoundManager soundManager;
    
    private JButton[][] boardButtons;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    
    private String playerXName;
    private String playerOName;
    
    // Tracks which player should start the next game.
    private String startingPlayer;
    
    /**
     * Constructs a new GUI for playing Tic Tac Toe, prompting the user for
     * Player X and Player O names, setting the initial player, initializing
     * the game model and related managers (scoreboard, sound), and laying out
     * the 3x3 button board. This constructor also creates a menu bar with
     * "New" and "Quit" options, and sets up the primary labels for score
     * tracking and status (i.e., whose turn it is).
     * 
     * Once constructed, the GUI frame is fully visible and ready for play.
     * 
     */
    public TicTacToeGUI() {
        super("Tic Tac Toe");
        
        // Get player names.
        playerXName = JOptionPane.showInputDialog(this, "Enter name for Player X:");
        if (playerXName == null || playerXName.trim().isEmpty())
            playerXName = "Player X";
        playerOName = JOptionPane.showInputDialog(this, "Enter name for Player O:");
        if (playerOName == null || playerOName.trim().isEmpty())
            playerOName = "Player O";
        
        // Set the default starting player.
        startingPlayer = TicTacToeGame.PLAYER_X;
        
        // Initialize game model, scoreboard, and sound manager.
        game = new TicTacToeGame(startingPlayer);
        scoreboard = new Scoreboard();
        soundManager = new SoundManager();
        
        // Create the scoreboard label.
        scoreLabel = new JLabel();
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        updateScoreboard();
        
        // Create the status label.
        statusLabel = new JLabel("It's " + getPlayerName(game.getCurrentPlayer()) + "'s turn.");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Create the board panel with a 3x3 grid of buttons.
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        boardButtons = new JButton[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                boardButtons[i][j] = new JButton(TicTacToeGame.EMPTY);
                boardButtons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                boardButtons[i][j].setPreferredSize(new Dimension(100, 100));
                boardButtons[i][j].addActionListener(this);
                boardPanel.add(boardButtons[i][j]);
            }
        }
        
        // Set up the menu bar with "New" and "Quit" options.
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        
        JMenuItem newItem = new JMenuItem("New");
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        newItem.addActionListener(e -> resetGame());
        gameMenu.add(newItem);
        
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        quitItem.addActionListener(e -> System.exit(0));
        gameMenu.add(quitItem);
        
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
        
        // Layout the frame.
        setLayout(new BorderLayout());
        add(scoreLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Returns the display name for the given player symbol.
     */
    private String getPlayerName(String playerSymbol) {
        if (playerSymbol.equals(TicTacToeGame.PLAYER_X))
            return playerXName;
        else if (playerSymbol.equals(TicTacToeGame.PLAYER_O))
            return playerOName;
        else
            return "";
    }
    
    /**
     * Updates the scoreboard label.
     */
    private void updateScoreboard() {
        scoreLabel.setText(scoreboard.getScoreString(playerXName, playerOName));
    }
    
    /**
     * Handles the button click events on the Tic-Tac-Toe board. If the game is still in progress,
     * identifies the clicked button, plays a click sound, and attempts to make a move in the
     * underlying game model. If a winning or tie condition is detected, it updates the status label,
     * records the result in the scoreboard, highlights the winning combination (if any), and disables
     * further moves. If the game continues, it switches to the next player and updates the status
     * accordingly.
     *
     * @param e the ActionEvent triggered when the user clicks one of the board buttons
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // If the game is over, ignore moves.
        if (game.isGameOver()) return;
        
        JButton clicked = (JButton) e.getSource();
        int row = -1, col = -1;
        // Find the position of the clicked button.
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (boardButtons[i][j] == clicked) {
                    row = i;
                    col = j;
                    break;
                }
            }
            if (row != -1) break;
        }
        
        // Ignore the click if the button is already marked.
        if (!clicked.getText().equals(TicTacToeGame.EMPTY))
            return;
        
        // Play the click sound.
        soundManager.playClick();
        
        // Make the move in the game model.
        if (game.makeMove(row, col)) {
            // Update the GUI button.
            boardButtons[row][col].setText(game.getCell(row, col));
        }
        
        // Check for game over.
        if (game.isGameOver()) {
            String win = game.getWinner();
            if (win.equals(TicTacToeGame.TIE)) {
                statusLabel.setText("Game Over: It's a tie!");
                scoreboard.recordDraw();
                // Toggle the starting player in case of a tie.
                startingPlayer = startingPlayer.equals(TicTacToeGame.PLAYER_X) ? TicTacToeGame.PLAYER_O : TicTacToeGame.PLAYER_X;
            } else {
                statusLabel.setText("Game Over: " + getPlayerName(win) + " wins!");
                scoreboard.recordWin(win);
                // Highlight winning combination.
                int[][] combo = game.getWinningCombination();
                if (combo != null) {
                    for (int[] pos : combo) {
                        boardButtons[pos[0]][pos[1]].setBackground(Color.GREEN);
                    }
                }
                // The winner starts the next game.
                startingPlayer = win;
                soundManager.playCelebration();
            }
            updateScoreboard();
            disableAllButtons();
        } else {
            // Switch player and update status.
            game.switchPlayer();
            statusLabel.setText("It's " + getPlayerName(game.getCurrentPlayer()) + "'s turn.");
        }
    }
    
    /**
     * Disables all board buttons.
     */
    private void disableAllButtons() {
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                boardButtons[i][j].setEnabled(false);
            }
        }
    }
    
    /**
     * Resets the game board for a new round.
     */
    private void resetGame() {
        game.initializeGame(startingPlayer);
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                boardButtons[i][j].setText(TicTacToeGame.EMPTY);
                boardButtons[i][j].setEnabled(true);
                boardButtons[i][j].setBackground(null); // Reset background color
            }
        }
        statusLabel.setText("It's " + getPlayerName(game.getCurrentPlayer()) + "'s turn.");
    }
}
