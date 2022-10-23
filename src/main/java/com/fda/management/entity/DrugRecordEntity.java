package com.fda.management.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "drug_record")
public class DrugRecordEntity {
    @Id
    @Column(name = "application_number")
    private String appNumber;

    @Column(name = "manufacturer_name")
    private String manufacturerName;

    @ElementCollection
    @CollectionTable(name = "drug_record_substances", joinColumns = @JoinColumn(name = "appNumber"))
    @Column(name = "substances")
    private Set<String> substances;

    @ElementCollection
    @CollectionTable(name = "drug_record_product_numbers", joinColumns = @JoinColumn(name = "appNumber"))
    @Column(name = "product_numbers")
    private Set<String> productNumbers;
}
