package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Model.Board.GameBoard.GameSpace;
import it.polimi.ingsw.LM34.Model.Enum.DiceColor;
import java.util.Random;

/**
 * Created by Giulio Comi on 5/2/2017.
 */
public class Dice {
    private Random rand = new Random();
    private final DiceColor color;
    private Integer value = rand.nextInt(6)+1;

    //Constructor
    public Dice(DiceColor color) {
        this.color = color;
    }

    public DiceColor getColor() {
        return this.color;
    }

    public Integer getValue() {
        return value;
    }

    //method used to set a new value for the Dice; this solution is preferred for sake of simplicity and integrity
    //against passing the new value from outside this class
    public void rollDice(){
        this.value = rand.nextInt(6)+1;
    }

}