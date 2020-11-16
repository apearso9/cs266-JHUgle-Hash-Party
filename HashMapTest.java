package hw7.tests;

import hw7.HashMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertEquals;


/**
 * Tests for hash map implementation.
 */
public class HashMapTest extends MapTest {

    @Override
    protected HashMap<String, ArrayList<String>> createMap() {
        return new HashMap<>();
    }

    @Test
    public void updateAValue() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("hello");
        m.insert("hi", list);
        assertEquals(list, m.get("hi"));
        list.add("hello again");
        m.put("hi", list);
        assertEquals(list, m.get("hi"));
    }

    @Test
    public void iterates() {
        Iterator it = m.iterator();
        while (it.hasNext()) {
            String curr = (String) it.next();
            assertTrue(m.has(curr));
        }
    }

    @Test
    public void rehash() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("hello");
        assertEquals(m.size(), 0);
        for (int i = 0; i < 2; i++) {
            m.insert("" + i, list);
        }
        assertEquals(m.size(), 2);
    }

}