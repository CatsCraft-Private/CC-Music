package modded.NoteBlockAPI;

import org.bukkit.Sound;
import simple.brainsynder.sound.SoundMaker;

public class Instrument {
    public Instrument() {
    }

    public static Sound getInstrument(byte instrument) {
        String soundName;
        switch (instrument) {
            case 0:
                soundName = SoundMaker.BLOCK_NOTE_HARP.getSound();//Sound.NOTE_PIANO
            case 1:
                soundName = SoundMaker.BLOCK_NOTE_BASS.getSound();//Sound.NOTE_BASS_GUITAR;
            case 2:
                soundName = SoundMaker.BLOCK_NOTE_BASEDRUM.getSound();//Sound.NOTE_BASS_DRUM;
            case 3:
                soundName = SoundMaker.BLOCK_NOTE_SNARE.getSound();//Sound.NOTE_SNARE_DRUM;
            case 4:
                soundName = SoundMaker.BLOCK_NOTE_HAT.getSound();//Sound.NOTE_STICKS;
            default:
                soundName = SoundMaker.BLOCK_NOTE_HARP.getSound();//Sound.NOTE_PIANO;
        }

        return Sound.valueOf(soundName);
    }

    public static org.bukkit.Instrument getBukkitInstrument(byte instrument) {
        switch (instrument) {
            case 0:
                return org.bukkit.Instrument.PIANO;
            case 1:
                return org.bukkit.Instrument.BASS_GUITAR;
            case 2:
                return org.bukkit.Instrument.BASS_DRUM;
            case 3:
                return org.bukkit.Instrument.SNARE_DRUM;
            case 4:
                return org.bukkit.Instrument.STICKS;
            default:
                return org.bukkit.Instrument.PIANO;
        }
    }
}