package com.edgardo.ionixtest.controllers;

import com.edgardo.ionixtest.dto.UserDTO;
import com.edgardo.ionixtest.dto.UserResponse;
import com.edgardo.ionixtest.services.UserService;
import com.edgardo.ionixtest.utils.GlobalConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public UserResponse getPublications(@RequestParam(value = "page", defaultValue = GlobalConstants.DEFAULT_PAGE, required = false) int pageNumber,
                                        @RequestParam(value = "itemsPerPages", defaultValue = GlobalConstants.ITEMS_PER_PAGE, required = false) int itemsPerPages,
                                        @RequestParam(value = "sortBy", defaultValue = GlobalConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                        @RequestParam(value = "sortDir", defaultValue = GlobalConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        return userService.getUsers(pageNumber, itemsPerPages, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDto){
        return new ResponseEntity <>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable(name = "id") Long id){
        return ResponseEntity.ok(userService.updateUser(userDTO,id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("Usuario eliminado con exito");
    }
}
