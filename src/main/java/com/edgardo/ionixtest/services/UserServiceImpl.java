package com.edgardo.ionixtest.services;

import com.edgardo.ionixtest.dto.UserDTO;
import com.edgardo.ionixtest.dto.UserResponse;
import com.edgardo.ionixtest.entities.UserEntity;
import com.edgardo.ionixtest.exceptions.AppException;
import com.edgardo.ionixtest.exceptions.ResourceNotFoundException;
import com.edgardo.ionixtest.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUsers(int pageNumber, int itemsPerPages, String sortBy, String sortDir) {
        //ordenamos
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        //paginamos
        Pageable pageable = PageRequest.of(pageNumber, itemsPerPages, sort);
        Page<UserEntity> users = userRepository.findAll(pageable);

        List<UserEntity> listUsers = users.getContent();
        List<UserDTO> content = listUsers.stream().map(user -> mapDTO(user)).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(content);
        userResponse.setPage(users.getNumber());
        userResponse.setItemsPerPages(users.getSize());
        userResponse.setCountElements(users.getTotalElements());
        userResponse.setCountPages(users.getTotalPages());
        userResponse.setLast(users.isLast());

        return userResponse;
    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {

        //Verificamos que el usuario no exista en la base de datos
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new AppException(HttpStatus.BAD_REQUEST, "El usuario ya esta registrado");
        }
        //Convertimos DTO a Entidad
        UserEntity user = mapEntity(userDTO);

        //Convertimos de entidad a DTO el nuevo usuario creado y la retornamos
        return mapDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDTO updateUser(UserDTO userDTO, Long id) {
        //verificar que el usuario exista
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        userEntity.setEmail(userDTO.getEmail());
        userEntity.setFirstname(userDTO.getFirstname());
        userEntity.setLastname(userDTO.getLastname());
        userEntity.setUsername(userDTO.getUsername());
        return mapDTO(userRepository.save(userEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return mapDTO(userEntity);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(userEntity);
    }

    //Convertir Entidad a DTO
    private UserDTO mapDTO(UserEntity user){
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
    //Convertir DTO a Entidad
    private UserEntity mapEntity(UserDTO userDTO){
        UserEntity user = modelMapper.map(userDTO, UserEntity.class);
        return user;
    }
}
