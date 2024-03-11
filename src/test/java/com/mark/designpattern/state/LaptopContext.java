package com.mark.designpattern.state;

public class LaptopContext {
    PowerState powerState;

    public LaptopContext() {
        this.powerState = OffState.getInstance();
    }

    public void changeState(PowerState state) {
        this.powerState = state;
    }

    public void setSavingState() {
        System.out.println("노트북 절전 모드");
        changeState(SavingState.getInstance());
    }

    public String powerButtonPush() {
        String msg = powerState.powerButtonPush(this);
        System.out.println(msg);
        return msg;
    }

    public String typeButtonPush() {
        String msg = powerState.typeButtonPush();
        System.out.println(msg);
        return msg;
    }

    public String currentStatePrint() {
        String msg = powerState.toString();
        System.out.println(msg);
        return msg;
    }
}
