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
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet"/>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2-bootstrap-5-theme@1.3.0/dist/select2-bootstrap-5-theme.min.css"/>
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
            <a class="nav-link" href="/cards-on-file/cards">Cards On File</a>
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
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous">
    </script>
    <script src="https://unpkg.com/@alenaksu/json-viewer@2.0.0/dist/json-viewer.bundle.js"></script>
    <script>

        $("[data-card-button]").on("click", function(event) {
            var reference = this.id;

            const queryString = window.location.search;
            const urlParams = new URLSearchParams(queryString);

            var shopperRef = urlParams.get('shopperRef');

            $.ajax({
                url: "/cards-on-file/delete-card",
                type: "POST",
                data: {
                    reference: reference,
                    shopperRef: shopperRef
                },
            }).then( () => window.location.reload());
        });

        $(document).ready(function () {
            $('#required_fast_checkout_fields').select2({
                theme: 'bootstrap-5',
                multiple: true,
            });

            $("#confirmFastCheckoutOrder").click(function() {
                console.log("submit ideal fast checkout order..")
                const form = document.getElementById('order');
                form.method = 'POST';
                form.action = '/webshop/idealFastCheckout';
                form.submit();
            });
        });

    </script>
    </div>
    </body>
    </html>
</#macro>