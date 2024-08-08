<#import "macros.ftl" as macros />

<@macros.header />
<table class="table">
    <tr>
        <td>Payment Brand</td>
        <td>Status</td>
    </tr>
    <#list paymentBrands as paymentBrand>
        <tr>
            <td>${paymentBrand.name} </td>
            <td>${paymentBrand.active?string('Active', 'Inactive')}</td>
        </tr>
    </#list>
</table>
<@macros.footer />