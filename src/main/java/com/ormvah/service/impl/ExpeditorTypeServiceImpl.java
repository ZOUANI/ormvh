package com.ormvah.service.impl;

import com.ormvah.service.ExpeditorTypeService;
import com.ormvah.domain.ExpeditorType;
import com.ormvah.repository.ExpeditorTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ExpeditorType}.
 */
@Service
@Transactional
public class ExpeditorTypeServiceImpl implements ExpeditorTypeService {

    private final Logger log = LoggerFactory.getLogger(ExpeditorTypeServiceImpl.class);

    private final ExpeditorTypeRepository expeditorTypeRepository;

    public ExpeditorTypeServiceImpl(ExpeditorTypeRepository expeditorTypeRepository) {
        this.expeditorTypeRepository = expeditorTypeRepository;
    }

    @Override
    public ExpeditorType save(ExpeditorType expeditorType) {
        log.debug("Request to save ExpeditorType : {}", expeditorType);
        return expeditorTypeRepository.save(expeditorType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpeditorType> findAll() {
        log.debug("Request to get all ExpeditorTypes");
        return expeditorTypeRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ExpeditorType> findOne(Long id) {
        log.debug("Request to get ExpeditorType : {}", id);
        return expeditorTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExpeditorType : {}", id);
        expeditorTypeRepository.deleteById(id);
    }
}
