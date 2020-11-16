package hw7;

//this implementation uses...
//chaining
//a string as a key, an arrayList as a val
// new HashMap<String, ArrayList<String>>();
import java.util.ArrayList;
import java.util.Iterator;

/** Implements a HashMap.
 * @param <K> type for keys.
 * @param <V> type for values.
 */
public class HashMapChaining<K, V> implements Map<K, V> {

    private class Entry {
        K key;
        V value;
        Entry next;
        Entry prev;

        Entry(K k, V v) {
            this.key = k;
            this.value = v;
            this.next = null;
            this.prev = null;
        }

    }

    private Entry[] data;
    private int capacity = 3;
    private int numUsed;

    /**
     * Create an empty map.
     */
    public HashMapChaining() {
        //first, create the ArrayList where all entries are stored
        this.data = (Entry[]) new HashMapChaining.Entry[capacity];
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

        if (data[hashCode] != null) {
            Entry curr = data[hashCode];
            if (k.equals(curr)) {
                return data[hashCode];
            }
            while (curr.next != null) {
                if (k.equals(curr.next)) {
                    return curr.next;
                }
                curr = curr.next;
            }
        }
        return null;
    }

    private void rehash() {
        capacity = (capacity * 2) + 1;
        Entry[] tempList = this.data;
        this.data = (Entry[]) new HashMapChaining.Entry[capacity];
        int count = 0;
        for (int i = 0; i < capacity; i++) {
            if (tempList[i] == null) {
                continue;
            }
            this.insert(tempList[i].key, tempList[i].value);
            this.numUsed--;
            Entry curr = tempList[i];
            count++;
            while (curr.next != null) {
                this.insert(curr.next.key, curr.next.value);
                this.numUsed--;
                count++;
            }
            if (count == numUsed) {
                break;
            }
        }
    }

    private Entry finalNode(Entry e) {
        Entry curr = e;
        while (curr.next != null) {
            curr = curr.next;
        }
        return curr;
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
            Entry e = new Entry(k, v);
            Entry last = finalNode(data[index]);
            last.next = e;
            e.prev = last;
        }
        if ((double) this.numUsed / (double) this.capacity > 0.5) {
            rehash();
        }
    }


    private int makeHashCode(K k) {
        int hash = Math.abs(k.hashCode());
        hash = hash % this.capacity;
        return hash;
    }

    private void removeNode(Entry e) {
        Entry prev = e.prev;
        Entry next = e.next;
        prev.next = next;
        next.prev = prev;
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
        removeNode(toRemove);
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
            if (this.data[i] != null) {
                K k = this.data[i].key;
                result.add(k);
                Entry curr = this.data[i];
                while (curr.next != null) {
                    result.add(curr.next.key);
                    curr = curr.next;
                }
            }
        }
        return result.iterator();
    }
}
