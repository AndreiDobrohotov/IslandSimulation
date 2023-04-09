package animals.enums;

import java.util.concurrent.ThreadLocalRandom;

//энам с типом животных
public enum AnimalType {

    BEAR("Медведь"),
    WOLF("Волк"),
    FOX("Лиса"),
    EAGLE("Орёл"),
    SNAKE("Змея"),

    BUFFALO("Буйвол"),
    HORSE("Лошадь"),
    BOAR("Кабан"),
    DEAR("Олень"),
    GOAT("Коза"),

    SHEEP("Овца"),
    DUCK("Утка"),
    RABBIT("Кролик"),
    MOUSE("Мышь"),
    CATERPILLAR("Гусеница");


    private final String name;

    AnimalType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //возвращает случайный тип хищника (от 0 до 4)
    public static AnimalType getRandomPredatorType() {
        return AnimalType.values()[ThreadLocalRandom.current().nextInt(5)];
    }
    //возвращает случайный типа травоядного (от 5 до 14)
    public static AnimalType getRandomHerbivorousType() {
        return AnimalType.values()[ThreadLocalRandom.current().nextInt(10)+5];
    }
}
