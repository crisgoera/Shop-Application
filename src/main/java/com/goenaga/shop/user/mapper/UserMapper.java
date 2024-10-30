package com.goenaga.shop.user.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goenaga.shop.user.model.User;
import com.goenaga.shop.user.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserMapper {
    public UserDTO mapUserToDTO (User user) {
        ObjectMapper mapper = new ObjectMapper();
        UserDTO userDTO = UserDTO.builder().build();

        Map<String, Object> userDTOMap = mapper.convertValue(userDTO, new TypeReference<Map<String, Object>>() {});
        Map<String, Object> userMap = mapper.convertValue(user, new TypeReference<Map<String, Object>>() {});

        userDTOMap.forEach((key, value) -> userDTOMap.put(key, userMap.get(key)));

        return mapper.convertValue(userDTOMap, UserDTO.class);
    }

    public User updateUserDetails (User user, User updateDetails) {
        ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Map<String, Object> updateMap = mapper.convertValue(updateDetails, new TypeReference<Map<String, Object>>() {});
        Map<String, Object> userMap = mapper.convertValue(user, new TypeReference<Map<String, Object>>() {});

        updateMap.forEach((key, value) -> userMap.put(key, value));

        return mapper.convertValue(userMap, User.class);
    }
}
