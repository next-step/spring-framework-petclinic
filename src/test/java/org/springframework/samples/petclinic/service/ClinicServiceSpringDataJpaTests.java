package org.springframework.samples.petclinic.service;

import org.springframework.samples.petclinic.system.BusinessConfig;
import org.springframework.samples.petclinic.system.DataSourceConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * <p> Integration test using the 'Spring Data' profile.
 *
 * @author Michael Isvy
 * @see AbstractClinicServiceTests AbstractClinicServiceTests for more details. </p>
 */

@SpringJUnitConfig(classes = {BusinessConfig.class, DataSourceConfig.class})
@ActiveProfiles("spring-data-jpa")
class ClinicServiceSpringDataJpaTests extends AbstractClinicServiceTests {

}
