package nl.rabobank.gict.payments_savings.omnikassa_frontend.test.webshop;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.Endpoint;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.TokenProvider;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.cardonfile.CardOnFile;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.cardonfile.ShopperPaymentDetailsResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.test.webshop.model.CustomTokenProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PropertySource("classpath:application.properties")
@RequestMapping("/cards-on-file")
class CardsOnFileController {

    private final byte[] signingKey;
    private final Endpoint endpoint;
    private final Logger logger = LoggerFactory.getLogger(CardsOnFileController.class);

    public CardsOnFileController(@Value("${signing_key}") String key,
                                 @Value("${refresh_token}") String token,
                                 @Value("${base_url}") String baseUrl,
                                 @Value("${user_agent:TestWebshop/1.14}") String userAgent,
                                 @Value("${partner_reference}") String partnerReference) {
        this.signingKey = getSigningKey(key);
        TokenProvider tokenProvider = new CustomTokenProvider(token);
        endpoint = Endpoint.createInstance(baseUrl, signingKey, tokenProvider, userAgent, partnerReference);
    }

    @GetMapping(value = "/cards")
    ModelAndView cardsOnFile(HttpServletRequest request, ModelMap modelMap, HttpSession httpSession)
            throws RabobankSdkException {
        String shopperRef = request.getParameter("shopperRef");

        if ( validateParameters(shopperRef)){
            List<CardOnFile> cards = getCardsOnFile(shopperRef).getCardOnFileList();
            modelMap.put("cardsOnFile", cards);
        }

        return new ModelAndView("cards-on-file", modelMap);
    }
    @PostMapping(value = "/delete-card")
    ModelAndView removeCardsOnFile( HttpServletRequest request, ModelMap modelMap)
            throws RabobankSdkException {

        String reference = request.getParameter("reference");
        String shopperRef = request.getParameter("shopperRef");

        if (validateParameters(shopperRef, reference)) {
            deleteCardsOnFile(shopperRef, reference);
        }
        return new ModelAndView("cards-on-file", modelMap);
    }

    private ShopperPaymentDetailsResponse getCardsOnFile(String shopperRef) throws RabobankSdkException {
        logger.info("Retrieving cards on file for shopperRef: {}", shopperRef);
        return endpoint.getShopperPaymentDetails(shopperRef);
    }
    private int deleteCardsOnFile(String shopperRef, String id)
            throws RabobankSdkException {
        logger.info("Deleting cards on file with id: {} for shopperRef: {}", id, shopperRef);
        return endpoint.deleteShopperPaymentDetails(shopperRef, id);
    }

    private byte[] getSigningKey(String base64EncodedSigningKey) {
        return Base64Utils.decodeFromString(base64EncodedSigningKey);
    }

    private boolean validateParameters(String... parameters) {
        return Arrays.stream(parameters).noneMatch(StringUtils::isEmpty);
    }
}
