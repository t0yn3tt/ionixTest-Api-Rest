package com.edgardo.ionixtest.services;
import com.edgardo.ionixtest.dto.UserDTO;
import com.edgardo.ionixtest.dto.UserResponse;

public interface UserService {
    public UserResponse getUsers(int pageNumber, int itemsPerPages, String sortBy, String sortDir);
    public UserDTO createUser(UserDTO userDTO);
    public UserDTO updateUser(UserDTO userDTO, Long id);
    public UserDTO getUserById(Long id);
    void deleteUser(Long id);
}
