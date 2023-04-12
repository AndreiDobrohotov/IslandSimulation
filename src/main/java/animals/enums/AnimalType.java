package animals.enums;

import java.util.concurrent.ThreadLocalRandom;

//энам с типом животных
public enum AnimalType {

    BEAR("Медведь","Медведица"),
    WOLF("Волк","Волчица"),
    FOX("Лис","Лиса"),
    EAGLE("Орёл","Орлица"),
    SNAKE("Змей","Змея"),

    BUFFALO("Буйвол","Буйволица"),
    HORSE("Конь","Кобыла"),
    BOAR("Кабан","Свиноматка"),
    DEAR("Олень","Олениха"),
    GOAT("Козёл","Коза"),
    SHEEP("Баран","Овца"),
    DUCK("Селезень","Утка"),
    RABBIT("Кролик","Крольчиха"),
    MOUSE("Мыш","Мышь"),
    CATERPILLAR("Гусеница");


    private final String maleName;
    private final String femaleName;

    AnimalType(String maleName, String femaleName) {
        this.maleName = maleName;
        this.femaleName = femaleName;
    }

    AnimalType(String sameName) {
        this.maleName = sameName;
        this.femaleName = sameName;
    }

    public String getName(Gender gender) {
        return gender == Gender.MALE ? maleName : femaleName;
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
