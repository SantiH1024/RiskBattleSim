package risk;

public class SafeRetreatStrategy implements AttackStrategy {
    private final int minTroopsToKeep;

    public SafeRetreatStrategy(int minTroopsToKeep) {
        this.minTroopsToKeep = minTroopsToKeep;
    }

    @Override
    public boolean keepAttacking(BattleState state) {
        return state.canStillFight() && state.getAttackerTroops() >= minTroopsToKeep;
    }

    @Override
    public int getAttackerDice(BattleState state) {
        return state.getMaxAttackerDice();
    }
}
