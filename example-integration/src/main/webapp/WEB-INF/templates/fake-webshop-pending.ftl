<#import 'macros.ftl' as macros />
<#import 'result.ftl' as res/>

<@macros.header />
<@nav.tabComponent ''/>
<div class="mt-3">
    <@res.result paymentStatusMessage='Check order status here.'/>
</div>

<@macros.footer />