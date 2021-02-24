package model;

import game.Arena;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Animal implements Player {
    private static Object lock = new Object();

    private String name;
    private int power;
    private AtomicInteger life = new AtomicInteger(1);
    private int id;


    public Animal(int id, String name, int power) {
        this.name = name;
        this.power = power;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public AtomicInteger getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life.set(life);
    }

    @Override
    public void bump() {
        int randomGladiator = new Random().nextInt(4);
        if (Arena.getInstance().getGladiators().containsKey(randomGladiator)) {
            Gladiator sufferer=Arena.getInstance().getGladiators().get(randomGladiator);
            sufferer.setLife(sufferer.getLife() - this.power);
            System.out.println(this.id+" bite the gladiator "+sufferer.getId()+" - "+sufferer.getLife());
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void run() {

        while (this.life.get() > 0 && !Arena.getInstance().isWinner()) {

            this.bump();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (this.life.get() <= 0) {

            Arena.getInstance().getAnimals().remove(this.id);
        }
    }

}
