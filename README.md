# RISK Battle Simulator

## Overview
This project is a dice and battle simulator based on the board game RISK. Rolling out large battles by hand takes a long time, so this program simulates combat between an attacking and defending army using standard RISK rules (attackers roll up to 3 dice and must leave 1 troop behind, defenders roll up to 2 dice, and defenders win ties).

For the core OO design, `BattleSimulator` codes to abstractions (`Dice`, `AttackStrategy`, `BattleObserver`) and uses constructor dependency injection instead of hardcoding classes or using big `if-else` statements. This also lets us pass in a `FixedDice` class to run repeatable unit tests in `BattleSimulatorTest`.

## Design Patterns Used (Mid-Project Review)

1. **Strategy Pattern (`AttackStrategy` -> `AllOutStrategy`, `SafeRetreatStrategy`)**
   - **Where:** Defined by the `AttackStrategy` interface and passed into `BattleSimulator` through its constructor.
   - **How it's used:** `BattleSimulator` calls `strategy.keepAttacking(state)` and `strategy.getAttackerDice(state)` to decide when to stop rolling and how many dice to use. `AllOutStrategy` attacks until one side runs out of troops, while `SafeRetreatStrategy` stops early if the attacker drops below a set troop count.

2. **Decorator Pattern (`Dice` -> `PlusOneDieDecorator`)**
   - **Where:** `PlusOneDieDecorator` implements the `Dice` interface and wraps another `Dice` object.
   - **How it's used:** In some RISK versions, a leader or bunker gives +1 to the highest die roll. Wrapping a `Dice` object in `PlusOneDieDecorator` boosts the top roll by +1 (up to 6) without changing `SixSidedDice` or adding extra `if` checks inside `BattleSimulator`.

3. **Observer Pattern (`BattleObserver` -> `ConsoleLogger`)**
   - **Where:** `BattleSimulator` stores a list of `BattleObserver` objects and notifies them after each round and at the end of a battle.
   - **How it's used:** `ConsoleLogger` listens to `BattleSimulator` and prints the dice rolls and troop losses to the terminal. This keeps all printing logic separate from the actual battle math.

## To-Do / Future Development
- **Visual Interface (UI):** Build a simple GUI (using Java Swing / Java 2D) that implements `BattleObserver` so users can enter troop counts, click to roll or auto-run battles, and see the results visually instead of just in the terminal.
- **4th Design Pattern:** Add a **Factory** or **Builder** pattern to easily set up different battle presets, dice types, and modifiers from the UI.
- **More Strategies & Probability Modes:** Add more `AttackStrategy` options (like single-round attacks or retreating based on win odds) and custom `Dice` probabilities (weighted dice or running 1,000+ simulations at once to calculate win percentages).
- **Saved Battle History:** Add an observer or file writer to save battle results and win-rate stats to a file.

## How to Run
- **Run simulator:** Run `risk.Main` in IntelliJ or `./gradlew run` in the terminal.
- **Run tests:** Run `BattleSimulatorTest` in IntelliJ or `./gradlew test` in the terminal.