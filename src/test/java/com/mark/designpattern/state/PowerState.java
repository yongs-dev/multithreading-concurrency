package com.mark.designpattern.state;

public interface PowerState {
    String powerButtonPush(LaptopContext cxt);
    String typeButtonPush();
}
