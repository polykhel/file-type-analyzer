package com.mpollente.analyzer;

public class KMPSearch implements Search {

    private int[] prefixFunction(String str) {
        int[] prefixFunction = new int[str.length()];

        for (int i = 1; i < str.length(); i++) {
            int j = prefixFunction[i - 1];

            while (j > 0 && str.charAt(i) != str.charAt(j)) {
                j = prefixFunction[j - 1];
            }

            if (str.charAt(i) == str.charAt(j)) {
                j += 1;
            }

            prefixFunction[i] = j;
        }

        return prefixFunction;
    }

    public boolean contains(byte[] text, String pattern) {
        int[] prefixFunction = prefixFunction(pattern);
        byte[] pat = pattern.getBytes();

        int i = 0;
        int j = 0;
        while (i < text.length) {
            while (j > 0 && text[i] != pat[j]) {
                j = prefixFunction[j - 1];
            }

            if (text[i] == pat[j]) {
                j += 1;
            }

            if (j == pat.length) {
                return true;
            }

            i++;
        }

        return false;
    }
}
