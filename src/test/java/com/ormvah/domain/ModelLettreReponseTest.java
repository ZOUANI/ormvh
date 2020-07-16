package com.ormvah.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ormvah.web.rest.TestUtil;

public class ModelLettreReponseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModelLettreReponse.class);
        ModelLettreReponse modelLettreReponse1 = new ModelLettreReponse();
        modelLettreReponse1.setId(1L);
        ModelLettreReponse modelLettreReponse2 = new ModelLettreReponse();
        modelLettreReponse2.setId(modelLettreReponse1.getId());
        assertThat(modelLettreReponse1).isEqualTo(modelLettreReponse2);
        modelLettreReponse2.setId(2L);
        assertThat(modelLettreReponse1).isNotEqualTo(modelLettreReponse2);
        modelLettreReponse1.setId(null);
        assertThat(modelLettreReponse1).isNotEqualTo(modelLettreReponse2);
    }
}
