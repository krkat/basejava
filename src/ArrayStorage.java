import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int countResume;
    Resume[] storage = new Resume[10000];

    void clear() {
        if (countResume != 0) {
            Arrays.fill(storage, 0, countResume, null);
            countResume = 0;
        }
    }

    void save(Resume r) {
        boolean saved = false;
        for (int i = 0; i < countResume; i++) {
            if (r.uuid.equals(storage[i].uuid)) {
                storage[i] = r;
                saved = true;
            }
        }
        if (!saved) {
            storage[countResume] = r;
            countResume++;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (uuid.equals(storage[i].uuid)) {
                System.arraycopy(storage, i + 1, storage, i, countResume - i - 1);
                storage[countResume - 1] = null;
                countResume--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    int size() {
        return countResume;
    }
}
