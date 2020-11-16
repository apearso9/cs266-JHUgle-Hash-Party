package hw7.tests;

import hw7.HashMap;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Testing implementations of PriorityQueues.
 */
public abstract class MapTest {

    protected HashMap<String, ArrayList<String>> m;

    /**
     * @return map
     */
    protected abstract HashMap<String, ArrayList<String>> createMap();

    @Before
    public void setupMap() {
        m = this.createMap();
    }


    @Test
    public void newMapEmpty() {
        assertEquals(0, m.size());
        assertFalse(m.has("hello"));

        int count = 0;
        for (String k : m) {
            count++;
        }
        assertEquals(count, 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void insertNullKey() {
        ArrayList<String> n = new ArrayList<String>();
        n.add("hello");
        m.insert(null, n);
    }

    @Test
    public void testHas() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("0");
        m.insert("A", list);
        assertTrue(m.has("A"));
    }

    @Test
    public void insertSingleNode() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Yup");
        m.insert("First", list);
        assertTrue(m.has("First"));
        assertEquals(m.size(), 1);
        ArrayList<String> answer = m.get("First");
        assertEquals(answer.indexOf("Yup"), 0);
    }

    @Test
    public void multipleInserts() {
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("1");
        ArrayList<String> list0 = new ArrayList<String>();
        list1.add("0");
        ArrayList<String> list2 = new ArrayList<String>();
        list1.add("2");
        m.insert("1", list1);
        m.insert("0", list0);
        m.insert("2", list2);
        assertEquals(m.size(), 3);
        assertTrue(m.has("0"));
        assertTrue(m.has("1"));
        assertTrue(m.has("2"));
        assertFalse(m.has("3"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertDuplicateKey() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("1");
        m.insert("hello", list);
        m.insert("hello", list);
        assertEquals(m.size(), 1);
    }

    @Test
    public void singleRemove() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("1");
        m.insert("hello", list);
        assertTrue(m.has("hello"));
        assertEquals(m.size(), 1);
        m.remove("hello");
        assertFalse(m.has("hello"));
        assertEquals(m.size(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeNotKey() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("1");
        m.insert("hello", list);
        m.remove("hell0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeNull() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("1");
        m.insert("hello", list);
        m.remove(null);
    }

    @Test
    public void insertThenGetKey() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("1");
        m.insert("one", list);
        assertEquals(list, m.get("one"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void insertExistingKey() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("1");
        m.insert("hi", list);
        m.insert("hi", list);
    }

    @Test (expected = IllegalArgumentException.class)
    public void removeNullKey() {
        m.remove("who");
    }

    @Test (expected = IllegalArgumentException.class)
    public void insertThenRemove() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("1");
        m.insert("1", list);
        assertEquals(list, m.get("1"));
        assertTrue(m.has("1"));
        m.remove("1");
        assertFalse(m.has("1"));
        m.get("1");
    }

    @Test
    public void putChanges() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("1");
        m.insert("1", list);
        assertEquals(list, m.get("1"));
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("1");
        m.put("1", list2);
        assertEquals(list2, m.get("1"));
    }

    @Test
    public void sizeChanges() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("1");
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("1");
        assertEquals(0, m.size());
        m.insert("1", list);
        m.insert("2", list2);
        assertEquals(2, m.size());
    }

}