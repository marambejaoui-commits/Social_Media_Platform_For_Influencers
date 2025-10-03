package com.example.social_media_platform_for_influencers.servicesImplement;
import com.example.social_media_platform_for_influencers.entities.Campaign;
import com.example.social_media_platform_for_influencers.entities.Invoice;
import com.example.social_media_platform_for_influencers.enums.PaymentMethod;
import com.example.social_media_platform_for_influencers.repositories.CampaignRepo;
import com.example.social_media_platform_for_influencers.repositories.InvoiceRepo;
import com.example.social_media_platform_for_influencers.services.InvoiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceImplement  implements InvoiceInterface {
 @Autowired
 private InvoiceRepo invoiceRepo;
 @Autowired
 private CampaignRepo  campaignRepo;

    @Override
    public Invoice addInvoiceByCampaignId(Invoice invoice, Long id) {
        Campaign campaign = campaignRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));
        invoice.setCampaign(campaign);

        if (invoice.isPaid()==false) {
            invoice.setPaymentDate(null);
            invoice.setPaymentMethod(null);

        } else {
            invoice.setPaymentDate(Timestamp.from(Instant.now()));
        }
        return invoiceRepo.save(invoice);
    }

    @Override
    public Invoice getInvoiceById(Long invoiceId) {
        return invoiceRepo.findById(invoiceId).orElseThrow(()->new RuntimeException("invoice not found"));
    }

    @Override
    public Invoice getInvoiceByCamapignId(Long id) {
        return invoiceRepo.findByCampaign_Id(id).orElseThrow(()->new RuntimeException("campaign not found"));
    }


    @Override
    public List<Invoice> getAllInvoices() {
        return  invoiceRepo.findAll();
    }

    @Override
    public boolean existsInvoice(Long invoiceId) {
        return invoiceRepo.existsById(invoiceId);
    }

    @Override
    public Long countAllInvoices() {
        return invoiceRepo.count();
    }
    @Override
    public void deleteInvoiceByCampaignId(Long id) {
        invoiceRepo.deleteByCampaign_Id(id);
    }

    @Override
    public List<Invoice> getAllInvoicesByPaymentDate(LocalDate date) {
        Timestamp start = Timestamp.valueOf(date.atStartOfDay());
        Timestamp end = Timestamp.valueOf(date.plusDays(1).atStartOfDay());
        return invoiceRepo.findByPaymentDateBetween(start, end);
    }

    @Override
    public List<Invoice> getAllInvoicesByPaymentMethod(PaymentMethod paymentMethod) {
        return invoiceRepo.findAllByPaymentMethod(paymentMethod);
    }



    @Override
    public List<Invoice> getUnpaidInvoices() {
        return invoiceRepo.findByPaidFalse();
    }
}
