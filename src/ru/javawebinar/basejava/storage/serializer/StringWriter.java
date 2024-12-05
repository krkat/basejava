package ru.javawebinar.basejava.storage.serializer;

import java.io.DataOutputStream;
import java.io.IOException;

public class StringWriter implements CustomConsumer<String, DataOutputStream> {
    @Override
    public void write(String s, DataOutputStream dos) throws IOException {
        dos.writeUTF((String) s);
    }
}