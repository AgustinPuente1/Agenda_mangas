package com.agenda;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class JSONUtil {
    private static final String FILE_PATH = "src/main/resources/agenda.json";
    private static final Gson gson = new Gson();

    public static void guardarAgenda(List<Manga> agenda) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(agenda, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Manga> leerAgenda() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type tipoListaManga = new TypeToken<List<Manga>>() {}.getType();
            return gson.fromJson(reader, tipoListaManga);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
