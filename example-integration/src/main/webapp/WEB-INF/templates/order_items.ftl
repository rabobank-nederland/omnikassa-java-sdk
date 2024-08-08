<form method="post" action="/webshop/items" name="item">
    <p class="h6">Order Items</p>
    <table class="table">
        <tr>
            <td>
                <label for="product_name" class="form-label">Animal:</label>
            </td>
            <td>
                <select name="name" id="product_name" class="form-select">
                    <option value="bunny">bunny</option>
                    <option value="cat">cat</option>
                    <option value="rat">rat</option>
                    <option value="platypus">platypus</option>
                </select>
            </td>
            <td>
                <label for="product_price" class="form-label">Price:</label>
            </td>
            <td>
                <select name="price" id="product_price" class="form-select">
                    <option value="100">1.00</option>
                    <option value="200">2.00</option>
                    <option value="300">3.00</option>
                    <option value="400">4.00</option>
                    <option value="500">5.00</option>
                </select>
            </td>
            <td>
                <label for="product_category" class="form-label">Category:</label>
            </td>
            <td>
                <select name="category" id="product_category" class="form-select">
                    <option value="DIGITAL">DIGITAL</option>
                    <option value="PHYSICAL">PHYSICAL</option>
                </select>
            </td>
            <td>
                <label for="product_vat_category" class="form-label">Vat category:</label>
            </td>
            <td>
                <#include 'vat-category.ftl' />
            </td>
            <td>
                <label for="product_tax_enabled" class="form-label">Tax enabled:</label>
            </td>
            <td>
                <input type="checkbox" name="taxEnabled" value="true" class="form-check-input">
            </td>
            <td>
                <label for="product_quantity" class="form-label">Quantity:</label>
            </td>
            <td style="width:10%">
                <input name="quantity" type="number" min="1" value="1" id="product_quantity" class="form-control">
            </td>
            <td>
                <input type="submit" name="submit" value="add items" id="add_to_cart" class="btn btn-secondary">
            </td>
        </tr>
    </table>
    <#if shoppingCart?? && (shoppingCart.orderItems?size > 0)>
        <p class="h6">Shopping cart:</p>
        <table class="table">
            <tr>
                <td>Price</td>
                <td>Quantity</td>
            </tr>
            <#list shoppingCart.orderItems as item>
                <tr>
                    <td>
                        ${item.amount.amount}
                    </td>
                    <td>
                        ${item.quantity}
                    </td>
                </tr>
            </#list>
            <tr>
                <td colspan="2">
                    <input type="submit" name="clearShoppingCart" value="clear shopping cart" class="btn btn-secondary">
                </td>
            </tr>
        </table>
    <#else>
        <p class="h6">Your shopping cart is empty!</p>
    </#if>
</form>