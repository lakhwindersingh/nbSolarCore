package com.neutronbinary.infectolabs.gateway.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.neutronbinary.infectolabs.gateway.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NBUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NBUser.class);
        NBUser nBUser1 = new NBUser();
        nBUser1.setId(1L);
        NBUser nBUser2 = new NBUser();
        nBUser2.setId(nBUser1.getId());
        assertThat(nBUser1).isEqualTo(nBUser2);
        nBUser2.setId(2L);
        assertThat(nBUser1).isNotEqualTo(nBUser2);
        nBUser1.setId(null);
        assertThat(nBUser1).isNotEqualTo(nBUser2);
    }
}
