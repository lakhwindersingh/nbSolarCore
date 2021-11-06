package com.neutronbinary.infectolabs.microservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.neutronbinary.infectolabs.microservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NBMapAttributesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NBMapAttributes.class);
        NBMapAttributes nBMapAttributes1 = new NBMapAttributes();
        nBMapAttributes1.setId(1L);
        NBMapAttributes nBMapAttributes2 = new NBMapAttributes();
        nBMapAttributes2.setId(nBMapAttributes1.getId());
        assertThat(nBMapAttributes1).isEqualTo(nBMapAttributes2);
        nBMapAttributes2.setId(2L);
        assertThat(nBMapAttributes1).isNotEqualTo(nBMapAttributes2);
        nBMapAttributes1.setId(null);
        assertThat(nBMapAttributes1).isNotEqualTo(nBMapAttributes2);
    }
}
