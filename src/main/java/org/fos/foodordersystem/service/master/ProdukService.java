package org.fos.foodordersystem.service.master;


import org.fos.foodordersystem.model.app.SimpleMap;
import org.fos.foodordersystem.model.filter.ProdukFilterRecord;
import org.fos.foodordersystem.model.request.ProdukRequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdukService {

    void add(ProdukRequestRecord request);

    void edit(ProdukRequestRecord request);

    Page<SimpleMap> findAll(ProdukFilterRecord filterRequest, Pageable pageable);

    SimpleMap findById(String id);

}
