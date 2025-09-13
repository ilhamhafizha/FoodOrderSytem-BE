package org.fos.foodordersystem.model.request;

import java.util.List;

public record OrderRequestRecord(
        List<ItemRequest> items
) {
    public record ItemRequest(String produkId, Integer jumlah) {}
}
