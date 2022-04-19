<#import "macros.ftl" as macros />

<@macros.header />
<form action="" method="get">
    <table class="table">
        <tr>
            <td>Transaction Id:</td>
            <td><input name="transactionId" class="form-control"/></td>
            <td><input type="submit" value="Find Transaction Refundable Amount" class="btn btn-secondary"></td>
        </tr>
    </table>
</form>


<#if transactionRefundableDetails??>
    <p class="h6">Initiate refund:</p>
    <table class="table">
        <tr>
            <td>Refundable Amount</td>
            <td>${transactionRefundableDetails.refundableMoney.amount/100.0}</td>
        </tr>
    </table>
    <form action="submitRefund" method="post">
        <input type="hidden" name="transactionId" value="${transactionRefundableDetails.transactionId}"/>
        <table class="table">
            <tr>
                <td>Amount to refund:</td>
                <td><input name="amount" class="form-control"/></td>
            </tr>
            <tr>
                <td>Vat Category:</td>
                <td><#include 'vat-category.ftl' /></td>
            </tr>
            <tr>
                <td>Description:</td>
                <td><input name="description" class="form-control"/></td>
            </tr>
            <tr>
                <td colspan="2" class="text-center"><input type="submit" value="Submit a Refund"
                                                           class="btn btn-primary"/></td>
            </tr>
        </table>

    </form>
</#if>
<@macros.footer />