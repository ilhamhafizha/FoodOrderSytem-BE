package org.fos.foodordersystem.managementuser.impl;


import lombok.RequiredArgsConstructor;

import org.fos.foodordersystem.builder.CustomBuilder;
import org.fos.foodordersystem.entitiy.managementuser.User;
import org.fos.foodordersystem.mapper.managementuser.UserMapper;
import org.fos.foodordersystem.model.app.AppPage;
import org.fos.foodordersystem.model.app.SimpleMap;
import org.fos.foodordersystem.model.filter.UserFilterRecord;
import org.fos.foodordersystem.model.request.UserRequestRecord;
import org.fos.foodordersystem.repository.managementuser.UserRepository;
import org.fos.foodordersystem.service.managementuser.UserService;
import org.fos.foodordersystem.util.FilterUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public void add(UserRequestRecord request) {
        // validasi mandatory
        validasiMandatory(request);

        // validasi data existing
        if (userRepository.existsByEmail(request.email().toLowerCase())) {
            throw new RuntimeException("Email [" + request.email() + "] sudah digunakan");
        }
        if (userRepository.existsByUsername(request.username().toLowerCase())) {
            throw new RuntimeException("Username [" + request.username() + "] sudah digunakan");
        }

        var user = userMapper.requestToEntity(request);

// set password dari request
        user.setPassword(request.password());

// set default createdBy, updatedBy, createdDate, modifiedDate
        user.setCreatedBy("system");
        user.setUpdatedBy("system");
        if (user.getCreatedDate() == null) user.setCreatedDate(LocalDateTime.now());
        if (user.getModifiedDate() == null) user.setModifiedDate(LocalDateTime.now());

        userRepository.save(user);

    }

    @Override
    public void edit(UserRequestRecord request) {
        // validasi mandatory
        validasiMandatory(request);

        var userExisting = userRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Data user tidak ditemukan"));

        // validasi data existing
        if (userRepository.existsByEmailAndIdNot(request.email().toLowerCase(), request.id())) {
            throw new RuntimeException("Email [" + request.email() + "] sudah digunakan");
        }
        if (userRepository.existsByUsernameAndIdNot(request.username().toLowerCase(), request.id())) {
            throw new RuntimeException("Username [" + request.username() + "] sudah digunakan");
        }

        var user = userMapper.requestToEntity(request);
        user.setId(userExisting.getId());

// set password dari request
        user.setPassword(request.password());

// set updatedBy dan modifiedDate
        user.setUpdatedBy("system");
        user.setModifiedDate(LocalDateTime.now());

        userRepository.save(user);

    }

    @Override
    public Page<SimpleMap> findAll(UserFilterRecord filterRequest, Pageable pageable) {
        CustomBuilder<User> builder = new CustomBuilder<>();

        FilterUtil.builderConditionNotBlankLike("nama", filterRequest.nama(), builder);
        FilterUtil.builderConditionNotBlankLike("email", filterRequest.email(), builder);
        FilterUtil.builderConditionNotBlankLike("username", filterRequest.username(), builder);
        FilterUtil.builderConditionNotNullEqual("status", filterRequest.status(), builder);
        FilterUtil.builderConditionNotNullEqual("role", filterRequest.role(), builder);

        Page<User> listUser = userRepository.findAll(builder.build(), pageable);
        List<SimpleMap> listData = listUser.stream().map(user -> {
            SimpleMap data = new SimpleMap();
            data.put("id", user.getId());
            data.put("nama", user.getNama());
            data.put("username", user.getUsername());
            data.put("email", user.getEmail());
            data.put("role", user.getStatus().getLabel());
            data.put("status", user.getRole().getLabel());
            return data;
        }).toList();

        return AppPage.create(listData, pageable, listUser.getTotalElements());
    }

    @Override
    public SimpleMap findById(String id) {
        var user = userRepository.findById(id).orElseThrow(() ->  new RuntimeException("Data user tidak ditemukan"));
        SimpleMap data = new SimpleMap();
        data.put("id", user.getId());
        data.put("nama", user.getNama());
        data.put("username", user.getUsername());
        data.put("email", user.getEmail());
        data.put("status", user.getStatus().getLabel());
        data.put("role", user.getRole().getLabel());
        return data;
    }

    private void validasiMandatory(UserRequestRecord request) {
        if (request.nama() == null || request.nama().isEmpty()) {
            throw new RuntimeException("Nama tidak boleh kosong");
        }
        if (request.username() == null || request.username().isEmpty()) {
            throw new RuntimeException("Username tidak boleh kosong");
        }
        if (request.email() == null || request.email().isEmpty()) {
            throw new RuntimeException("Email tidak boleh kosong");
        }
        if (request.password() == null || request.password().isEmpty()) {
            throw new RuntimeException("Email tidak boleh kosong");
        }
        if (request.status() == null) {
            throw new RuntimeException("Status tidak boleh kosong");
        }
        if (request.role() == null) {
            throw new RuntimeException("Role tidak boleh kosong");
        }
    }

}
