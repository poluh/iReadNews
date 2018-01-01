package com.application.text.filter.wrap;

public class WrapText {

    public static String wrapText(char[] text, int wight) {
        int index = 0;
        int wightText = 0;
        StringBuilder answer = new StringBuilder();
        while (index < text.length) {
            answer.append(text[index]);
            wightText++;
            index++;
            if (wightText == wight) {
                answer.append("\n");
                wightText = 0;
            }
        }
        return answer.toString();
    }
}
