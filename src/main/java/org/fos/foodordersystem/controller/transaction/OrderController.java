package org.fos.foodordersystem.controller.transaction;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.fos.foodordersystem.config.UserLoggedInConfig;
import org.fos.foodordersystem.model.request.OrderRequestRecord;
import org.fos.foodordersystem.model.response.BaseResponse;
import org.fos.foodordersystem.service.transaction.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
@Tag(name = "Order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("create")
    @PreAuthorize("hasRole('PEMBELI')")
    public BaseResponse<?> buatPesanan(@RequestBody OrderRequestRecord request,
                                       @AuthenticationPrincipal UserLoggedInConfig userLoggedIn) {
        var user = userLoggedIn.getUser();
        return BaseResponse.ok("Pesanan berhasil dibuat",
                orderService.buatPesanan(request, user.getId()));
    }

    @GetMapping("history")
    @PreAuthorize("hasRole('PEMBELI')")
    public BaseResponse<?> getHistory(@AuthenticationPrincipal UserLoggedInConfig userLoggedIn) {
        var user = userLoggedIn.getUser();
        return BaseResponse.ok("History pesanan ditemukan",
                orderService.getHistoryPesanan(user.getId()));
    }

    @GetMapping("/find-all")
    @PreAuthorize("hasAnyRole('ADMIN', 'PENJUAL')")
    public BaseResponse<?> findAllOrders() {
        return BaseResponse.ok("Data order ditemukan", orderService.findAll());
    }


}
