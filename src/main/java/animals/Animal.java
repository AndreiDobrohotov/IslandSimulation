package animals;

import animals.enums.AnimalType;
import animals.enums.Gender;
import animals.enums.HungerState;
import animals.herbivorous.*;
import animals.predators.*;
import island.EventLog;
import island.Field;
import lombok.Getter;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


@Getter
public abstract class Animal {

    private final static Properties props = new Properties();

    static {
        try (FileInputStream inputStream = new FileInputStream("src/main/resources/config.properties")) {
            props.load(inputStream);
        } catch (IOException ignored) {
        }
    }

    protected final ThreadLocalRandom random;
    protected final Gender gender;
    protected final String name;
    protected final AnimalType type;
    @Setter
    protected Field currentField;
    protected HungerState hungerState;
    protected double mustEat;
    protected double weight;
    protected int speed;
    protected int limit;
    protected boolean isAlive;
    protected boolean isPregnant;
    protected Map<AnimalType, Integer> food;


    public Animal(int count) {
        //Получаем тип животного из enum через название класса
        type = AnimalType.valueOf(getClass().getSimpleName().toUpperCase());
        //Получаем пол животного исходя из значения счетчина при создании животного
        gender = count % 2 == 0 ? Gender.FEMALE : Gender.MALE;
        name = type.getName() + "[" + gender + "]" + "-" + count;
        hungerState = HungerState.FULL;
        isAlive = true;
        isPregnant = false;
        random = ThreadLocalRandom.current();
        try {
            String className = getClass().getSimpleName().toLowerCase();
            speed = Integer.parseInt(props.getProperty(className + ".speed"));
            weight = Double.parseDouble(props.getProperty(className + ".weight"));
            mustEat = Double.parseDouble(props.getProperty(className + ".mustEat"));
            limit = Integer.parseInt(props.getProperty(className + ".limit"));
            food = Arrays.stream(props.getProperty(className + ".food").split(","))
                    .filter(str -> str.contains(":"))
                    .map(str -> str.split(":"))
                    .collect(Collectors.toMap(arr -> AnimalType.valueOf(arr[0]), arr -> Integer.parseInt(arr[1])));
        } catch (Exception ignored) {
        }

    }


    //метод отвечает за перемещение животного
    //в качестве аргумета приходит перетасованный список полей, куда можно пойти
    public void move(List<Field> canGoTo) {
        //сразу удаляем животное из текущего поля, чтобы освободить место
        currentField.removeAnimal(this);
        EventLog.animalLeftTheField(this);
        //проходимся по всем полям и пытаемся переместиться
        for (Field field : canGoTo) {
            //если поле занято, идем на следующее поле
            if (field.addAnimal(this)) {
                EventLog.animalCameToTheField(this);
                return;
            }
        }
        //если все доступные поля оказались заняты, считаем что животное умерло по неизвестной причине
    }


    //реализация в классах наследниках
    public abstract void eat();


    public void reproduction(List<Animal> animalList) {
        if (gender == Gender.MALE) {
            for (Animal animal : animalList) {
                if (type == animal.getType() && animal.gender == Gender.FEMALE && !animal.isPregnant) {
                    animal.isPregnant = true;
                    EventLog.animalMated(this, animal);
                    return;
                }
            }
            EventLog.animalNoPartner(this);
        } else {
            if (isPregnant) {
                isPregnant = false;
                int count = ThreadLocalRandom.current().nextInt(2)+1;
                if (count == 0) {
                    EventLog.animalNoChild(this);
                } else {
                    for (int i = 0; i < count; i++) {
                        Animal child = getCurrentField().createAnimalOnField(this.getType());
                        if (child != null) {
                            EventLog.animalGaveABirth(this, child);
                        }
                    }
                }
            }
        }
    }


    @Override
    public String toString() {
        return name;
    }

    //метод получает вес съеденой пиши и насыщает животное (полностью или частично)
    protected void decreaseHunger(double weightOfFood) {
        if (weightOfFood >= mustEat) {
            hungerState = HungerState.FULL;
        } else if (hungerState != HungerState.FULL) {
            hungerState = HungerState.values()[hungerState.ordinal() - 1];
        }
    }

    //метод усиливает голод
    //выполняется перед началом питания
    protected void increaseHunger() {
        if (hungerState == HungerState.HUNGRY) {
            if (isAlive) {
                isAlive = false;
                die();
                EventLog.animalDiedOfHunger(this);
            }
        } else {
            hungerState = HungerState.values()[hungerState.ordinal() + 1];
        }

    }

    protected void die() {
        currentField.removeAnimal(this);
    }


}
