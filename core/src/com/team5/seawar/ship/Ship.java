package com.team5.seawar.ship;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.team5.seawar.screens.PlayScreen;
import com.team5.seawar.utils.Assets;
import com.team5.seawar.utils.CanonCalcul;

public class Ship{
    private int maxLifePoints;
    private int currentLifePoints;

    private int maxMovements;
    private int movements;

    private Canon mainCanon;
    private Canon secondaryCanon;

    private ShipPosition shipPosition;

    private Sprite sprite;

    private boolean canFire;
    private boolean hasFinished;

    public Ship(int maxLifePoints, Canon mainCanon, Canon secondaryCanon, int maxMovements, int colonne, int ligne, ShipPosition.Orientation orientation){
        this.maxLifePoints = maxLifePoints;
        this.currentLifePoints = maxLifePoints;
        this.mainCanon = mainCanon;
        this.secondaryCanon = secondaryCanon;
        this.maxMovements = maxMovements;
        this.shipPosition = new ShipPosition(colonne,ligne,orientation);
        this.sprite = new Sprite(Assets.getInstance().getTexture("Shiptextures/ShipH.png"));
        if (colonne%2==0){
            sprite.setPosition(colonne * PlayScreen.hexWidth *3/4, ligne * PlayScreen.hexHeight);
        } else {
            sprite.setPosition(colonne * PlayScreen.hexWidth *3/4, ligne * PlayScreen.hexHeight + PlayScreen.hexHeight/2);
        }
        sprite.setSize(PlayScreen.hexWidth, PlayScreen.hexHeight);
    }

    public Sprite getSprite() {
        switch (shipPosition.getOrientation()){
            case TOP:
                if (!hasFinished) {
                    sprite.setTexture(Assets.getInstance().getTexture("Shiptextures/ShipH.png"));
                } else {
                    sprite.setTexture(Assets.getInstance().getTexture("Shiptextures/ShipH2.png"));
                }
                break;
            case TOP_RIGHT:
                if (!hasFinished) {
                    sprite.setTexture(Assets.getInstance().getTexture("Shiptextures/ShipHD.png"));
                } else {
                    sprite.setTexture(Assets.getInstance().getTexture("Shiptextures/ShipHD2.png"));
                }
                break;
            case BOTTOM_RIGHT:
                if (!hasFinished) {
                    sprite.setTexture(Assets.getInstance().getTexture("Shiptextures/ShipBD.png"));
                } else {
                    sprite.setTexture(Assets.getInstance().getTexture("Shiptextures/ShipBD2.png"));
                }
                break;
            case BOTTOM:
                if (!hasFinished) {
                    sprite.setTexture(Assets.getInstance().getTexture("Shiptextures/ShipB.png"));
                } else {
                    sprite.setTexture(Assets.getInstance().getTexture("Shiptextures/ShipB2.png"));
                }
                break;
            case BOTTOM_LEFT:
                if (!hasFinished) {
                    sprite.setTexture(Assets.getInstance().getTexture("Shiptextures/ShipBG.png"));
                } else {
                    sprite.setTexture(Assets.getInstance().getTexture("Shiptextures/ShipBG2.png"));
                }
                break;
            case TOP_LEFT:
                if (!hasFinished) {
                    sprite.setTexture(Assets.getInstance().getTexture("Shiptextures/ShipHG.png"));
                } else {
                    sprite.setTexture(Assets.getInstance().getTexture("Shiptextures/ShipHG2.png"));
                }
                break;
        }
        return sprite;
    }

