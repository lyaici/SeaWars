package com.team5.seawar.screens.playstates;

import com.badlogic.gdx.utils.Array;
import com.team5.seawar.inputHandler.Inputs;
import com.team5.seawar.player.Player;
import com.team5.seawar.screens.PlayScreen;
import com.team5.seawar.ship.Ship;

public class ShipSelect implements State {
    private PlayScreen playScreen;
    private Player player;
    private Player ennemie;

    private static ShipSelect instance = new ShipSelect();

    private ShipSelect(){
    }

    public static ShipSelect getInstance(){
        return instance;
    }

    public static void init(PlayScreen playScreen){
        instance.playScreen = playScreen;
        instance.player = playScreen.getMap().getPlayer2();
    }

    public void update(float dt){
        if (player.hasFinished()){
            playScreen.changeState(EndTurn.getInstance(player));
        }
        if ((Inputs.isPressed(Inputs.A) || Inputs.isPressed(Inputs.CLICK)) && player.getShips().contains(playScreen.getCurrentCase().getShip(), true) && !playScreen.getCurrentCase().getShip().hasFinished()){
            playScreen.changeState(ShipSelected.getInstance(playScreen.getCurrentCase(),ennemie));
        }
    }

    public void newTurn() {
        if (player.equals(playScreen.getMap().getPlayer1())) {
            player = playScreen.getMap().getPlayer2();
            ennemie = playScreen.getMap().getPlayer1();
        } else {
            player = playScreen.getMap().getPlayer1();
            ennemie = playScreen.getMap().getPlayer2();
        }
        player.newTurn();
    }
}
