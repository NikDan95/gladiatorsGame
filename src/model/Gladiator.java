package model;

import game.Arena;

import java.io.IOException;
import java.util.Random;

public class Gladiator implements Player {
    private final int id;
    private final String Name;
    private final int power;
    private volatile  int life = 100;


    public Gladiator(int id, String name, int power) {
            this.id = id;
            Name = name;
        this.power = power;

    }

    public String getName() {
        return Name;
    }

    public int getPower() {
        return power;
    }

    public int getId() {
        return id;
    }


    public int getLife() {

        return life;
    }

    public void setLife(int life) {
        this.life=life;

    }


    @Override
    public void bump() {
        if (new Random().nextBoolean()) {
            Arena.getInstance().beatenGladiator(this, new Random().nextInt(Arena.getInstance().getGladiators().size()+1));
        } else {
            Arena.getInstance().beatenAnimal(this, new Random().nextInt(Arena.getInstance().getAnimals().size()+
                    Arena.getInstance().getGladiators().size()+ 1));
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "Gladiator{" +
                "Name='" + Name + '\'' +
                ", power=" + power +
                '}';
    }

    @Override
    public void run() {
        while (true) {
            if (this.getLife() <= 0) {
                try {
                    synchronized (Arena.OBJECT_LOCK) {
                        Arena.getInstance().killOrCure(this.id);
                        return;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (Arena.getInstance().isWinner()) {
                System.out.println("Winner is " + Arena.getInstance().getGladiators().values().iterator().next());
                return;
            }
            this.bump();

        }

    }

}
