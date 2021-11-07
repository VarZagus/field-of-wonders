package com.varzagus.game;

import com.varzagus.domain.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Класс, который получает пользователей,
 * которые ищут игру и при необходиомом кол-ве создаёт новую комнату.
 * Пока представлен прототип, который не учитывает что пользователь может выйти из очереди.
 */
public class RoomWorker  {
    private final List<Room> rooms;
    private final Queue<User> usersQueue;

    public RoomWorker(){
        rooms = new ArrayList<>();
        usersQueue = new LinkedList<>();
    }

    public Room createRoom(){
        Room room = null;
        if(usersQueue.size() >= 3){
            List<User> users = new ArrayList<>();
            int count = 0;
            while(count != 3){
                User user = usersQueue.poll();
                users.add(user);
                count++;
            }
            room = new Room(users);
            rooms.add(room);
        }
        return room;
    }

    public List<Room> getRooms(){
        return rooms;
    }

    public void addUser(User user){
        usersQueue.add(user);
    }

    public Queue<User> getUserQueue(){
        return usersQueue;
    }
}
