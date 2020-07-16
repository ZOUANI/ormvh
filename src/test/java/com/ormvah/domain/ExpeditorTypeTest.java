package com.ormvah.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ormvah.web.rest.TestUtil;

public class ExpeditorTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpeditorType.class);
        ExpeditorType expeditorType1 = new ExpeditorType();
        expeditorType1.setId(1L);
        ExpeditorType expeditorType2 = new ExpeditorType();
        expeditorType2.setId(expeditorType1.getId());
        assertThat(expeditorType1).isEqualTo(expeditorType2);
        expeditorType2.setId(2L);
        assertThat(expeditorType1).isNotEqualTo(expeditorType2);
        expeditorType1.setId(null);
        assertThat(expeditorType1).isNotEqualTo(expeditorType2);
    }
}
