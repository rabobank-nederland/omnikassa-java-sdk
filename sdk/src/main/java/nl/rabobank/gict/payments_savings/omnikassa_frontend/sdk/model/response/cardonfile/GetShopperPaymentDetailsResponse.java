package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.cardonfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import kong.unirest.json.JSONObject;

public class GetShopperPaymentDetailsResponse {

    private final List<CardOnFile> cardOnFileList;
    public GetShopperPaymentDetailsResponse(JSONObject jsonObject) {

        List<CardOnFile> cofileList = Collections.emptyList();
        if(jsonObject.has("cardOnFileList")) {
            cofileList = StreamSupport.stream(() -> jsonObject.getJSONArray("cardOnFileList").spliterator(), Spliterator.ORDERED, false)
                                        .map((cardOnFileListJsonObject) -> new CardOnFile((JSONObject) cardOnFileListJsonObject))
                                        .collect(Collectors.toList());
        }
        this.cardOnFileList = Collections.unmodifiableList(cofileList);
    }

    public List<CardOnFile> getCardOnFileList() { return cardOnFileList; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GetShopperPaymentDetailsResponse that = (GetShopperPaymentDetailsResponse) o;
        return Objects.equals(cardOnFileList, that.cardOnFileList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cardOnFileList);
    }
}
