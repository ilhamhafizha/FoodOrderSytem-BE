package org.fos.foodordersystem.service.transaction.impl;

import lombok.RequiredArgsConstructor;
import org.fos.foodordersystem.entitiy.managementuser.User;
import org.fos.foodordersystem.entitiy.master.Produk;
import org.fos.foodordersystem.entitiy.transaction.Order;
import org.fos.foodordersystem.entitiy.transaction.OrderItem;
import org.fos.foodordersystem.model.app.SimpleMap;
import org.fos.foodordersystem.model.request.OrderRequestRecord;
import org.fos.foodordersystem.repository.managementuser.UserRepository;
import org.fos.foodordersystem.repository.master.ProdukRepository;
import org.fos.foodordersystem.repository.transaction.OrderRepository;
import org.fos.foodordersystem.service.transaction.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProdukRepository produkRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public SimpleMap buatPesanan(OrderRequestRecord request, String userId) {
        User pembeli = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        Order order = Order.builder()
                .pembeli(pembeli)
                .createdBy(pembeli.getUsername())
                .updatedBy(pembeli.getUsername())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        double total = 0.0;

        for (OrderRequestRecord.ItemRequest itemReq : request.items()) {
            Produk produk = produkRepository.findById(itemReq.produkId())
                    .orElseThrow(() -> new RuntimeException("Produk tidak ditemukan"));

            if (produk.getStok() < itemReq.jumlah()) {
                throw new RuntimeException("Stok produk " + produk.getNama() + " tidak cukup");
            }

            // kurangi stok
            produk.setStok(produk.getStok() - itemReq.jumlah());
            produkRepository.save(produk);

            double subTotal = produk.getHarga() * itemReq.jumlah();

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .produk(produk)
                    .jumlah(itemReq.jumlah())
                    .hargaSatuan(produk.getHarga())
                    .subTotal(subTotal)
                    .createdBy(pembeli.getUsername())
                    .updatedBy(pembeli.getUsername())
                    .createdDate(LocalDateTime.now())
                    .modifiedDate(LocalDateTime.now())
                    .build();

            order.getItems().add(item);
            total += subTotal;
        }

        order.setTotalHarga(total);
        orderRepository.save(order);

        SimpleMap data = new SimpleMap();
        data.put("orderId", order.getId());
        data.put("totalHarga", order.getTotalHarga());
        data.put("jumlahItem", order.getItems().size());
        return data;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleMap> getHistoryPesanan(String userId) {
        List<Order> orders = orderRepository.findByPembeliIdOrderByCreatedDateDesc(userId);

        return orders.stream().map(order -> {
            SimpleMap data = new SimpleMap();
            data.put("orderId", order.getId());
            data.put("tanggal", order.getCreatedDate());
            data.put("totalHarga", order.getTotalHarga());
            data.put("jumlahItem", order.getItems().size());

            List<SimpleMap> detailItem = order.getItems().stream().map(item -> {
                SimpleMap i = new SimpleMap();
                i.put("produkId", item.getProduk().getId());
                i.put("namaProduk", item.getProduk().getNama());
                i.put("jumlah", item.getJumlah());
                i.put("hargaSatuan", item.getHargaSatuan());
                i.put("subTotal", item.getSubTotal());
                return i;
            }).toList();

            data.put("items", detailItem);
            return data;
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleMap> findAll() {
        List<Order> orders = orderRepository.findAllByOrderByCreatedDateDesc();

        return orders.stream().map(order -> {
            SimpleMap data = new SimpleMap();
            data.put("orderId", order.getId());
            data.put("pembeli", order.getPembeli().getNama());
            data.put("tanggal", order.getCreatedDate());
            data.put("totalHarga", order.getTotalHarga());
            data.put("jumlahItem", order.getItems().size());

            List<SimpleMap> detailItem = order.getItems().stream().map(item -> {
                SimpleMap i = new SimpleMap();
                i.put("produkId", item.getProduk().getId());
                i.put("namaProduk", item.getProduk().getNama());
                i.put("jumlah", item.getJumlah());
                i.put("hargaSatuan", item.getHargaSatuan());
                i.put("subTotal", item.getSubTotal());
                return i;
            }).toList();

            data.put("items", detailItem);
            return data;
        }).toList();
    }


}
