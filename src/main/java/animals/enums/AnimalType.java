package animals.enums;

import java.util.concurrent.ThreadLocalRandom;

public enum AnimalType {

    BEAR("Медведь"),
    EAGLE("Орёл"),
    FOX("Лиса"),
    SNAKE("Змея"),
    WOLF("Волк"),

    BOAR("Кабан"),
    BUFFALO("Буйвол"),
    CATERPILLAR("Гусеница"),
    DEAR("Олень"),
    DUCK("Утка"),
    GOAT("Коза"),
    HORSE("Лошадь"),
    MOUSE("Мышь"),
    RABBIT("Кролик"),
    SHEEP("Овца");

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
