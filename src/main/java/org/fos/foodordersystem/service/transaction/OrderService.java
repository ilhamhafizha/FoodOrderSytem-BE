package org.fos.foodordersystem.service.transaction;

import org.fos.foodordersystem.model.request.OrderRequestRecord;
import org.fos.foodordersystem.model.app.SimpleMap;

import java.util.List;

public interface OrderService {
    SimpleMap buatPesanan(OrderRequestRecord request, String userId);
    List<SimpleMap> getHistoryPesanan(String userId);
    List<SimpleMap> findAll();
}
