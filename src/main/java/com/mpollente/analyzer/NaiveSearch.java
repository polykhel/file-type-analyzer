package com.mpollente.analyzer;

public class NaiveSearch implements Search {

    public boolean contains(byte[] text, String pattern) {
        byte[] pat = pattern.getBytes();
        for (int i = 0; i < text.length - pat.length; i++) {
            int j;

            for (j = 0; j < pat.length; j++) {
                if (text[i + j] != pat[j]) {
                    break;
                }
            }

            if (j <= pat.length) {
                return true;
            }
        }

        return false;
    }
}
