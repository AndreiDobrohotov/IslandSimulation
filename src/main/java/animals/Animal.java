package animals;

import animals.enums.AnimalType;
import animals.enums.Gender;
import animals.enums.HungerState;
import lombok.EqualsAndHashCode;
import statictic.EventLog;
import island.Field;
import lombok.Getter;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@EqualsAndHashCode
@Getter
public abstract class Animal {

    private final static Properties props = new Properties();

    static {
        try (FileInputStream inputStream = new FileInputStream("src/main/resources/Animal.properties")) {
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
    protected int offspring;
    protected boolean isAlive;
    protected boolean isPregnant;
    protected Map<AnimalType, Integer> food;


    public Animal(int count) {
        //Получаем тип животного из enum через название класса
        type = AnimalType.valueOf(getClass().getSimpleName().toUpperCase());
        //Получаем пол животного исходя из значения счетчина при создании животного
        gender = count % 2 == 0 ? Gender.FEMALE : Gender.MALE;
        name = type.getName(gender) + "(" + count+")";
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
            offspring = Integer.parseInt(props.getProperty(className + ".offspring"));
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
        if (speed == 0) return;
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
        //если все поля заняты, пытаемся вернутся на первоначальное поле
        if (currentField.addAnimal(this)) {
            EventLog.animalCameToTheField(this);
        }
        //если и оно занято, считаем что животное умерло по неизвестной причине

    }


    //реализация в классах наследниках
    public abstract void eat();


    //метод отвечает за размножение животных
    public void reproduction() {
        //если самец - ищем самку и меняем ее состояние на "беременна"
        if (gender == Gender.MALE) {
            for (Animal animal : currentField.getAnimalsOnFieldByType(type)) {
                if (animal.gender == Gender.FEMALE && !animal.isPregnant) {
                    animal.isPregnant = true;
                    EventLog.animalMated(this, animal);
                    return;
                }
            }
            EventLog.animalNoPartner(this);
        } else {
            //если самка - пытаемся создать случайное число детенышей в текущем поле
            if (isPregnant) {
                isPregnant = false;
                int count = ThreadLocalRandom.current().nextInt(offspring+1);
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
                EventLog.animalDiedOfHunger(this);
            }
        } else {
            hungerState = HungerState.values()[hungerState.ordinal() + 1];
        }

    }

    //если умирает от голода или от хищника, удаляем из текущего поля


    @Override
    public String toString() {
        return name;
    }

}
