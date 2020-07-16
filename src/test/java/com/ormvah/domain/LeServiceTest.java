package com.ormvah.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ormvah.web.rest.TestUtil;

public class LeServiceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeService.class);
        LeService leService1 = new LeService();
        leService1.setId(1L);
        LeService leService2 = new LeService();
        leService2.setId(leService1.getId());
        assertThat(leService1).isEqualTo(leService2);
        leService2.setId(2L);
        assertThat(leService1).isNotEqualTo(leService2);
        leService1.setId(null);
        assertThat(leService1).isNotEqualTo(leService2);
    }
}
