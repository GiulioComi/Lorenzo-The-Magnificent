package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Exception.Model.InvalidResourceTypeException;
import it.polimi.ingsw.LM34.Model.Board.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Enum.DiceColor;
import it.polimi.ingsw.LM34.Model.Enum.PawnColor;
import it.polimi.ingsw.LM34.Model.Enum.ResourceType;

import java.util.ArrayList;

/**
 * Created by Giulio Comi on 03/05/2017.
 */
public class Player {
    private final PawnColor pawnColor; //COLOR OF THE PAWN ASSOCIATED TO THE PLAYER
    private ArrayList<FamilyMember>  familyMembers;
    private PersonalBoard personalBoard;
    private ArrayList <LeaderCard> leadercards;


    public Player(PawnColor pawnColor, PersonalBoard personalBoard) {
        this.pawnColor= pawnColor;
        this.personalBoard= personalBoard;

        familyMembers= new ArrayList<FamilyMember>();
        for ( DiceColor diceColor : DiceColor.values())
            familyMembers.add(new FamilyMember(pawnColor, diceColor));
            familyMembers.add(new FamilyMember(pawnColor, true));
    }


    //the controller updates the resources and bonuses of the player directly in the personalBoard;
    public PersonalBoard getPersonalBoard() {
        return this.personalBoard;
    }

}