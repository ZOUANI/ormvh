package com.ormvah.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ormvah.web.rest.TestUtil;

public class NatureCourrierTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatureCourrier.class);
        NatureCourrier natureCourrier1 = new NatureCourrier();
        natureCourrier1.setId(1L);
        NatureCourrier natureCourrier2 = new NatureCourrier();
        natureCourrier2.setId(natureCourrier1.getId());
        assertThat(natureCourrier1).isEqualTo(natureCourrier2);
        natureCourrier2.setId(2L);
        assertThat(natureCourrier1).isNotEqualTo(natureCourrier2);
        natureCourrier1.setId(null);
        assertThat(natureCourrier1).isNotEqualTo(natureCourrier2);
    }
}
