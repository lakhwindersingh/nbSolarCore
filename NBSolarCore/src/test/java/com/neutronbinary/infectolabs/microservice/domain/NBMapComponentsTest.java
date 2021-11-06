package com.neutronbinary.infectolabs.microservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.neutronbinary.infectolabs.microservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NBMapComponentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NBMapComponents.class);
        NBMapComponents nBMapComponents1 = new NBMapComponents();
        nBMapComponents1.setId(1L);
        NBMapComponents nBMapComponents2 = new NBMapComponents();
        nBMapComponents2.setId(nBMapComponents1.getId());
        assertThat(nBMapComponents1).isEqualTo(nBMapComponents2);
        nBMapComponents2.setId(2L);
        assertThat(nBMapComponents1).isNotEqualTo(nBMapComponents2);
        nBMapComponents1.setId(null);
        assertThat(nBMapComponents1).isNotEqualTo(nBMapComponents2);
    }
}
