package com.financiera.loansandbooks.service;

import com.financiera.loansandbooks.controller.Dtos.ReaderDTO;
import com.financiera.loansandbooks.exception.ResourceNotFoundException;
import com.financiera.loansandbooks.model.reader.Reader;
import com.financiera.loansandbooks.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReaderService {
    @Autowired
    private ReaderRepository readerRepository;

    public Reader createReader(ReaderDTO readerDTO){
        Reader reader = new Reader();
        reader.setName(readerDTO.getName());
        reader.setActive(readerDTO.getActive());
        reader.setPremium(readerDTO.getPremium());
        return readerRepository.save(reader);
    }

    public List<Reader> getAll(){
        return readerRepository.findAll();
    }

    public Reader findById(Long id){
        return readerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reader with ID " + id + " does not exist."));
    }

    @Transactional
    public Reader rehabilitateReader(Long id){
        Reader reader = findById(id);
        reader.setActive(true);
        return readerRepository.save(reader);
    }
}
