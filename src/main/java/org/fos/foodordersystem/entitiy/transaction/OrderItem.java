package org.fos.foodordersystem.entitiy.transaction;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.fos.foodordersystem.entitiy.app.BaseEntity;
import org.fos.foodordersystem.entitiy.master.Produk;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_order_item")
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produk_id", nullable = false)
    private Produk produk;

    private Integer jumlah;
    private Double hargaSatuan;
    private Double subTotal;
}
