package com.example.social_media_platform_for_influencers.repositories;
import com.example.social_media_platform_for_influencers.entities.Invoice;

import com.example.social_media_platform_for_influencers.enums.PaymentMethod;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice,Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM  invoice WHERE campaign_id = :id", nativeQuery = true)
    void deleteByCampaign_Id(@Param("id") Long id);
    @Override
    boolean existsById(Long invoiceId);
    List<Invoice> findAllByPaymentMethod(PaymentMethod paymentMethod);
    List<Invoice> findByPaymentDateBetween(Timestamp start, Timestamp end);
    Optional<Invoice> findByCampaign_Id(Long id);
    List<Invoice> findByPaidFalse();


}
