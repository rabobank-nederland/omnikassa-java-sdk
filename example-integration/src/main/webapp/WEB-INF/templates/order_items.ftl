<#import "macros.ftl" as macros >

<div class="container-fluid mt-3">
    <@macros.card "Order items">
        <form method="post" action="/webshop/items" name="item">
            <div class="row">
                <@macros.inputSelectVertical "product_name" "Animal:" "name">
                    <option value="bunny">bunny</option>
                    <option value="cat">cat</option>
                    <option value="rat">rat</option>
                    <option value="platypus">platypus</option>
                </@macros.inputSelectVertical>

                <@macros.inputSelectVertical "product_price" "Price:" "price">
                    <option value="100">1.00</option>
                    <option value="200">2.00</option>
                    <option value="300">3.00</option>
                    <option value="400">4.00</option>
                    <option value="500">5.00</option>
                </@macros.inputSelectVertical>

                <@macros.inputSelectVertical "product_category" "Category:" "category">
                    <option value="DIGITAL">DIGITAL</option>
                    <option value="PHYSICAL">PHYSICAL</option>
                </@macros.inputSelectVertical>

                <@macros.inputSelectVertical "product_vat_category" "Vat category:" "vat">
                    <option value="HIGH">HIGH</option>
                    <option value="LOW">LOW</option>
                    <option value="ZERO">ZERO</option>
                    <option value="NONE">NONE</option>
                </@macros.inputSelectVertical>

                <@macros.inputCheckboxVertical "product_tax_enables" "Tax enabled:" "taxEnabled"></@macros.inputCheckboxVertical>
                <@macros.inputQuantityVertical "product_quantity" "Quantity:" "quantity"></@macros.inputQuantityVertical>

                <div class="col">
                    <br/>
                    <@macros.submitButton "add items" "add_to_card"></@macros.submitButton>
                </div>
            </div>
        </form>
    </@macros.card>
</div>