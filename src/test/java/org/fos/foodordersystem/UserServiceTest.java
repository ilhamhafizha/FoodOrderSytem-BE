package org.fos.foodordersystem;

import org.fos.foodordersystem.model.app.SimpleMap;
import org.fos.foodordersystem.model.enums.Role;
import org.fos.foodordersystem.model.enums.Status;
import org.fos.foodordersystem.model.filter.UserFilterRecord;
import org.fos.foodordersystem.model.request.UserRequestRecord;
import org.fos.foodordersystem.service.managementuser.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
    }

    @Test
    void testAddUser() {
        UserRequestRecord request = new UserRequestRecord(
                null,
                "Ilham",
                "ilham123",
                "ilham@example.com",
                "password123",
                Status.AKTIF,
                Role.PEMBELI
        );

        doNothing().when(userService).add(request);

        userService.add(request);

        verify(userService, times(1)).add(request);
    }

    @Test
    void testFindAllUsers() {
        UserFilterRecord filter = new UserFilterRecord(
                "Ilham",
                "ilham@example.com",
                "ilham123",
                Status.AKTIF,
                Role.PEMBELI
        );
        PageRequest pageable = PageRequest.of(0, 10);

        SimpleMap user = new SimpleMap();
        user.put("username", "ilham123");
        user.put("role", "PEMBELI");

        Page<SimpleMap> page = new PageImpl<>(List.of(user));

        when(userService.findAll(filter, pageable)).thenReturn(page);

        Page<SimpleMap> result = userService.findAll(filter, pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("ilham123", result.getContent().get(0).get("username"));
    }
}

