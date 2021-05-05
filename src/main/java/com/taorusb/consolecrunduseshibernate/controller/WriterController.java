package com.taorusb.consolecrunduseshibernate.controller;

import com.taorusb.consolecrunduseshibernate.model.Region;
import com.taorusb.consolecrunduseshibernate.model.Writer;
import com.taorusb.consolecrunduseshibernate.service.RegionService;
import com.taorusb.consolecrunduseshibernate.service.WriterService;
import org.hibernate.ObjectNotFoundException;

import java.util.List;
import java.util.NoSuchElementException;

public class WriterController {

    private WriterService writerService;
    private RegionService regionService;

    public void setWriterService(WriterService writerService) {
        this.writerService = writerService;
    }

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    public List<Writer> showAll() {
        return writerService.getAllWriters();
    }

    public Writer addNewWriter(ResponseStatus responseStatus, String firstName, String lastName, long regionId) {

        Writer writer = null;
        try {
            Region region = regionService.getById(regionId);
            writer = writerService.saveWriter(new Writer(firstName, lastName, region));
            responseStatus.setSuccessful();
        } catch (ObjectNotFoundException e) {
            responseStatus.setElementNotFoundStatus();
        }

        return writer;
    }

    public Writer updateWriter(ResponseStatus responseStatus,long id, String firstName, String lastName, long regionId) {

        Writer writer = null;
        try {
            Region region = regionService.getById(regionId);
            writer = writerService.updateWriter(new Writer(id, firstName, lastName, region));
            responseStatus.setSuccessful();
        } catch (ObjectNotFoundException e) {
            responseStatus.setElementNotFoundStatus();
        }
        return writer;
    }

    public void deleteWriter(ResponseStatus responseStatus, long id) {

        try {
            writerService.deleteWriter(id);
            responseStatus.setSuccessful();
        } catch (ObjectNotFoundException e) {
            responseStatus.setElementNotFoundStatus();
        }
    }
}