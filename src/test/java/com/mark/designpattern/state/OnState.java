package com.mark.designpattern.state;

public class OnState implements PowerState {
    private OnState() {}

    private static class SingletonInstanceHolder {
        private static final OnState INSTANCE = new OnState();
    }

    public static OnState getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public String powerButtonPush(LaptopContext cxt) {
        cxt.changeState(OffState.getInstance());
        return "노트북 전원 OFF";
    }

    @Override
    public String typeButtonPush() {
        return "키 입력";
    }

    @Override
    public String toString() {
        return "노트북 전원 ON 상태 입니다.";
    }
}
