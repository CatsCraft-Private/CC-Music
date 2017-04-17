package modded.NoteBlockAPI;

import java.util.ArrayList;
import bobcatsss.music.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import simple.brainsynder.utils.Reflection;

public abstract class SongPlayer {
    protected Song song;
    protected boolean playing = false;
    protected short tick = -1;
    protected ArrayList<String> playerList = new ArrayList<>();
    protected boolean autoDestroy = true;
    protected boolean destroyed = false;
    protected Thread playerThread;
    protected byte fadeTarget = 100;
    protected byte volume = 100;
    protected byte fadeStart;
    protected int fadeDuration;
    protected int fadeDone;
    protected FadeType fadeType;

    public SongPlayer(Song song) {
        this.fadeStart = this.volume;
        this.fadeDuration = 60;
        this.fadeDone = 0;
        this.fadeType = FadeType.FADE_LINEAR;
        this.song = song;
        this.createThread();
    }

    protected void calculateFade() {
        if (this.fadeDone != this.fadeDuration) {
            double targetVolume = Interpolator.interpolateLinear(new double[]{0.0D, (double) this.fadeStart, (double) this.fadeDuration, (double) this.fadeTarget}, (double) this.fadeDone);
            this.setVolume((byte) ((int) targetVolume));
            ++this.fadeDone;
        }
    }

    public Song getSong () {
        return song;
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
        this.playerThread = new Thread(new Runnable() {
            public void run() {
                while (!destroyed) {
                    long startTime = System.currentTimeMillis();
                    if (playing) {
                        calculateFade();
                        ++tick;
                        int time = tick;
                        int seconds = (time / 20);
                        for (String s : playerList) {
                            Player p = Bukkit.getPlayerExact(s);
                            if (p != null) {
                                Reflection.getActionMessage().sendMessage(p, "§8[§aMusic§8] §3Time Elasped: §7" + formatHHMMSS(seconds));
                            }
                        }
                        if (tick > song.getLength()) {
                            playing = false;
                            tick = -1;
                            for (String s : SongPlayer.this.playerList) {
                                Player p = Bukkit.getPlayerExact(s);
                                if (p != null) {
                                    Reflection.getActionMessage().sendMessage(p, "§8[§aMusic§8] §3Song has ended");
                                }
                            }
                            if (SongPlayer.this.autoDestroy) {
                                SongPlayer.this.destroy();
                                return;
                            }
                        }

                        for (String s : SongPlayer.this.playerList) {
                            Player p = Bukkit.getPlayerExact(s);
                            if (p != null) {
                                SongPlayer.this.playTick(p, tick);
                            }
                        }
                    }


                    long duration = System.currentTimeMillis() - startTime;
                    int delayMillis = song.getDelay() * 50;
                    if (duration < (long) delayMillis) {
                        try {
                            Thread.sleep((long) delayMillis - duration);
                        } catch (InterruptedException ignored) {
                        }
                    }
                }

            }
        });
        this.playerThread.setPriority(10);
        this.playerThread.start();
    }

    public void addPlayer(Player p) {
        if (!this.playerList.contains(p.getName())) {
            this.playerList.add(p.getName());
            ArrayList<SongPlayer> songs = Main.plugin.playingSongs.get(p.getName());
            if (songs == null) {
                songs = new ArrayList<>();
            }

            songs.add(this);
            Main.plugin.playingSongs.put(p.getName(), songs);
        }
    }

    public abstract void playTick(Player var1, int var2);

    public void destroy() {
        this.destroyed = true;
        this.playing = false;
        this.setTick((short) -1);
    }

    public void setTick(short tick) {
        this.tick = tick;
    }

    public void removePlayer(Player p) {
        this.playerList.remove(p.getName());
        if (Main.plugin.playingSongs.get(p.getName()) != null) {
            ArrayList<SongPlayer> songs = new ArrayList<>(Main.plugin.playingSongs.get(p.getName()));
            songs.remove(this);
            Main.plugin.playingSongs.put(p.getName(), songs);
            if (this.playerList.isEmpty() && this.autoDestroy) {
                this.destroy();
            }
        }
    }

    public void setVolume(byte volume) {
        this.volume = volume;
    }
}
