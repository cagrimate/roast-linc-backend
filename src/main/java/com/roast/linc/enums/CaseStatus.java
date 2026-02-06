package com.roast.linc.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.deser.std.StdDeserializer;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = CaseStatus.CaseStatusDeserializer.class)

public enum CaseStatus {
    WAITING(0, "Delil Bekleniyor"),
    ON_TRIAL(1, "Oylama Devam Ediyor"),
    VERDICT_REACHED(2, "Karar Verildi"),
    CLOSED(3, "Dava Kapatıldı");

    private final int id;
    private final String name;

    private CaseStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // JSON'dan Enum'a çevrim yapan iç sınıf
    public static class CaseStatusDeserializer extends StdDeserializer<CaseStatus> {
        private static final long serialVersionUID = 1L;

        public CaseStatusDeserializer() {
            super(CaseStatus.class);
        }

        @Override
        public CaseStatus deserialize(JsonParser jp, DeserializationContext dc) {
            final JsonNode jsonNode = jp.readValueAsTree();

            // Eğer JSON'da sadece sayı gelirse veya {"id": 1} gelirse her iki durumu da kapsayalım
            int id = jsonNode.isNumber() ? jsonNode.asInt() : jsonNode.get("id").asInt();

            for (CaseStatus type : CaseStatus.values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return WAITING; // Geçersiz ID gelirse çekimser dön
        }
    }
}
