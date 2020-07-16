package com.ormvah.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ormvah.web.rest.TestUtil;

public class ExpeditorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Expeditor.class);
        Expeditor expeditor1 = new Expeditor();
        expeditor1.setId(1L);
        Expeditor expeditor2 = new Expeditor();
        expeditor2.setId(expeditor1.getId());
        assertThat(expeditor1).isEqualTo(expeditor2);
        expeditor2.setId(2L);
        assertThat(expeditor1).isNotEqualTo(expeditor2);
        expeditor1.setId(null);
        assertThat(expeditor1).isNotEqualTo(expeditor2);
    }
}
