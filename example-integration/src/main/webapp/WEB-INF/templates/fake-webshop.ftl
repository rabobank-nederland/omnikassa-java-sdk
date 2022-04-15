<!DOCTYPE html>

<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <title>Fake Webshop</title>
</head>

<body>
<#import "macros.ftl" as macros />
<#include 'order_items.ftl' />
<#include 'order_details.ftl' />

<div class="container">
    <div class="d-grid gap-3 col-3">
        <form method="get" action="/webshop/logs">
            <@macros.submitButton "Show Logging" "show_logs" />
        </form>

        <form method="get" action="/webshop/retrieveUpdates">
            <@macros.submitButton "Retrieve updates" "retrieve_updates" />
        </form>

        <form method="get" action="/webshop/retrievePaymentBrands">
            <@macros.submitButton "Retrieve Payment Brands" "retrieve_payment_brands" />
        </form>

        <form method="get" action="/webshop/retrieveIdealIssuers">
            <@macros.submitButton "Retrieve iDEAL issuers" "retrieve_ideal_issuers" />
        </form>
    </div>
</div>
</body>

</html>