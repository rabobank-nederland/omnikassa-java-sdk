<!DOCTYPE html>
<meta http-equiv="refresh" content="5">
<html lang="en">
<body>

<br/>

<p id="paymentBrands">
    <table class="paymentBrands">
    <tr><td>Payment Brand</td><td>Status</td></tr>
    <#list paymentBrands as paymentBrand>
        <tr> <td>${paymentBrand.name} </td> <td>${paymentBrand.active?string('Active', 'Inactive')}</td></tr>
    </#list>
</table>
<br/>
<form METHOD="get" ACTION="/webshop/home">
    <p><input type="submit" value="home" id="home_button"></p>
</form>

</body>
</html>