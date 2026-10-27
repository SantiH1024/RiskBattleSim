package risk;

public interface AttackStrategy {
    boolean keepAttacking(BattleState state);
    int getAttackerDice(BattleState state);
}
