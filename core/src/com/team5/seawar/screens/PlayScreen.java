package com.team5.seawar.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team5.seawar.game.Game;
import com.team5.seawar.maps.Map;
import com.team5.seawar.utils.Assets;

public class PlayScreen extends ScreenAdapter{
    private Game game;
    private Map map;

    private OrthographicCamera cam;
    private Viewport viewport;

    public static final float SCALE = 12;
    public static final float hexWidth = Assets.getInstance().getTexture("hexEau.png").getWidth()/SCALE;
    public static final float hexHeight = Assets.getInstance().getTexture("hexEau.png").getHeight()/SCALE;

    public PlayScreen(Game game, Map map){
        this.game = game;
        this.map = map;
        cam = new OrthographicCamera();
        viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, cam);
    }

    public void update(float dt){

    }

    //A deléguer plus tard...
    public void render(float dt) {
        update(dt);
        //game.getBatch().setProjectionMatrix(cam.combined);
        game.getBatch().begin();
        for (int i=0; i<map.getColonne(); i++){
            for (int j=0; j<map.getLigne(); j++) {
                renderTexture(map.getElement(i,j).getTexture(), i, j);
            }
        }
        game.getBatch().end();
    }

    public void renderTexture(Texture texture, float colonne, float ligne){
        if (colonne%2==0){
            game.getBatch().draw(texture, colonne * hexWidth *3/4, ligne * hexHeight, hexWidth, hexHeight);
        } else {
            game.getBatch().draw(texture, colonne * hexWidth *3/4, ligne * hexHeight + hexHeight/2, hexWidth, hexHeight);
        }
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public void dispose() {
    }
}