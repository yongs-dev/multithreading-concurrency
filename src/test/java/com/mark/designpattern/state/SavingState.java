package com.mark.designpattern.state;

public class SavingState implements PowerState {

    private SavingState() {}

    private static class SingletonInstanceHolder {
        private static final SavingState INSTANCE = new SavingState();
    }

    public static SavingState getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public String powerButtonPush(LaptopContext cxt) {
        cxt.changeState(OnState.getInstance());
        return "노트북 전원 ON";
    }

    @Override
    public String typeButtonPush() {
        throw new IllegalStateException("노트북 절전 모드인 상태");
    }

    @Override
    public String toString() {
        return "노트북 절전 모드 상태 입니다.";
    }
}
