package com.fda.management.mapping;

import com.fda.management.entity.DrugRecordEntity;
import com.fda.management.openapi.model.DrugRecordDto;
import com.fda.management.service.impl.OpenFdaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DrugRecordMapper {
    @Mapping(target = "appNumber", source = "applicationRecord.applicationNumber")
    @Mapping(target = "manufacturerName", expression = "java(getFirstManufacturer(applicationRecord))")
    @Mapping(target = "substances", expression = "java(collectSubstances(applicationRecord))")
    @Mapping(target = "productNumbers", expression = "java(collectProductNumbers(applicationRecord))")
    DrugRecordDto toDrugRecordFromApiResponse(OpenFdaResponse.ApplicationRecord applicationRecord);

    DrugRecordDto toDrugRecordFromDbEntity(DrugRecordEntity drugRecordEntity);

    DrugRecordEntity toDrugRecordEntity(DrugRecordDto drugRecordDto);

    default String getFirstManufacturer(OpenFdaResponse.ApplicationRecord applicationRecord) {
        if (applicationRecord != null) {
            OpenFdaResponse.OpenFda openFda = applicationRecord.getOpenFda();
            if (openFda != null) {
                return openFda.getManufacturerName()[0];
            }
        }
        return null;
    }

    default List<String> collectSubstances(OpenFdaResponse.ApplicationRecord applicationRecord) {
        if (applicationRecord != null) {
            OpenFdaResponse.OpenFda openFda = applicationRecord.getOpenFda();
            if (openFda != null) {
                return Arrays.asList(openFda.getSubstanceName());
            }
        }
        return Collections.emptyList();
    }

    default List<String> collectProductNumbers(OpenFdaResponse.ApplicationRecord applicationRecord) {
        if (applicationRecord != null) {
            List<OpenFdaResponse.DrugProduct> products = applicationRecord.getProducts();
            return products.stream()
                    .map(OpenFdaResponse.DrugProduct::getProductNumber)
                    .toList();
        }
        return Collections.emptyList();
    }

}
