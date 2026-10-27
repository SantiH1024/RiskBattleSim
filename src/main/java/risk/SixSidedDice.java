package risk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SixSidedDice implements Dice {
    private final Random random;

    public SixSidedDice() {
        this.random = new Random();
    }

    public SixSidedDice(Random random) {
        this.random = random;
    }

    @Override
    public List<Integer> roll(int numDice) {
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < numDice; i++) {
            results.add(random.nextInt(6) + 1);
        }
        // Sort highest to lowest so we can compare top dice directly
        Collections.sort(results, Collections.reverseOrder());
        return results;
    }
}
