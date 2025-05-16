package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.cardonfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import kong.unirest.json.JSONObject;

public class CardsOnFileResponse {

    private final List<CardOnFile> cardOnFileList;
    public CardsOnFileResponse(JSONObject jsonObject) {

        List<CardOnFile> cardOnFileList = new ArrayList<>();
        if(jsonObject.has("cardOnFileList")) {
            cardOnFileList = StreamSupport.stream(() -> jsonObject.getJSONArray("cardOnFileList").spliterator(), Spliterator.ORDERED, false)
                                        .map((cardOnFileListJsonObject) -> new CardOnFile((JSONObject) cardOnFileListJsonObject))
                                        .collect(Collectors.toList());
        }
        this.cardOnFileList = Collections.unmodifiableList(cardOnFileList);
    }

    public List<CardOnFile> getCardOnFileList() { return cardOnFileList; }
}
