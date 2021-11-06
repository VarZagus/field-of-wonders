package com.varzagus.game;

import com.varzagus.enums.DrumPosition;

import java.util.Random;


/**
 * Барабан. Вовзращает значение после прокрутки и даёт игрокам соответствующие очки.
 *
 */
public class Drum {
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

    public DrumPosition rollDrum(Player player) {
        int act = random.nextInt(16) + 1;
        if(act == 1) {
            return DrumPosition.X2;
        }
        if(act == 2){
            return DrumPosition.X4;
        }
        if(act == 3){
            return DrumPosition.SECTOR_PLUS;
        }
        if(act == 4 || act == 5) {
            return DrumPosition.BANKRUPT;
        }
        if(act == 6){
            return DrumPosition.SECTOR_NULL;
        }
        if(act == 7 || act == 8 || act == 9) {
            return DrumPosition.FIVE_POINTS;
        }
        if(act == 10 || act == 11){
            return DrumPosition.TEN_POINTS;
        }
        if(act == 12 || act == 13){
            return DrumPosition.FIFTEEN_POINTS;
        }
        if(act == 14 || act == 15) {
            return DrumPosition.TWENTY_POINTS;
        }
        return DrumPosition.TWENTY_FIVE_POINTS;

    }

}
