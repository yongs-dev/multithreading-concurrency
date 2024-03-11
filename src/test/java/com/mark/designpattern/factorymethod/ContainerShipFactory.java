package com.mark.designpattern.factorymethod;

public class ContainerShipFactory extends ShipFactory {

    private ContainerShipFactory() {}

    private static class SingletonInstanceHolder {
        private static final ContainerShipFactory INSTANCE = new ContainerShipFactory();
    }

    public static ContainerShipFactory getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    protected Ship createShip() {
        return new ContainerShip("ContainerShip", "20t", "green");
    }
}
