package com.csi.controller;

import com.csi.entity.GSTBill;
import com.csi.exception.RecordNotFoundException;

import com.csi.service.IGSTBillService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

@RestController
@RequestMapping("/v1/gst")
@Tag(name = "GST Billing", description = "APIS Of GST Bill Controller")
@SecurityRequirement(name = "Bearer Auth")
@Slf4j
public class GSTBillController {

    @Autowired
    private IGSTBillService billService;

    @PostMapping("/generatebill")
    public ResponseEntity<GSTBill> generateGSTBill(@Valid @RequestBody GSTBill gstBill) {
        log.info("Generating invoice for Customer: " + gstBill.getCustName());

        return new ResponseEntity<>(billService.generateBill(gstBill), HttpStatus.CREATED);
    }

    @GetMapping("/findbillbyid/{invoiceId}")
    public ResponseEntity<Optional<GSTBill>> findById(@PathVariable long invoiceId) {
        return new ResponseEntity<>(billService.findById(invoiceId), HttpStatus.OK);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<GSTBill>> findAll() {
        return new ResponseEntity<>(billService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/update/{invoiceId}")
    public ResponseEntity<GSTBill> update(@PathVariable long invoiceId, @Valid @RequestBody GSTBill gstBill) {

        GSTBill gstBill1 = billService.findById(invoiceId).orElseThrow(() -> new RecordNotFoundException("Invoice ID Does Not Exist"));

        gstBill1.setCustName(gstBill.getCustName());
        gstBill1.setCustAddress(gstBill.getCustAddress());
        gstBill1.setInvoiceDate(gstBill.getInvoiceDate());
        gstBill1.setCustEmailId(gstBill.getCustEmailId());
        gstBill1.setInvoiceAmount(gstBill.getInvoiceAmount());
        gstBill1.setCustContactNumber(gstBill.getCustContactNumber());

        return new ResponseEntity<>(billService.updatte(gstBill1), HttpStatus.CREATED);
    }

    @DeleteMapping("/deletebyid/{invoiceId}")
    public ResponseEntity<String> deletebyId(@PathVariable long invoiceId) {
        billService.deleteById(invoiceId);
        return new ResponseEntity<>("Invoice Deleted Successfully", HttpStatus.OK);
    }
}
