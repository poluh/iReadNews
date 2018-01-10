package com.application.tags.get;

import java.util.*;

public class GetTags {

    private static class Word {

        private static Map<String, Word> allWord = new HashMap<>();

        private Integer wt;
        private String word;

        Word(String word, Integer wt) {
            Set keys = allWord.keySet();
            if (!keys.contains(word)) {
                this.wt = wt;
                this.word = word;
                allWord.put(word, new Word(word, wt));
            } else allWord.put(word, new Word(word, wt + 1));
        }

        public String getWord() {
            return word;
        }

        public int getWt() {
            return wt;
        }

        static Map<String, Word> getAllWord() {
            return allWord;
        }
    }

    public static Map<String, Word> getTags(String text) {

        Arrays.stream(text.split("\\s+")).forEach(word -> new Word(word.toLowerCase(), 1));
        System.out.println(Word.getAllWord());
        return Word.getAllWord();
    }

}
