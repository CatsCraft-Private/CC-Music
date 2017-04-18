package modded.NoteBlockAPI;

import java.util.Iterator;

import bobcatsss.music.Main;
import org.bukkit.entity.Player;

public class RadioSongPlayer extends SongPlayer {
    public RadioSongPlayer(Song song) {
        super(song);
    }

    public void playTick(Player p, int tick) {
        byte playerVolume = Main.getPlayerVolume(p);

        for (Layer l : song.getLayerHashMap().values()) {
            Note note = l.getNote(tick);
            if (note == null) {
                continue;
            }
            p.playSound(p.getEyeLocation(),
                    Instrument.getInstrument(note.getInstrument()),
                    (l.getVolume() * (int) volume * (int) playerVolume) / 1000000f,
                    NotePitch.getPitch(note.getKey() - 33));
        }
    }
}
