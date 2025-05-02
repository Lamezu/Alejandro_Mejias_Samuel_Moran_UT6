package controller;

import model.characters.Characters;
import model.battle.*;
import model.util.DamageCalculator;
import java.util.Scanner;

public class BattleController {
    private Battle battle;
    private DamageCalculator damageCalculator;
    private boolean isAutoBattle;
    private view.BattleInterface gameView;

    public BattleController(Characters player1, Characters player2, view.BattleInterface gameView) {
        this.battle = new Battle(player1, player2);
        this.damageCalculator = new DamageCalculator();
        this.isAutoBattle = false;
        this.gameView = gameView;
    }

    public BattleController(Characters player1, Characters player2, boolean isAutoBattle) {
        this.battle = new Battle(player1, player2);
        this.damageCalculator = new DamageCalculator();
        this.isAutoBattle = isAutoBattle;
        this.gameView = null;
    }

    public void startBattle() {
        if (isAutoBattle) {
            autoBattle();
        } else {
            Scanner scanner = new Scanner(System.in);
            battle.startBattle(scanner);
        }
    }

    private void autoBattle() {
        System.out.println("=== BATALLA AUTOMÁTICA INICIADA ===");
        
        while (!isBattleOver()) {
            Characters attacker = battle.getCurrentTurn();
            Characters defender = getOpponent(attacker);
            
            // Lógica simple de IA para selección de acciones
            int action = selectAutoAction(attacker);
            performAutoAction(action, attacker, defender);
            
            if (!isBattleOver()) {
                battle.switchTurn();
            }
        }
        
        showBattleResult();
    }

    private int selectAutoAction(Characters character) {
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

    private void performAutoAction(int action, Characters attacker, Characters defender) {
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
        
        Characters attacker = battle.getCurrentTurn();
        Characters defender = getOpponent(attacker);
        
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

    private void applyDamage(int baseDamage, Characters attacker, Characters defender) {
        System.out.println("\n⚡ Elementos en juego:");
        System.out.println("- Atacante (" + attacker.getName() + "): " + 
                        (attacker.getElement() != null ? attacker.getElement() : "Ninguno"));
        System.out.println("- Objetivo (" + defender.getName() + "): " + 
                        (defender.getActiveElement() != null ? defender.getActiveElement() : "Ninguno"));
        ElementalSystem.applyElementToAttacker(attacker);
    
        // Procesar la reacción elemental usando nuestro nuevo sistema
        int finalDamage = ElementalSystem.processReaction(attacker, defender, baseDamage);
                        
        // Aplicar el daño calculado
        defender.receiveDamage(finalDamage);
        
        // Si no hubo reacción y el atacante tiene un elemento, aplicamos el elemento al defensor
        if (finalDamage == baseDamage && attacker.getElement() != null && 
            !attacker.getElement().equalsIgnoreCase("ninguno") && 
            defender.getActiveElement() == null) {
            
            defender.applyElement(attacker.getElement());
        }
        
        // Aplicamos el daño final
        defender.receiveDamage(finalDamage);
        System.out.println("☠️ " + defender.getName() + " sufre " + finalDamage + " puntos de daño" + 
                         (finalDamage != baseDamage ? " (Base: " + baseDamage + ")" : ""));
    }

    public void usePotion(Characters user, Characters target, Potion potion) {
        boolean success = potion.use(user, target);
        if (success && potion.getPenaltyTurns() > 0) {
            battle.applyPenaltyToPlayer(user, potion.getPenaltyTurns());
        }
    }

    public Characters getOpponent(Characters character) {
        return battle.getPlayer1() == character ? battle.getPlayer2() : battle.getPlayer1();
    }

    public boolean isBattleOver() {
        return !battle.getPlayer1().isAlive() || !battle.getPlayer2().isAlive();
    }

    public Characters getWinner() {
        if (!battle.getPlayer1().isAlive() && !battle.getPlayer2().isAlive()) {
            return null; // Empate
        }
        return battle.getPlayer1().isAlive() ? battle.getPlayer1() : battle.getPlayer2();
    }

    public void showBattleResult() {
        Characters winner = getWinner();
        if (winner == null) {
            System.out.println("¡La batalla terminó en empate!");
        } else {
            System.out.println("¡" + winner.getName() + " ha ganado la batalla!");
        }
    }

    // Métodos para la interfaz de usuario
    public Characters getCurrentTurnCharacter() {
        return battle.getCurrentTurn();
    }

    public Characters getPlayer1() {
        return battle.getPlayer1();
    }

    public Characters getPlayer2() {
        return battle.getPlayer2();
    }

    public int getTurnNumber() {
        return battle.getTurnNumber();
    }
}