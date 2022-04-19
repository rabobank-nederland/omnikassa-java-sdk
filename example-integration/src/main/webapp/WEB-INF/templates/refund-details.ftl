<#import "macros.ftl" as macros />

<@macros.header />
<form action="fetchRefundDetails" method="get">
    <table class="table">
        <tr>
            <td>Transaction id:</td>
            <td><input name="transactionId" class="form-control"/></td>
            <td>Refund id:</td>
            <td><input name="refundId" class="form-control"/></td>
            <td><input type="submit" value="Query" class="btn btn-secondary"/></td>
        </tr>
    </table>
</form>
<#if refundDetailsResponse ??>
    <p class="h6">Refund details:</p>
    <table class="table">
        <tr>
            <td>Refund id:</td>
            <td>${refundDetailsResponse.refundId}</td>
        </tr>
        <tr>
            <td>Transaction id:</td>
            <td>${refundDetailsResponse.transactionId}</td>
        </tr>
        <tr>
            <td>Amount:</td>
            <td>${refundDetailsResponse.refundMoney.amount/100.0}</td>
        </tr>
        <tr>
            <td>Vat:</td>
            <td>${refundDetailsResponse.vatCategory}</td>
        </tr>
        <tr>
            <td>Description:</td>
            <td>${refundDetailsResponse.description}</td>
        </tr>
        <tr>
            <td>Status:</td>
            <td>${refundDetailsResponse.status}</td>
        </tr>
        <tr>
            <td>Payment Brand:</td>
            <td>${refundDetailsResponse.paymentBrand}</td>
        </tr>
        <#if transactionRefundableDetails??>
            <tr>
                <td>Refundable Amount:</td>
                <td>${transactionRefundableDetails.refundableMoney.amount/100.0}</td>
            </tr>
        </#if>
    </table>
</#if>
<@macros.footer />