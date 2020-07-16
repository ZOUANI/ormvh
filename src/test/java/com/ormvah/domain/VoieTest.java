package com.ormvah.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ormvah.web.rest.TestUtil;

public class VoieTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Voie.class);
        Voie voie1 = new Voie();
        voie1.setId(1L);
        Voie voie2 = new Voie();
        voie2.setId(voie1.getId());
        assertThat(voie1).isEqualTo(voie2);
        voie2.setId(2L);
        assertThat(voie1).isNotEqualTo(voie2);
        voie1.setId(null);
        assertThat(voie1).isNotEqualTo(voie2);
    }
}
