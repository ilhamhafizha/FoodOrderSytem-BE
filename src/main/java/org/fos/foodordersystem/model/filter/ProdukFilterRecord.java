package org.fos.foodordersystem.model.filter;

import org.fos.foodordersystem.model.enums.Status;

import java.util.List;

public record ProdukFilterRecord(
        String nama,
        Status status,
        Integer stok,
        Double hargaBawah,
        Double hargaAtas,
        List<String> listImage
) {

}
