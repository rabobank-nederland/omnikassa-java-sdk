package nl.rabobank.gict.payments_savings.omnikassa_frontend.test.webshop;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.Endpoint;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.TokenProvider;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.MerchantOrder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.CountryCode;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Gender;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.ItemCategory;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrand;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrandForce;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.VatCategory;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.Address;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.CustomerInformation;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.OrderItem;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.OrderItem.Builder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request.InitiateRefundRequest;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.ApiNotification;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.IdealIssuersResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderResult;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderStatusResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.PaymentBrandsResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.PaymentCompletedResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.RefundDetailsResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.TransactionRefundableDetailsResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.test.webshop.model.CustomTokenProvider;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.test.webshop.model.WebShopOrder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.test.webshop.model.enums.PaymentStatusMessage;

import javax.servlet.http.HttpServletRequest;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Logger;

import static java.lang.Boolean.parseBoolean;
import static java.math.RoundingMode.HALF_UP;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency.EUR;
import static nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.PaymentCompletedResponse.newPaymentCompletedResponse;
import static org.apache.commons.lang3.EnumUtils.isValidEnum;
import static org.apache.commons.lang3.StringUtils.isBlank;


@Controller
@PropertySource("classpath:application.properties")
@RequestMapping("/webshop")
class WebshopController {
    private static final String FAKE_WEBSHOP = "fake-webshop";

    private int iterator = 0;
    private final String baseUrl;
    private final Map<Integer, WebShopOrder> webShopOrderMap = new HashMap<>();
    private final Endpoint endpoint;
    private ApiNotification latestApiNotification;
    private final byte[] signingKey;
    private final Logger logger = Logger.getLogger(WebshopController.class.toString());
    private String loggingPath;

    WebshopController(@Value("${signing_key}") String key,
                      @Value("${refresh_token}") String token,
                      @Value("${base_url}") String baseUrl,
                      @Value("${user_agent:TestWebshop/1.14}") String userAgent,
                      @Value("${partner_reference}") String partnerReference) {
        this.signingKey = getSigningKey(key);
        this.baseUrl = baseUrl;
        TokenProvider tokenProvider = new CustomTokenProvider(token);
        endpoint = Endpoint.createInstance(baseUrl, signingKey, tokenProvider, userAgent, partnerReference);
        
        webShopOrderMap.put(iterator, new WebShopOrder(iterator));
    }

    @GetMapping(value = "/home")
    ModelAndView fakeWebShop(ModelMap modelMap) {
        modelMap.addAttribute("shoppingCart", webShopOrderMap.get(iterator));
        return new ModelAndView(FAKE_WEBSHOP, modelMap);
    }

    @PostMapping(value = "/orders")
    ModelAndView placeOrder(HttpServletRequest request) throws RabobankSdkException {
        MerchantOrderResponse orderResponse = announceOrder(request);
        createNewOrder();
        return new ModelAndView("redirect:" + orderResponse.getRedirectUrl());
    }

    @PostMapping(value = "/webhook")
    ResponseEntity<Void> webhook(@RequestBody ApiNotification apiNotification) {
        logger.info("webhook is called");
        latestApiNotification = apiNotification;
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/payment/complete")
    String paymentComplete(ModelMap modelMap, HttpServletRequest request) throws RabobankSdkException {
        PaymentCompletedResponse notificationCompletedPaymentResponse = createPaymentCompletedResponse(request);
        logger.info("payment with order_id: " + notificationCompletedPaymentResponse.getOrderID() + " has been completed with status: " + notificationCompletedPaymentResponse.getStatus());
        modelMap.addAttribute("paymentStatusMessage", PaymentStatusMessage.valueOf(notificationCompletedPaymentResponse.getStatus()).getMessage());
        return "fake-webshop-result";
    }

    @PostMapping(value = "/items")
    ModelAndView addItem(HttpServletRequest request, ModelMap modelMap) {
        if (request.getParameter("clearShoppingCart") != null) {
            webShopOrderMap.get(iterator).clear();
        } else {
            OrderItem orderItem = createOrderItem(request);
            webShopOrderMap.get(iterator).addItem(orderItem);
        }
        modelMap.addAttribute("shoppingCart", webShopOrderMap.get(iterator));
        return new ModelAndView(FAKE_WEBSHOP, modelMap);
    }

    @GetMapping(value = "/logs")
    String displayLogs(ModelMap model) throws IOException {
        FileReader fileReader = new FileReader(loggingPath);
        Scanner in = new Scanner(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.contains("INFO")) {
                stringBuilder.append(line);
                stringBuilder.append("<br />");
            }
        }
        in.close();
        fileReader.close();
        model.addAttribute("logs", stringBuilder.toString());
        return "fake-webshop-logs";
    }

