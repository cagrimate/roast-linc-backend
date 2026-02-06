package com.roast.linc.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = VoteType.VoteTypeDeserializer.class)
public enum VoteType {
    GUILTY(1, "Suçlu"),
    NOT_GUILTY(2, "Suçsuz"),
    CRINGE(3, "Cringe / Utanç Verici"),
    USELESS(4, "Bomboş İş"),
    BOO(5, "Yuh!"),
    LEGEND(6, "Efsane Hareket"),
    ABSTAIN(7, "Çekimser");

    private final int id;
    private final String name;

    private VoteType(int id, String name) {
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
    public static class VoteTypeDeserializer extends StdDeserializer<VoteType> {
        private static final long serialVersionUID = 1L;

        public VoteTypeDeserializer() {
            super(VoteType.class);
        }

        @Override
        public VoteType deserialize(JsonParser jp, DeserializationContext dc) {
            final JsonNode jsonNode = jp.readValueAsTree();

            // Eğer JSON'da sadece sayı gelirse veya {"id": 1} gelirse her iki durumu da kapsayalım
            int id = jsonNode.isNumber() ? jsonNode.asInt() : jsonNode.get("id").asInt();

            for (VoteType type : VoteType.values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return ABSTAIN; // Geçersiz ID gelirse çekimser dön
        }
    }
}
