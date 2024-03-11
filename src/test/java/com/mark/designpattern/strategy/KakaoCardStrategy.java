package com.mark.designpattern.strategy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoCardStrategy implements PaymentStrategy {
    private final String name;
    private final String cardNumber;
    private final String cvv;
    private final String dateOfExpiry;

    @Override
    public String pay(int amount) {
        return amount + "원 paid using KakaoCard";
    }
}
