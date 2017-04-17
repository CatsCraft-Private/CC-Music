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

        for (Layer l : this.song.getLayerHashMap().values()) {
            Note note = l.getNote(tick);
            if (note != null) {
                p.playSound(p.getEyeLocation(), Instrument.getInstrument(note.getInstrument()), (float) (l.getVolume() * this.volume * playerVolume) / 1000000.0F, NotePitch.getPitch(note.getKey() - 33));
            }
        }

    }
}
