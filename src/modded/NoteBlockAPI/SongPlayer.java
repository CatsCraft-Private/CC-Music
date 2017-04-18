package modded.NoteBlockAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import bobcatsss.music.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import simple.brainsynder.utils.Reflection;

public abstract class SongPlayer {
    protected Song song;
    protected boolean playing = false;
    protected short tick = -1;
    //protected ArrayList<String> playerList = new ArrayList<String>();
    protected HashMap<String, Boolean> playerList = new HashMap<String, Boolean>();
    protected boolean autoDestroy = true;
    protected boolean destroyed = false;
    protected Thread playerThread;
    protected byte fadeTarget = 100;
    protected byte volume = 100;
    protected byte fadeStart = volume;
    protected int fadeDuration = 60;
    protected int fadeDone = 0;
    protected FadeType fadeType = FadeType.FADE_LINEAR;

    public SongPlayer(Song song) {
        this.song = song;
        createThread();
    }

    public FadeType getFadeType() {
        return fadeType;
    }

    public void setFadeType(FadeType fadeType) {
        this.fadeType = fadeType;
    }

    public byte getFadeTarget() {
        return fadeTarget;
    }

    public void setFadeTarget(byte fadeTarget) {
        this.fadeTarget = fadeTarget;
    }

    public byte getFadeStart() {
        return fadeStart;
    }

    public void setFadeStart(byte fadeStart) {
        this.fadeStart = fadeStart;
    }

    public int getFadeDuration() {
        return fadeDuration;
    }

    public void setFadeDuration(int fadeDuration) {
        this.fadeDuration = fadeDuration;
    }

    public int getFadeDone() {
        return fadeDone;
    }

    public void setFadeDone(int fadeDone) {
        this.fadeDone = fadeDone;
    }

    protected void calculateFade() {
        if (fadeDone == fadeDuration) {
            return; // no fade today
        }
        double targetVolume = Interpolator.interpLinear(new double[]{0, fadeStart, fadeDuration, fadeTarget}, fadeDone);
        setVolume((byte) targetVolume);
        fadeDone++;
    }

    private String formatHHMMSS(long secondsCount) {
        int seconds = (int) (secondsCount % 60);
        secondsCount -= seconds;
        long minutesCount = secondsCount / 60;
        long minutes = minutesCount % 60;
        minutesCount -= minutes;
        long hoursCount = minutesCount / 60;
        StringBuilder builder = new StringBuilder();
        if (hoursCount > 0)
            builder.append(hoursCount).append(":");
        if (minutes > 0)
            builder.append(minutes).append(":");
        builder.append((seconds < 10) ? "0" + seconds : seconds);
        return builder.toString();
    }


    protected void createThread() {
        playerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!destroyed) {
                    long startTime = System.currentTimeMillis();
                    try {
                        synchronized (Bukkit.getScheduler()) {
                            if (destroyed) {
                                break;
                            }
                            Bukkit.getScheduler().callSyncMethod(Main.plugin, new Callable<Boolean>() {
                                public Boolean call() {
                                    if (playing) {
                                        calculateFade();
                                        tick++;
                                        int time = tick;
                                        int seconds = (time / 20);
                                        for (String s : playerList.keySet()) {
                                            Player p = Bukkit.getPlayerExact(s);
                                            if (p != null) {
                                                Reflection.getActionMessage().sendMessage(p, "§8[§aMusic§8] §3Time Elasped: §7" + formatHHMMSS(seconds));
                                            }
                                        }
                                        if (tick > song.getLength()) {
                                            playing = false;
                                            tick = -1;
                                            for (String s : playerList.keySet()) {
                                                Player p = Bukkit.getPlayerExact(s);
                                                if (p != null) {
                                                    Reflection.getActionMessage().sendMessage(p, "§8[§aMusic§8] §3Song has ended");
                                                }
                                            }
                                            if (autoDestroy) {
                                                destroy();
                                                return false;
                                            }
                                        }
                                        for (String s : playerList.keySet()) {
                                            Player p = Bukkit.getPlayerExact(s);
                                            if (p == null) {
                                                continue;
                                            }
                                            playTick(p, tick);
                                        }
                                    }
                                    return true;
                                }
                            });
                        }
                    } catch (java.lang.IllegalStateException e) {
                        e.printStackTrace();
                    }

                    if (destroyed) {
                        break;
                    }

                    long duration = System.currentTimeMillis() - startTime;
                    float delayMillis = song.getDelay() * 50;
                    if (duration < delayMillis) {
                        try {
                            Thread.sleep((long) (delayMillis - duration));
                        } catch (InterruptedException ignored) {}
                    }
                }
            }
        });
        playerThread.setPriority(Thread.MAX_PRIORITY);
        playerThread.start();
    }

    public List<String> getPlayerList() {
        List<String> list = new ArrayList<>();
        list.addAll(playerList.keySet());
        return Collections.unmodifiableList(list);
    }

    public void addPlayer(Player p) {
        synchronized (this) {
            if (!playerList.containsKey(p.getName())) {
                playerList.put(p.getName(), false);
                ArrayList<SongPlayer> songs = Main.plugin.playingSongs
                        .get(p.getName());
                if (songs == null) {
                    songs = new ArrayList<>();
                }
                songs.add(this);
                Main.plugin.playingSongs.put(p.getName(), songs);
            }
        }
    }


    public boolean getAutoDestroy() {
        synchronized (this) {
            return autoDestroy;
        }
    }

    public void setAutoDestroy(boolean value) {
        synchronized (this) {
            autoDestroy = value;
        }
    }

    public abstract void playTick(Player p, int tick);

    public void destroy() {
        synchronized (this) {
            destroyed = true;
            playing = false;
            setTick((short) -1);
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public short getTick() {
        return tick;
    }

    public void setTick(short tick) {
        this.tick = tick;
    }

    public void removePlayer(Player p) {
        synchronized (this) {
            playerList.remove(p.getName());
            if (Main.plugin.playingSongs.get(p.getName()) == null) {
                return;
            }
            ArrayList<SongPlayer> songs = new ArrayList<>(
                    Main.plugin.playingSongs.get(p.getName()));
            songs.remove(this);
            Main.plugin.playingSongs.put(p.getName(), songs);
            if (playerList.isEmpty() && autoDestroy) {
                destroy();
            }
        }
    }

    public byte getVolume() {
        return volume;
    }

    public void setVolume(byte volume) {
        this.volume = volume;
    }

    public Song getSong() {
        return song;
    }
}
