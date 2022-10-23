package com.fda.management.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OpenFdaResponse {
    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("results")
    private List<ApplicationRecord> results = new ArrayList<>();

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ApplicationRecord implements Serializable {
        @JsonProperty("application_number")
        private String applicationNumber;
        @JsonProperty("sponsor_name")
        private String sponsorName;
        @JsonProperty("products")
        private List<DrugProduct> products= new ArrayList<>();
        @JsonProperty("submissions")
        private List<Submission> submissions= new ArrayList<>();
        @JsonProperty("openfda")
        private OpenFda openFda;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class OpenFda implements Serializable {
        @JsonProperty("application_number")
        private String[] applicationNumber;
        @JsonProperty("brand_name")
        private String[] brandName;
        @JsonProperty("generic_name")
        private String[] genericName;
        @JsonProperty("manufacturer_name")
        private String[] manufacturerName;
        @JsonProperty("nui")
        private String[] nui;
        @JsonProperty("unii")
        private String[] unii;
        @JsonProperty("rxcui")
        private String[] rxcui;
        @JsonProperty("spl_id")
        private String[] spl_id;
        @JsonProperty("spl_set_id")
        private String[] spl_set_id;
        @JsonProperty("package_ndc")
        private String[] package_ndc;
        @JsonProperty("product_ndc")
        private String[] product_ndc;
        @JsonProperty("product_type")
        private String[] productType;
        @JsonProperty("route")
        private String[] route;
        @JsonProperty("substance_name")
        private String[] substanceName;

        @JsonProperty("package_class_cs")
        private String[] package_class_cs;
        @JsonProperty("package_class_epc")
        private String[] package_class_epc;
        @JsonProperty("package_class_pe")
        private String[] package_class_pe;
        @JsonProperty("package_class_moa")
        private String[] package_class_moa;
        @JsonProperty("pharm_class_epc")
        private String[] pharm_class_epc;
        @JsonProperty("pharm_class_cs")
        private String[] pharm_class_cs;
        @JsonProperty("pharm_class_pe")
        private String[] pharm_class_pe;
        @JsonProperty("pharm_class_moa")
        private String[] pharm_class_moa;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class Submission implements Serializable {
        @JsonProperty("submission_type")
        private String submissionType;
        @JsonProperty("submission_number")
        private String submissionNumber;
        @JsonProperty("submission_public_notes")
        private String submissionPublicNotes;
        @JsonProperty("submission_status")
        private String submissionStatus;
        @JsonProperty("submission_status_date")
        private String submissionStatusDate;
        @JsonProperty("review_priority")
        private String reviewPriority;
        @JsonProperty("submission_class_code")
        private String submissionClassCode;
        @JsonProperty("submission_class_code_description")
        private String submissionClassCodeDescription;
        @JsonProperty("application_docs")
        private List<ApplicationDocument> applicationDocuments= new ArrayList<>();
        @JsonProperty("submission_property_type")
        private List<SubmissionPropertyType> submissionPropertyTypes= new ArrayList<>();

    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class SubmissionPropertyType implements Serializable {
        @JsonProperty("code")
        private String code;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class ApplicationDocument implements Serializable {
        @JsonProperty("id")
        private String id;
        @JsonProperty("url")
        private String url;
        @JsonProperty("date")
        private String date;
        @JsonProperty("type")
        private String type;
        @JsonProperty("title")
        private String title;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DrugProduct implements Serializable {
        @JsonProperty("active_ingredients")
        private List<Ingredient> ingredients = new ArrayList<>();
        @JsonProperty("brand_name")
        private String brandName;
        @JsonProperty("dosage_form")
        private String dosageForm;
        @JsonProperty("marketing_status")
        private String marketingStatus;
        @JsonProperty("product_number")
        private String productNumber;
        @JsonProperty("reference_drug")
        private String referenceDrug;
        @JsonProperty("reference_standard")
        private String referenceStandard;
        @JsonProperty("route")
        private String route;
        @JsonProperty("te_code")
        private String teCode;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class Ingredient implements Serializable {
        @JsonProperty("name")
        private String name;
        @JsonProperty("strength")
        private String strength;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static
    class Meta implements Serializable {
        @JsonProperty("disclaimer")
        private String disclaimer;
        @JsonProperty("terms")
        private String terms;
        @JsonProperty("license")
        private String license;
        @JsonProperty("last_updated")
        private String last_updated;
        @JsonProperty("results")
        private Result results;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static
    class Result implements Serializable {
        private Integer skip;
        private Integer limit;
        private Integer total;
    }
}
