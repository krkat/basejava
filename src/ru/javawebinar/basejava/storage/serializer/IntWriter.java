package ru.javawebinar.basejava.storage.serializer;

import java.io.DataOutputStream;
import java.io.IOException;

public class IntWriter implements CustomConsumer<Integer, DataOutputStream> {
    @Override
    public void write(Integer integer, DataOutputStream dos) throws IOException {
        dos.writeInt(integer.intValue());
    }
}
