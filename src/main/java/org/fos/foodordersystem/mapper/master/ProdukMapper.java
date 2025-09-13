package org.fos.foodordersystem.mapper.master;


import org.fos.foodordersystem.entitiy.master.Produk;
import org.fos.foodordersystem.entitiy.master.ProdukImage;
import org.fos.foodordersystem.model.request.ProdukRequestRecord;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProdukMapper {

    public Produk requestToEntity(ProdukRequestRecord request) {
        Produk produk = Produk.builder()
                .nama(request.nama().toUpperCase())
                .deskripsi(request.deskripsi())
                .harga(request.harga())
                .stok(request.stok())
                .status(request.status())
                .build();

//        produk.setListImage(request.listImage().stream()
//                .map(path -> ProdukImage.builder()
//                        .path(path)
//                        .produk(produk)
//                        .build())
//                .collect(Collectors.toSet()));

        return produk;
    }

}
