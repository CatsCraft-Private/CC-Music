package modded.NoteBlockAPI;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SongStoppedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private SongPlayer song;

    public SongStoppedEvent(SongPlayer song) {
        this.song = song;
    }

    public SongPlayer getSongPlayer() {
        return this.song;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
