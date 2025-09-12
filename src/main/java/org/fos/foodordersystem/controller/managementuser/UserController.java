package org.fos.foodordersystem.controller.managementuser;


import lombok.RequiredArgsConstructor;
import org.fos.foodordersystem.model.filter.UserFilterRecord;
import org.fos.foodordersystem.model.request.UserRequestRecord;
import org.fos.foodordersystem.model.response.BaseResponse;
import org.fos.foodordersystem.service.managementuser.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("save")
    public BaseResponse<?> save(@RequestBody UserRequestRecord request) {
        userService.add(request);
        return BaseResponse.ok("Data berhasil disimpan", null);
    }

    @PostMapping("edit")
    public BaseResponse<?> edit(@RequestBody UserRequestRecord request) {
        userService.edit(request);
        return BaseResponse.ok("Data berhasil diubah", null);
    }

    @PostMapping("find-all")
    public BaseResponse<?> findAll(@PageableDefault(direction = Sort.Direction.DESC, sort = "modifiedDate") Pageable pageable,
                                   @RequestBody UserFilterRecord filterRequest) {
        return BaseResponse.ok(null, userService.findAll(filterRequest, pageable));
    }

    @GetMapping("find-by-id/{id}")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok(null, userService.findById(id));
    }

}
