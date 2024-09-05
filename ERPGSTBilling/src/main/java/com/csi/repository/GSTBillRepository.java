package com.csi.repository;

import com.csi.entity.GSTBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GSTBillRepository extends JpaRepository<GSTBill, Long> {

    // Custom methods

    GSTBill findByCustEmailId(String custEmailId);

}
