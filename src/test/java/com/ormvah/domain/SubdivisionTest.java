package com.ormvah.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ormvah.web.rest.TestUtil;

public class SubdivisionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subdivision.class);
        Subdivision subdivision1 = new Subdivision();
        subdivision1.setId(1L);
        Subdivision subdivision2 = new Subdivision();
        subdivision2.setId(subdivision1.getId());
        assertThat(subdivision1).isEqualTo(subdivision2);
        subdivision2.setId(2L);
        assertThat(subdivision1).isNotEqualTo(subdivision2);
        subdivision1.setId(null);
        assertThat(subdivision1).isNotEqualTo(subdivision2);
    }
}
