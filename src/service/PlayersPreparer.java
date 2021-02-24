package service;

import game.Arena;
import model.Animal;
import model.Gladiator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayersPreparer {
    private static File gladiatorsData = new File("resource\\gladiators.txt");
    private static File animalsData = new File("resource\\animals.txt");
    private static PlayersPreparer instance = new PlayersPreparer();

    private Arena arena = Arena.getInstance();
    private int id = 0;


    private PlayersPreparer() {
        loadGladiators();
        loadAnimals();

    }

    // read gladiators and put in map
    public static PlayersPreparer getPreparer() {
        return instance;
    }

    private void loadGladiators() {

        String data = null;
        String[] splittedData = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(gladiatorsData))) {
            while (true) {
                data = reader.readLine();
                if (data == null) {
                    break;
                }
                splittedData = data.split(" ");
                id++;
                arena.getGladiators().put(id, new Gladiator(id, splittedData[0], Integer.parseInt(splittedData[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //read animals and put in map
    public void loadAnimals() {
        String data = null;
        String[] splittedData = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(animalsData))) {
            while (true) {
                data = reader.readLine();
                if (data != null) {
                    splittedData = data.split(" ");
                    id++;
                    arena.getAnimals().put(id, new Animal(id, splittedData[0], Integer.parseInt(splittedData[1])));
                } else break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeGladiator(Gladiator gladiator) throws IOException {

        List<String> str=new ArrayList<>();
        String temp;
        try (BufferedReader reader = new BufferedReader(new FileReader(gladiatorsData)) ) {
            while (true) {
                temp=reader.readLine();
                if (temp!=null ){
                    if (temp.contains(gladiator.getName())){
                        continue;
                    }
                    str.add(temp);
                }
                else break;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(gladiatorsData))){
            {
                for (String s:str){
                    writer.write(s);
                    writer.newLine();
                }
            }

        }
    }

}
