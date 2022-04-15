<form action="fetchRefundDetails" method="get">
    <table>
        <tr>
            <td>transaction id:</td>
            <td><input name="transactionId"/></td>
            <td>refund id:</td>
            <td><input name="refundId"/></td>
            <td><input type="submit" value="Query"/></td>
        </tr>
    </table>
</form>
<#if refundDetailsResponse ??>
    <table>
        <tr>
            <td>refund id</td>
            <td>${refundDetailsResponse.refundId}</td>
        </tr>
        <tr>
            <td>transaction id</td>
            <td>${refundDetailsResponse.transactionId}</td>
        </tr>
        <tr>
            <td>Amount</td>
            <td>${refundDetailsResponse.refundMoney.amount/100.0}</td>
        </tr>
        <tr>
            <td>Vat</td>
            <td>${refundDetailsResponse.vatCategory}</td>
        </tr>
        <tr>
            <td>Description</td>
            <td>${refundDetailsResponse.description}</td>
        </tr>
        <tr>
            <td>Status</td>
            <td>${refundDetailsResponse.status}</td>
        </tr>
        <tr>
            <td>Payment Brand</td>
            <td>${refundDetailsResponse.paymentBrand}</td>
        </tr>
        <#if transactionRefundableDetails??>
            <tr>
                <td>Refundable Amount</td>
                <td>${transactionRefundableDetails.refundableMoney.amount/100.0}</td>
            </tr>
        </#if>
    </table>
</#if>