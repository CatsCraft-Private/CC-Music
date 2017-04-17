package modded.NoteBlockAPI;

import java.util.Iterator;

import bobcatsss.music.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class NoteBlockSongPlayer extends SongPlayer {
    private Block noteBlock;

    public NoteBlockSongPlayer(Song song) {
        super(song);
    }

    public Block getNoteBlock() {
        return this.noteBlock;
    }

    public void setNoteBlock(Block noteBlock) {
        this.noteBlock = noteBlock;
    }

    public void playTick(Player p, int tick) {
        if(this.noteBlock.getType() == Material.NOTE_BLOCK) {
            if(p.getWorld().getName().equals(this.noteBlock.getWorld().getName())) {
                byte playerVolume = Main.getPlayerVolume(p);
                for (Layer l : this.song.getLayerHashMap().values()) {
                    Note note = l.getNote(tick);
                    if (note != null) {
                        p.playNote(this.noteBlock.getLocation(), Instrument.getBukkitInstrument(note.getInstrument()), new org.bukkit.Note(note.getKey() - 33));
                        p.playSound(this.noteBlock.getLocation(), Instrument.getInstrument(note.getInstrument()), (float) (l.getVolume() * this.volume * playerVolume) / 1000000.0F, NotePitch.getPitch(note.getKey() - 33));
                    }
                }

            }
        }
    }
}