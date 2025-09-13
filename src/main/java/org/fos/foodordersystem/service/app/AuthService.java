package org.fos.foodordersystem.service.app;


import org.fos.foodordersystem.entitiy.managementuser.User;
import org.fos.foodordersystem.model.app.SimpleMap;
import org.fos.foodordersystem.model.response.LoginRequestRecord;

public interface AuthService {

    SimpleMap login(LoginRequestRecord request);

    void logout(User userLoggedIn);

}
