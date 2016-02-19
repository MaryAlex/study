package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

//I don't know another way to do Pause/Resume, so...
//Bezverhi go fuck yourself, I don't want to waste my time!
public class Window extends JFrame {
    public  static Integer FIRST_SEARCH = 1;
    public  static Integer SECOND_SEARCH = 2;

    private JTextField path1;
    private JButton open1;
    private JTextField find1;
    private JPanel panel;
    private JButton search1;
    private JTextArea out1;
    private JCheckBox template1;
    private JTextField str1;
    private JCheckBox searchLine1;
    private JTextField str;
    private JCheckBox searchLine;
    private JButton open;
    private JTextField path;
    private JTextField find;
    private JButton search;
    private JTextArea out;
    private JCheckBox template;
    private JButton continue1;
    private JButton pause1;
    private JButton continueButton;
    private JButton pause;
    Search firstSearch;
    Search secondSearch;


    public Window() throws HeadlessException {
        super("Hello");
        setContentPane(panel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        open1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openL(path1);
            }
        });
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 openL(path);
            }
        });
        search1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search(searchLine1, template1, str1, find1, path1, out1, FIRST_SEARCH);
            }
        });
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search(searchLine, template, str, find, path, out, SECOND_SEARCH);
            }
        });
        pause1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (firstSearch != null ) {
//                    firstSearch.stop();
                    firstSearch.pause();
                }
            }
        });
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (secondSearch != null) {
//                    secondSearch.stop();
                    secondSearch.pause();
                }
            }
        });
        continue1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (firstSearch != null) {
//                    firstSearch.resume();
                    firstSearch.myResume();
                }
            }
        });
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (secondSearch != null) {
//                    secondSearch.resume();
                    secondSearch.myResume();
                }
            }
        });
        setVisible(true);
    }

    public void search (JCheckBox searchLine, JCheckBox template, JTextField str, JTextField find, JTextField path, JTextArea out, Integer searchThread) {
        if (searchLine.isSelected() && str.getText().isEmpty()) {
            JOptionPane.showMessageDialog(Window.this, "Input what line I must search in files, please");
            return;
        }
        if (template.isSelected() && !find.getText().contains("*")) {
            JOptionPane.showMessageDialog(Window.this, "Template must contains '*'");
        } else if (!template.isSelected() && find.getText().contains("*")) {
            JOptionPane.showMessageDialog(Window.this, "Only template can contains '*'");
        } else {
            if (!find.getText().isEmpty() && !path.getText().isEmpty()) {
                out.setText("");
                if (searchThread == 1) {
                    firstSearch = null;
                    firstSearch = new Search();
                    firstSearch.start(path.getText(), find.getText(), template.isSelected(), str.getText(), searchLine.isSelected(), out);
                } else if (searchThread == 2){
                    secondSearch = null;
                    secondSearch = new Search();
                    secondSearch.start(path.getText(), find.getText(), template.isSelected(), str.getText(), searchLine.isSelected(), out);
                }
            } else {
                JOptionPane.showMessageDialog(Window.this, "Input file name and folder, please");
            }
        }
    }

    public void openL(JTextField path) {
        JFileChooser c = new JFileChooser();
        c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // Demonstrate "Open" dialog:
        int rVal = c.showOpenDialog(Window.this);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            path.setText(c.getSelectedFile().toString());
        }
        if (rVal == JFileChooser.CANCEL_OPTION) {

        }
    }
}
