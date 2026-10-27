package risk;

import java.util.ArrayList;
import java.util.List;

public class BattleSimulator {
    private final Dice attackerDice;
    private final Dice defenderDice;
    private final AttackStrategy strategy;
    private final List<BattleObserver> observers;

    // Dependency Injection via constructor
    public BattleSimulator(Dice attackerDice, Dice defenderDice, AttackStrategy strategy) {
        this.attackerDice = attackerDice;
        this.defenderDice = defenderDice;
        this.strategy = strategy;
        this.observers = new ArrayList<>();
    }

    public void addObserver(BattleObserver observer) {
        observers.add(observer);
    }

    public BattleState runBattle(int startingAttackers, int startingDefenders) {
        if (startingAttackers < 2 || startingDefenders < 1) {
            throw new IllegalArgumentException("Need at least 2 attackers and 1 defender.");
        }

        BattleState state = new BattleState(startingAttackers, startingDefenders);

        while (strategy.keepAttacking(state)) {
            playSingleRound(state);
        }

        for (BattleObserver obs : observers) {
            obs.onBattleFinished(state);
        }

        return state;
    }

    private void playSingleRound(BattleState state) {
        int numAttDice = Math.min(strategy.getAttackerDice(state), state.getMaxAttackerDice());
        int numDefDice = state.getMaxDefenderDice();

        List<Integer> attRolls = attackerDice.roll(numAttDice);
        List<Integer> defRolls = defenderDice.roll(numDefDice);

        int diceToCompare = Math.min(attRolls.size(), defRolls.size());
        int attLost = 0;
        int defLost = 0;

        for (int i = 0; i < diceToCompare; i++) {
            if (attRolls.get(i) > defRolls.get(i)) {
                defLost++;
            } else {
                // Defender wins ties in RISK
                attLost++;
            }
        }

        state.recordLosses(attLost, defLost);

        for (BattleObserver obs : observers) {
            obs.onRoundFinished(state.getRoundsPlayed(), attRolls, defRolls, attLost, defLost, state);
        }
    }
}
