package com.taorusb.consolecrunduseshibernate.service;

import com.taorusb.consolecrunduseshibernate.model.Writer;

import java.util.List;

public interface WriterService {

    Writer getById(Long id);

    Writer saveWriter(Writer writer);

    Writer updateWriter(Writer writer);

    void deleteWriter(Long id);

    List<Writer> getAllWriters();
}
