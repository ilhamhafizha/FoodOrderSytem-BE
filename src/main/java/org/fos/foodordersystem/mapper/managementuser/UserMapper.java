package org.fos.foodordersystem.mapper.managementuser;

import org.fos.foodordersystem.entitiy.managementuser.User;
import org.fos.foodordersystem.model.request.UserRequestRecord;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User requestToEntity(UserRequestRecord request) {
        return User.builder()
                .nama(request.nama().toUpperCase())
                .username(request.username().toLowerCase())
                .email(request.email().toLowerCase())
                .status(request.status())
                .role(request.role())
                .build();
    }

}
