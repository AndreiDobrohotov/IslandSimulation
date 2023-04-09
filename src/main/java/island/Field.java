package island;

import animals.Animal;
import animals.enums.AnimalType;
import animals.herbivorous.*;
import animals.predators.*;
import lombok.Getter;
import lombok.Setter;
import statictic.EventLog;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

//класс описывающий одно поле на острове
@Getter
public class Field {

    private final Map<AnimalType, List<Animal>> animalsOnField;
    private final int x;
    private final int y;
    @Setter private FieldType type;
    private final EventLog eventLog;
    private double grass;
    @Setter private int animalsEaten;
    @Setter private int animalsBorn;
    @Setter private int animalsDiedOfHunger;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        animalsOnField = new HashMap<>();
        for (AnimalType type : AnimalType.values()) {
            animalsOnField.put(type, new ArrayList<>());
        }
        eventLog = new EventLog();
    }

    //методы для подсчета статистики рождения и смерти. возвращают значение в зависимости от типа животного
    public synchronized void incrementAnimalsBornCount(){animalsBorn++;}
    public synchronized void incrementAnimalsDiedOfHunger(){animalsDiedOfHunger++;}
    public synchronized void incrementAnimalsEaten(){animalsEaten++;}

    //метод добавления животного на поле. Если лимит превышен то животное не добавляется
    public synchronized boolean addAnimal(Animal animal) {
        if (animalsOnField.get(animal.getType()).size() < animal.getLimit()) {
            animalsOnField.get(animal.getType()).add(animal);
            animal.setCurrentField(this);
            return true;
        } else {
            return false;
        }
    }

    //удаляет животное из поля
    public synchronized void removeAnimal(Animal animal) {
        animalsOnField.get(animal.getType()).remove(animal);
    }

    //получает список животных на поле определенного типа
    public synchronized List<Animal> getAnimalsOnField(AnimalType type) {
        return animalsOnField.get(type);
    }

    //получает количестве животных на поле определенного типа
    public int getAnimalsCountOnField(AnimalType type) {
        return animalsOnField.get(type).size();
    }

    //выращивает случайное количество травы, в зависимости от типа местности
    public synchronized void growGrass() {
        grass = Math.min(grass + ThreadLocalRandom.current().nextInt(type.getGrassGrowth()) + type.getGrassGrowth(), type.getGrassLimit());
    }

    //съесть определенно число травы. Если осталось меньше чем требуется, возвращает остаток.
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


    //создать новое животное по типу и сразу добавить его на поле.
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

}
