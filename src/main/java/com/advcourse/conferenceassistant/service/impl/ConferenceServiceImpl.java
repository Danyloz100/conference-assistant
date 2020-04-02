package com.advcourse.conferenceassistant.service.impl;

import com.advcourse.conferenceassistant.exception.NoSuchConferenceException;
import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.repository.ConferenceRepository;
import com.advcourse.conferenceassistant.service.ConferenceService;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.mapper.ConferenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    @Autowired
    private ConferenceRepository conferenceRepository;


    @Override
    public ConferenceDto findById(Long id) {
        try {
           return ConferenceMapper.toDto(conferenceRepository.findById(id).get());
        }catch (NoSuchElementException e){
            throw new NoSuchConferenceException();
        }


    }

    @Override
    public ConferenceDto saveConference(ConferenceDto dto) {
        Conference conference = conferenceRepository.saveAndFlush(ConferenceMapper.fromDto(dto));
    return ConferenceMapper.toDto(conference);
    }

    @Override
    public List<ConferenceDto> findAll() {
        return ConferenceMapper.toDto(conferenceRepository.findAll());
    }

    @Override
    public void deleteById(Long id) {
        conferenceRepository.deleteById(id);
    }

}
