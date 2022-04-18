<#import "macros.ftl" as orderMacros>
<#assign id = "customer_information">
<div class="container-fluid">
    <form method="post" action="/webshop/orders" name="order">
        <br/>
        <div class="row">
            <!-- row 1: Customer information -->
            <div class="col-6">
                <@orderMacros.card "Customer information">
                    <@orderMacros.inputField "${id}initials" "Initials:" "initials"></@orderMacros.inputField>
                    <@orderMacros.inputField "${id}_full_name" "Full name:" "fullName"></@orderMacros.inputField>
                    <@orderMacros.inputField "${id}_birth_date" "Birth date:" "birthDate" "DD-MM-YYYY"></@orderMacros.inputField>
                    <@orderMacros.inputField "${id}_phone_number" "First name:" "phoneNumber"></@orderMacros.inputField>
                    <@orderMacros.inputField "${id}_email" "E-mail address:" "email"></@orderMacros.inputField>
                    <@orderMacros.inputSelect "${id}_gender" "Gender:" "gender">
                        <option value="" selected="selected"></option>
                        <option value="F">Female</option>
                        <option value="M">Male</option>
                    </@orderMacros.inputSelect>
                </@orderMacros.card>
            </div>
        </div>
        <br/>
        <div class="row">
            <!-- row 2: Shipping details -->
            <div class="col-6">
                <@orderMacros.card "Shipping details">
                    <@orderMacros.address "Shipping"/>
                </@orderMacros.card>
            </div>

            <!-- row 2: Billing details -->
            <div class="col-6">
                <@orderMacros.card "Billing details">
                    <@orderMacros.address "Billing"/>
                </@orderMacros.card>
            </div>
        </div>
        <br/>

        <div class="row">
            <div class="col-6">
                <@orderMacros.card "Order details">
                    <@orderMacros.inputSelect "payment_brand" "Payment brand:" "paymentBrand">
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
                    </@orderMacros.inputSelect>

                    <@orderMacros.inputSelect "payment_brand_force" "Payment brand force:" "paymentBrandForce">
                        <option value="" selected="selected"></option>
                        <option value="FORCE_ONCE">Force Once</option>
                        <option value="FORCE_ALWAYS">Force Always</option>
                    </@orderMacros.inputSelect>

                    <@orderMacros.inputSelectWithButton "idealIssuers" "Issuer ID:" "issuerId" "refreshIssuerId" "Refresh"
                    "/webshop/refreshIdealIssuers">
                        <option value="" selected="selected"></option>
                        <#if idealIssuers??>
                            <#list idealIssuers as idealIssuer>
                                <option value="${idealIssuer.id}">${idealIssuer.name}</option>
                            </#list>
                        </#if>
                    </@orderMacros.inputSelectWithButton>

                    <@orderMacros.inputField "initiating_party" "Initiating party:" "initiatingParty"></@orderMacros.inputField>

                    <label class="form-check-label" for="skip_hpp_result_page">Skip HPP result page?</label>
                    <input class="form-check-input" type="checkbox" id="skip_hpp_result_page" name="skipHppResultPage"
                           value="true">
                    <br/>
                    <br/>
                    <div class="col-3">
                        <@orderMacros.submitButton "place order" "place_order"/>
                    </div>

                </@orderMacros.card>
            </div>
        </div>
    </form>
</div>
