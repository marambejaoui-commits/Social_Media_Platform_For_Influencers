package com.example.social_media_platform_for_influencers.controllers;

import com.example.social_media_platform_for_influencers.entities.Invoice;
import com.example.social_media_platform_for_influencers.enums.PaymentMethod;
import com.example.social_media_platform_for_influencers.services.InvoiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/invoice")
public class invoiceController {

    @Autowired
    private InvoiceInterface invoiceInterface;

    // Ajouter une facture (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("addinvoice/{id}")
    public Invoice addInvoiceByCampaignId(@RequestBody Invoice invoice ,@PathVariable Long id){
        return invoiceInterface.addInvoiceByCampaignId(invoice,id);
    }

    // Supprimer une facture (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public void deleteInvoiceByCampaignId(@PathVariable Long id) {
        invoiceInterface.deleteInvoiceByCampaignId(id);
    }

    // Récupérer une facture par ID (ADMIN ou propriétaire de la campagne)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("get/{invoiceId}")
    public Invoice getInvoiceById(@PathVariable Long invoiceId){
        return invoiceInterface.getInvoiceById(invoiceId);
    }

    // Récupérer toutes les factures (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("getall")
    public List<Invoice> getAllInvoices(){
        return invoiceInterface.getAllInvoices();
    }

    // Récupérer une facture par campagne (ADMIN ou propriétaire de la campagne)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("getbycampaign/{id}")
    public Invoice getInvoiceByCamapignId(@PathVariable Long id){
        return invoiceInterface.getInvoiceByCamapignId(id);
    }

    // Vérifier si une facture existe (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("exist/{invoiceId}")
    public boolean existsInvoice(@PathVariable Long invoiceId){
        return invoiceInterface.existsInvoice(invoiceId);
    }

    // Compter toutes les factures (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("countall")
    public Long countAllInvoices(){
        return invoiceInterface.countAllInvoices();
    }

    // Récupérer toutes les factures par date de paiement (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("getalls/{date}")
    public List<Invoice> getAllInvoicesByPaymentDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return invoiceInterface.getAllInvoicesByPaymentDate(date);
    }

    // Récupérer toutes les factures par méthode de paiement (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("getall/{paymentMethod}")
    public List<Invoice> getAllInvoicesByPaymentMethod(@PathVariable PaymentMethod paymentMethod){
        return invoiceInterface.getAllInvoicesByPaymentMethod(paymentMethod);
    }

    // Récupérer toutes les factures impayées (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("getunpaid")
    public List<Invoice> getUnpaidInvoices(){
        return invoiceInterface.getUnpaidInvoices();
    }
}
