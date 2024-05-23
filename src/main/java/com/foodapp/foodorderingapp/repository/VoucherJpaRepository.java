package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.entity.Voucher;
import com.foodapp.foodorderingapp.enumeration.VoucherStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherJpaRepository extends JpaRepository<Voucher, Long> {
    List<Voucher> findAllByStatusAndUserId(VoucherStatus status, Long userId);
}
