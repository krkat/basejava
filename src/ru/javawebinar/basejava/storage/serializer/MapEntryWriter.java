package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.ContactType;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class MapEntryWriter implements CustomConsumer<Map.Entry<ContactType, String>, DataOutputStream>{
    @Override
    public void write(Map.Entry<ContactType, String> entry, DataOutputStream dos) throws IOException {
        dos.writeUTF(entry.getKey().name());
        dos.writeUTF(entry.getValue());
    }
}
