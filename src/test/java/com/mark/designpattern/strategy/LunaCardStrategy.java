package com.mark.designpattern.strategy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LunaCardStrategy implements PaymentStrategy {
    private final String emailId;
    private final String password;

    @Override
    public String pay(int amount) {
        return amount + "원 paid using LunaCard";
    }
}
