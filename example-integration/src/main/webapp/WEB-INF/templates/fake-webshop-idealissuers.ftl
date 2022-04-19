<#import "macros.ftl" as macros />

<@macros.header />
<table class="table">
    <tr>
        <td>id</td>
        <td>name</td>
        <td>logo</td>
        <td>mime type</td>
        <td>country names</td>
    </tr>
    <#list idealIssuers as idealIssuer>
        <tr>
            <td>${idealIssuer.id}</td>
            <td>${idealIssuer.name}</td>
            <td><img src=${idealIssuer.logos[0].url} alt=${idealIssuer.name} width="50" height="50"/></td>
            <td>${idealIssuer.logos[0].mimeType}</td>
            <td>${idealIssuer.countryNames}</td>
        </tr>
    </#list>
</table>
<@macros.footer />