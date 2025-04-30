package controller;

import model.characters.Character;
import model.battle.*;
import model.util.DamageCalculator;

public class BattleController {
    private Battle battle;
    private DamageCalculator damageCalculator;
    private boolean isAutoBattle;

    public BattleController(Character player1, Character player2, boolean isAutoBattle) {
        this.battle = new Battle(player1, player2);
        this.damageCalculator = new DamageCalculator();
        this.isAutoBattle = isAutoBattle;
    }

    public void startBattle() {
        if (isAutoBattle) {
            autoBattle();
        } else {
            battle.startBattle();
        }
    }

    private void autoBattle() {
        System.out.println("=== BATALLA AUTOMÁTICA INICIADA ===");
        
        while (!isBattleOver()) {
            Character attacker = battle.getCurrentTurn();
            Character defender = getOpponent(attacker);
            
            // Lógica simple de IA para selección de acciones
            int action = selectAutoAction(attacker);
            performAutoAction(action, attacker, defender);
            
            if (!isBattleOver()) {
                battle.switchTurn();
            }
        }
        
        showBattleResult();
    }

    private int selectAutoAction(Character character) {
        // Lógica simple para seleccionar acción automáticamente
        if (character.getCurrentHealth() < character.getMaxHealth() * 0.3 
            && character instanceof model.interfaces.Healable) {
            return 4; // Curar
        } else if (character.getCurrentMana() > 30 
                   && character instanceof model.interfaces.Magical) {
            return 3; // Lanzar hechizo
        } else if (character instanceof model.characters.Warrior 
                   && Math.random() > 0.7) {
            return 5; // Ataque cargado
        } else {
            return 1; // Ataque normal
        }
    }

    private void performAutoAction(int action, Character attacker, Character defender) {
        switch (action) {
            case 1:
                int damage = attacker.attack();
                applyDamage(damage, attacker, defender);
                break;
                
            case 2:
                if (attacker instanceof model.interfaces.Defendable) {
                    ((model.interfaces.Defendable)attacker).defend();
                }
                break;
                
            case 3:
                if (attacker instanceof model.interfaces.Magical) {
                    damage = ((model.interfaces.Magical)attacker).castSpell(defender);
                    applyDamage(damage, attacker, defender);
                }
                break;
                
            case 4:
                if (attacker instanceof model.interfaces.Healable) {
                    ((model.interfaces.Healable)attacker).heal(attacker);
                }
                break;
                
            case 5:
                if (attacker instanceof model.characters.Warrior) {
                    damage = ((model.characters.Warrior)attacker).chargeAttack();
                    applyDamage(damage, attacker, defender);
                } else if (attacker instanceof model.characters.Mage) {
                    ((model.characters.Mage)attacker).regenerateMana();
                }
                break;
        }
    }

    public void playerAction(int action) {
        if (isBattleOver()) return;
        
        Character attacker = battle.getCurrentTurn();
        Character defender = getOpponent(attacker);
        
        switch (action) {
            case 1: // Ataque normal
                int damage = attacker.attack();
                applyDamage(damage, attacker, defender);
                break;
                
            case 2: // Defender
                if (attacker instanceof model.interfaces.Defendable) {
                    ((model.interfaces.Defendable)attacker).defend();
                }
                break;
                
            case 3: // Lanzar hechizo
                if (attacker instanceof model.interfaces.Magical) {
                    damage = ((model.interfaces.Magical)attacker).castSpell(defender);
                    applyDamage(damage, attacker, defender);
                }
                break;
                
            case 4: // Curar
                if (attacker instanceof model.interfaces.Healable) {
                    ((model.interfaces.Healable)attacker).heal(attacker);
                }
                break;
                
            case 5: // Acción especial
                if (attacker instanceof model.characters.Warrior) {
                    damage = ((model.characters.Warrior)attacker).chargeAttack();
                    applyDamage(damage, attacker, defender);
                } else if (attacker instanceof model.characters.Mage) {
                    ((model.characters.Mage)attacker).regenerateMana();
                }
                break;
                
            case 6: // Usar poción
                // Implementación para usar pociones
                break;
        }
        
        if (!isBattleOver()) {
            battle.switchTurn();
        }
    }

    private void applyDamage(int baseDamage, Character attacker, Character defender) {
        int finalDamage = Reaction.calculateDamageWithReaction(attacker, defender, baseDamage);
        defender.receiveDamage(finalDamage);
        System.out.println(attacker.getName() + " inflige " + finalDamage + " daño a " + defender.getName());
    }

    public void usePotion(Character target, Potion potion) {
        potion.use(target);
        if (potion.hasPenalty()) {
            battle.applyPenaltyToPlayer(target, potion.getPenaltyTurns());
        }
    }

    public Character getOpponent(Character character) {
        return battle.getPlayer1() == character ? battle.getPlayer2() : battle.getPlayer1();
    }

    public boolean isBattleOver() {
        return !battle.getPlayer1().isAlive() || !battle.getPlayer2().isAlive();
    }

    public Character getWinner() {
        if (!battle.getPlayer1().isAlive() && !battle.getPlayer2().isAlive()) {
            return null; // Empate
        }
        return battle.getPlayer1().isAlive() ? battle.getPlayer1() : battle.getPlayer2();
    }

    public void showBattleResult() {
        Character winner = getWinner();
        if (winner == null) {
            System.out.println("¡La batalla terminó en empate!");
        } else {
            System.out.println("¡" + winner.getName() + " ha ganado la batalla!");
        }
    }

    // Métodos para la interfaz de usuario
    public Character getCurrentTurnCharacter() {
        return battle.getCurrentTurn();
    }

    public Character getPlayer1() {
        return battle.getPlayer1();
    }

    public Character getPlayer2() {
        return battle.getPlayer2();
    }

    public int getTurnNumber() {
        return battle.getTurnNumber();
    }
}