package org.fos.foodordersystem.service.master.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.fos.foodordersystem.builder.CustomBuilder;
import org.fos.foodordersystem.builder.CustomSpecification;
import org.fos.foodordersystem.builder.MultipleCriteria;
import org.fos.foodordersystem.builder.SearchCriteria;
import org.fos.foodordersystem.entitiy.master.Produk;
import org.fos.foodordersystem.entitiy.master.ProdukImage;
import org.fos.foodordersystem.mapper.master.ProdukMapper;
import org.fos.foodordersystem.model.app.AppPage;
import org.fos.foodordersystem.model.app.SimpleMap;
import org.fos.foodordersystem.model.filter.ProdukFilterRecord;
import org.fos.foodordersystem.model.request.ProdukRequestRecord;
import org.fos.foodordersystem.repository.master.ProdukRepository;
import org.fos.foodordersystem.service.app.ValidatorService;
import org.fos.foodordersystem.service.master.ProdukService;
import org.fos.foodordersystem.util.FilterUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdukServiceImpl implements ProdukService {

    private final ProdukRepository produkRepository;
    private final ValidatorService validatorService;
    private final ProdukMapper produkMapper;

    @Override
    public void add(ProdukRequestRecord request) {
        try {
            log.trace("Masuk ke menu tambah data produk");
            log.debug("Request data produk: {}", request);

            // validator mandatory
            validatorService.validator(request);

            if (request.stok() < 0) {
                log.warn("Stok produk tidak boleh kurang dari 0");
            }

            var produk = produkMapper.requestToEntity(request);

            // Ambil username user yang login
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : "system";

            produk.setCreatedBy(username);
            produk.setUpdatedBy(username);

            if (produk.getCreatedDate() == null) produk.setCreatedDate(LocalDateTime.now());
            if (produk.getModifiedDate() == null) produk.setModifiedDate(LocalDateTime.now());

            produkRepository.save(produk);

            log.info("Produk {} berhasil ditambahkan", request.nama());
            log.trace("Tambah data produk berhasil dan selesai");
        } catch (Exception e) {
            log.error("Tambah data produk gagal: {}", e.getMessage());
            throw e; // optional: biar bisa ditangani di controller
        }
    }

    @Override
    public void edit(ProdukRequestRecord request) {
        // validator mandatory
        validatorService.validator(request);

        var produkExisting = produkRepository.findById(request.id()).orElseThrow(() -> new RuntimeException("Produk tidak ditemukan"));
        var produk = produkMapper.requestToEntity(request);
        produk.setId(produkExisting.getId());
        produkRepository.save(produk);
    }

    @Override
    public Page<SimpleMap> findAll(ProdukFilterRecord filterRequest, Pageable pageable) {
        CustomBuilder<Produk> builder = new CustomBuilder<>();

        FilterUtil.builderConditionNotBlankLike("nama", filterRequest.nama(), builder);
        FilterUtil.builderConditionNotNullEqual("status", filterRequest.status(), builder);
        FilterUtil.builderConditionNotNullEqual("stok", filterRequest.stok(), builder);

        if (filterRequest.hargaBawah() != null && filterRequest.hargaAtas() != null) {
            builder.with(MultipleCriteria.builder().criterias(
                    SearchCriteria.OPERATOR_AND,
                    SearchCriteria.of("harga", CustomSpecification.OPERATION_GREATER_THAN_EQUAL, filterRequest.hargaBawah()),
                    SearchCriteria.of("harga", CustomSpecification.OPERATION_LESS_THAN_EQUAL, filterRequest.hargaAtas())
            ));
        } else if (filterRequest.hargaAtas() != null) {
            builder.with("harga", CustomSpecification.OPERATION_LESS_THAN_EQUAL, filterRequest.hargaAtas());
        } else if (filterRequest.hargaBawah() != null) {
            builder.with("harga", CustomSpecification.OPERATION_GREATER_THAN_EQUAL, filterRequest.hargaBawah());
        }

        Page<Produk> listProduk = produkRepository.findAll(builder.build(), pageable);
        List<SimpleMap> listData = listProduk.stream().map(produk -> {
            SimpleMap data = new SimpleMap();
            data.put("id", produk.getId());
            data.put("nama", produk.getNama());
            data.put("deskripsi", produk.getDeskripsi());
            data.put("harga", produk.getHarga());
            data.put("stok", produk.getStok());
            data.put("status", produk.getStatus());
            data.put("createdDate", produk.getCreatedDate());
            data.put("modifiedDate", produk.getModifiedDate());
            data.put("listImage", produk.getListImage().stream().map(ProdukImage::getPath).collect(Collectors.toSet()));
            return data;
        }).toList();

        return AppPage.create(listData, pageable, listProduk.getTotalElements());
    }

    @Override
    public SimpleMap findById(String id) {
        var produk = produkRepository.findById(id).orElseThrow(() -> new RuntimeException("Produk tidak ditemukan"));

        SimpleMap data = new SimpleMap();
        data.put("id", produk.getId());
        data.put("nama", produk.getNama());
        data.put("deskripsi", produk.getDeskripsi());
        data.put("harga", produk.getHarga());
        data.put("stok", produk.getStok());
        data.put("status", produk.getStatus());
        data.put("createdDate", produk.getCreatedDate());
        data.put("modifiedDate", produk.getModifiedDate());
        data.put("listImage", produk.getListImage().stream().map(ProdukImage::getPath).collect(Collectors.toSet()));
        
        return data;
    }

}
