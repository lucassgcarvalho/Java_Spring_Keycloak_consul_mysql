package com.essential.app.service;

import com.essential.app.model.Gender;
import com.essential.app.repository.GenderRepository;
import com.essential.app.resource.dto.GenderDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenderServiceImpl implements GenderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private GenderRepository genderRepository;
    private ModelMapper modelMapper;

    @Autowired
    public GenderServiceImpl(GenderRepository genderRepository, ModelMapper modelMapper) {
        this.genderRepository = genderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<GenderDto> findAll(Pageable pageable) {
        return genderRepository.findAll(pageable)
                .stream()
                .map(gender -> this.modelMapper.map(gender, GenderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GenderDto> findByNameIgnoreCase(String name) {
        Gender gender = this.genderRepository.getByNameIgnoreCase(name).orElseThrow(() -> getGenderNotFoundException(name));
        return Optional.ofNullable(modelMapper.map(gender, GenderDto.class));
    }

    @Override
    public void create(GenderDto genderDto) {
        Gender gender = modelMapper.map(genderDto, Gender.class);
        gender.setName(genderDto.getName().toLowerCase());
        if(this.findByNameIgnoreCase(gender.getName()).isPresent()){
            throw new DataIntegrityViolationException("Gender already exists with this name ["+gender.getName()+"]");
        }
        this.genderRepository.save(gender);
    }

    @Override
    public void update(String genderName, GenderDto genderDto) {
        Gender genderFound = this.genderRepository
                .getByNameIgnoreCase(genderName)
                .orElseThrow(() -> getGenderNotFoundException(genderName));

        genderFound.setName(genderDto.getName());
        this.genderRepository.save(genderFound);
    }

    @Override
    public void delete(String name) {
       try {
           this.genderRepository.deleteByName(name);
       }catch (EmptyResultDataAccessException e){
           logger.info(e.getMessage());
       }
    }

    private EntityNotFoundException getGenderNotFoundException(Object object) {
        return new EntityNotFoundException("Gender ["+String.valueOf(object)+"] Not Found");
    }
}
