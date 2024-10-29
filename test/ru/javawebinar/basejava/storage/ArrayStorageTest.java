package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

public class ArrayStorageTest extends AbstractArrayStorageTest{

    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Test
    public void fillDeletedElement() {
        Resume[] expected = new Resume[3];
        expected[0] = new Resume(UUID_3);
        expected[1] = new Resume(UUID_2);
        expected[2] = new Resume(UUID_3);

        ArrayStorage actual = (ArrayStorage) storage;
        actual.fillDeletedElement(0);
        Assert.assertArrayEquals(expected, actual.getAll());
    }

    @Test
    public void insertElement() {
        Resume resume = new Resume("uuid");
        Resume[] expected = new Resume[4];
        expected[0] = new Resume(UUID_1);
        expected[1] = new Resume(UUID_2);
        expected[2] = new Resume(UUID_3);
        expected[3] = resume;

        ArrayStorage actual = (ArrayStorage) storage;
        actual.insertElement(resume, 0);
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], actual.storage[i]);
        }
    }

    @Test
    public void getIndex() {
        ArrayStorage actual = (ArrayStorage) storage;
        Assert.assertEquals(0, actual.getIndex(UUID_1));
        Assert.assertEquals(-1, actual.getIndex("dummy"));
    }
}