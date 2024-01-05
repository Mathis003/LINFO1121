package strings;

import java.util.HashMap;

/**
 * Author Pierre Schaus
 *
 * We are interested in the Rabin-Karp algorithm.
 * We would like to modify it a bit to determine
 * if a word among a list (!!! all words are of the same length !!!)
 * is present in the text.
 * To do this, you need to modify the Rabin-Karp
 * algorithm which is shown below (page 777 of the book).
 * More precisely, you are asked to modify this class
 * so that it has a constructor of the form:
 * public RabinKarp(String[] pat)
 *
 * Moreover the search function must return
 * the index of the beginning of the first
 * word (among the pat array) found in the text or
 * the size of the text if no word appears in the text.
 *
 * Example: If txt = "Here find interesting
 * exercise for Rabin Karp" and pat={"have", "find", "Karp"}
 * the search function must return 5 because
 * the word "find" present in the text and in the list starts at index 5.
 *
 */
public class RabinKarp
{
    // BEGIN : STUDENT
    private HashMap<Long, String> patMap;
    // END : STUDENT

    private int M;         // pattern length
    private long Q;        // a large prime
    private int R = 2048;  // alphabet size
    private long RM;       // R^(M-1) % Q

    public RabinKarp(String[] pat)
    {
        // BEGIN : STUDENT
        this.patMap = new HashMap<>();
        this.M = pat[0].length();
        // END : STUDENT

        Q = 4463;
        RM = 1;

        // Compute R^(M-1) % Q for use
        for (int i = 1; i <= M - 1; i++) RM = (R * RM) % Q; // in removing leading digit.

        // BEGIN : STUDENT
        for (String pattern : pat) this.patMap.put(hash(pattern, this.M), pattern);
        // END : STUDENT
    }

    // BEGIN : STUDENT
    // For Las Vegas, check pat vs txt(i..i-M+1).
    public boolean check(int i, String txt, String pattern) { return pattern.equals(txt.substring(i, i + this.M)); }
    // END : STUDENT

    // Compute hash for key[0..M-1].
    private long hash(String key, int M)
    {
        long h = 0;
        for (int j = 0; j < M; j++) h = (R * h + key.charAt(j)) % Q;
        return h;
    }

    // Search for hash match in text.
    public int search(String txt)
    {
        int N = txt.length();
        long txtHash = hash(txt, M);

        // BEGIN : STUDENT
        // Match at beginning.
        if (this.patMap.containsKey(txtHash) && check(0, txt, this.patMap.get(txtHash))) return 0;
        // END : STUDENT

        // Remove leading digit, add trailing digit, check for match.
        for (int i = M; i < N; i++)
        {
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            // BEGIN : STUDENT
            if (this.patMap.containsKey(txtHash) && check(i - M + 1, txt, this.patMap.get(txtHash))) return i - M + 1;
            // END : STUDENT
        }

        // No match found
        return N;
    }
}