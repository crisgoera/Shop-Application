package com.goenaga.shop.user.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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

    public User mapDTOToUser (UserDTO userDTO) {
        ObjectMapper mapper = new ObjectMapper();
        User user = User.builder().build();

        Map<String, Object> userDTOMap = mapper.convertValue(userDTO, new TypeReference<Map<String, Object>>() {});
        Map<String, Object> userMap = mapper.convertValue(user, new TypeReference<Map<String, Object>>() {});

        userDTOMap.forEach((key, value) -> userMap.put(key, userDTOMap.get(key)));

        return mapper.convertValue(userMap, User.class);
    }

    public User updateUserDetails (User user, UserDTO updateDetails) {
        ObjectMapper updateMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectMapper userMapper = new ObjectMapper();

        Map<String, Object> updateMap = updateMapper.convertValue(updateDetails, new TypeReference<Map<String, Object>>() {});
        Map<String, Object> userMap = userMapper.convertValue(user, new TypeReference<Map<String, Object>>() {});

        updateMap.forEach((key, value) -> userMap.put(key, value));

        return userMapper.convertValue(userMap, User.class);
    }
}
