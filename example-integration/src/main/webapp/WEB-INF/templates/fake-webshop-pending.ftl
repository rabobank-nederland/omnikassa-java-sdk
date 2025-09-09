<#import 'macros.ftl' as macros />
<#import 'result.ftl' as res/>

<@macros.header />
<@nav.tabComponent ''/>
<div class="mt-3">
    <@res.result paymentStatusMessage='Check order status or wait for webhook to arrive' orderStatus='Poll and find out :)'/>
</div>

<@macros.footer />