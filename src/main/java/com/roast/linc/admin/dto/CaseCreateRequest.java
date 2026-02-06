package com.roast.linc.admin.dto;

import com.roast.linc.enums.CaseCategory;

public class CaseCreateRequest {
    private String title;
    private String description;

    // JSON'da "caseCategory" gönderiyorsun, Java'da adı "caseCategory" olmalı.
    private CaseCategory caseCategory;

    // Boş Constructor ŞART!
    public CaseCreateRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CaseCategory getCaseCategory() {
        return caseCategory;
    }

    public void setCaseCategory(CaseCategory caseCategory) {
        this.caseCategory = caseCategory;
    }
}

