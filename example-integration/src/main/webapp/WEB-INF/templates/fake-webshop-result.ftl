<#import 'macros.ftl' as macros />
<#import 'result.ftl' as res/>
<@macros.header />
<div class="mt-3">
    <#switch orderStatus>
        <#case "COMPLETED">
            <@res.result paymentStatusMessage='Payment successful' orderStatus=orderStatus/>
            <#break>
        <#case "CANCELLED">
            <@res.result paymentStatusMessage='You cancelled your payment' orderStatus=orderStatus/>
            <#break>
        <#case "IN_PROGRESS">
            <@res.result paymentStatusMessage='We can not verify your purchase at this moment' orderStatus=orderStatus/>
            <#break>
        <#case "EXPIRED">
            <@res.result paymentStatusMessage='Your payment session is expired' orderStatus=orderStatus/>
            <#break>
    </#switch>
</div>
<@macros.footer />

