package com.mark.designpattern.strategy;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ShoppingCart {
    final List<Item> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public String pay(PaymentStrategy paymentMethod) {
        int amount = 0;
        for (Item item : items) {
            amount += item.getPrice();
        }

        return paymentMethod.pay(amount);
    }
}
