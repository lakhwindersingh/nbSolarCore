package com.neutronbinary.infectolabs.microservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.neutronbinary.infectolabs.microservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NBChartTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NBChart.class);
        NBChart nBChart1 = new NBChart();
        nBChart1.setId(1L);
        NBChart nBChart2 = new NBChart();
        nBChart2.setId(nBChart1.getId());
        assertThat(nBChart1).isEqualTo(nBChart2);
        nBChart2.setId(2L);
        assertThat(nBChart1).isNotEqualTo(nBChart2);
        nBChart1.setId(null);
        assertThat(nBChart1).isNotEqualTo(nBChart2);
    }
}
