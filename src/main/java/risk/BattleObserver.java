package risk;

import java.util.List;

public interface BattleObserver {
    void onRoundFinished(int roundNum, List<Integer> attRolls, List<Integer> defRolls,
                         int attLost, int defLost, BattleState currentState);
    void onBattleFinished(BattleState finalState);
}
