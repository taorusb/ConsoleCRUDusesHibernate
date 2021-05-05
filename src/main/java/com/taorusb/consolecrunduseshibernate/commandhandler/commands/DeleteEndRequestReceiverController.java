package com.taorusb.consolecrunduseshibernate.commandhandler.commands;

import com.taorusb.consolecrunduseshibernate.commandhandler.Handler;
import com.taorusb.consolecrunduseshibernate.commandhandler.Warehouse;
import com.taorusb.consolecrunduseshibernate.view.ShowPost;
import com.taorusb.consolecrunduseshibernate.view.ShowRegion;
import com.taorusb.consolecrunduseshibernate.view.ShowWriter;

public class DeleteEndRequestReceiverController implements Handler {

    private ShowWriter showWriter;
    private ShowPost showPost;
    private ShowRegion showRegion;

    public void setShowWriter(ShowWriter showWriter) {
        this.showWriter = showWriter;
    }

    public void setShowPost(ShowPost showPost) {
        this.showPost = showPost;
    }

    public void setShowRegion(ShowRegion showRegion) {
        this.showRegion = showRegion;
    }

    @Override
    public void handle(String[] query) {

        if (checkString(query)) {

            Warehouse reqNumber = getCommandType(query[2]);
            String id = query[4].substring(3, query[4].length());

            switch (reqNumber) {
                case WRITERS:
                        showWriter.deleteWriter(id);
                        break;
                case POSTS:
                        showPost.deletePost(id);
                        break;
                case REGIONS:
                    showRegion.deleteRegion(id);
                    break;
            }
        }
    }

    private boolean checkString(String[] strings) {

        if (!strings[3].toLowerCase().equals("where")) {
            System.out.println("Incorrect key: " + strings[3]);
            return false;
        } else if (!strings[4].toLowerCase().startsWith("id=")) {
            System.out.println("Incorrect argument name: " + strings[4]);
            return false;
        }
        return true;
    }
}