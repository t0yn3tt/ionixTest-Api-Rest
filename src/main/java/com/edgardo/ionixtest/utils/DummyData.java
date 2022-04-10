package com.edgardo.ionixtest.utils;

import com.edgardo.ionixtest.dto.UserDTO;
import com.edgardo.ionixtest.dto.UserResponse;

import java.util.ArrayList;
import java.util.List;

public class DummyData {
    public static UserDTO user1(){
        UserDTO user = new UserDTO(1L,"Juan", "Perez", "juan@gmail.com", "juanjo");
        return user;
    }
    public static UserResponse userResponse(){
        List<UserDTO> users = new ArrayList<>();
        users.add(user1());

        UserResponse userResponse = new UserResponse();
        userResponse.setPage(0);
        userResponse.setUsers(users);
        userResponse.setItemsPerPages(10);
        userResponse.setCountElements(1L);
        userResponse.setCountPages(1);
        userResponse.setLast(true);

        return userResponse;
    }
}
