package island;

import animals.Animal;
import animals.enums.AnimalType;
import animals.herbivorous.*;
import animals.predators.*;
import lombok.Getter;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Field {

    private final Map<AnimalType, List<Animal>> animalsOnField;
    @Getter private final int x;
    @Getter private final int y;
    @Getter private final EventLog eventLog;
    private double grass = 0;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        animalsOnField = new HashMap<>();
        for (AnimalType type : AnimalType.values()) {
            animalsOnField.put(type, new ArrayList<>());
        }
        growGrass();
        eventLog = new EventLog();
    }



    public synchronized boolean addAnimal(Animal animal) {
        if (animalsOnField.get(animal.getType()).size() < animal.getLimit()) {
            animalsOnField.get(animal.getType()).add(animal);
            animal.setCurrentField(this);
            return true;
        } else {
            return false;
        }
    }

    public synchronized void removeAnimal(Animal animal) {
        animalsOnField.get(animal.getType()).remove(animal);
    }

    public synchronized List<Animal> getAnimalsOnField(AnimalType type) {
        return animalsOnField.get(type);
    }

    public synchronized void growGrass() {
        grass = Math.min(grass + ThreadLocalRandom.current().nextInt(100) + 50, 200);
    }

    public synchronized double eatSomeGrass(double amount) {
        if (grass > amount) {
            grass -= amount;
            return amount;
        } else {
            double temp = grass;
            grass = 0;
            return temp;
        }
    }

    public void fillingTheFieldWithAnimals(int predatorCount, int herbivorousCount) {
        for (int i = 0; i < predatorCount; i++) {
            createAnimalOnField(AnimalType.getRandomPredatorType());
        }
        for (int i = 0; i < herbivorousCount; i++) {
            createAnimalOnField(AnimalType.getRandomHerbivorousType());
        }
    }

    public void print() {
        System.out.println(EventLog.getNumberOfIteration() + "-я итерация. Поле: [" + x + "," + y+ "]");
        //System.out.println(this);
            if(eventLog.getLogs().get(EventLog.getNumberOfIteration()) != null) {
                for(String message : eventLog.getLogs().get(EventLog.getNumberOfIteration())) {
                    System.out.println(message);
                }
            }


    }

    public synchronized Animal createAnimalOnField(AnimalType type) {
        Animal animal = switch (type) {
            case WOLF -> new Wolf();
            case BEAR -> new Bear();
            case HORSE -> new Horse();
            case RABBIT -> new Rabbit();
            case SHEEP -> new Sheep();
            case MOUSE -> new Mouse();
            case BOAR -> new Boar();
            case BUFFALO -> new Buffalo();
            case DEAR -> new Dear();
            case DUCK -> new Duck();
            case GOAT -> new Goat();
            case EAGLE -> new Eagle();
            case FOX -> new Fox();
            case SNAKE -> new Snake();
            case CATERPILLAR -> new Caterpillar();
        };
        return addAnimal(animal)?animal:null;
    }




    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Field: ").append(x).append(" ").append(y).append("\n");
        for (AnimalType animalType : AnimalType.values()) {
            for (Animal animal : animalsOnField.get(animalType)) {
                result.append(animal.toString());
                result.append(" ");
            }

        }
        return result.toString();
    }
}
