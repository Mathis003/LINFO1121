package strings;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * Implement the class WordCounter that counts the number of occurrences
 * of each word in a given piece of text.
 * Feel free to use existing java classes.
 */
public class WordCounter implements Iterable<String>
{
    // BEGIN : STUDENT
    TreeMap<String, Integer> wordCounter;

    public WordCounter()
    {
        this.wordCounter = new TreeMap<>();
    }

    /**
     * Add the word so that the counter of the word is increased by 1
     */
    public void addWord(String word)
    {
        int currentWords = getCount(word);
        this.wordCounter.put(word, currentWords + 1);
    }

    /**
     * Return the number of times the word has been added so far
     */
    public int getCount(String word) { return this.wordCounter.getOrDefault(word, 0); }

    // iterate over the words in ascending lexicographical order
    @Override
    public Iterator<String> iterator() { return this.wordCounter.keySet().iterator(); }
    // END : STUDENT
}