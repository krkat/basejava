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
        expected[0] = RESUME_3;
        expected[1] = RESUME_2;
        expected[2] = RESUME_3;

        ArrayStorage actual = (ArrayStorage) storage;
        actual.fillDeletedElement(0);
        Assert.assertArrayEquals(expected, actual.getAll());
    }

    @Test
    public void insertElement() {
        Resume[] expected = new Resume[4];
        expected[0] = RESUME_1;
        expected[1] = RESUME_2;
        expected[2] = RESUME_3;
        expected[3] = RESUME_4;

        ArrayStorage actual = (ArrayStorage) storage;
        actual.insertElement(RESUME_4, 0);
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], actual.storage[i]);
        }
    }

    @Test
    public void getIndex() {
        ArrayStorage actual = (ArrayStorage) storage;
        Assert.assertEquals(0, actual.getIndex(UUID_1));
        Assert.assertEquals(-1, actual.getIndex(DUMMY));
    }
}