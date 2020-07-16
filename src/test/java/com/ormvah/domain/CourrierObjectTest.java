package com.ormvah.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ormvah.web.rest.TestUtil;

public class CourrierObjectTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourrierObject.class);
        CourrierObject courrierObject1 = new CourrierObject();
        courrierObject1.setId(1L);
        CourrierObject courrierObject2 = new CourrierObject();
        courrierObject2.setId(courrierObject1.getId());
        assertThat(courrierObject1).isEqualTo(courrierObject2);
        courrierObject2.setId(2L);
        assertThat(courrierObject1).isNotEqualTo(courrierObject2);
        courrierObject1.setId(null);
        assertThat(courrierObject1).isNotEqualTo(courrierObject2);
    }
}
