package model.battle;

import java.util.Scanner;
import model.characters.Characters;
import model.characters.Mage;
import model.characters.Warrior;
import model.interfaces.Defendable;
import model.interfaces.Healable;
import model.interfaces.Magical;

public class Battle {
    private Characters player1;
    private Characters player2;
    private Characters currentTurn;
    private int turnNumber;
    private int player1PenaltyTurns;
    private int player2PenaltyTurns;
    
    public Battle(Characters player1, Characters player2) {
        this.player1 = player1;
        this.player2 = player2;
        resetPlayersForBattle();
        this.currentTurn = player1;
        this.turnNumber = 1;
        this.player1PenaltyTurns = 0;
        this.player2PenaltyTurns = 0;
    }

    private void resetPlayersForBattle() {
        player1.resetForBattle();
        player2.resetForBattle();
        System.out.println("¡Los personajes han sido restaurados para la batalla!");
    }
    
    public void startBattle(Scanner mainScanner) {
        boolean battleOngoing = true;
        
        System.out.println("\n=== LA BATALLA COMIENZA ===");
        System.out.println("⚔️ " + player1.getName() + " (Elemento: " + player1.getElement() + ") VS " + 
                         player2.getName() + " (Elemento: " + player2.getElement() + ") ⚔️");
        
        while (battleOngoing) {
            showBattleStatus();
            
            Characters opponent = (currentTurn == player1) ? player2 : player1;
            int currentPenalty = (currentTurn == player1) ? player1PenaltyTurns : player2PenaltyTurns;
            
            if (currentPenalty > 0) {
                System.out.println("\n⏳ " + currentTurn.getName() + " está bajo penalización (" + currentPenalty + " turnos restantes)");
                if (currentTurn == player1) player1PenaltyTurns--;
                else player2PenaltyTurns--;
            } else {
                showActionMenu();
                int option = mainScanner.nextInt();
                performAction(option, opponent);
            }
            
            if (!player1.isAlive() || !player2.isAlive()) {
                battleOngoing = false;
                showBattleResult();
            } else {
                switchTurn();
            }
        }
    }
    
    private void showBattleStatus() {
        System.out.println("\n=== TURNO " + turnNumber + " ===");
        System.out.println("👉 Turno de: " + currentTurn.getName());
        printCharacterStatus(player1);
        printCharacterStatus(player2);
    }

    private void printCharacterStatus(Characters character) {
        System.out.println("\n" + character.getName() + ":");
        System.out.println("❤️ HP: " + character.getCurrentHealth() + "/" + character.getMaxHealth());
        showProgressBar("HP", character.getCurrentHealth(), character.getMaxHealth(), 20);
        System.out.println("🔵 Maná: " + character.getCurrentMana() + "/" + character.getMaxMana());
        showProgressBar("Mana", character.getCurrentMana(), character.getMaxMana(), 20);
        if (character.getActiveElement() != null) {
            System.out.println("✨ Elemento activo: " + character.getActiveElement() + 
                             " (" + character.getActiveElementTurns() + " turnos restantes)");
        }
    }
    
    private void showProgressBar(String label, int current, int max, int length) {
        int filled = (int) ((double) current / max * length);
        String color = label.equals("HP") ? "\u001B[32m" : "\u001B[34m";
        System.out.print(color + "[");
        for (int i = 0; i < length; i++) {
            System.out.print(i < filled ? "█" : " ");
        }
        System.out.println("] " + current + "/" + max + "\u001B[0m");
    }
    
    private void showActionMenu() {
        System.out.println("\n🎮 ACCIONES DISPONIBLES:");
        System.out.println("1. ⚔️ Ataque Normal");
        System.out.println("2. 🛡️ Defender");
        if (currentTurn instanceof Magical) System.out.println("3. 🔮 Lanzar Hechizo");
        if (currentTurn instanceof Healable) System.out.println("4. ❤️‍🩹 Curar");
        if (currentTurn instanceof Warrior) System.out.println("5. 💥 Ataque Cargado");
        else if (currentTurn instanceof Mage) System.out.println("5. 🔄 Regenerar Maná");
        System.out.print("👉 Elige una opción: ");
    }
    
