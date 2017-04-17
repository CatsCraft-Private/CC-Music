package modded.NoteBlockAPI;

import java.util.HashMap;

public class Layer {
    private HashMap<Integer, Note> hashMap = new HashMap();
    private byte volume = 100;
    private String name = "";

    public Layer() {
    }

    public HashMap<Integer, Note> getHashMap() {
        return this.hashMap;
    }

    public void setHashMap(HashMap<Integer, Note> hashMap) {
        this.hashMap = hashMap;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Note getNote(int tick) {
        return (Note)this.hashMap.get(Integer.valueOf(tick));
    }

    public void setNote(int tick, Note note) {
        this.hashMap.put(Integer.valueOf(tick), note);
    }

    public byte getVolume() {
        return this.volume;
    }

    public void setVolume(byte volume) {
        this.volume = volume;
    }
}