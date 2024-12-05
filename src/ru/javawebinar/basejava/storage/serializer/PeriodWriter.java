package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.Period;

import java.io.DataOutputStream;
import java.io.IOException;

public class PeriodWriter implements CustomConsumer<Period, DataOutputStream> {
    @Override
    public void write(Period period, DataOutputStream dos) throws IOException {
        dos.writeUTF(period.getStartDate().toString());
        dos.writeUTF(period.getEndDate().toString());
        dos.writeUTF(period.getPosition());
        dos.writeUTF(period.getDescription());
    }
}
