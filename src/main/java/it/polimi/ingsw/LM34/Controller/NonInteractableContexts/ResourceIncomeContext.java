package it.polimi.ingsw.LM34.Controller.NonInteractableContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 16/05/2017.
 */

//This context does not provide an interaction with the player but it is important
// for the effects that are applied when a player receives resources
public class ResourceIncomeContext extends AbstractGameContext {
    private ResourcesBonus income;
    //TODO: handle santa rita here or in towerscontext?


    public ResourceIncomeContext() {
        this.contextType = ContextType.RESOURCE_INCOME_CONTEXT;
        income = new ResourcesBonus(new Resources(), 0);
    }

    @Override
    public void interactWithPlayer(Player player) {

    }

    public void handleResources(Player player, ResourcesBonus resources) {
        income = resources;
        System.out.println("SERVANTS before "+ income.getResources().getResourceByType(ResourceType.SERVANTS));
        setChanged();
        notifyObservers(resources);
        //here the resources have already been changed by malus and bonus observers
        player.addResources(income.getResources());
        System.out.println("SERVANTS after "+ income.getResources().getResourceByType(ResourceType.SERVANTS));
    }

    /**
     * Called by the observers so that they add or subtract their effects from the final income bonus
     */
    public void setIncome(Resources res) {
        income.getResources().sumResources(res);
    }


}
