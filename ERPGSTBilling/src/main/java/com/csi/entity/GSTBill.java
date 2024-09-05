package com.csi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "GST_BILL")
public class GSTBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceId;

    @Size(min = 2, message = "Customer Name should be atleast 2 character")
    private String custName;

    private String custAddress;

    @NotNull
    private long custContactNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date invoiceDate;

    @Email(message = "Email ID Must be Valid")
    private String custEmailId;

    private double invoiceAmount;


}
