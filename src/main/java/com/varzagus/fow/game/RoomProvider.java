package com.varzagus.fow.game;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("singleton")
public class RoomProvider {
    private List<Room> rooms = new ArrayList<>();

    public List<Room> getRooms(){
        return rooms;
    }

}
