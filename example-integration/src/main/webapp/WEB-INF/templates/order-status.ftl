<#import "macros.ftl" as macros />s

<@macros.header />
<#assign activeTab = 'order-status'>
<#assign columnWidth = '200px'>
<#assign columnForOrderIdWidth = '600px'>
<#assign columnForAmountWidth = '100px'>
<#assign columnForTimestampWidth = '400px'>

<div class="mt-4">
    <form action="" method="get">
        <div class="d-flex flex-column gap-4">
            <div class="d-flex align-items-center">
                <label for="orderId" style="width: ${columnWidth}">Order Id:</label>
                <input id="orderId" name="orderId" class="form-control" style="width: calc(100% - ${columnWidth})"/>
            </div>
            <div>
                <input type="submit" id="getOrderStatus" value="Get order status" class="btn btn-primary">
            </div>
        </div>
    </form>

    <#if webshopOrders?? && (webshopOrders?size > 0)>
        <form id="mostRecentOrders" action="" method="get">
            <div class="mt-4 mb-4">
                <h6>Most recent orders created with the simple webshop:</h6>
                <div class="d-flex flex-column">
                    <div class="d-flex flex-row">
                        <span style="width: ${columnForOrderIdWidth}">Order Id</span>
                        <span style="width: ${columnForAmountWidth}">Amount</span>
                        <span style="width: ${columnForTimestampWidth}">Timestamp</span>
                    </div>
                    <#list webshopOrders as entry>
                        <div class="d-flex flex-row mt-2 order" data-order-id="${entry.value.omnikassaOrderId}" role="button">
                            <span style="width: ${columnForOrderIdWidth}">${entry.value.omnikassaOrderId}</span>
                            <span style="width: ${columnForAmountWidth}">${entry.value.amount} EUR</span>
                            <span style="width: ${columnForTimestampWidth}">${entry.value.formattedTimestamp}</span>
                        </div>
                    </#list>
                </div>
            </div>
        </form>
    </#if>

    <json-viewer expandAll id="json">
        ${orderStatus}
    </json-viewer>
</div>

<@macros.footer />