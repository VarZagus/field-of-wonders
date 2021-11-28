package com.varzagus.fow;

import com.varzagus.fow.enums.DrumPosition;
import com.varzagus.fow.game.Drum;
import com.varzagus.fow.game.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

class DrumTests {

    @Test
    void drumTest(){
        Drum drum = new Drum();
        Player playerMock = Mockito.mock(Player.class);
        Assertions.assertTrue(Arrays.asList(DrumPosition.values()).contains(drum.rollDrum(playerMock)));
    }
}
