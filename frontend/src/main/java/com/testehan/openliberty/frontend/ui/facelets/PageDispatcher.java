package com.testehan.openliberty.frontend.ui.facelets;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named              // TODO is the @Named annotation needed here?
@SessionScoped
public class PageDispatcher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    @ManagedProperty(value = "#{pageLoader}")
    private PageLoader pageLoader;

    String currentRole = null;

    public PageLoader getPageLoader() {
        return pageLoader;
    }

    public void setPageLoader(PageLoader pageLoader) {
        this.pageLoader = pageLoader;
    }

    public void showEventForm() {
        pageLoader.setContent("content/eventForm.xhtml");
        pageLoader.setCurrentPage("Event Creation");
    }

    public void showMainPage() {
        pageLoader.setContent("content/mainPage.xhtml");
        pageLoader.setCurrentPage("Events");
    }

    public void showEditPage() {
        pageLoader.setContent("content/updateEventForm.xhtml");
        pageLoader.setCurrentPage("Edit Event");
    }

}
