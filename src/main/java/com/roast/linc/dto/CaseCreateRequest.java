package com.roast.linc.dto;

import com.roast.linc.enums.CaseCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CaseCreateRequest {
    @NotBlank(message = "Başlık boş olamaz")
    @Size(min = 5, max = 100)
    private String title;

    @NotBlank(message = "Açıklama boş olamaz")
    private String description;

    @NotNull(message = "Kategori seçilmelidir")
    private CaseCategory caseCategory;

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
