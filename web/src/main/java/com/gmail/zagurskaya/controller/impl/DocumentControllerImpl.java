package com.gmail.zagurskaya.controller.impl;

import com.gmail.zagurskaya.controller.DocumentController;
import com.gmail.zagurskaya.model.DocumentDTO;
import com.gmail.zagurskaya.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

@Controller
public class DocumentControllerImpl implements DocumentController {

    private DocumentService documentService;

    @Autowired
    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public DocumentDTO add(DocumentDTO document) {
        Assert.hasLength(document.getDescription(), "description is mandatory field");
        Assert.isTrue(document.getDescription().length() <= 100 , "description max length 100");

        return documentService.add(document);
    }

    @Override
    public DocumentDTO getDocumentById(Long id) {
        Assert.notNull(id, "id is mandatory field");
        Assert.isTrue(id <= 0 , "id more than zero");

        return documentService.getDocumentById(id);
    }

    @Override
    public void delete(Long id) {
        Assert.notNull(id, "id is mandatory field");
        Assert.isTrue(id <= 0 , "id more than zero");

        documentService.delete(id);
    }
}
