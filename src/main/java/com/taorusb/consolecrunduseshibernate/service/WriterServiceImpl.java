package com.taorusb.consolecrunduseshibernate.service;

import com.taorusb.consolecrunduseshibernate.model.Writer;
import com.taorusb.consolecrunduseshibernate.repository.WriterRepository;

import java.util.List;

public class WriterServiceImpl implements WriterService {

    private WriterRepository writerRepository;

    public void setWriterRepository(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    @Override
    public Writer getById(Long id) {
        return writerRepository.getById(id);
    }

    @Override
    public Writer saveWriter(Writer writer) {
        return writerRepository.save(writer);
    }

    @Override
    public Writer updateWriter(Writer writer) {
        return writerRepository.update(writer);
    }

    @Override
    public void deleteWriter(Long id) {
        writerRepository.deleteById(id);
    }

    @Override
    public List<Writer> getAllWriters() {
        return writerRepository.findAll();
    }
}