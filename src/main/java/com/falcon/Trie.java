package com.falcon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Trie {
    private final TrieNode root = new TrieNode();

    private static final Trie trie = getInstance();

    public static Trie getInstance() {
        if (trie == null) {
            return new Trie();
        }
        return trie;
    }

    private Trie() {

    }

    public void loadOffensiveDictionary(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line;
            while((line = reader.readLine()) != null) {
                if (!line.trim().startsWith("#")) {
                    trie.insert(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!node.children.containsKey(ch)) {
                node.children.put(ch, new TrieNode());
            }
            node = node.children.get(ch);
        }
        node.isEnd = true;
    }


    public String replaceSensitiveWords(String text, String replacement) {

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            // Skip spaces
            if (text.charAt(i) == ' ') {
                result.append(' ');
                continue;
            }

            int j = i, k = i;
            TrieNode node = root;
            while (j < text.length()) {
                while (text.charAt(j) == ' ' && j < text.length() - 1) {
                    j++;
                }
                if (text.charAt(j) != ' ' && node.children.containsKey(text.charAt(j))) {
                    node = node.children.get(text.charAt(j));
                    if (node.isEnd) {
                        k = j;
                    }
                }
                j++;
            }
            if (k != i) {
                result.append(replacement.repeat(k - i + 1));
                i = k;
            } else {
                result.append(text.charAt(i));
            }
        }
        return result.toString();

    }

//    public static void main(String[] args) {
//        Trie trie = Trie.getInstance();
//        trie.loadOffensiveDictionary("vn_offensive_words.txt");
//        String message = "    dcm con choas dnasodnqd";
//        System.out.println(trie.replaceSensitiveWords(message, "*"));
//    }
}
