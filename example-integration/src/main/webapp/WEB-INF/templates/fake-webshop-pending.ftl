<#import 'macros.ftl' as macros />
<#import 'result.ftl' as res/>

<@macros.header />
<div class="mt-4">
    <@res.result paymentStatusMessage='The result of the payment is unknown. Retrieve order status for the final result.' orderStatus='PENDING'/>
</div>

<@macros.footer />