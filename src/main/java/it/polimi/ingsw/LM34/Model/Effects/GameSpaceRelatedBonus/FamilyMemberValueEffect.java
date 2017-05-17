package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Enums.DiceColor;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/13/2017.
 *
 * This class aggregates instant and permanent effects from different development card types, leader cards and
 * excomunication tiles, all of which are related to the context where a player places a family member in a game space so that
 * the dice values associated to his pawns has to be incremented or decreased by the effects of the cards mentioned above
 */
public class FamilyMemberValueEffect extends AbstractEffect implements Observer {

    /**
     * if null then the value is related to the neutral family member
     * if MULTICOLOR, the value is applied to all the dices
     */
    private DiceColor color; //keep track on what dice the effect is applied to

    private Integer value;

    /**
     * indicates if the value is:
     *      absolute (substitutes the 'old' value)
     *      relative (adds/subs to 'old' value)
     */
    private Boolean relative;

    public FamilyMemberValueEffect(DiceColor color, Integer value, Boolean relative) {
        this.color = color;
        this.value = value;
        this.relative = relative;
    }

    public DiceColor getDiceColor() {
        return this.color;
    }

    public Integer getValue() {
        return this.value;
    }

    public Boolean isRelative() {
        return this.relative;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}