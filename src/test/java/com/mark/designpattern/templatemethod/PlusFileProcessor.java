package com.mark.designpattern.templatemethod;

public class PlusFileProcessor extends FileProcessor {

    public PlusFileProcessor(String path) {
        super(path);
    }

    @Override
    protected int calculate(int result, int number) {
        return result += number;
    }

    @Override
    protected int getResult() {
        return 0;
    }
}
