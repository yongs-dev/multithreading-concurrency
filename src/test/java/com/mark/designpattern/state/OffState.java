package com.mark.designpattern.state;

public class OffState implements PowerState {

    private OffState() {}

    private static class SingletonInstanceHolder {
        private static final OffState INSTANCE = new OffState();
    }

    public static OffState getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public String powerButtonPush(LaptopContext cxt) {
        cxt.changeState(OnState.getInstance());
        return "노트북 전원 ON";
    }

    @Override
    public String typeButtonPush() {
        throw new IllegalStateException("노트북 OFF 인 상태");
    }

    @Override
    public String toString() {
        return "노트북 전원 OFF 상태 입니다.";
    }
}
