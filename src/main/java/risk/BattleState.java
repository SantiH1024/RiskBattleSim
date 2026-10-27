package risk;

public class BattleState {
    private int attackerTroops;
    private int defenderTroops;
    private int roundsPlayed;

    public BattleState(int attackerTroops, int defenderTroops) {
        this.attackerTroops = attackerTroops;
        this.defenderTroops = defenderTroops;
        this.roundsPlayed = 0;
    }

    public void recordLosses(int attackerLost, int defenderLost) {
        this.attackerTroops -= attackerLost;
        this.defenderTroops -= defenderLost;
        this.roundsPlayed++;
    }

    public boolean canStillFight() {
        // In RISK, you need at least 2 troops to attack (1 has to stay behind)
        return attackerTroops > 1 && defenderTroops > 0;
    }

    public boolean didAttackerWin() {
        return defenderTroops == 0;
    }

    public int getMaxAttackerDice() {
        return Math.min(3, Math.max(0, attackerTroops - 1));
    }

    public int getMaxDefenderDice() {
        return Math.min(2, Math.max(0, defenderTroops));
    }

    public int getAttackerTroops() {
        return attackerTroops;
    }

    public int getDefenderTroops() {
        return defenderTroops;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }
}
