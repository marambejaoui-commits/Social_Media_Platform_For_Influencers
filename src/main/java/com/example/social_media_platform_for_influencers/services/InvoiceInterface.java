package com.example.social_media_platform_for_influencers.services;
import com.example.social_media_platform_for_influencers.entities.Invoice;
import com.example.social_media_platform_for_influencers.enums.PaymentMethod;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Service
public interface InvoiceInterface {
        Invoice addInvoiceByCampaignId(Invoice invoice ,Long id );
        Invoice  getInvoiceById(Long invoiceId);
        Invoice  getInvoiceByCamapignId(Long id);
        List<Invoice> getAllInvoices();
        boolean existsInvoice(Long invoiceId);
        Long countAllInvoices();
        void deleteInvoiceByCampaignId(Long id);
        List<Invoice> getAllInvoicesByPaymentDate(LocalDate paymentDate);
        List<Invoice> getAllInvoicesByPaymentMethod(PaymentMethod paymentMethod);
        List<Invoice> getUnpaidInvoices();

}
