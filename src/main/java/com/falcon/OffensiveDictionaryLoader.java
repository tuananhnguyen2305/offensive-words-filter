package com.falcon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author anhnt
 */
public class OffensiveDictionaryLoader {
    private final TrieNode dictionary = new TrieNode();

    private OffensiveDictionaryLoader() {
    }

    public OffensiveDictionaryLoader(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line;
            while((line = reader.readLine()) != null) {
                if (!line.trim().startsWith("#")) {
                    this.insert(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error in loading offensive dictionary: " + e.getMessage());
        }
    }

    public TrieNode getDictionary() {
        return dictionary;
    }

    private void insert(String word) {
        TrieNode node = dictionary;
        for (int i = 0; i < word.length(); i++) {
            char ch = Character.toLowerCase(word.charAt(i));
            if (ch != ' ') {
                if (!node.children.containsKey(ch)) {
                    node.children.put(ch, new TrieNode());
                }
                node = node.children.get(ch);
            }
        }

        node.isEnd = true;
    }
}
