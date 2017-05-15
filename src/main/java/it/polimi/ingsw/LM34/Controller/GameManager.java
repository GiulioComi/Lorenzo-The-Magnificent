package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Exception.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Board.GameBoard.*;
import it.polimi.ingsw.LM34.Model.Cards.*;
import it.polimi.ingsw.LM34.Model.Dice;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Enum.PawnColor;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.RemotePlayer;
import it.polimi.ingsw.LM34.Utils.Configurator;
import it.polimi.ingsw.LM34.Utils.SetupDecks;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GiulioComi on 05/05/2017.
 */
public class GameManager {

    private Integer period; //3 in a game
    private Integer turn; //3*2 in a game
    private Integer phase; //equal to #players

    private ArrayList<Dice> dices;
    private ArrayList<Player> players;
    private Map<Integer, Integer> faithPath = new HashMap<Integer, Integer>();
    HashMap<Player, Integer> victoryPointsByPlayer = new HashMap<Player, Integer>();

    //TODO: apply factory patterns to DECKS
    //DECKS cointaining all the 96 development cards of the game, that at the beginning of each period will be partially loaded in the towers
    private ArrayList<AbstractDevelopmentCard> territoryCardDeck;
    private ArrayList<AbstractDevelopmentCard> characterCardDeck;
    private ArrayList<AbstractDevelopmentCard> ventureCardDeck;
    private ArrayList<AbstractDevelopmentCard> buildingCardDeck;
    private ArrayList<LeaderCard> leaderCardsDeck;
    private ArrayList<ExcommunicationCard> excommunicationCards;

    /*GAMEBOARD COMPONENTS*/
    private Market market;
    private CouncilPalace councilPalace;
    private ArrayList<Tower> towers;
    private WorkingArea harvestArea;
    private WorkingArea productionArea;

    public void startGame() {
        //check all the clients are connected, load configurations from file, setup all the game components with the configuration

        //Load all the configurations from json
        Configurator.loadConfigs();
        market = Configurator.getMarket();

        //TODO: load development cards, leaders, excommunication tiles and others GameBoards & PersonalBoard spaces
        Collections.shuffle(players); //randomly set the initial play order
        shuffleDecksByPeriod();

    }

    //TODO: chain together remotePlayer (client) and the player
    public void addPlayer(RemotePlayer remotePlayer, Player player) {
        players.add(player);
    }

    public void endGame() {
        //TODO
        onEndCalculateVictoryPointsPerPlayer();
        onEndGameCalculatePointsByDevelopmentCardsOwned(victoryPointsByPlayer);
    }

    //method called only at the start of the game
    public void shuffleDecksByPeriod() {
        Collections.shuffle(territoryCardDeck);
        Collections.shuffle(characterCardDeck);
        Collections.shuffle(ventureCardDeck);
        Collections.shuffle(buildingCardDeck);
        Collections.shuffle(excommunicationCards);
        Collections.shuffle(leaderCardsDeck);
        SetupDecks.orderCardByPeriod(characterCardDeck);
        SetupDecks.orderCardByPeriod(territoryCardDeck);
        SetupDecks.orderCardByPeriod(buildingCardDeck);
        SetupDecks.orderCardByPeriod(territoryCardDeck);
        SetupDecks.orderCardByPeriod(territoryCardDeck);
    }

    public void setNewTurnOrder() {
        ArrayList<FamilyMember> temp;
        ArrayList<Player> oldPlayersOrder = players;
        temp = councilPalace.getNextTurnOrder();
        players.clear();
        for (FamilyMember fm : temp) {
            PawnColor pawnColor = fm.getFamilyMemberColor();
            for (Player rm : oldPlayersOrder)
                if (rm.getPawnColor() == pawnColor)
                    players.add(rm);

        }

    }

    public void nextPeriod() { //round = mezzo periodo
        turn++;
        if (turn % 2 == 0)
            doCurchReport();
    }

