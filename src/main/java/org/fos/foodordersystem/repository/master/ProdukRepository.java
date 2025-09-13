package org.fos.foodordersystem.repository.master;

import org.fos.foodordersystem.entitiy.master.Produk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProdukRepository extends JpaRepository<Produk, String>, JpaSpecificationExecutor<Produk> {
}
