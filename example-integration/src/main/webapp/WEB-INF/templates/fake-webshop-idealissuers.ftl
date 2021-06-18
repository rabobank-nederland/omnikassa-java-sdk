<!DOCTYPE html>
<meta http-equiv="refresh" content="5">
<html lang="en">
<body>

<br/>

<p id="idealIssuers">
    <table class="idealIssuers">
    <tr>
        <td>Ideal Issuers</td>
        <td>id</td>
        <td>name</td>
        <td>logo url</td>
        <td>mime type</td>
        <td>country names</td>
    </tr>
    <#list idealIssuers as idealIssuer>
        <tr>
            <td>${idealIssuer.id}</td>
            <td>${idealIssuer.name}</td>
            <td>${idealIssuer.logos[0].url}</td>
            <td>${idealIssuer.logos[0].mimeType}</td>
            <td>${idealIssuer.countryNames}</td>
        </tr>
    </#list>
</table>
<br/>
<form METHOD="get" ACTION="/webshop/home">
    <p><input type="submit" value="home" id="home_button"></p>
</form>

</body>
</html>