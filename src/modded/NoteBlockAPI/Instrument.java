package modded.NoteBlockAPI;

import org.bukkit.Sound;
import simple.brainsynder.sound.SoundMaker;
import simple.brainsynder.utils.ServerVersion;

public class Instrument {
    public Instrument() {
    }
    
    public static Sound getInstrument(byte instrument) {
        SoundMaker soundName;
        switch (instrument) {
            case 0:
                soundName = SoundMaker.BLOCK_NOTE_HARP;//Sound.NOTE_PIANO
                break;
            case 1:
                soundName = SoundMaker.BLOCK_NOTE_BASS;//Sound.NOTE_BASS_GUITAR;
                break;
            case 2:
                soundName = SoundMaker.BLOCK_NOTE_BASEDRUM;//Sound.NOTE_BASS_DRUM;
                break; 
            case 3:
                soundName = SoundMaker.BLOCK_NOTE_SNARE;//Sound.NOTE_SNARE_DRUM;
                break;
            case 4:
                soundName = SoundMaker.BLOCK_NOTE_HAT;//Sound.NOTE_STICKS;
                break;
            
            default:
                if (ServerVersion.getVersion().getIntVersion() >= 112) {
                    switch (instrument) {
                        case 5:
                            soundName = SoundMaker.BLOCK_NOTE_FLUTE;
                            break;
                        case 6:
                            soundName = SoundMaker.BLOCK_NOTE_BELL;
                            break;
                        case 7:
                            soundName = SoundMaker.BLOCK_NOTE_GUITAR;
                            break;
                        case 8:
                            soundName = SoundMaker.BLOCK_NOTE_CHIME;
                            break;
                        case 9:
                            soundName = SoundMaker.BLOCK_NOTE_XYLOPHONE;
                            break;
                        default:
                            soundName = SoundMaker.BLOCK_NOTE_HARP;//Sound.NOTE_PIANO;
                            break;
                    }
                } else {
                    soundName = SoundMaker.BLOCK_NOTE_HARP;//Sound.NOTE_PIANO;
                }
                break;
        }
        
        return Sound.valueOf(soundName.getSound());
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