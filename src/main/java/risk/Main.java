package risk;

public class Main {
    public static void main(String[] args) {
        ConsoleLogger logger = new ConsoleLogger();

        System.out.println("=== Battle 1: All-Out Attack (10 vs 5) ===");
        BattleSimulator sim1 = new BattleSimulator(
                new SixSidedDice(),
                new SixSidedDice(),
                new AllOutStrategy()
        );
        sim1.addObserver(logger);
        sim1.runBattle(10, 5);

        System.out.println("=== Battle 2: Safe Retreat Strategy vs +1 Bunker Defender (10 vs 6) ===");
        Dice bunkerDefenderDice = new PlusOneDieDecorator(new SixSidedDice());
        BattleSimulator sim2 = new BattleSimulator(
                new SixSidedDice(),
                bunkerDefenderDice,
                new SafeRetreatStrategy(6)
        );
        sim2.addObserver(logger);
        sim2.runBattle(10, 6);
    }
}
