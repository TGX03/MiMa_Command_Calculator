package de.tgx03.mimacommandcalculator;

import javax.swing.*;

class CheckBox extends JCheckBox {

    protected final byte exponent;

    public CheckBox(byte exponent) {
        this.exponent = exponent;
    }

    public String toString() {
        return getText() + " : " + exponent;
    }
}
