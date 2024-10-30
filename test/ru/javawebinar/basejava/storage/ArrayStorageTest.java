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
        Resume[] expected = {RESUME_3, RESUME_2, RESUME_3};
        ArrayStorage actual = (ArrayStorage) storage;
        actual.fillDeletedElement(0);
        Assert.assertArrayEquals(expected, actual.getAll());
    }

    @Test
    public void insertElement() {
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3, RESUME_4};
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