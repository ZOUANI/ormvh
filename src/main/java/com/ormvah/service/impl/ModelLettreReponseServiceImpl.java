package com.ormvah.service.impl;

import com.ormvah.service.ModelLettreReponseService;
import com.ormvah.domain.ModelLettreReponse;
import com.ormvah.repository.ModelLettreReponseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ModelLettreReponse}.
 */
@Service
@Transactional
public class ModelLettreReponseServiceImpl implements ModelLettreReponseService {

    private final Logger log = LoggerFactory.getLogger(ModelLettreReponseServiceImpl.class);

    private final ModelLettreReponseRepository modelLettreReponseRepository;

    public ModelLettreReponseServiceImpl(ModelLettreReponseRepository modelLettreReponseRepository) {
        this.modelLettreReponseRepository = modelLettreReponseRepository;
    }

    @Override
    public ModelLettreReponse save(ModelLettreReponse modelLettreReponse) {
        log.debug("Request to save ModelLettreReponse : {}", modelLettreReponse);
        return modelLettreReponseRepository.save(modelLettreReponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModelLettreReponse> findAll() {
        log.debug("Request to get all ModelLettreReponses");
        return modelLettreReponseRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ModelLettreReponse> findOne(Long id) {
        log.debug("Request to get ModelLettreReponse : {}", id);
        return modelLettreReponseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ModelLettreReponse : {}", id);
        modelLettreReponseRepository.deleteById(id);
    }
}
