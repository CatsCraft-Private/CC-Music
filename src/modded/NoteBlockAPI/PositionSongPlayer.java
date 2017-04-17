package modded.NoteBlockAPI;

import java.util.Iterator;

import bobcatsss.music.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PositionSongPlayer extends SongPlayer {
    private Location targetLocation;

    public PositionSongPlayer(Song song) {
        super(song);
    }

    public Location getTargetLocation() {
        return this.targetLocation;
    }

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    public void playTick(Player p, int tick) {
        if(p.getWorld().getName().equals(this.targetLocation.getWorld().getName())) {
            byte playerVolume = Main.getPlayerVolume(p);
            for (Layer l : this.song.getLayerHashMap().values()) {
                Note note = l.getNote(tick);
                if (note != null) {
                    p.playSound(this.targetLocation, Instrument.getInstrument(note.getInstrument()), (float) (l.getVolume() * this.volume * playerVolume) / 1000000.0F, NotePitch.getPitch(note.getKey() - 33));
                }
            }

        }
    }
}