import java.net.URL;
import java.applet.AudioClip;
import java.applet.Applet;

/**
 * This class loads and plays sound effects.
 * 
 * @author Dumany Lombe
 * @version Student Number: 101316658
 */
public class SoundManager {
    
    private AudioClip clickSound;
    private AudioClip celebrationSound;
    
    /**
     * Constructs a new SoundManager by attempting to load the "click.wav" and "celebration.wav"
     * sound files from the current classpath. If successfully located, these sounds are stored
     * as AudioClips for later playback. If a file is not found, the corresponding AudioClip
     * field remains null.
     */
    public SoundManager() {
        // Load sound files; ensure click.wav and celebration.wav are available in your classpath.
        URL clickURL = getClass().getResource("click.wav");
        if (clickURL != null)
            clickSound = Applet.newAudioClip(clickURL);
        URL celebrationURL = getClass().getResource("celebration.wav");
        if (celebrationURL != null)
            celebrationSound = Applet.newAudioClip(celebrationURL);
    }
    
    /**
     * Plays the click sound.
     */
    public void playClick() {
        if (clickSound != null)
            clickSound.play();
    }
    
    /**
     * Plays the celebration sound.
     */
    public void playCelebration() {
        if (celebrationSound != null)
            celebrationSound.play();
    }
}
