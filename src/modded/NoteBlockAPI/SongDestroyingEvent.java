package modded.NoteBlockAPI;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SongDestroyingEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private SongPlayer song;
    private boolean cancelled = false;

    public SongDestroyingEvent(SongPlayer song) {
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

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean arg0) {
        this.cancelled = arg0;
    }
}
