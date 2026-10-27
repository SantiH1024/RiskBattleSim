package risk;

public class AllOutStrategy implements AttackStrategy {
    @Override
    public boolean keepAttacking(BattleState state) {
        return state.canStillFight();
    }

    @Override
    public int getAttackerDice(BattleState state) {
        return state.getMaxAttackerDice();
    }
}
