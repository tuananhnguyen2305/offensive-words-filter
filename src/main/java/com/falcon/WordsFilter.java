package com.falcon;

/**
 * @author anhnt
 */
public class WordsFilter {
    private TrieNode root;
    private static final WordsFilter WORDS_FILTER = getInstance();

    private WordsFilter() {
    }

    public static WordsFilter getInstance(){
        if (WORDS_FILTER == null) {
            return new WordsFilter();
        }
        return WORDS_FILTER;
    }

    public void setDictionary(OffensiveDictionaryLoader offensiveDictionaryLoader) {
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
                result.append(replacement.repeat(k - i + 1));
                //result.append(replacement);
                i = k;
            } else {
                result.append(text.charAt(i));
            }
        }
        return result.toString();
    }

//    public static void main(String[] args) {
//        WordsFilter wordsFilter = WordsFilter.getInstance();
//        OffensiveDictionaryLoader.setOffensiveDictionaryLoader("vn_offensive_words.txt");
//        wordsFilter.setDictionary(OffensiveDictionaryLoader.getInstance());
//
//        String message = " cac";
//        System.out.println(message);
//        System.out.println(wordsFilter.replaceSensitiveWords(message, "*"));
//    }
}
