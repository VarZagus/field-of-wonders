package com.varzagus.fow;

import com.varzagus.fow.domain.User;
import com.varzagus.fow.game.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class RoomTests {

    public List<User> createUserList(){
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin("user1");
        user2.setLogin("user2");
        user3.setLogin("user3");
        return Arrays.asList(user1,user2,user3);
    }

    @Test
    void addUserTest(){
        Room room = new Room();
        Assertions.assertFalse(room.isFull());
        List<User> userList = createUserList();
        userList.forEach(u->room.addUser(u));
        Assertions.assertTrue(room.isFull());
    }

    @Test
    void deleteUserTest(){
        Room room = new Room();
        List<User> userList = createUserList();
        userList.forEach(u->room.addUser(u));
        User user = room.getUserList().get(0);
        room.deleteUser("user1");
        Assertions.assertFalse(room.isFull());
        Assertions.assertFalse(room.getUserList().contains(user));
    }
}
