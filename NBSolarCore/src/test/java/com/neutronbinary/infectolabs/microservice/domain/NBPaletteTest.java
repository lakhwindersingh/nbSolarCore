package com.neutronbinary.infectolabs.microservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.neutronbinary.infectolabs.microservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NBPaletteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NBPalette.class);
        NBPalette nBPalette1 = new NBPalette();
        nBPalette1.setId(1L);
        NBPalette nBPalette2 = new NBPalette();
        nBPalette2.setId(nBPalette1.getId());
        assertThat(nBPalette1).isEqualTo(nBPalette2);
        nBPalette2.setId(2L);
        assertThat(nBPalette1).isNotEqualTo(nBPalette2);
        nBPalette1.setId(null);
        assertThat(nBPalette1).isNotEqualTo(nBPalette2);
    }
}
