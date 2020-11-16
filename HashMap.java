package hw7;

//this implementation uses...
//linear probing
//a string as a key, an arrayList as a val
// new HashMap<String, ArrayList<String>>();
import java.util.ArrayList;
import java.util.Iterator;

/** Implements a HashMap.
 * @param <K> type for keys.
 * @param <V> type for values.
 */
public class HashMap<K, V> implements Map<K, V> {

    private class Entry {
        K key;
        V value;

        Entry(K k, V v) {
            this.key = k;
            this.value = v;
        }

    }

    private Entry[] data;
    private int capacity = 3;
    private int numUsed;

    /**
     * Create an empty map.
     */
    public HashMap() {
        //first, create the ArrayList where all entries are stored
        this.data = (Entry[]) new HashMap.Entry[capacity];
        this.numUsed = 0;
    }

    //returns the entry if K exists, null if it doesn't
    private Entry find(K k) {
        if (k == null) {
            throw new IllegalArgumentException();
        }

        if (numUsed == 0) {
            return null;
        }

        int hashCode = this.makeHashCode(k);

        for (int i = hashCode; i < this.data.length; i++) {
            if (this.data[i] != null) {
                if (k.equals(this.data[i].key)) {
                    return this.data[i];
                }
            }
            else {
                return null;
            }
        }
        for (int i = 0; i < hashCode; i++) {
            if (this.data[i] != null) {
                if (this.data[i].key.equals(k)) {
                    return this.data[i];
                }
            } else {
                return null;
            }
        }
        return null;
    }

    private void rehash() {
        capacity = (capacity * 2) + 1;
        Entry[] tempList = this.data;
        this.data = (Entry[]) new HashMap.Entry[capacity];
        int count = 0;
        for (int i = 0; i < capacity; i++) {
            if (tempList[i] == null) {
                continue;
            }
            this.insert(tempList[i].key, tempList[i].value);
            this.numUsed--;
            count++;
            if (count == numUsed) {
                break;
            }
        }
    }

    /**
     * Insert a new key/value pair.
     *
     * @param k  The key.
     * @param v The value to be associated with k.
     * @throws IllegalArgumentException If k is null or already mapped.
     */
    @Override
    public void insert(K k, V v) throws IllegalArgumentException {
        if (k == null) {
            throw new IllegalArgumentException();
        }

        int index = makeHashCode(k);
        if (this.data[index] == null) {
            Entry e = new Entry(k, v);
            this.data[index] = e;
            numUsed++;
        } else if (this.data[index].key == null) {
            this.data[index].key = k;
            this.data[index].value = v;
            numUsed++;
        } else if (this.data[index].key == k) {
            throw new IllegalArgumentException();
        } else {
            linear(index, k, v);
        }
        if ((double) this.numUsed / (double) this.capacity > 0.5) {
            rehash();
        }
    }

    private void linear(int c, K k, V v) throws IllegalArgumentException {
        boolean inserted = false;
        for (int i = c + 1; i < this.data.length; i++) {
            if (this.data[i] == null) {
                Entry e = new Entry(k, v);
                this.data[i] = e;
                inserted = true;
                numUsed++;
                break;
            } else if (this.data[i].key == null) {
                this.data[i].key = k;
                this.data[i].value = v;
                inserted = true;
                numUsed++;
            }
            else if (k.equals(this.data[i].key)) {
                throw new IllegalArgumentException();
            }
        }
        if (!inserted) {
            for (int i = 0; i < c; i++) {
                if (this.data[i] == null) {
                    Entry e = new Entry(k, v);
                    this.data[i] = e;
                    numUsed++;
                    break;
                } else if (this.data[i].key == null) {
                    this.data[i].key = k;
                    this.data[i].value = v;
                    numUsed++;
                }
                else if (k.equals(this.data[i].key)) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    private int makeHashCode(K k) {
        int hash = Math.abs(k.hashCode());
        hash = hash % this.capacity;
        return hash;
    }

    private int makeUpdateHashCode(K k, int size) {
        int hash = Math.abs(k.hashCode());
        hash = hash % size;
        return hash;
    }

    /**
     * Remove an existing key/value pair.
     *
     * @param k The key.
     * @return The value that was associated with k.
     * @throws IllegalArgumentException If k is null or not mapped.
     */
    @Override
    public V remove(K k) throws IllegalArgumentException {
        if (k == null || !this.has(k)) {
            throw new IllegalArgumentException();
        }
        Entry toRemove = this.find(k);
        V toReturn = toRemove.value;
        toRemove.value = null;
        toRemove.key = null;
        numUsed--;
        return toReturn;
    }

    /**
     * Update the value associated with a key.
     *
     * @param k  The key.
     * @param v The value to be associated with k.
     * @throws IllegalArgumentException If k is null or not mapped.
     */
    @Override
    public void put(K k, V v) throws IllegalArgumentException {
        if (k == null) {
            throw new IllegalArgumentException();
        }
        Entry toChange = find(k);
        if (toChange == null) {
            throw new IllegalArgumentException();
        }
        toChange.value = v;
    }

    /**
     * Get the first value associated with a key.
     *
     * @param k The key.
     * @return The value associated with k.
     * @throws IllegalArgumentException If k is null or not mapped.
     */
    @Override
    public V get(K k) throws IllegalArgumentException {
        if (k == null) {
            throw new IllegalArgumentException();
        }
        Entry toGet = find(k);
        if (toGet == null) {
            throw new IllegalArgumentException();
        }
        return toGet.value;
    }

    /**
     * Check existence of a key.
     *
     * @param k The key.
     * @return True if k is mapped, false otherwise.
     */
    @Override
    public boolean has(K k) {
        Entry toGet = find(k);
        return (toGet != null);
    }

    /**
     * Number of mappings.
     * @return Number of key/value pairs in the map.
     */
    @Override
    public int size() {
        return this.numUsed;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     * @return an Iterator.
     */
    @Override
    public Iterator iterator() {
        ArrayList<K> result = new ArrayList<K>();
        for (int i = 0; i < this.data.length; i++) {
            if (this.data[i] != null && this.data[i].key != null) {
                K k = this.data[i].key;
                result.add(k);
            }
        }
        return result.iterator();
    }
}