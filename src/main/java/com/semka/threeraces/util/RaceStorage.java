package com.semka.threeraces.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ConcurrentHashMap;

import java.util.*;

public class RaceStorage {
    private final File dataFile;
    private final Path dataFilePath;
    private final Map<UUID,RaceEnum> map = new ConcurrentHashMap<>();
    public RaceStorage(File pluginFolder) {
        this.dataFilePath = Path.of(pluginFolder.getAbsolutePath(),"playerdata.dat");
        this.dataFile = dataFilePath.toFile();
    }
    public void setRace(UUID uuid, RaceEnum race) {
        map.put(uuid, race == null? RaceEnum.NONE : race);
    }
    public RaceEnum getRace(UUID uuid) {
        return map.getOrDefault(uuid, RaceEnum.NONE);
    }
    public void save() {
        try {
            // Исправленное создание директорий
            if (dataFilePath.getParent() != null) {
                Files.createDirectories(dataFilePath.getParent());
            }

            // Пишем сначала во временный файл, чтобы не повредить основной при сбое
            Path tempFile = dataFilePath.resolveSibling("_playerdata.dat.tmp");

            try (DataOutputStream dos = new DataOutputStream(
                    new BufferedOutputStream(Files.newOutputStream(tempFile)))) {
                
                dos.writeInt(map.size());
                for (Map.Entry<UUID, RaceEnum> entry : map.entrySet()) {
                    UUID uuid = entry.getKey();
                    dos.writeLong(uuid.getMostSignificantBits());
                    dos.writeLong(uuid.getLeastSignificantBits());
                    dos.writeByte(entry.getValue().getId());
                }
            }

            Files.move(tempFile, dataFilePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean load() {
        if (Files.notExists(dataFilePath)) return false;
        try (FileInputStream fis = new FileInputStream(dataFile);
             BufferedInputStream bis = new BufferedInputStream(fis);
             DataInputStream dis = new DataInputStream(bis)) {

            int size = dis.readInt();
            map.clear();
            for (int i = 0; i < size; i++) {
                long most = dis.readLong();
                long least = dis.readLong();
                UUID uuid = new UUID(most, least);
                
                int id = dis.readByte();
                map.put(uuid, RaceEnum.fromId(id));
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public Set<UUID> _keySet() {
        return map.keySet();
    }
}
