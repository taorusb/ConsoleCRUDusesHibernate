package com.taorusb.consolecrunduseshibernate;

import com.taorusb.consolecrunduseshibernate.commandhandler.commands.*;
import com.taorusb.consolecrunduseshibernate.controller.PostController;
import com.taorusb.consolecrunduseshibernate.controller.RegionController;
import com.taorusb.consolecrunduseshibernate.controller.ResponseStatus;
import com.taorusb.consolecrunduseshibernate.controller.WriterController;
import com.taorusb.consolecrunduseshibernate.repository.impl.PostRepositoryImpl;
import com.taorusb.consolecrunduseshibernate.repository.impl.RegionRepositoryImpl;
import com.taorusb.consolecrunduseshibernate.repository.impl.WriterRepositoryImpl;
import com.taorusb.consolecrunduseshibernate.service.PostServiceImpl;
import com.taorusb.consolecrunduseshibernate.service.RegionServiceImpl;
import com.taorusb.consolecrunduseshibernate.service.WriterServiceImpl;
import com.taorusb.consolecrunduseshibernate.view.ShowPost;
import com.taorusb.consolecrunduseshibernate.view.ShowRegion;
import com.taorusb.consolecrunduseshibernate.view.ShowWriter;

public class Facade {

    private HibernateUtil connectionSupplier;

    private QueryManipulationCommandController qmcc;
    private DeleteDirectionController ddc;
    private DeleteEndRequestReceiverController derrc;
    private InsertDirectionController idc;
    private InsertEndRequestReceiverController ierrc;
    private SelectDirectionController sdc;
    private SelectEndRequestReceiverController serrc;
    private UpdateDirectionController udc;
    private UpdateEndRequestReceiverController uerrc;

    private WriterRepositoryImpl writerRepository;
    private PostRepositoryImpl postRepository;
    private RegionRepositoryImpl regionRepository;

    private WriterServiceImpl writerService;
    private PostServiceImpl postService;
    private RegionServiceImpl regionService;

    private WriterController writerController;
    private PostController postController;
    private RegionController regionController;

    private ShowWriter showWriter;
    private ShowPost showPost;
    private ShowRegion showRegion;

    private ResponseStatus responseStatus;

    private static Facade instance;

    private Facade() {
    }

    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }


    public void assembleApplication() {
        assembleService();
    }

    public void startApp(String query) {
        String[] arr = query.strip().split(" +");
        qmcc.handle(arr);
    }

    private void assembleService() {

        connectionSupplier = HibernateUtil.getInstance();

        writerRepository = WriterRepositoryImpl.getInstance();
        postRepository = PostRepositoryImpl.getInstance();
        regionRepository = RegionRepositoryImpl.getInstance();

        writerRepository.setConnectionSupplier(connectionSupplier);
        postRepository.setConnectionSupplier(connectionSupplier);
        regionRepository.setConnectionSupplier(connectionSupplier);

        writerService = new WriterServiceImpl();
        postService = new PostServiceImpl();
        regionService = new RegionServiceImpl();

        writerService.setWriterRepository(writerRepository);
        postService.setPostRepository(postRepository);
        regionService.setRegionRepository(regionRepository);

        assembleControllers();
    }

    private void assembleControllers() {

        responseStatus = new ResponseStatus();

        writerController = new WriterController();
        postController = new PostController();
        regionController = new RegionController();

        writerController.setWriterService(writerService);
        writerController.setRegionService(regionService);
        postController.setPostService(postService);
        postController.setWriterService(writerService);
        regionController.setRegionService(regionService);

        assembleView();
    }

    private void assembleView() {
        showWriter = new ShowWriter(writerController, responseStatus);
        showPost = new ShowPost(postController, responseStatus);
        showRegion = new ShowRegion(regionController, responseStatus);

        assembleChain();
    }

    private void assembleChain() {

        qmcc = new QueryManipulationCommandController();
        ddc = new DeleteDirectionController();
        derrc = new DeleteEndRequestReceiverController();
        idc = new InsertDirectionController();
        ierrc = new InsertEndRequestReceiverController();
        sdc = new SelectDirectionController();
        serrc = new SelectEndRequestReceiverController();
        udc = new UpdateDirectionController();
        uerrc = new UpdateEndRequestReceiverController();

        qmcc.setUpdateDirectionController(udc);
        qmcc.setInsertDirectionController(idc);
        qmcc.setDeleteDirectionController(ddc);
        qmcc.setSelectDirectionController(sdc);

        ddc.setEndRequestReceiverController(derrc);

        derrc.setShowWriter(showWriter);
        derrc.setShowRegion(showRegion);
        derrc.setShowPost(showPost);

        idc.setInsertEndRequestReceiverController(ierrc);

        ierrc.setShowWriter(showWriter);
        ierrc.setShowRegion(showRegion);
        ierrc.setShowPost(showPost);

        sdc.setSelectEndRequestReceiverController(serrc);

        serrc.setShowWriter(showWriter);
        serrc.setShowRegion(showRegion);
        serrc.setShowPost(showPost);

        udc.setEndRequestReceiverController(uerrc);

        uerrc.setShowWriter(showWriter);
        uerrc.setShowRegion(showRegion);
        uerrc.setShowPost(showPost);
    }
}