    @GetMapping(value = "/retrieveUpdates")
    String retrieveUpdates() throws RabobankSdkException {
        if (latestApiNotification != null) {
            logger.info("Retrieving updates...");
            MerchantOrderStatusResponse merchantOrderStatusResponse = endpoint.retrieveAnnouncement(latestApiNotification);
            logOrderUpdate(merchantOrderStatusResponse.getOrderResults());
            while (merchantOrderStatusResponse.moreOrderResultsAvailable()) {
                merchantOrderStatusResponse = endpoint.retrieveAnnouncement(latestApiNotification);
                logOrderUpdate(merchantOrderStatusResponse.getOrderResults());
            }
        }
        return FAKE_WEBSHOP;
    }

    @GetMapping(value = "/retrievePaymentBrands")
    String retrievePaymentBrands(ModelMap model) throws RabobankSdkException {
        PaymentBrandsResponse paymentBrandsResponse = endpoint.retrievePaymentBrands();
        model.addAttribute("paymentBrands", paymentBrandsResponse.getPaymentBrands());
        return "fake-webshop-paymentbrands";
    }

    @GetMapping("/retrieveIdealIssuers")
    String retrieveIdealIssuers(ModelMap model) throws RabobankSdkException {
        updateModelWithIdealIssuers(model);
        return "fake-webshop-idealissuers";
    }

    @PostMapping("/refreshIdealIssuers")
    String refreshIdealIssuers(ModelMap model) throws RabobankSdkException {
        updateModelWithIdealIssuers(model);
        return FAKE_WEBSHOP;
    }

    @GetMapping("/initiateRefund")
    String initiateRefund(HttpServletRequest request, ModelMap model) throws RabobankSdkException {
        if (!StringUtils.isEmpty(request.getParameter("transactionId"))) {
            model.addAttribute("transactionRefundableDetails", fetchTransactionRefundableDetails(request.getParameter("transactionId")));
        }
        return "initiate-refund";
    }

    @PostMapping("/submitRefund")
    RedirectView submitRefund(HttpServletRequest request, RedirectAttributes model) throws RabobankSdkException {
        RefundDetailsResponse refundDetailsResponse = initiateRefundTransaction(request);
        model.addAttribute("refundId", refundDetailsResponse.getRefundId());
        model.addAttribute("transactionId", refundDetailsResponse.getTransactionId());
        return new RedirectView("fetchRefundDetails");
    }

    @GetMapping("/fetchRefundDetails")
    String fetchRefundTransaction(HttpServletRequest request, ModelMap model)
            throws RabobankSdkException, IllegalArgumentException {
        if (!StringUtils.isEmpty(request.getParameter("transactionId")) && !StringUtils.isEmpty(request.getParameter("refundId"))) {
            UUID transactionId = UUID.fromString(request.getParameter("transactionId"));
            UUID refundId = UUID.fromString(request.getParameter("refundId"));
            RefundDetailsResponse refundDetailsResponse = endpoint.fetchRefundTransaction(transactionId, refundId);
            model.addAttribute("refundDetailsResponse", refundDetailsResponse);
            model.addAttribute("transactionRefundableDetails", fetchTransactionRefundableDetails(request.getParameter("transactionId")));
        }
        return "refund-details";
    }

