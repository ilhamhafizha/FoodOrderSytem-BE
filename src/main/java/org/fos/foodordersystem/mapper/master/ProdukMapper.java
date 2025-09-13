package org.fos.foodordersystem.mapper.master;

import org.fos.foodordersystem.entitiy.master.Produk;
import org.fos.foodordersystem.entitiy.master.ProdukImage;
import org.fos.foodordersystem.model.request.ProdukRequestRecord;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class ProdukMapper {

    public Produk requestToEntity(ProdukRequestRecord request) {
        // Ambil username dari user yang login, default "system" jika tidak ada
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : "system";

        LocalDateTime now = LocalDateTime.now();

        Produk produk = Produk.builder()
                .nama(request.nama().toUpperCase())
                .deskripsi(request.deskripsi())
                .harga(request.harga())
                .stok(request.stok())
                .status(request.status())
                .createdBy(username)
                .updatedBy(username)
                .createdDate(now)
                .modifiedDate(now)
                .build();

        produk.setListImage(request.listImage().stream()
                .map(path -> ProdukImage.builder()
                        .path(path)
                        .produk(produk)
                        .createdBy(username)
                        .updatedBy(username)
                        .createdDate(now)
                        .modifiedDate(now)
                        .build())
                .collect(Collectors.toSet()));

        return produk;
    }
}
