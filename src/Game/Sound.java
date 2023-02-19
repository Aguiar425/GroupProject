package Game;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    static Clip dungeonSoundLoop;
    Clip soundLoop;

    public static Clip getDungeonSoundLoopVar() {
        return dungeonSoundLoop;
    }

    public void setSoundClip(String soundPath) {
        final Clip soundClip;
        try {
            File soundFile = new File(soundPath);
            AudioInputStream soundToPlay = AudioSystem.getAudioInputStream(soundFile);
            soundClip = AudioSystem.getClip();
            soundClip.open(soundToPlay);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        if (soundClip.isRunning()) soundClip.stop();   // Stop the player if it is still running
        soundClip.setFramePosition(0); // rewind to the beginning
        soundClip.start();     // Start playing
    }

    public void setSoundLoop(String path) {
        String soundPath = path;
        try {
            File soundFile = new File(soundPath);
            AudioInputStream soundToPlay = AudioSystem.getAudioInputStream(soundFile);
            soundLoop = AudioSystem.getClip();
            soundLoop.open(soundToPlay);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        soundLoop.setFramePosition(0);
        soundLoop.start();

    }

    public void setDungeonSoundLoop(String path) {
        String soundPath = path;
        try {
            File soundFile = new File(soundPath);
            AudioInputStream soundToPlay = AudioSystem.getAudioInputStream(soundFile);
            dungeonSoundLoop = AudioSystem.getClip();
            dungeonSoundLoop.open(soundToPlay);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        dungeonSoundLoop.setFramePosition(0);
        dungeonSoundLoop.start();

    }

    public Clip getSoundLoopVar() {
        return soundLoop;
    }
}