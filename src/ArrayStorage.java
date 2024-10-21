import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        if (size != 0) {
            Arrays.fill(storage, 0, size, null);
            size = 0;
        }
    }

    void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Ошибка! Хранилище переполнено.");
        }
        if (isPresent(r.uuid) >= 0) {
            System.out.println("Ошибка! Резюме с таким uuid уже есть.");
            return;
        }
        storage[size] = r;
        size++;
    }

    void update(String uuid, Resume r) {
        int indexForUpdate = isPresent(uuid);
        if (indexForUpdate == -1) {
            System.out.println("Ошибка! Резюме с таким uuid нет.");
            return;
        }
        storage[indexForUpdate] = r;
    }

    Resume get(String uuid) {
        if (isPresent(uuid) == -1) {
            System.out.println("Ошибка! Резюме с таким uuid нет.");
        } else {
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].uuid)) {
                    return storage[i];
                }
            }
        }
        return null;
    }

    void delete(String uuid) {
        if (isPresent(uuid) == -1) {
            System.out.println("Ошибка! Резюме с таким uuid нет.");
        } else {
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].uuid)) {
                    storage[i] = storage[size - 1];
                    storage[size - 1] = null;
                    size--;
                    break;
                }
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }

    private int isPresent(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return i;
            }
        }
        return -1;
    }
}
