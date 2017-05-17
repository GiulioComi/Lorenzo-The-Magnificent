package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 16/05/2017.
 */

public interface ContextInterface {

    /**
     *
     * @param player that is playing his turn
     */
    public void initContext(Player player);


    public void endContext();

}