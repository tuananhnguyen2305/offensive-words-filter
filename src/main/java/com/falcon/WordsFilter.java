package com.falcon;

/**
 * @author anhnt
 */
public class WordsFilter {
    private final TrieNode root;

    public WordsFilter(OffensiveDictionaryLoader offensiveDictionaryLoader) {
        this.root = offensiveDictionaryLoader.getDictionary();
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
                if (text.charAt(j) != ' ' && node.children.containsKey(Character.toLowerCase(text.charAt(j)))) {
                    node = node.children.get(Character.toLowerCase(text.charAt(j)));
                    if (node.isEnd) {
                        k = j;
                    }
                    j++;
                } else {
                    break;
                }
            }
            if (k != i) {
                //result.append(replacement.repeat(k - i + 1));
                result.append(replacement);
                i = k;
            } else {
                result.append(text.charAt(i));
            }
        }
        return result.toString();
    }

//    private static final WordsFilter WORDS_FILTER = getInstance();
//
//    public static WordsFilter getInstance() {
//        if (WORDS_FILTER == null) {
//            return new WordsFilter();
//        }
//        return WORDS_FILTER;
//    }
//
//    private WordsFilter() {
//
//    }

//    public void loadOffensiveDictionary(String filePath) {
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(filePath));
//
//            String line;
//            while((line = reader.readLine()) != null) {
//                if (!line.trim().startsWith("#")) {
//                    WORDS_FILTER.insert(line);
//                }
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void insert(String word) {
//        TrieNode node = root;
//        for (int i = 0; i < word.length(); i++) {
//            char ch = word.charAt(i);
//            if (!node.children.containsKey(ch)) {
//                node.children.put(ch, new TrieNode());
//            }
//            node = node.children.get(ch);
//        }
//        node.isEnd = true;
//    }
//
//    public static void main(String[] args) {
//        WordsFilter wordsFilter = new WordsFilter(new OffensiveDictionaryLoader("vn_offensive_words.txt"));
//
//        String message = "  con ca vAn g la Co n ca co n";
//        System.out.println(message);
//        System.out.println(wordsFilter.replaceSensitiveWords(message, "*"));
//    }
}
