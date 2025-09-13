package org.fos.foodordersystem;


import org.fos.foodordersystem.entitiy.master.Produk;
import org.fos.foodordersystem.mapper.master.ProdukMapper;
import org.fos.foodordersystem.model.enums.Status;
import org.fos.foodordersystem.model.request.ProdukRequestRecord;
import org.fos.foodordersystem.repository.master.ProdukRepository;
import org.fos.foodordersystem.service.app.ValidatorService;
import org.fos.foodordersystem.service.master.impl.ProdukServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdukServiceTest {

    @Mock
    private ProdukRepository produkRepository;

    @Mock
    private ValidatorService validatorService;

    @Mock
    private ProdukMapper produkMapper;

    @InjectMocks
    private ProdukServiceImpl produkService;

    @Test
    void testAddProduk_Success() {
        Set<String> listImage = new HashSet<>();
        listImage.add("path1");

        var request = new ProdukRequestRecord(null, "Macbook Air M1", "Macbook Air M1",
                10000000D, 10, Status.AKTIF, listImage);

        var produkEntity = new Produk();
        when(produkMapper.requestToEntity(request)).thenReturn(produkEntity);

        // when
        produkService.add(request);

        // then
        verify(validatorService, times(1)).validator(request);
        verify(produkRepository, times(1)).save(produkEntity);
    }

}
