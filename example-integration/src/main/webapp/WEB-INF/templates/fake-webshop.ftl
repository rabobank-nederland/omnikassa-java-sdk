<!DOCTYPE html>

<html lang="en">

<body>
<#include 'order_items.ftl' />
<#include 'order_details.ftl' />

<form method="get" action="/webshop/logs">
    <p><input type="submit" value="Show Logging" id="show_logs"></p>
</form>

<form method="get" action="/webshop/retrieveUpdates">
    <p><input type="submit" value="retrieve updates" id="retrieve_updates"></p>
</form>

<form method="get" action="/webshop/retrievePaymentBrands">
    <p><input type="submit" value="retrieve Payment Brands" id="retrieve_payment_brands"></p>
</form>

</body>

</html>