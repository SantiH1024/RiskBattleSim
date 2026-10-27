package risk;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BattleSimulatorTest {

    // Simple fake dice class so we can test exact rolls without randomness
    static class FixedDice implements Dice {
        private final List<Integer> presetRolls;

        public FixedDice(List<Integer> presetRolls) {
            this.presetRolls = presetRolls;
        }

        @Override
        public List<Integer> roll(int numDice) {
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < Math.min(numDice, presetRolls.size()); i++) {
                result.add(presetRolls.get(i));
            }
            Collections.sort(result, Collections.reverseOrder());
            return result;
        }
    }

    @Test
    void testDefenderWinsTies() {
        // Both roll 5 and 4 -> Defender wins ties, so Attacker loses 2 troops
        Dice attDice = new FixedDice(List.of(5, 4, 1));
        Dice defDice = new FixedDice(List.of(5, 4));
        BattleSimulator sim = new BattleSimulator(attDice, defDice, new SafeRetreatStrategy(4));

        BattleState result = sim.runBattle(5, 2);

        assertFalse(result.didAttackerWin());
        assertEquals(3, result.getAttackerTroops());
        assertEquals(2, result.getDefenderTroops());
        assertEquals(1, result.getRoundsPlayed());
    }

    @Test
    void testAttackerWinsBattleWhenDefendersHitZero() {
        // Attacker rolls 6, 5 vs Defender 4, 3 -> Defender loses both troops
        Dice attDice = new FixedDice(List.of(6, 5, 2));
        Dice defDice = new FixedDice(List.of(4, 3));
        BattleSimulator sim = new BattleSimulator(attDice, defDice, new AllOutStrategy());

        BattleState result = sim.runBattle(4, 2);

        assertTrue(result.didAttackerWin());
        assertEquals(4, result.getAttackerTroops());
        assertEquals(0, result.getDefenderTroops());
        assertEquals(1, result.getRoundsPlayed());
    }

    @Test
    void testSplitRoundLosses() {
        // Attacker [6, 2, 1] vs Defender [5, 3] -> 6 beats 5 (Def -1), 2 loses to 3 (Att -1)
        Dice attDice = new FixedDice(List.of(6, 2, 1));
        Dice defDice = new FixedDice(List.of(5, 3));
        BattleSimulator sim = new BattleSimulator(attDice, defDice, new SafeRetreatStrategy(4));

        BattleState result = sim.runBattle(4, 2);

        assertEquals(3, result.getAttackerTroops());
        assertEquals(1, result.getDefenderTroops());
    }

    @Test
    void testPlusOneDecoratorTurnsDefenderLossIntoTieWin() {
        // Attacker rolls 5, Defender rolls 4 but has +1 decorator -> becomes 5 and wins the tie
        Dice attDice = new FixedDice(List.of(5));
        Dice defDice = new PlusOneDieDecorator(new FixedDice(List.of(4)));
        BattleSimulator sim = new BattleSimulator(attDice, defDice, new AllOutStrategy());

        BattleState result = sim.runBattle(2, 1);

        assertFalse(result.didAttackerWin());
        assertEquals(1, result.getAttackerTroops());
        assertEquals(1, result.getDefenderTroops());
    }

    @Test
    void testSafeRetreatStrategyStopsAttackEarly() {
        // Attacker starts with 6, loses 2 in round 1 (down to 4), and retreats because threshold is 5
        Dice attDice = new FixedDice(List.of(1, 1, 1));
        Dice defDice = new FixedDice(List.of(6, 6));
        BattleSimulator sim = new BattleSimulator(attDice, defDice, new SafeRetreatStrategy(5));

        BattleState result = sim.runBattle(6, 5);

        assertEquals(1, result.getRoundsPlayed());
        assertEquals(4, result.getAttackerTroops());
        assertEquals(5, result.getDefenderTroops());
    }
}
