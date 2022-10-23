package com.fda.management.repository;

import com.fda.management.entity.DrugRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRecordEntityRepository extends JpaRepository<DrugRecordEntity, String> {


}