    private void performAction(int option, Characters target) {
        switch (option) {
            case 1:
                int damage = currentTurn.attack();
                applyDamage(damage, target);
                break;
            case 2:
                if (currentTurn instanceof Defendable) {
                    int defense = ((Defendable) currentTurn).defend();
                    System.out.println("🛡️ " + currentTurn.getName() + " se defiende (+" + defense + " DEF)");
                } else {
                    System.out.println("❌ " + currentTurn.getName() + " no puede defenderse");
                }
                break;
            case 3:
                if (currentTurn instanceof Magical) {
                    damage = ((Magical) currentTurn).castSpell(target);
                    if (damage > 0) applyDamage(damage, target);
                } else {
                    System.out.println("❌ ¡Opción inválida!");
                }
                break;
            case 4:
                if (currentTurn instanceof Healable) {
                    ((Healable) currentTurn).heal(currentTurn);
                } else {
                    System.out.println("❌ ¡Opción inválida!");
                }
                break;
            case 5:
                if (currentTurn instanceof Warrior) {
                    damage = ((Warrior) currentTurn).chargeAttack();
                    if (damage > 0) applyDamage(damage, target);
                } else if (currentTurn instanceof Mage) {
                    ((Mage) currentTurn).regenerateMana();
                }
                break;
            default:
                System.out.println("⚠️ ¡Opción inválida! Pierdes tu turno.");
        }
    }
    
    private void applyDamage(int baseDamage, Characters target) {
        // Asegurar que el atacante tenga su propio elemento activo
        ElementalSystem.applyElementToAttacker(currentTurn);
        
        // Procesar la reacción elemental y calcular el daño resultante
        int finalDamage = ElementalSystem.processReaction(currentTurn, target, baseDamage);
        
        // Aplicar el daño calculado
        target.receiveDamage(finalDamage);
        System.out.println("☠️ " + target.getName() + " sufre " + finalDamage + " puntos de daño" + 
                         (finalDamage != baseDamage ? " (Base: " + baseDamage + ")" : ""));
    }
    
    public void switchTurn() {
        player1.updateElementStatus();
        player2.updateElementStatus();
        currentTurn = (currentTurn == player1) ? player2 : player1;
        if (currentTurn == player1) turnNumber++;
        System.out.println("\n=================================");
    }
    
    public void applyPenaltyToPlayer(Characters player, int turns) {
        if (player == player1) player1PenaltyTurns = turns;
        else if (player == player2) player2PenaltyTurns = turns;
        System.out.println("⏳ " + player.getName() + " recibe penalización (" + turns + " turnos)");
    }
    
    private void showBattleResult() {
        System.out.println("\n=== 🏁 FIN DE LA BATALLA ===");
        System.out.println("⏲️ Duración: " + (turnNumber - 1) + " turnos");
        if (!player1.isAlive() && !player2.isAlive()) {
            System.out.println("\n🤝 ¡EMPATE! Ambos combatientes han caído");
        } else if (!player1.isAlive()) {
            System.out.println("\n🎉 " + player2.getName() + " ¡GANA LA BATALLA!");
        } else {
            System.out.println("\n🎉 " + player1.getName() + " ¡GANA LA BATALLA!");
        }
        System.out.println("\n📊 ESTADÍSTICAS FINALES:");
        printCharacterStatus(player1);
        printCharacterStatus(player2);
    }
    
    // Getters
    public Characters getPlayer1() { return player1; }
    public Characters getPlayer2() { return player2; }
    public Characters getCurrentTurn() { return currentTurn; }
    public int getTurnNumber() { return turnNumber; }
}