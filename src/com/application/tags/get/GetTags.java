package com.application.tags.get;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GetTags {

    private static class Word {


        private static Map<String, Word> allWord = new HashMap<>();

        private Integer wt;
        private String word;

        Word(String word, Integer wt) {

            Set keys = allWord.keySet();

            if (keys.contains(word)) {
                this.wt = wt;
            } else this.wt = wt + 1;
            this.word = word;
            allWord.put(word, this);

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

        @Override
        public String toString() {
            return word + " " + wt;
        }
    }

    public static Map<String, Word> getTags(String text) {

        Arrays.stream(text.split("\\s+")).forEach(word -> new Word(word.toLowerCase(), 1));
        //System.out.println(Word.getAllWord() + "\n\n");
        Word.getAllWord().clear();
        return Word.getAllWord();
    }

}
