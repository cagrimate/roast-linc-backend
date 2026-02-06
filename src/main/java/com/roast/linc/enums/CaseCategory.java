package com.roast.linc.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.deser.std.StdDeserializer;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = CaseCategory.CaseCategoryDeserializer.class)

public enum CaseCategory {
    TRAFFIC(10, "Trafik İhlali"),
    CYBER_BULLYING(20, "Siber Zorbalık"),
    ENVIRONMENT(30, "Çevre Kirliliği"),
    GENERAL(40, "Genel Etik Sorunları");

    private final int id;
    private final String name;

    private CaseCategory(int id, String name) {
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
    public static class CaseCategoryDeserializer extends StdDeserializer<CaseCategory> {
        private static final long serialVersionUID = 1L;

        public CaseCategoryDeserializer() {
            super(CaseCategory.class);
        }

        @Override
        public CaseCategory deserialize(JsonParser jp, DeserializationContext dc) {
            final JsonNode jsonNode = jp.readValueAsTree();

            // Eğer JSON'da sadece sayı gelirse veya {"id": 1} gelirse her iki durumu da kapsayalım
            int id = jsonNode.isNumber() ? jsonNode.asInt() : jsonNode.get("id").asInt();

            for (CaseCategory type : CaseCategory.values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return GENERAL; // Geçersiz ID gelirse çekimser dön
        }
    }
}
