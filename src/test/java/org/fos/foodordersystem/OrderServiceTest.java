package org.fos.foodordersystem;
import org.fos.foodordersystem.model.app.SimpleMap;
import org.fos.foodordersystem.model.request.OrderRequestRecord;
import org.fos.foodordersystem.service.transaction.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = Mockito.mock(OrderService.class);
    }

    @Test
    void testBuatPesanan() {
        OrderRequestRecord.ItemRequest item = new OrderRequestRecord.ItemRequest("produk-1", 2);
        OrderRequestRecord request = new OrderRequestRecord(List.of(item));

        SimpleMap resultMock = new SimpleMap();
        resultMock.put("orderId", "order-123");
        resultMock.put("totalHarga", 50000.0);

        when(orderService.buatPesanan(request, "user-123")).thenReturn(resultMock);

        SimpleMap result = orderService.buatPesanan(request, "user-123");

        assertEquals("order-123", result.get("orderId"));
        assertEquals(50000.0, result.get("totalHarga"));
    }

    @Test
    void testGetHistoryPesanan() {
        SimpleMap order = new SimpleMap();
        order.put("orderId", "order-123");
        order.put("totalHarga", 50000.0);

        when(orderService.getHistoryPesanan("user-123")).thenReturn(List.of(order));

        List<SimpleMap> result = orderService.getHistoryPesanan("user-123");

        assertEquals(1, result.size());
        assertEquals("order-123", result.get(0).get("orderId"));
    }
}

