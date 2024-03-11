package com.mark.designpattern.factorymethod;

public class OilTankerShipFactory extends ShipFactory {

    private OilTankerShipFactory() {}

    private static class SingletonInstanceHolder {
        private static final OilTankerShipFactory INSTANCE = new OilTankerShipFactory();
    }

    public static OilTankerShipFactory getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    protected Ship createShip() {
        return new OilTankerShip("OilTankerShip", "15t", "blue");
    }
}
