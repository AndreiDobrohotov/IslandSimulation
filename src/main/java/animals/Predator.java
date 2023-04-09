package animals;

import animals.enums.AnimalType;
import statictic.EventLog;

import java.util.ArrayList;
import java.util.List;

public abstract class Predator extends Animal {

    public Predator(int count) {
        super(count);
    }

    @Override
    public void eat() {
        //повышаем уровень голода
        increaseHunger();
        if (isAlive) {
            List<Animal> preys = new ArrayList<>();
            //добавляем всех животных с поля подходящих под рацион в список жертв
            for (AnimalType type : getFood().keySet()) {
                preys.addAll(getCurrentField().getAnimalsOnField(type));
            }
            //если подходящие жертвы нашлись
            if (preys.size() > 0) {
                //выбираем случайную жертву
                Animal prey = preys.get(getRandom().nextInt(preys.size()));
                //начинаем охоту
                int chance = random.nextInt(100) + 1;
                int success = food.get(prey.getType());
                if (chance <= success) {
                    //если охото прошла удачно - понижаем уровень голода, передавая вес жертвы
                    decreaseHunger(prey.weight);
                    prey.isAlive = false;
                    prey.die();
                    EventLog.animalAteAnimal(this, prey);
                } else {
                    EventLog.animalRanAwayFrom(prey, this);
                }
            }
            else {
                EventLog.animalNoPray(this);
            }
        }
    }
}
