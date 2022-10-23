package com.fda.management.repository;

import com.fda.management.entity.DrugRecordEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Set;

import static com.fda.management.mock.FdaManagementTestMocks.createDrugRecordEntity;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class,})
@DataJpaTest
public class BaseRepositoryUnitTest {

    @Autowired
    DrugRecordEntityRepository drugRecordEntityRepository;

    @BeforeAll
    void initH2Data() {
        DrugRecordEntity drugRecordEntity = createDrugRecordEntity("ANDA088551", "Taro Pharmaceuticals U.S.A., Inc.",
                Set.of("PROPAFENONE HYDROCHLORIDE"), Set.of("001", "002"));

        drugRecordEntityRepository.saveAll(List.of(drugRecordEntity));
    }
}
