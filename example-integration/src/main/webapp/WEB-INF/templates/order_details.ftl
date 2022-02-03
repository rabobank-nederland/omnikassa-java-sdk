<#import "macros.ftl" as macros />

<form method="post" action="/webshop/orders" name="order">
    <br />
    Customer information
    <table class="order">
        <tr>
            <td>
                <label for="customer_information_initials">Initials:</label>
                <input type="text" name="initials" id="customer_information_initials">
            </td>
        </tr>
        <tr>
            <td>
                <label for="customer_information_full_name">Full name:</label>
                <input type="text" name="fullName" id="customer_information_full_name">
            </td>
        </tr>
        <tr>
            <td>
                <label for="customer_information_birth_date">Birth date:</label>
                <input type="text" name="birthDate" id="customer_information_birth_date" placeholder="DD-MM-YYYY">
        </tr>
        <tr>
            <td>
                <label for="customer_information_phone_number">Phone number:</label>
                <input type="text" name="phoneNumber" id="customer_information_phone_number">
            </td>
        </tr>
        <tr>
            <td>
                <label for="customer_information_email">E-mail address:</label>
                <input type="text" name="email" id="customer_information_email">
            </td>
        </tr>
        <tr>
            <td>
                <label for="customer_information_gender">Gender:</label>
                <select name="gender" id="customer_information_gender">
                    <option value="" selected="selected"></option>
                    <option value="F">Female</option>
                    <option value="M">Male</option>
                </select>
            </td>
        </tr>
    </table>
    <br />
    <table>
        <tr>
            <td><@macros.address "Shipping"/></td>
            <td><@macros.address "Billing"/></td>
        </tr>
    </table>
    <br/>
    <table>
        <tr>
            <td>
                <label for="payment_brand">Payment brand:</label>
                <select name="paymentBrand" id="payment_brand">
                    <option value="ANY" selected="selected">Any</option>
                    <option value="IDEAL">iDEAL</option>
                    <option value="PAYPAL">PayPal</option>
                    <option value="AFTERPAY">AfterPay</option>
                    <option value="MASTERCARD">Mastercard</option>
                    <option value="VISA">Visa</option>
                    <option value="BANCONTACT">Bancontact</option>
                    <option value="MAESTRO">Maestro</option>
                    <option value="V_PAY">V Pay</option>
                    <option value="SOFORT">Sofort</option>
                    <option value="CARDS">Cards</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label for="payment_brand_force">Payment brand force:</label>
                <select name="paymentBrandForce" id="payment_brand_force">
                    <option value="" selected="selected"></option>
                    <option value="FORCE_ONCE">Force Once</option>
                    <option value="FORCE_ALWAYS">Force Always</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label for="idealIssuers">Issuer ID:</label>
                <select name="issuerId" id="idealIssuers">
                    <option value="" selected="selected"></option>
                    <#if idealIssuers??>
                        <#list idealIssuers as idealIssuer>
                            <option value="${idealIssuer.id}">${idealIssuer.name}</option>
                        </#list>
                    </#if>
                </select>
                <input type="submit" name="refreshIssuersId" value="Refresh" formaction="/webshop/refreshIdealIssuers" />
            </td>
        </tr>
        <tr>
            <td>
                <label for="initiating_party">initiating Party</label>
                <input type="text" name="initiatingParty" id="initiating_party">
            </td>
        </tr>
    </table>
    <br />
    <table>
        <tr>
            <td>
                <label for="skip_hpp_result_page">Skip HPP result page?</label>
                <input type="checkbox" id="skip_hpp_result_page" name="skipHppResultPage" value="true">
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="place order" id="place_order">
            </td>
        </tr>
    </table>
</form>