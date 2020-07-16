package com.ormvah.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ormvah.web.rest.TestUtil;

public class CategorieModelLettreReponseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieModelLettreReponse.class);
        CategorieModelLettreReponse categorieModelLettreReponse1 = new CategorieModelLettreReponse();
        categorieModelLettreReponse1.setId(1L);
        CategorieModelLettreReponse categorieModelLettreReponse2 = new CategorieModelLettreReponse();
        categorieModelLettreReponse2.setId(categorieModelLettreReponse1.getId());
        assertThat(categorieModelLettreReponse1).isEqualTo(categorieModelLettreReponse2);
        categorieModelLettreReponse2.setId(2L);
        assertThat(categorieModelLettreReponse1).isNotEqualTo(categorieModelLettreReponse2);
        categorieModelLettreReponse1.setId(null);
        assertThat(categorieModelLettreReponse1).isNotEqualTo(categorieModelLettreReponse2);
    }
}
