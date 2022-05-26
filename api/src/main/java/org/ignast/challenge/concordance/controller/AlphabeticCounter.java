package org.ignast.challenge.concordance.controller;

public class AlphabeticCounter {

    private int counter = 0;

    public String next() {
        counter++;
        if ((counter - 1) / 26 > 0) {
            return "" + (char) ((counter - 1) / 26 + 'a' - 1) + (char) ((counter - 1) % 26 + 'a');
        } else {
            return (char) (counter - 1 + 'a') + "";
        }
    }
}
