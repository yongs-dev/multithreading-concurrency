package com.mark.designpattern.factorymethod;

public abstract class ShipFactory {

    public final Ship orderShip(String email) {
        validate(email);

        Ship ship = createShip();

        sendEmailTo(email, ship);

        return ship;
    }

    protected abstract Ship createShip();

    private void validate(String email) {
        if (email == null) {
            throw new IllegalArgumentException("이메일을 남겨주세요");
        }
    }

    private void sendEmailTo(String email, Ship ship) {
        System.out.println(ship.name + " 다 만들었다고 " + email + "로 메일을 보냈습니다.");
    }
}
