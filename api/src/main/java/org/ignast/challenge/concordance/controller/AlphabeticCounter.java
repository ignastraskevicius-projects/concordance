package org.ignast.challenge.concordance.controller;

import org.springframework.stereotype.Component;

@Component
public class AlphabeticCounter {

    private int counter = 0;

    public String next() {
        counter++;
        int n = counter;
        char[] str = "        ".toCharArray();
        int i = 7;

        while (n > 0) {
            int rem = n % 26;

            if (rem == 0) {
                str[i--] = 'z';
                n = (n / 26) - 1;
            } else {
                str[i--] = (char) ((rem - 1) + 'a');
                n = n / 26;
            }
        }
        return new String(str).strip();
    }
}
