<#macro result(paymentStatusMessage, orderStatus,)>
    <div class="mt-4">

        <div style="position: relative;">
            <a href="/webshop/home" style="display: block; position: absolute; z-index: 1; height: 500px; width: 100%;"></a>
        </div>
        <div class="d-flex flex-column gap-4">
            <h4 id="payment_status_message" data-order-status="${orderStatus}" style="text-align: center;">${paymentStatusMessage}</h4>
        </div>
    </div>
</#macro>