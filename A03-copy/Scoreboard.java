/**
 * This class keeps track of wins, draws, and total games played.
 * 
 * @author Dumany Lombe
 * @version Student Number: 101316658
 * 
 */
public class Scoreboard {
    private int winsX;
    private int winsO;
    private int draws;
    private int gamesPlayed;
    
    /**
     * Constructs a new Scoreboard, initializing the counts of X wins, O wins, draws, 
     * and total games played to zero. This provides a clean starting point for tracking
     * future game results.
     */
    public Scoreboard() {
        winsX = 0;
        winsO = 0;
        draws = 0;
        gamesPlayed = 0;
    }
    
    /**
     * Records a win for the specified player.
     */
    public void recordWin(String player) {
        if (player.equals(TicTacToeGame.PLAYER_X))
            winsX++;
        else if (player.equals(TicTacToeGame.PLAYER_O))
            winsO++;
        gamesPlayed++;
    }
    
    /**
     * Records a draw.
     */
    public void recordDraw() {
        draws++;
        gamesPlayed++;
    }
    
    /**
     * Returns a formatted score string.
     */
    public String getScoreString(String playerXName, String playerOName) {
        return String.format("Score: %s: %d wins | %s: %d wins | Draws: %d | Games Played: %d",
                             playerXName, winsX, playerOName, winsO, draws, gamesPlayed);
    }
}
