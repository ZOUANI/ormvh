package com.ormvah.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ormvah.web.rest.TestUtil;

public class CourrierTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Courrier.class);
        Courrier courrier1 = new Courrier();
        courrier1.setId(1L);
        Courrier courrier2 = new Courrier();
        courrier2.setId(courrier1.getId());
        assertThat(courrier1).isEqualTo(courrier2);
        courrier2.setId(2L);
        assertThat(courrier1).isNotEqualTo(courrier2);
        courrier1.setId(null);
        assertThat(courrier1).isNotEqualTo(courrier2);
    }
}
