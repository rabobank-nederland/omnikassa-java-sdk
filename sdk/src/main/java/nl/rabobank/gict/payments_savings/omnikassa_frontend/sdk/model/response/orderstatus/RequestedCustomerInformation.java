package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.orderstatus;

import kong.unirest.json.JSONObject;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.JSONFieldUtil;

public class RequestedCustomerInformation {
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final String telephoneNumber;
    private final RequestedCompanyInformation company;


    public RequestedCustomerInformation(JSONObject jsonObject) {
        this.firstName = JSONFieldUtil.getString(jsonObject, "firstName");
        this.lastName = JSONFieldUtil.getString(jsonObject, "lastName");
        this.emailAddress = JSONFieldUtil.getString(jsonObject, "emailAddress");
        this.telephoneNumber = JSONFieldUtil.getString(jsonObject, "telephoneNumber");
        this.company = new RequestedCompanyInformation(JSONFieldUtil.getJSONObject(jsonObject, "company"));
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getEmailAddress() { return emailAddress; }

    public String getTelephoneNumber() { return telephoneNumber; }

    public RequestedCompanyInformation getCompany() { return company; }
}
