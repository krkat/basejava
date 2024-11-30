package ru.javawebinar.basejava.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        ObjectFileStorageTest.class,
        ObjectPathStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class
})
public class SuiteTestClass {
}
