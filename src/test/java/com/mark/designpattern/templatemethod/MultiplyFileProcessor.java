package com.mark.designpattern.templatemethod;

public class MultiplyFileProcessor extends FileProcessor {
    public MultiplyFileProcessor(String path) {
        super(path);
    }

    @Override
    protected int calculate(int result, int number) {
        return result *= number;
    }

    @Override
    protected int getResult() {
        return 1;
    }
}
