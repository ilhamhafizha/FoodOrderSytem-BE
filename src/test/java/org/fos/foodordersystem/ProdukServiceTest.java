package org.fos.foodordersystem;

import org.fos.foodordersystem.model.app.SimpleMap;
import org.fos.foodordersystem.model.enums.Status;
import org.fos.foodordersystem.model.filter.ProdukFilterRecord;
import org.fos.foodordersystem.model.request.ProdukRequestRecord;
import org.fos.foodordersystem.service.master.ProdukService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdukServiceTest {

    private ProdukService produkService;

    @BeforeEach
    void setUp() {
        produkService = Mockito.mock(ProdukService.class);
    }

    @Test
    void testAddProduk() {
        ProdukRequestRecord request = new ProdukRequestRecord(
                null,
                "Nasi Goreng Spesial",
                "Nasi goreng enak dengan topping telur",
                25000.0,
                10,
                Status.AKTIF,
                Set.of("nasi_goreng.jpg")
        );

        doNothing().when(produkService).add(request);

        produkService.add(request);

        verify(produkService, times(1)).add(request);
    }

    @Test
    void testFindAllProduk() {
        ProdukFilterRecord filter = new ProdukFilterRecord(
                "Nasi Goreng",
                Status.AKTIF,
                null,
                null,
                null,
                List.of("nasi_goreng.jpg")
        );

        PageRequest pageable = PageRequest.of(0, 10);

        SimpleMap produk = new SimpleMap();
        produk.put("nama", "Nasi Goreng Spesial");
        produk.put("harga", 25000.0);

        Page<SimpleMap> page = new PageImpl<>(List.of(produk));

        when(produkService.findAll(filter, pageable)).thenReturn(page);

        Page<SimpleMap> result = produkService.findAll(filter, pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("Nasi Goreng Spesial", result.getContent().get(0).get("nama"));
    }
}
