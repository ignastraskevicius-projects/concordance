package org.ignast.challenge.concordance.controller;

public class AlphabeticCounter {

    private int counter = 0;

    public String next() {
        counter++;
        return (char) (counter + 97 - 1) + "";
    }
}
