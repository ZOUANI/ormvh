package com.ormvah.service.impl;

import com.ormvah.service.CategorieModelLettreReponseService;
import com.ormvah.domain.CategorieModelLettreReponse;
import com.ormvah.repository.CategorieModelLettreReponseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CategorieModelLettreReponse}.
 */
@Service
@Transactional
public class CategorieModelLettreReponseServiceImpl implements CategorieModelLettreReponseService {

    private final Logger log = LoggerFactory.getLogger(CategorieModelLettreReponseServiceImpl.class);

    private final CategorieModelLettreReponseRepository categorieModelLettreReponseRepository;

    public CategorieModelLettreReponseServiceImpl(CategorieModelLettreReponseRepository categorieModelLettreReponseRepository) {
        this.categorieModelLettreReponseRepository = categorieModelLettreReponseRepository;
    }

    @Override
    public CategorieModelLettreReponse save(CategorieModelLettreReponse categorieModelLettreReponse) {
        log.debug("Request to save CategorieModelLettreReponse : {}", categorieModelLettreReponse);
        return categorieModelLettreReponseRepository.save(categorieModelLettreReponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategorieModelLettreReponse> findAll() {
        log.debug("Request to get all CategorieModelLettreReponses");
        return categorieModelLettreReponseRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CategorieModelLettreReponse> findOne(Long id) {
        log.debug("Request to get CategorieModelLettreReponse : {}", id);
        return categorieModelLettreReponseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategorieModelLettreReponse : {}", id);
        categorieModelLettreReponseRepository.deleteById(id);
    }
}
