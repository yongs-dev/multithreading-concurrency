package com.mark.designpattern;

import com.mark.designpattern.factorymethod.*;
import com.mark.designpattern.singleton.SingletonInner;
import com.mark.designpattern.state.LaptopContext;
import com.mark.designpattern.state.OffState;
import com.mark.designpattern.strategy.Item;
import com.mark.designpattern.strategy.KakaoCardStrategy;
import com.mark.designpattern.strategy.LunaCardStrategy;
import com.mark.designpattern.strategy.ShoppingCart;
import com.mark.designpattern.templatemethod.MultiplyFileProcessor;
import com.mark.designpattern.templatemethod.PlusFileProcessor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DesignPatternTest {

    @Test
    public void singletonTest() throws Exception {
        SingletonInner singleton1 = SingletonInner.getInstance();
        SingletonInner singleton2 = SingletonInner.getInstance();

        assertThat(singleton1 == singleton2).isTrue();
    }

    @Test
    public void strategyTest() throws Exception {
        ShoppingCart cart = new ShoppingCart();

        cart.addItem(new Item("맥북 프로", 50000));
        cart.addItem(new Item("아이 패드", 10000));

        assertThat(cart.pay(new KakaoCardStrategy("yong-seok", "1234567890", "123", "12/23"))).contains("KakaoCard");
        assertThat(cart.pay(new LunaCardStrategy("mail@google.com", "password"))).contains("LunaCard");
    }

    @Test
    public void templateTest() throws Exception {
        PlusFileProcessor plusFileProcessor = new PlusFileProcessor("/Users/yong/Documents/Local_test/text.txt");
        int plusResult = plusFileProcessor.process();
        assertThat(plusResult).isEqualTo(55);

        MultiplyFileProcessor multiplyFileProcessor = new MultiplyFileProcessor("/Users/yong/Documents/Local_test/text.txt");
        int multiplyResult = multiplyFileProcessor.process();
        assertThat(multiplyResult).isEqualTo(10 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2);
    }

    @Test
    public void stateTest() throws Exception {
        LaptopContext laptop = new LaptopContext();
        assertThat(laptop).isExactlyInstanceOf(OffState.getInstance().getClass());

        System.out.println("\n노트북 켜기: OffState -> OnState");
        laptop.powerButtonPush();
        laptop.currentStatePrint();
        laptop.typeButtonPush();

        System.out.println("\n노트북 절전: OnState -> SavingState");
        laptop.setSavingState();
        laptop.currentStatePrint();

        System.out.println("\n노트북 다시 켜기: SavingState -> OnState");
        laptop.powerButtonPush();
        laptop.currentStatePrint();

        System.out.println("\n노트북 끄기: OnState -> OffState");
        laptop.powerButtonPush();
        laptop.currentStatePrint();
    }

    @Test
    public void factoryMethodTest() throws Exception {
        Ship containerShip = ContainerShipFactory.getInstance().orderShip("www.naver.com");
        assertThat(containerShip).isInstanceOf(ContainerShip.class);

        Ship oilTankerShip = OilTankerShipFactory.getInstance().orderShip("www.naver.com");
        assertThat(oilTankerShip).isInstanceOf(OilTankerShip.class);
    }
}
