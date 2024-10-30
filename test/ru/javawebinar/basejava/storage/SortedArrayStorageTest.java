package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

public class SortedArrayStorageTest extends AbstractArrayStorageTest{

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Test
    public void fillDeletedElement() {
        Resume[] expected = new Resume[3];
        expected[0] = RESUME_2;
        expected[1] = RESUME_3;
        expected[2] = RESUME_3;

        SortedArrayStorage actual = (SortedArrayStorage) storage;
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

        SortedArrayStorage actual = (SortedArrayStorage) storage;
        actual.insertElement(RESUME_4, -4);
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], actual.storage[i]);
        }
    }

    @Test
    public void getIndex() {
        SortedArrayStorage actual = (SortedArrayStorage) storage;
        Assert.assertEquals(2, actual.getIndex(UUID_3));
        Assert.assertEquals(-4, actual.getIndex(UUID_4));
    }
}