<#import "macros.ftl" as macros />

<@macros.header />
<#assign activeTab = 'cards-on-file'>
<#assign columnWidth = '200px'>
<#assign columnForReferenceIdWidth = '500px'>
<#assign columnForBrand = '200px'>
<#assign columnForLastDigits = '200px'>

<div class="mt-4">
    <form action="" method="get">
        <div class="d-flex flex-column gap-4">
            <div class="d-flex align-items-center">
                <div class="p-2" style="width: ${columnWidth}">
                    <label for="shopperRef">Shopper-Ref:</label>
                </div>
                <div class="p-2" style="width: calc(100% - ${columnWidth})">
                    <input id="card_on_file_shopper_ref" name="shopperRef" class="form-control"/>
                </div>
                <div class="p-2" style="width: calc(100% - ${columnWidth})">
                    <input type="submit" id="getCardsOnFile" value="Find" class="btn btn-primary">
                </div>
            </div>
        </div>
    </form>

    <#if cardsOnFile?? && (cardsOnFile?size > 0)>
        <form id="savedCards" action="" method="get">
            <div class="mt-4 mb-4">
                <h6>Saved cards:</h6>
                <table class="d-flex flex-column">
                    <#list cardsOnFile as entry>
                        <tr class="d-flex flex-row mt-2 order" data-card-id="${entry.id}">
                            <td style="width: ${columnForReferenceIdWidth}">${entry.id}</td>
                            <td style="width: ${columnForBrand}">${entry.brand}</td>
                            <td style="width: ${columnForLastDigits}">${entry.last4Digits}</td>
                            <td style="flex-grow: 1">${entry.status}</td>
                            <td style="width: 100px">
                                <span
                                        data-card-button="${entry.id}"
                                        class="btn btn-primary"
                                        style="display: <#if entry.status == 'DELETED'>none<#else>inline-block</#if>;"
                                        id="${entry.id}"
                                        role="button">Delete
                                </span>
                            </td>
                        </tr>
                    </#list>
                </table>
            </div>
        </form>
    </#if>
</div>

<@macros.footer />