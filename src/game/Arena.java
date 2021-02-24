package game;

import model.Animal;
import model.Caesar;
import model.Gladiator;
import service.PlayersPreparer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Arena {

    //class is singleton
    private static Arena arenaInstance = new Arena();

    private ConcurrentHashMap<Integer,Gladiator> gladiators = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer,Animal> animals = new ConcurrentHashMap<>();
    public static final Object OBJECT_LOCK = new Object();

    private volatile boolean IsDied = false;
    private Set<Thread> gladiatorsThread = new HashSet<>();
    private Set<Thread> animalsThread = new HashSet<>();

    private Arena() {
    }

    public static Arena getInstance() {
        return arenaInstance;
    }

    public boolean isDied() {
        synchronized (OBJECT_LOCK) {
            return IsDied;
        }
    }

    public void setDied(boolean died) {
        synchronized (OBJECT_LOCK) {
            IsDied = died;
        }
    }

    public ConcurrentHashMap<Integer, Animal> getAnimals() {
        return animals;
    }

    public ConcurrentHashMap<Integer, Gladiator> getGladiators() {
        return gladiators;
    }


    public boolean isWinner() {
        synchronized (OBJECT_LOCK){
            if (gladiators.size()==1){
                return true;
            }
            else {
                return false;
            }
        }
    }

    public void beatenGladiator( Gladiator gladiator,int randomPlayer){
        synchronized (OBJECT_LOCK){
        if (randomPlayer!=gladiator.getId() && getGladiator(randomPlayer)!=null){


                if (getGladiator(randomPlayer).getLife()<=0){
                    return;
                }
                getGladiator(randomPlayer).setLife(getGladiator(randomPlayer).getLife()-gladiator.getPower());
                System.err.println("N"+gladiator.getId() + " bump to  N" + randomPlayer + "    current life is - " + getGladiator(randomPlayer).getLife());
            }
        }
    }
    public void beatenAnimal(Gladiator gladiator,int randomPlayer){
        synchronized (OBJECT_LOCK){
        if (animals.get(randomPlayer)!=null){
            if (animals.get(randomPlayer).getLife().get()<=0){
                return;
            }
            animals.get(randomPlayer).setLife(animals.get(randomPlayer).getLife().get()-gladiator.getPower());
            System.err.println("N"+gladiator.getId() + " bump to animal   N" + randomPlayer + "  -  life is - " + animals.get(randomPlayer).getLife().get());
        }
        }
    }


    public void killOrCure(int id) throws InterruptedException, IOException {

        synchronized (OBJECT_LOCK) {
            if (Caesar.getCaesar().getVerdict()) {
                PlayersPreparer.getPreparer().removeGladiator(gladiators.get(id));
            }
            gladiators.remove(id);
            System.err.println("size is - " + gladiators.size());
        }
    }

    public  Gladiator getGladiator(int gladiatorsId) {
        synchronized (OBJECT_LOCK){
            return gladiators.get(gladiatorsId);
        }
    }


    public void startGame() {

        for (Gladiator gladiator : gladiators.values()) {
            gladiatorsThread.add(new Thread(gladiator));
        }

        for (Thread gladiator : gladiatorsThread) {
            gladiator.start();
        }
        for (Animal animal : animals.values()) {
            animalsThread.add(new Thread(animal));
        }
        for (Thread animal : animalsThread) {
            animal.start();
        }
    }

}