    private void updateModelWithIdealIssuers(ModelMap model) throws RabobankSdkException {
        IdealIssuersResponse idealIssuersResponse = endpoint.retrieveIdealIssuers();
        model.addAttribute("idealIssuers", idealIssuersResponse.getIssuers());
    }

    private void logOrderUpdate(List<MerchantOrderResult> orderResults) {
        for (MerchantOrderResult merchantOrderResult : orderResults) {
            logger.info("update for merchant order: " + merchantOrderResult.getMerchantOrderId() + " Status:" + merchantOrderResult.getOrderStatus());
        }
    }

    private byte[] getSigningKey(String base64EncodedSigningKey) {
        return Base64Utils.decodeFromString(base64EncodedSigningKey);
    }

    private void iterate() {
        iterator++;
    }

    private void createNewOrder() {
        iterate();
        webShopOrderMap.put(iterator, new WebShopOrder(iterator));
    }

    private MerchantOrderResponse announceOrder(HttpServletRequest request) throws RabobankSdkException {
        MerchantOrder merchantOrder = prepareOrder(request);
        logger.info(() -> "Merchant order is Announced with order Id: " + iterator);
        return endpoint.announce(merchantOrder);
    }

    private MerchantOrder prepareOrder(HttpServletRequest request) {
        Address shippingDetails = createShippingDetails(request);
        Address billingDetails = createBillingDetails(request);
        CustomerInformation customerInformation = createCustomerInformation(request);
        PaymentBrand paymentBrand = createPaymentBrand(request);
        PaymentBrandForce paymentBrandForce = getEnum(PaymentBrandForce.class, request.getParameter("paymentBrandForce"));
        String preselectedIssuerId = getPreselectedIssuerId(request);
        boolean skipHppResultPage = parseBoolean(request.getParameter("skipHppResultPage"));
        String shopperRef = request.getParameter("shopperRef");
        boolean cof = parseBoolean(request.getParameter("enableCardsOnFile"));
        String initiatingParty = request.getParameter("initiatingParty");
        String shopperBankstatementReference = request.getParameter("shopperBankstatementReference");
        if (cof && ( shopperRef.isEmpty() || customerInformation.getEmailAddress().isEmpty())) {
            throw new IllegalArgumentException("shopperRef and Customer email are required when Cards on File is enabled");
        }
        return webShopOrderMap.get(iterator).prepareMerchantOrder(customerInformation, shippingDetails, billingDetails,
                                                                  paymentBrand, paymentBrandForce, preselectedIssuerId, cof, shopperRef,
                                                                  initiatingParty, skipHppResultPage, shopperBankstatementReference);
    }

    private CustomerInformation createCustomerInformation(HttpServletRequest request) {
        return new CustomerInformation.Builder()
                .withTelephoneNumber(request.getParameter("phoneNumber"))
                .withInitials(request.getParameter("initials"))
                .withGender(getEnum(Gender.class, request.getParameter("gender")))
                .withEmailAddress(request.getParameter("email"))
                .withDateOfBirth(request.getParameter("birthDate"))
                .withFullName(request.getParameter("fullName"))
                .build();
    }

    private Address createBillingDetails(HttpServletRequest request) {
        return createAddressDetails(request, "billing");
    }

    private Address createShippingDetails(HttpServletRequest request) {
        return createAddressDetails(request, "shipping");
    }

