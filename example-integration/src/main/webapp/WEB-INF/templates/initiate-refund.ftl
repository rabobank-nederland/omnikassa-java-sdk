<form action="" method="get">
    <table>
        <tr>
            <td>Transaction Id</td>
            <td><input name="transactionId"/></td>
            <td><input type="submit" value="Find Transaction Refundable Amount"></td>
        </tr>
    </table>
</form>

<#if transactionRefundableDetails??>
    <table>
        <tr>
            <td>Refundable Amount</td>
            <td>${transactionRefundableDetails.refundableMoney.amount/100.0}</td>
        </tr>
    </table>
    <form action="submitRefund" method="post">
        <input type="hidden" name="transactionId" value="${transactionRefundableDetails.transactionId}"/>
        <table>
            <tr>
                <td>Amount</td>
                <td><input name="amount"/></td>
            </tr>
            <tr>
                <td>Vat Category</td>
                <td><#include 'vat-category.ftl' /></td>
            </tr>
            <tr>
                <td>Description</td>
                <td><input name="description"/></td>
            </tr>
        </table>
        <input type="submit" value="Submit a Refund"/>
    </form>
</#if>