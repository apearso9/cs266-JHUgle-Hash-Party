package hw7;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/** A search engine!
 */
public final class JHUgle {

    /** Make Checkstyle happy.
     */
    private JHUgle() {}

    /** The main method.
     * @param args the name of the text file we need.
     * @throws FileNotFoundException if file not found.
     */
    public static void main(String[] args) throws FileNotFoundException {

        if (args.length == 0) {
            System.err.println("Need the file name as a command line arg.");
        }

        System.out.println("Index Created");

        HashMap<String, ArrayList<String>> m = new HashMap<>();

        //now read in the file.
        FileReader file = new FileReader(args[0]);
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            String url = scan.nextLine();
            String words = scan.nextLine();
            String[] keyWords = words.split("\\s+");
            for (String myWord : keyWords) {
                if (!m.has(myWord)) {
                    ArrayList<String> firstVal = new ArrayList<>();
                    firstVal.add(url);
                    m.insert(myWord, firstVal);
                } else {
                    ArrayList<String> values = m.get(myWord);
                    values.add(url);
                    m.put(myWord, values);
                }
            }
        }

        System.out.print("> ");
        Scanner s = new Scanner(System.in);
        Stack<String> stack = new Stack<String>();
        String curr = "";
        while (s.hasNextLine()) {
            if (!s.hasNextLine()) {
                break;
            } else {
                curr = s.next();
                if ("!".equals(curr)) {
                    break;
                }
            }
            if ("?".equals(curr)) {
                if (stack.size() > 0) {
                    d(stack, m);
                }
            } else {
                stack.push(curr);
            }
            System.out.print("> ");
        }
    }


    private static void printArrayList(ArrayList<String> result) {
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i) != null) {
                System.out.println(result.get(i));
            }
        }
    }

    private static void orCase(Stack<String> s,
                               HashMap<String, ArrayList<String>> m) {
        String first = s.pop();
        String second = s.pop();
        ArrayList<String> l1 = new ArrayList<>(0);
        if (m.has(first)) {
            l1 = m.get(first);
        }
        ArrayList<String> l2 = new ArrayList<>(0);
        if (m.has(second)) {
            l2 = m.get(second);
        }
        printArrayList(l1);
        for (int i = 0; i < l2.size(); i++) {
            if (!l1.contains(l2.get(i))) {
                System.out.println(l2.get(i));
            }
        }
        s.push(first);
        s.push(second);
    }

    private static void andCase(Stack<String> s,
                                HashMap<String, ArrayList<String>> m) {
        String first = s.pop();
        String second = s.pop();
        ArrayList<String> l1 = new ArrayList<>(0);
        if (m.has(first)) {
            l1 = m.get(first);
        }
        ArrayList<String> l2 = new ArrayList<>(0);
        if (m.has(second)) {
            l2 = m.get(second);
        }
        for (int i = 0; i < l1.size(); i++) {
            if (l2.contains(l1.get(i))) {
                System.out.println(l1.get(i));
            }
        }
        s.push(first);
        s.push(second);
    }

    private static void d(Stack<String> s,
                          HashMap<String, ArrayList<String>> m) {
        String top = s.pop();
        switch (top) {
            case ("&&"):
                if (s.size() > 1) {
                    andCase(s, m);
                }
                break;
            case ("||"):
                if (s.size() > 1) {
                    orCase(s, m);
                }
                break;
            default:
                if (m.has(top)) {
                    ArrayList<String> list = m.get(top);
                    printArrayList(list);
                }
                s.push(top);
        }
    }
}