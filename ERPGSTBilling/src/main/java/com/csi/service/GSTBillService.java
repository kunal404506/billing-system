package com.csi.service;

import com.csi.entity.GSTBill;
import com.csi.repository.GSTBillRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GSTBillService implements IGSTBillService {

    @Autowired
    GSTBillRepository gstBillRepository;

    @Override
    public GSTBill generateBill(GSTBill gstBill) {

        gstBill.setInvoiceAmount(gstBill.getInvoiceAmount() + (gstBill.getInvoiceAmount() * 0.18));

        log.info("After GST Calculation Total Amount: " + gstBill.getInvoiceAmount());
        return gstBillRepository.save(gstBill);
    }

    @Override
    public Optional<GSTBill> findById(long invoiceId) {
        return gstBillRepository.findById(invoiceId);
    }

    @Override
    public List<GSTBill> findAll() {
        return gstBillRepository.findAll();
    }

    @Override
    public GSTBill updatte(GSTBill gstBill) {
        return gstBillRepository.save(gstBill);
    }

    @Override
    public void deleteById(long invoiceId) {
        gstBillRepository.deleteById(invoiceId);
    }
}
