package de.tgx03.mimacommandcalculator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI {

    private static final short addressMinValue = 0;
    private static final short addressMaxValue = 255;

    private static final CheckBox[] bits = new CheckBox[17];
    private static final JTextField nextAddress = new JTextField("Next address", 10);
    private static final JFrame window = new JFrame();
    private static final JLabel result = new JLabel("Result: 0");
    private static final JComboBox<String> representations = new JComboBox<>(new String[]{"Decimal", "Binary", "Hexadecimal", "Octal"});
    private static final JComboBox<String> ALU = new JComboBox<>(new String[]{"idle", "ADD", "rotate", "AND", "OR", "XOR", "NOT", "if x = y, -1 -> z, else 0 -> z"});
    private static final Updater updater = new Updater();

    public static void main(String[] args) {
        window.setTitle("MiMa Bit Calculator");
        window.setSize(1200, 110);
        window.setResizable(true);
        window.setLayout(new FlowLayout());
        createCheckboxes();
        for (int i = bits.length - 1; i >= 4; i--) {
            window.add(bits[i]);
        }
        window.add(ALU);
        ALU.addActionListener(updater);
        for (int i = 3; i >= 0; i--) {
            window.add(bits[i]);
        }
        nextAddress.getDocument().addDocumentListener(updater);
        window.add(nextAddress);
        representations.setSelectedIndex(2);
        representations.addActionListener(updater);
        window.add(representations);
        window.add(result);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private static void createCheckboxes() {
        for (byte i = 0; i < bits.length; i++) {
            if (i < 4) {
                bits[i] = new CheckBox((byte) (i + 8));
            } else {
                bits[i] = new CheckBox((byte) (i + 11));
            }
            bits[i].addChangeListener(updater);
        }
        bits[16].setText("Ar");
        bits[15].setText("Aw");
        bits[14].setText("X");
        bits[13].setText("Y");
        bits[12].setText("Z");
        bits[11].setText("E");
        bits[10].setText("Pr");
        bits[9].setText("Pw");
        bits[8].setText("Ir");
        bits[7].setText("Iw");
        bits[6].setText("Dr");
        bits[5].setText("Dw");
        bits[4].setText("S");
        bits[3].setText("R");
        bits[2].setText("W");
        bits[1].setText("Proprietary 1");
        bits[0].setText("Proprietary 0");
    }

    private static void update() {
        int result;
        try {
            result = Integer.decode(nextAddress.getText());
        } catch (NumberFormatException e) {
            nextAddress.setForeground(Color.RED);
            return;
        }
        if (result < addressMinValue || result > addressMaxValue) {
            nextAddress.setForeground(Color.RED);
            return;
        }
        nextAddress.setForeground(Color.BLACK);
        for (CheckBox checkBox : bits) {
            if (checkBox.isSelected()) {
                result = result + pow(2, checkBox.exponent);
            }
        }
        int aluCode = ALU.getSelectedIndex() << 12;
        result = result + aluCode;
        switch (representations.getSelectedIndex()) {
            case 0 -> UI.result.setText(Integer.toString(result));
            case 1 -> UI.result.setText(Integer.toBinaryString(result));
            case 2 -> UI.result.setText(Integer.toHexString(result));
            case 3 -> UI.result.setText(Integer.toOctalString(result));
            default -> throw new RuntimeException("Unknown error occurred");
        }
    }

    private static int pow(int base, int exponent) {
        if (exponent == 0) {
            return 1;
        } else if (exponent == 1) {
            return base;
        } else {
            int result = 1;
            for (int i = 1; i <= exponent; i++) {
                result = result * base;
            }
            return result;
        }
    }

    private static class Updater implements ActionListener, ChangeListener, DocumentListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            UI.update();
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            UI.update();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            UI.update();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            UI.update();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            UI.update();
        }
    }
}
