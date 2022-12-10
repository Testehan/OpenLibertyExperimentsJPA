package com.testehan.openliberty.frontend.ui.facelets;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("pageLoader")        // needed because I think that this is how the instance of PageLoader is accessed from
@SessionScoped              // eventmanager.xhtml
public class PageLoader implements Serializable {

    private static final long serialVersionUID = 1L;

    private String content = "content/mainPage.xhtml";
    private String headerContent = "header/header.xhtml";
    private String navBar = "navBar/leftNav.xhtml";
    private String currentPage = "Events";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNavBar() {
        return navBar;
    }

    public void setNavBar(String navBar) {
        this.navBar = navBar;
    }

    public String getHeaderContent() {
        return this.headerContent;
    }

    public void setHeaderContent(String headerContent) {
        this.headerContent = headerContent;
    }

    public String getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

}
