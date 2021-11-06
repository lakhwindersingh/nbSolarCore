package com.neutronbinary.infectolabs.microservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.neutronbinary.infectolabs.microservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NBMapComponentAttributesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NBMapComponentAttributes.class);
        NBMapComponentAttributes nBMapComponentAttributes1 = new NBMapComponentAttributes();
        nBMapComponentAttributes1.setId(1L);
        NBMapComponentAttributes nBMapComponentAttributes2 = new NBMapComponentAttributes();
        nBMapComponentAttributes2.setId(nBMapComponentAttributes1.getId());
        assertThat(nBMapComponentAttributes1).isEqualTo(nBMapComponentAttributes2);
        nBMapComponentAttributes2.setId(2L);
        assertThat(nBMapComponentAttributes1).isNotEqualTo(nBMapComponentAttributes2);
        nBMapComponentAttributes1.setId(null);
        assertThat(nBMapComponentAttributes1).isNotEqualTo(nBMapComponentAttributes2);
    }
}
