package com.ormvah.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ormvah.web.rest.TestUtil;

public class BordereauTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bordereau.class);
        Bordereau bordereau1 = new Bordereau();
        bordereau1.setId(1L);
        Bordereau bordereau2 = new Bordereau();
        bordereau2.setId(bordereau1.getId());
        assertThat(bordereau1).isEqualTo(bordereau2);
        bordereau2.setId(2L);
        assertThat(bordereau1).isNotEqualTo(bordereau2);
        bordereau1.setId(null);
        assertThat(bordereau1).isNotEqualTo(bordereau2);
    }
}
