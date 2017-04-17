package modded.NoteBlockAPI;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class NBSDecoder {
    public NBSDecoder() {
    }

    public static Song parse(File decodeFile) {
        HashMap layerHashMap = new HashMap();

        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(decodeFile));
            short length = readShort(dis);
            short songHeight = readShort(dis);
            String title = readString(dis);
            String author = readString(dis);
            readString(dis);
            String description = readString(dis);
            short speed = (short)(readShort(dis) / 100);
            dis.readBoolean();
            dis.readByte();
            dis.readByte();
            readInt(dis);
            readInt(dis);
            readInt(dis);
            readInt(dis);
            readInt(dis);
            readString(dis);
            short tick = -1;

            while(true) {
                short jumpTicks = readShort(dis);
                if(jumpTicks == 0) {
                    for(int i = 0; i < songHeight; ++i) {
                        Layer l = (Layer)layerHashMap.get(Integer.valueOf(i));
                        if(l != null) {
                            l.setName(readString(dis));
                            l.setVolume(dis.readByte());
                        }
                    }

                    return new Song(speed, layerHashMap, songHeight, length, title, author, description, decodeFile);
                }

                tick += jumpTicks;
                short layer = -1;

                while(true) {
                    short jumpLayers = readShort(dis);
                    if(jumpLayers == 0) {
                        break;
                    }

                    layer += jumpLayers;
                    setNote(layer, tick, dis.readByte(), dis.readByte(), layerHashMap);
                }
            }
        } catch (FileNotFoundException var13) {
            var13.printStackTrace();
        } catch (IOException var14) {
            var14.printStackTrace();
        }

        return null;
    }

    private static void setNote(int layer, int ticks, byte instrument, byte key, HashMap<Integer, Layer> layerHashMap) {
        Layer l = (Layer)layerHashMap.get(Integer.valueOf(layer));
        if(l == null) {
            l = new Layer();
            layerHashMap.put(Integer.valueOf(layer), l);
        }

        l.setNote(ticks, new Note(instrument, key));
    }

    private static short readShort(DataInputStream dis) throws IOException {
        int byte1 = dis.readUnsignedByte();
        int byte2 = dis.readUnsignedByte();
        return (short)(byte1 + (byte2 << 8));
    }

    private static int readInt(DataInputStream dis) throws IOException {
        int byte1 = dis.readUnsignedByte();
        int byte2 = dis.readUnsignedByte();
        int byte3 = dis.readUnsignedByte();
        int byte4 = dis.readUnsignedByte();
        return byte1 + (byte2 << 8) + (byte3 << 16) + (byte4 << 24);
    }

    private static String readString(DataInputStream dis) throws IOException {
        int length = readInt(dis);

        StringBuilder sb;
        for(sb = new StringBuilder(length); length > 0; --length) {
            char c = (char)dis.readByte();
            if(c == 13) {
                c = 32;
            }

            sb.append(c);
        }

        return sb.toString();
    }
}
