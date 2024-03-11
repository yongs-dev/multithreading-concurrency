package com.mark.designpattern.strategy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Item {
    private final String name;
    private final int price;
}
