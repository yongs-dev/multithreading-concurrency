package com.mark.designpattern.templatemethod;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class FileProcessor {
    private String path;

    public FileProcessor(String path) {
        this.path = path;
    }

    public final int process() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            int result = getResult();
            String line = null;

            while ((line = reader.readLine()) != null) {
                result = calculate(result, Integer.parseInt(line));
            }

            return result;
        } catch (IOException ioe) {
            throw new IllegalArgumentException(path + "에 해당하는 파일이 없습니다.", ioe);
        }
    }

    protected abstract int calculate(int result, int number);
    protected abstract int getResult();
}

