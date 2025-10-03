package com.example.social_media_platform_for_influencers.entities;
import com.example.social_media_platform_for_influencers.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;
@Entity
@Table (name="invoice")
@Data
@Setter
@Getter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long invoiceId;
    private boolean paid;
    private Timestamp paymentDate;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @OneToOne
    @JsonIgnore
    private Campaign campaign;


    public boolean isPaid() {
        return paid;
    }


    public void setPaid(boolean paid) {
        this.paid = paid;
    }

}
