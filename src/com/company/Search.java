package com.company;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Search extends Thread {
    public static String slash = File.separator;
    public List<String> findedPath;
    private String path;
    private String find;
    private Boolean template;
    private String str;
    private Boolean searchLine;
    private JTextArea out;
    public Integer count = 1;
    private Boolean running = false;
    

    public Search() {
        findedPath = new ArrayList<String>();
    }

    public void start(String path, String find, Boolean template, String str, Boolean searchLine, JTextArea out) {
        this.path = path;
        this.find = find;
        this.template = template;
        this.str = str;
        this.searchLine = searchLine;
        this.out = out;
        running = true;
        super.start();
    }

    @Override
    public void run() {
        func(path);
        if (findedPath.size() <= 0) {
            findedPath.add("No files found :(");
        }
//        } else {
//            for (String file : findedPath) {
//                out.append(file);
//            }
//        }
    }

    public void pause() {
        running = false;
    }
    public void myResume() {
        running = true;
    }

    public void func(String path) {
        while (!running) {
            try {
                this.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        File f = new File(path);
        String[] list = f.list();
        if (!path.endsWith(slash)) {
            path += slash;
        }
        if (list == null) {
            return;
        }
        for (String file : list) {
            if (!template) {
                if (file.contains(find)) {
                    if ((searchLine && isFileContains(path + file, str)) || !searchLine) {
                        findedPath.add(path + file + "\n");
                        out.append(count++ + ". " + path + file + "\n");
//                    System.out.println(path + file + " find!");
                    }
                }
            } else {
                //Function is suck! You need to repair it. (Use split)
                if (file.contains(".") && (!find.contains(".") || file.substring(file.indexOf(".")).equals(find.substring(find.indexOf("."))))) {
                    String begin = find.substring(0, find.indexOf("*"));
                    if (file.startsWith(begin)) {
                        String end;
                        if (find.contains(".")) {
                            end = find.substring(find.indexOf("*") + 1, find.indexOf("."));
                        } else {
                            end = find.substring(find.indexOf("*")+1);
                        }
                        String fileName = file.substring(0, file.indexOf("."));
                        if (fileName.endsWith(end)) {
                            if ((searchLine && isFileContains(path + file, str)) || !searchLine) {
                                findedPath.add(path + file + "\n");
                                out.append(count++ + ". " + path + file + "\n");
                                //System.out.println(path + slash + file + " find!");
                            }
                        }
                    }
                }
            }
            File tempfile = new File(path + file);
            if (!file.equals(".") && !file.equals("")) {
                if (tempfile.isDirectory()) {
                    func(path + file);
                }
            }
        }
    }

    public Boolean isFileContains(String path, String neededLine) {
        if (path.endsWith(".txt")) {
            File file = new File(path);
            try {
                java.util.List<String> list = Files.readAllLines(file.toPath());
                for (String line : list) {
                    if (line.contains(neededLine)) {
                        return true;
                    }
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }
        return false;
    }
}
