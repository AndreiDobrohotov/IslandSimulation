package animals;

import animals.Animal;
import animals.enums.AnimalType;
import island.EventLog;

import java.util.ArrayList;
import java.util.List;

public abstract class Herbivorous extends Animal {

    public Herbivorous(int count) {
        super(count);
    }

    @Override
    public void eat() {
        //повышаем уровень голода
        increaseHunger();
        if (isAlive) {
            double eaten = 0;
            //если питается чем-то кроме травы
            if (!food.isEmpty()) {
                List<Animal> preys = new ArrayList<>();
                //добавляем всех животных с поля подходящим под рацион в список жертв
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
                        //если охото прошла удачно, добавляем вес жертвы к съеденному
                        eaten += prey.weight;
                        prey.isAlive = false;
                        prey.die();
                        EventLog.animalAteAnimal(this, prey);
                    } else {
                        EventLog.animalRanAwayFrom(prey, this);
                    }
                }
            }
            //если съеденного достаточно для насыщения, понижаем уровень голода
            if (eaten >= mustEat) {
                decreaseHunger(eaten);
            }
            //если съели не достаточно, восполняем недостаток травой
            else {
                //eatSomeGrass вернет сколько травы было съедено
                double eatenGrass = currentField.eatSomeGrass(mustEat - eaten);
                if(eatenGrass > 0) {
                    decreaseHunger(eatenGrass);
                    EventLog.animalAteGrass(this, eatenGrass);
                }
                else if(eatenGrass == 0 && eaten == 0){
                    EventLog.animalNoGrass(this);
                }
            }
        }
    }
}