    private Address createAddressDetails(HttpServletRequest request, String addressType) {
        String countryCode = request.getParameter(addressType + "CountryCode");
        return new Address.Builder()
                .withFirstName(request.getParameter(addressType + "FirstName"))
                .withMiddleName(request.getParameter(addressType + "MiddleName"))
                .withLastName(request.getParameter(addressType + "LastName"))
                .withStreet(request.getParameter(addressType + "Street"))
                .withHouseNumber(request.getParameter(addressType + "HouseNumber"))
                .withHouseNumberAddition(request.getParameter(addressType + "HouseNumberAddition"))
                .withPostalCode(request.getParameter(addressType + "PostalCode"))
                .withCity(request.getParameter(addressType + "City"))
                .withCountryCode(CountryCode.valueOf(countryCode))
                .build();
    }

    private OrderItem createOrderItem(HttpServletRequest request) {
        String quantity = request.getParameter("quantity");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        boolean taxEnabled = parseBoolean(request.getParameter("taxEnabled"));
        ItemCategory itemCategory = ItemCategory.valueOf(request.getParameter("category"));
        VatCategory vatCategory = getEnum(VatCategory.class, request.getParameter("vat"));

        double priceDouble = Double.parseDouble(price) / 100;
        BigDecimal priceBigDecimal = BigDecimal.valueOf(priceDouble);
        priceBigDecimal = priceBigDecimal.setScale(2, HALF_UP);
        Money amount = Money.fromEuros(EUR, priceBigDecimal);

        Money tax = null;
        if (taxEnabled) {
            tax = getTax(priceDouble);
        }

        return new Builder()
                .withId(String.valueOf(iterator))
                .withQuantity(Integer.parseInt(quantity))
                .withName(name)
                .withDescription(name)
                .withAmount(amount)
                .withTax(tax)
                .withItemCategory(itemCategory)
                .withVatCategory(vatCategory)
                .build();
    }

    private Money getTax(double priceDouble) {
        double taxDouble = priceDouble * 0.1;
        BigDecimal taxBigDecimal = BigDecimal.valueOf(taxDouble);
        taxBigDecimal = taxBigDecimal.setScale(2, HALF_UP);
        return Money.fromEuros(EUR, taxBigDecimal);
    }

    private PaymentBrand createPaymentBrand(HttpServletRequest request) {
        String paymentBrand = request.getParameter("paymentBrand");
        if ("any".equalsIgnoreCase(paymentBrand)) {
            return null;
        }
        return getEnum(PaymentBrand.class, paymentBrand);
    }

    private String getPreselectedIssuerId(HttpServletRequest request) {
        String preselectedIssuerId = request.getParameter("issuerId");
        return isBlank(preselectedIssuerId) ? null : preselectedIssuerId;
    }

    private <E extends Enum<E>> E getEnum(Class<E> clazz, String value) {
        if (isValidEnum(clazz, value)) {
            return Enum.valueOf(clazz, value);
        }
        return null;
    }

    private PaymentCompletedResponse createPaymentCompletedResponse(HttpServletRequest request)
            throws RabobankSdkException {
        String orderId = request.getParameter("order_id");
        String status = request.getParameter("status");
        String signature = request.getParameter("signature");
        return newPaymentCompletedResponse(orderId, status, signature, signingKey);
    }

    private RefundDetailsResponse initiateRefundTransaction(HttpServletRequest request)
            throws RabobankSdkException, IllegalArgumentException {
        Money money = Money.fromEuros(EUR, BigDecimal.valueOf(Double.parseDouble(request.getParameter("amount"))));
        InitiateRefundRequest refundRequest = new InitiateRefundRequest(money, request.getParameter("description"),
                                                                        StringUtils.isEmpty(request.getParameter("vat")) ? null : VatCategory.valueOf(request.getParameter("vat")));

        RefundDetailsResponse response = endpoint.initiateRefundTransaction(refundRequest, UUID.fromString(request.getParameter("transactionId")), UUID.randomUUID());

        return response;
    }

    private TransactionRefundableDetailsResponse fetchTransactionRefundableDetails(String uuid)
            throws RabobankSdkException {
        return endpoint.fetchRefundableTransactionDetails(UUID.fromString(uuid));
    }
}