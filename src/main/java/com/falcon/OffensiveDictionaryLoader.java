package com.falcon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * @author anhnt
 */
public class OffensiveDictionaryLoader {
    private final TrieNode dictionary = new TrieNode();

    private static final OffensiveDictionaryLoader OFFENSIVE_DICTIONARY_LOADER = getInstance();

    private OffensiveDictionaryLoader() {
    }

    public static OffensiveDictionaryLoader getInstance() {
        return Objects.requireNonNullElseGet(OFFENSIVE_DICTIONARY_LOADER, OffensiveDictionaryLoader::new);
    }


    public static void setOffensiveDictionaryLoader(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line;
            while((line = reader.readLine()) != null) {
                if (!line.trim().startsWith("#")) {
                    OFFENSIVE_DICTIONARY_LOADER.insert(line);
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
