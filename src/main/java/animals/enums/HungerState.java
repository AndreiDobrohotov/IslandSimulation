package animals.enums;

//энам степени голода, для механики питания
public enum HungerState {

    FULL("сытый"),
    SATISFIED("доволен"),
    HUNGRY("голоден");

    private final String state;

    HungerState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}
