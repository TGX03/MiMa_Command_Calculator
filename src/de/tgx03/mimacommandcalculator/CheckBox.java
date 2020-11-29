package de.tgx03.mimacommandcalculator;

import javax.swing.*;

/**
 * A normal checkbox which holds an additional number representing which bit this controls
 */
class CheckBox extends JCheckBox {

    protected final byte exponent;

    /**
     * Creates a new JCheckBox with an additional value for the corresponding bit
     * @param exponent Which bit this checkbox refers to
     */
    public CheckBox(byte exponent) {
        this.exponent = exponent;
    }

    public String toString() {
        return getText() + " : " + exponent;
    }
}
