package risk;

import java.util.List;

public class ConsoleLogger implements BattleObserver {
    @Override
    public void onRoundFinished(int roundNum, List<Integer> attRolls, List<Integer> defRolls,
                                int attLost, int defLost, BattleState currentState) {
        System.out.println("Round " + roundNum + ": Attacker rolled " + attRolls
                + " vs Defender " + defRolls
                + " | Lost -> Att: " + attLost + ", Def: " + defLost
                + " | Left -> Att: " + currentState.getAttackerTroops()
                + ", Def: " + currentState.getDefenderTroops());
    }

    @Override
    public void onBattleFinished(BattleState finalState) {
        if (finalState.didAttackerWin()) {
            System.out.println("Result: Attacker conquered the territory in "
                    + finalState.getRoundsPlayed() + " rounds! ("
                    + finalState.getAttackerTroops() + " attackers remaining)\n");
        } else {
            System.out.println("Result: Defender held the territory after "
                    + finalState.getRoundsPlayed() + " rounds! (Attacker: "
                    + finalState.getAttackerTroops() + ", Defender: "
                    + finalState.getDefenderTroops() + ")\n");
        }
    }
}
