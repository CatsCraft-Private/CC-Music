package modded.NoteBlockAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bobcatsss.music.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import simple.brainsynder.utils.Reflection;

@SuppressWarnings("ALL")
public abstract class SongPlayer {
    protected Song song;
    protected boolean playing = false;
    protected short tick = -1;
    protected ArrayList<String> playerList = new ArrayList<>();
    protected boolean autoDestroy = false;
    protected boolean destroyed = false;
    protected Thread playerThread;
    protected byte fadeTarget = 100;
    protected byte volume = 100;
    protected byte fadeStart;
    protected int fadeDuration;
    protected int fadeDone;
    protected FadeType fadeType;

    public FadeType getFadeType() {
        return this.fadeType;
    }

    public void setFadeType(FadeType fadeType) {
        this.fadeType = fadeType;
    }

    public byte getFadeTarget() {
        return this.fadeTarget;
    }

    public void setFadeTarget(byte fadeTarget) {
        this.fadeTarget = fadeTarget;
    }

    public byte getFadeStart() {
        return this.fadeStart;
    }

    public void setFadeStart(byte fadeStart) {
        this.fadeStart = fadeStart;
    }

    public int getFadeDuration() {
        return this.fadeDuration;
    }

    public void setFadeDuration(int fadeDuration) {
        this.fadeDuration = fadeDuration;
    }

    public int getFadeDone() {
        return this.fadeDone;
    }

    public void setFadeDone(int fadeDone) {
        this.fadeDone = fadeDone;
    }

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

    private String formatHHMMSS(long secondsCount) {
        int seconds = (int) (secondsCount % 60);
        secondsCount -= seconds;
        long minutesCount = secondsCount / 60;
        long minutes = minutesCount % 60;
        minutesCount -= minutes;
        long hoursCount = minutesCount / 60;
        StringBuilder builder = new StringBuilder();
        if (hoursCount > 0)
            builder.append(hoursCount + ":");
        if (minutes > 0)
            builder.append(minutes + ":");
        builder.append((seconds < 10) ? "0" + seconds : seconds);
        return builder.toString();
    }

    protected void createThread() {
        this.playerThread = new Thread(new Runnable() {
            public void run() {
                while (!SongPlayer.this.destroyed) {
                    long startTime = System.currentTimeMillis();
                    SongPlayer var3 = SongPlayer.this;
                    if (SongPlayer.this.playing) {
                        SongPlayer.this.calculateFade();
                        ++SongPlayer.this.tick;
                        int time = SongPlayer.this.tick;
                        int seconds = (time / 20);
                        int hours = seconds / 3600;
                        int minutes = (seconds % 3600) / 60;
                        for (String s : SongPlayer.this.playerList) {
                            Player p = Bukkit.getPlayerExact(s);
                            if (p != null) {
                                Reflection.getActionMessage().sendMessage(p, "§8[§aMusic§8] §3Time Elasped: §7" + formatHHMMSS(seconds));
                            }
                        }
                        if (SongPlayer.this.tick > SongPlayer.this.song.getLength()) {
                            SongPlayer.this.playing = false;
                            SongPlayer.this.tick = -1;
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
                                SongPlayer.this.playTick(p, SongPlayer.this.tick);
                            }
                        }
                    }


                    long duration = System.currentTimeMillis() - startTime;
                    int delayMillis = SongPlayer.this.song.getDelay() * 50;
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

    public List<String> getPlayerList() {
        return Collections.unmodifiableList(this.playerList);
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

    public void setAutoDestroy(boolean value) {
        this.autoDestroy = value;
    }

    public boolean getAutoDestroy() {
        return this.autoDestroy;
    }

    public abstract void playTick(Player var1, int var2);

    public void destroy() {
        this.destroyed = true;
        this.playing = false;
        this.setTick((short) -1);
    }

    public boolean isPlaying() {
        return this.playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public short getTick() {
        return this.tick;
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

    public byte getVolume() {
        return this.volume;
    }

    public void setVolume(byte volume) {
        this.volume = volume;
    }

    public Song getSong() {
        return this.song;
    }
}
