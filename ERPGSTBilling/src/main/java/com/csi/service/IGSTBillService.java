package com.csi.service;

import com.csi.entity.GSTBill;

import java.util.List;
import java.util.Optional;

public interface IGSTBillService {

    GSTBill generateBill(GSTBill gstBill);

    Optional<GSTBill> findById(long invoiceId);

    List<GSTBill> findAll();

    GSTBill updatte(GSTBill gstBill);

    void deleteById(long invoiceId);
}