    public void nextTurn() {
        //resetMillisTImer //unico per giocatori
        sweepActionSlots();
        replaceCards();
        setNewTurnOrder();
        rollDices();
        if (phase == players.size()) {
            nextPeriod();
            turn = 0;
        } else phase++;
    }

    public void buyCard(Player player, TowerSlot slot) throws InvalidCardType {
        player.getPersonalBoard().addCard(slot.getCardStored());
    }

    public void doCurchReport() {
        //TODO
    }

    public void replaceCards() {
        //TODO
    }

    public void sweepActionSlots() {
        for (Tower tower : towers)
            tower.sweep();

        market.sweep();
        councilPalace.sweep();
        productionArea.sweep();
        harvestArea.sweep();
    }

    public void rollDices() {
        for (Dice dice : dices) dice.rollDice();
    }

    /**
     * provide the players the initial amount of resources
     */
    public void setupPlayersResources() {
        Integer incrementalCoins = 5;
        for (Player player : players) {
            player.addResources(new Resources(incrementalCoins, 2, 2, 3));
            incrementalCoins++;
        }
    }

    public HashMap<Player, Integer> onEndGameCalculatePointsByDevelopmentCardsOwned(HashMap<Player, Integer> victoryPointsByPlayer) {

        return victoryPointsByPlayer;
    }

    /**
     * @return the hashmap with a correlation between players and their points earned by venture cards
     */
    public HashMap<Player, Integer> onEndCalculateVictoryPointsPerPlayer() {
        HashMap<Player, Integer> victoryPointsToPlayers = new HashMap<Player, Integer>();
        Integer totalVictoryPointsByVentureCardReward = 0;
        ArrayList<AbstractDevelopmentCard> tempPlayerVentureCards = new ArrayList<AbstractDevelopmentCard>();
        //for each player we calculate the sum of the victory points rewards provided by his venture cards stored in the personal board
        try {
            for (Player p : players) {
                //TODO: check if the player has the excommunication card that disable this step
                /*if(p.getMalus== noCalculateEndPoints)
                    victoryPointsToPlayers(p, 0);*/
                // else
                tempPlayerVentureCards = p.getPersonalBoard().getDevelopmentCardsByType(DevelopmentCardColor.PURPLE);
                totalVictoryPointsByVentureCardReward = 0;
                for (AbstractDevelopmentCard dci : tempPlayerVentureCards) {
                    VentureCard dciVenture = (VentureCard) dci;
                    totalVictoryPointsByVentureCardReward += dciVenture.getEndingVictoryPointsReward();
                }
                victoryPointsToPlayers.put(p, totalVictoryPointsByVentureCardReward);
            }
        } catch (InvalidCardType ict) {
            ict.printStackTrace();
        } //TODO:  adjust this exception handle

        return victoryPointsToPlayers;
    }

    //a testing porpuse main
    public static void main(String[] args) {
        Configurator.loadConfigs();
        GameManager gm = new GameManager();
    }




}



   /* try {
        gm.testSerialization();
    } catch (Exception e) {
        e.printStackTrace();
    }
    try {
        gm.testDeserialization();
    } catch (Exception e) {
        e.printStackTrace();
    }*/



    /*public void testDeserialization() throws Exception {

        try {
            FileInputStream fin = new FileInputStream("C:\\Users\\Julius\\Desktop\\ProvaFinale\\test.dat");
            System.out.println("file opened");



            ObjectInputStream ins = new ObjectInputStream(fin);
            Market copy = (Market) ins.readObject();
            System.out.println(copy.getSize());
            ins.close();
            fin.close();

        } catch (Exception e) {//System.out.println(e);
            }
    }*/

//TODO: test deeply if each object of the game is really Serializable and compressable in a DTO
    /*public void testSerialization() throws Exception {

        try {
            FileOutputStream fout = new FileOutputStream("C:\\Users\\Julius\\Desktop\\ProvaFinale\\test.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(market);
            oos.flush();
            oos.close();
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/