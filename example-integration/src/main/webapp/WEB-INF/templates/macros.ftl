<#macro address address_type>
    <#include 'address.ftl' />
</#macro>

<#macro header>
    <!DOCTYPE html>

    <html lang="en">
    <head>
        <title>Rabobank Omnikassa SDK Test Webshop</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
                crossorigin="anonymous"></script>
    </head>
    <body>
    <div class="container">
    <ul class="nav">
        <li class="nav-item">
            <a class="nav-link" href="/webshop/home">Initiate Order</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/webshop/logs">Get Logs</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/webshop/retrieveUpdates">Get Updates</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/webshop/retrievePaymentBrands">Get PaymentBrands</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/webshop/retrieveIdealIssuers">Get iDEAL issuers</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/webshop/initiateRefund">Initiate Refund</a>
        </li>
    </ul>
</#macro>
<#macro footer>
    </div>
    </body>
    </html>
</#macro>