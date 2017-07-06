package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.FamilyMemberSelectionContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.UseCouncilPrivilegeContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Cards.TerritoryCard;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import java.util.logging.Level;
import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.*;
import static it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor.GREEN;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class HarvestAreaContext extends AbstractGameContext {

    public HarvestAreaContext() {
        this.contextType = HARVEST_AREA_CONTEXT;
    }

    @Override
    public Void interactWithPlayer(Object... args) throws IncorrectInputException, OccupiedSlotException {
        Integer selectedSlot;
        try {
            selectedSlot = (Integer) args[0];
        } catch(Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            throw new IncorrectInputException();
        }

        if(selectedSlot > this.gameManager.getHarvestArea().getActionSlots().size() - 1)
            throw new IncorrectInputException();

        ActionSlot slot = this.gameManager.getHarvestArea().getActionSlots().get(selectedSlot);

        ActionSlotContext actionSlotContext = (ActionSlotContext) getContextByType(ACTION_SLOT_CONTEXT);
        Boolean canPlace = actionSlotContext.interactWithPlayer(this, slot);
        if (canPlace) {
            FamilyMember selectedFamilyMember = ((FamilyMemberSelectionContext) getContextByType(FAMILY_MEMBER_SELECTION_CONTEXT)).interactWithPlayer(slot.getDiceValue(), true, this.contextType);

            if (actionSlotContext.getIgnoreOccupiedSlot())
                slot.insertFamilyMemberIgnoringSlotLimit(selectedFamilyMember);
            else
                slot.insertFamilyMember(selectedFamilyMember);
            selectedFamilyMember.placePawn();

            if(this.getCurrentPlayer().getPersonalBoard().getPersonalBonusTile().getHarvestDiceValue() <= selectedFamilyMember.getValue()) {
                ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).initIncome();
                ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).setIncome(this.getCurrentPlayer().getPersonalBoard().getPersonalBonusTile().getHarvestBonus().getResources());
                ((UseCouncilPrivilegeContext) getContextByType(USE_COUNCIL_PRIVILEGE_CONTEXT)).interactWithPlayer(this.getCurrentPlayer().getPersonalBoard().getPersonalBonusTile().getHarvestBonus().getCouncilPrivilege());
                ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).interactWithPlayer();
            }

            ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).initIncome();
            this.getCurrentPlayer().getPersonalBoard().getDevelopmentCardsByType(GREEN).ifPresent(list ->
                list.forEach(card -> {
                    if (((TerritoryCard) card).getDiceValueToHarvest() <= selectedFamilyMember.getValue())
                        card.getPermanentBonus().applyEffect(this);
                })
            );
            ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).interactWithPlayer();
        }
        else
            throw new OccupiedSlotException();

        return null;
    }


}
