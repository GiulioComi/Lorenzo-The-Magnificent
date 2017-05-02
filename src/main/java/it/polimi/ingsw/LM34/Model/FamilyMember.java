package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Model.Enum.DiceColor;
import it.polimi.ingsw.LM34.Model.Enum.PawnColor;

/**
 * Created by Giulio Comi on 5/2/2017.
 */
public class FamilyMember {
    private final PawnColor pawnColor;
    private DiceColor diceColor;
    private boolean neutral; //used to set the Pawn as a neutral one
    private Integer value;
    private boolean isUsed;


    //constructor used only at the beginning of the game during setup up
    FamilyMember(PawnColor pawnColor, DiceColor diceColor, boolean neutral) {
        this.pawnColor = pawnColor;
        this.diceColor = diceColor;
        this.neutral = neutral;
        this.isUsed = false;

    }
    //check if this pawn is the neutral one
    public boolean isNeutral() {
        return this.neutral;
    }

    //used to verify if a action is allowed based on the corresponding dice value
    public Integer getValue() {
        return this.value;
    }

    //the controller updates the family member values as soon as the corresponding dice is rolled at the beginning of each turn
    public void setValue(Integer diceValue) {
        this.value = diceValue;
    }

    //mark the FamilyMember as used after it has been placed in a action slot
    public void placePawn () {
        this.isUsed = true;
    }

    //the controller calls this at the end of each turn to "return" to players their pawns
    public void freePawn () {
        this.isUsed = false;
    }

    //check availability of this family member
    public boolean isUsed() {
        return this.isUsed;
    }
}