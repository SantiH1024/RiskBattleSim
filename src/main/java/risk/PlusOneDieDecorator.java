package risk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Decorator Pattern: adds +1 to the highest die (up to 6) for leader/bunker bonuses
public class PlusOneDieDecorator implements Dice {
    private final Dice wrappedDice;

    public PlusOneDieDecorator(Dice wrappedDice) {
        this.wrappedDice = wrappedDice;
    }

    @Override
    public List<Integer> roll(int numDice) {
        List<Integer> rolls = new ArrayList<>(wrappedDice.roll(numDice));
        if (!rolls.isEmpty()) {
            int highest = rolls.get(0);
            rolls.set(0, Math.min(6, highest + 1));
            Collections.sort(rolls, Collections.reverseOrder());
        }
        return rolls;
    }
}
