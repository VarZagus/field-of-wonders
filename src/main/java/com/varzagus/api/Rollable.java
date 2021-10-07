package com.varzagus.api;

import com.varzagus.enums.DrumPosition;
import com.varzagus.game.Player;

public interface Rollable {
    public DrumPosition rollDrum(Player player);

    public void multiTwo(Player player);

    public void multiFour(Player player);

    public void bankrupt(Player player);

    public void sectorNull(Player player);

    public void fivePoint(Player player);

    public void tenPoint(Player player);

    public void fiveTeenPoint(Player player);

    public void twentyPoint(Player player);

    public void twentyFivePoint(Player player);

    public void sectorPlus(Player player);


}
