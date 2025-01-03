package ru.javawebinar.basejava.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.javawebinar.basejava.model.Section;

import java.io.Reader;
import java.io.Writer;

public class JsonParser {
    private static Gson GSOM = new GsonBuilder()
            .registerTypeAdapter(Section.class, new JsonSectionAdapter())
            .create();

    public static <T> T read(Reader reader, Class<T> classType) {
        return GSOM.fromJson(reader, classType);
    }

    public static <T> void write(T object, Writer writer) {
        GSOM.toJson(object, writer);
    }

    public static <T> T read(String content, Class<T> clazz) {
        return GSOM.fromJson(content, clazz);
    }

    public static <T> String write(T object) {
        return GSOM.toJson(object);
    }

    public static <T> String write(T object, Class<T> clazz) {
        return GSOM.toJson(object, clazz);
    }
}
