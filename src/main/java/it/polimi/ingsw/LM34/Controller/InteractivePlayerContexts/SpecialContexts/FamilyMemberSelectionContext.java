package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Utils.Validator;

import java.util.ArrayList;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT;

/**
 * Created by GiulioComi on 04/06/2017.
 */

/*Called by DiceDependentContext classes*/
public class FamilyMemberSelectionContext extends AbstractGameContext {


    @Override
    public void interactWithPlayer(Player player) {
        /*VOID*/
    }


    public FamilyMember familyMemberSelection(Player player) {
        FamilyMember selectedPawn = null;
        ArrayList<FamilyMember> playerPawns = new ArrayList();
        Integer selected = this.gameManager.getActivePlayerNetworkController().familyMemberSelection(playerPawns);

        try {
            Validator.checkValidity(selected.toString(),playerPawns);
            selectedPawn = playerPawns.get(selected);
            gameManager.getContextByType(INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT).interactWithPlayer(player);
        }
        /*If input mismatches expected informations... the player is able to try again*/
        catch(IncorrectInputException ide){
            familyMemberSelection(player);
        }
        return selectedPawn;
    }
}