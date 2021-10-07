package com.varzagus.game;

import com.varzagus.api.Rollable;
import com.varzagus.enums.DrumPosition;

import java.util.Random;


/**
 * Барабан. Вовзращает значение после прокрутки и даёт игрокам соответствующие очки.
 *
 */
public class Drum implements Rollable {
    private Random random = new Random();

    /**
     * Так как для разных действий различные шансы, то им будут соответствоать несоклько позиций барабана,
     * т.е. несколько подряд идущих чисел, в зависимости от шанса
     * X2:1
     * X4:2
     * СЕКТОР ПЛЮС: 3
     * БАНКРОТ: 4,5
     * СЕКТОР НОЛЬ: 6
     * 5 ОЧКОВ: 7,8,9
     * 10 ОЧКОВ: 10, 11
     * 15 ОЧКОВ: 12, 13
     * 20 ОЧКОВ 14, 15
     * 25 ОЧКОВ: 16
     * @param player игрок, который крутит барабан
     * @return значение, на котором остановился барабан.
     */
    @Override
    public DrumPosition rollDrum(Player player) {
        int act = random.nextInt(16) + 1;
        if(act == 1) {
            multiTwo(player);
            return DrumPosition.X2;
        }
        if(act == 2){
            multiFour(player);
            return DrumPosition.X4;
        }
        if(act == 3){
            sectorPlus(player);
            return DrumPosition.SECTOR_PLUS;
        }
        if(act == 4 || act == 5) {
            bankrupt(player);
            return DrumPosition.BANKRUPT;
        }
        if(act == 6){
            sectorNull(player);
            return DrumPosition.SECTOR_NULL;
        }
        if(act == 7 || act == 8 || act == 9) {
            fivePoint(player);
            return DrumPosition.FIVE_POINTS;
        }
        if(act == 10 || act == 11){
            tenPoint(player);
            return DrumPosition.TEN_POINTS;
        }
        if(act == 12 || act == 13){
            fiveTeenPoint(player);
            return DrumPosition.FIVE_TEEN_POINTS;
        }
        if(act == 14 || act == 15) {
            twentyPoint(player);
            return DrumPosition.TWENTY_POINTS;
        }
        twentyFivePoint(player);
        return DrumPosition.TWENTY_FIVE_POINTS;

    }

    @Override
    public void multiTwo(Player player) {
        player.setScore(player.getScore()*2);
    }

    @Override
    public void multiFour(Player player) {
        player.setScore(player.getScore()*4);
    }

    @Override
    public void bankrupt(Player player) {
        player.setScore(0);
    }

    @Override
    public void sectorNull(Player player) {

    }

    @Override
    public void fivePoint(Player player) {
        player.setScore(player.getScore()+5);
    }

    @Override
    public void tenPoint(Player player) {
        player.setScore(player.getScore()+10);
    }

    @Override
    public void fiveTeenPoint(Player player) {
        player.setScore(player.getScore() + 15);

    }

    @Override
    public void twentyPoint(Player player) {
        player.setScore(player.getScore()+10);
    }

    @Override
    public void twentyFivePoint(Player player) {
        player.setScore(player.getScore()+25);
    }

    @Override
    public void sectorPlus(Player player) {
    }
}
