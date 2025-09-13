package org.fos.foodordersystem.service.managementuser;

import org.fos.foodordersystem.model.app.SimpleMap;
import org.fos.foodordersystem.model.filter.UserFilterRecord;
import org.fos.foodordersystem.model.request.UserRequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void add(UserRequestRecord request);

    void edit(UserRequestRecord request);

    Page<SimpleMap> findAll(UserFilterRecord filterRequest, Pageable pageable);

    SimpleMap findById(String id);
    void delete(String id);

}