    public void move(Vector2 arrive){
        float deltaX = arrive.x - shipPosition.getColonne();
        float deltaY = arrive.y - shipPosition.getLigne();
        if (arrive.x%2==0){
            sprite.setPosition(arrive.x * PlayScreen.hexWidth *3/4, arrive.y * PlayScreen.hexHeight);
            if (deltaX == 0 && deltaY == 1)
                shipPosition.setOrientation(ShipPosition.Orientation.TOP);
            if (deltaX == 1 && deltaY == 1)
                shipPosition.setOrientation(ShipPosition.Orientation.TOP_RIGHT);
            if (deltaX == 1 && deltaY == 0)
                shipPosition.setOrientation(ShipPosition.Orientation.BOTTOM_RIGHT);
            if (deltaX == 0 && deltaY == -1)
                shipPosition.setOrientation(ShipPosition.Orientation.BOTTOM);
            if (deltaX == -1 && deltaY == 0)
                shipPosition.setOrientation(ShipPosition.Orientation.BOTTOM_LEFT);
            if (deltaX == -1 && deltaY == 1)
                shipPosition.setOrientation(ShipPosition.Orientation.TOP_LEFT);
        } else {
            sprite.setPosition(arrive.x * PlayScreen.hexWidth *3/4, arrive.y * PlayScreen.hexHeight + PlayScreen.hexHeight/2);
            if (deltaX == 0 && deltaY == 1)
                shipPosition.setOrientation(ShipPosition.Orientation.TOP);
            if (deltaX == 1 && deltaY == 0)
                shipPosition.setOrientation(ShipPosition.Orientation.TOP_RIGHT);
            if (deltaX == 1 && deltaY == -1)
                shipPosition.setOrientation(ShipPosition.Orientation.BOTTOM_RIGHT);
            if (deltaX == 0 && deltaY == -1)
                shipPosition.setOrientation(ShipPosition.Orientation.BOTTOM);
            if (deltaX == -1 && deltaY == -1)
                shipPosition.setOrientation(ShipPosition.Orientation.BOTTOM_LEFT);
            if (deltaX == -1 && deltaY == 0)
                shipPosition.setOrientation(ShipPosition.Orientation.TOP_LEFT);
        }
        shipPosition.setPosiiton(arrive);
        movements--;
        if (!canMove() && !canFire){
            hasFinished = true;
        }
    }

    public void setMainCanon(Canon mainCanon) {
        this.mainCanon = mainCanon;
    }

    public void setSecondaryCanon(Canon secondaryCanon) {
        this.secondaryCanon = secondaryCanon;
    }

    public ShipPosition getPosition() {
        return shipPosition;
    }

    public int getMaxLifePoints(){
        return maxLifePoints;
    }

    public int getCurrentLifePoints(){
        return currentLifePoints;
    }

    public void takeDamages(int damages){
        currentLifePoints = currentLifePoints - damages;
        if (currentLifePoints<0)
            currentLifePoints = 0;
    }

    public boolean canMove(){
        return movements > 0;
    }

    public void newTurn(){
        hasFinished = false;
        movements = maxMovements;
        mainCanon.newTurn();
        secondaryCanon.newTurn();
        if (mainCanon.canAttack() || secondaryCanon.canAttack()){
            canFire = true;
        } else {
            canFire = false;
        }
    }

    public boolean canFire(){
        return canFire;
    }

    public void attack(){
        canFire = false;
        if (!canMove()){
            hasFinished = true;
        }
    }

    public boolean hasFinished(){
        return hasFinished;
    }

    public void finish(){
        hasFinished = true;
    }

    public Array<Vector2> getRangeMainCanon(){
        Array<Vector2> range = new Array<Vector2>();
        for (Vector2 vector2 : mainCanon.getRange()){
            if (shipPosition.getColonne()%2 == 0){
                range.add(CanonCalcul.rotation(vector2, shipPosition.getOrientation(), false));
            } else {
                range.add(CanonCalcul.rotation(CanonCalcul.even2odd(vector2), shipPosition.getOrientation(), true));
            }
        }
        return range;
    }

    public Array<Vector2> getRangeSecondaryCanon(){
        Array<Vector2> range = new Array<Vector2>();
        for (Vector2 vector2 : secondaryCanon.getRange()){
            if (shipPosition.getColonne()%2 == 0){
                range.add(CanonCalcul.rotation(vector2, shipPosition.getOrientation(), false));
            } else {
                range.add(CanonCalcul.rotation(CanonCalcul.even2odd(vector2), shipPosition.getOrientation(), true));
            }
        }
        return range;
    }

    public Canon getMainCanon() {
        return mainCanon;
    }

    public Canon getSecondaryCanon() {
        return secondaryCanon;
    }
}
