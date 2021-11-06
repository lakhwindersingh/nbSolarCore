package com.neutronbinary.infectolabs.microservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.neutronbinary.infectolabs.microservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NBMapTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NBMap.class);
        NBMap nBMap1 = new NBMap();
        nBMap1.setId(1L);
        NBMap nBMap2 = new NBMap();
        nBMap2.setId(nBMap1.getId());
        assertThat(nBMap1).isEqualTo(nBMap2);
        nBMap2.setId(2L);
        assertThat(nBMap1).isNotEqualTo(nBMap2);
        nBMap1.setId(null);
        assertThat(nBMap1).isNotEqualTo(nBMap2);
    }
}
