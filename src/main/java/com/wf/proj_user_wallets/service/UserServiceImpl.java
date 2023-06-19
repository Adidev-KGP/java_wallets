// UserServiceImpl.java
package com.wf.proj_user_wallets.service.impl;

import com.wf.proj_user_wallets.dto.UserDto;
import com.wf.proj_user_wallets.model.User;
import com.wf.proj_user_wallets.repository.UserRepository;
import com.wf.proj_user_wallets.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @CircuitBreaker(name = "getAllUsers", fallbackMethod = "getAllUsersFallback")
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Add fallback method for getAllUsers
    public List<UserDto> getAllUsersFallback(Exception e) {
        // Handle the fallback logic, such as returning cached data or a default response
        // This method will be called when the circuit is open or an exception occurs
        // Return cached user data from the cache for our case

        logger.error("Fallback triggered for getAllUsers: " + e.getMessage());


        List<UserDto> cachedData = cacheService.getAllUsers();
        return cachedData;

            // Return a default response
            // List<UserDto> defaultUsers = new ArrayList<>();
            // defaultUsers.add(new UserDto("Fallback User 1"));
            // defaultUsers.add(new UserDto("Fallback User 2"));
            // return defaultUsers;

        // return null;
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = convertToEntity(userDto);
        User createdUser = userRepository.save(user);
        return convertToDto(createdUser);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());

        User updatedUser = userRepository.save(existingUser);
        return convertToDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    private User convertToEntity(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }
}
