<form method="post" action="/webshop/items" name="item">
    Order items
    <table>
        <tr>
            <td>
                <label for="product_name">Animal:</label>
                <select name="name" id="product_name">
                    <option value="bunny">bunny</option>
                    <option value="cat">cat</option>
                    <option value="rat">rat</option>
                    <option value="platypus">platypus</option>
                </select>
            </td>
            <td>
                <label for="product_price">Price:</label>
                <select name="price" id="product_price">
                    <option value="100">1.00</option>
                    <option value="200">2.00</option>
                    <option value="300">3.00</option>
                    <option value="400">4.00</option>
                    <option value="500">5.00</option>
                </select>
            </td>
            <td>
                <label for="product_category">Category:</label>
                <select name="category" id="product_category">
                    <option value="DIGITAL">DIGITAL</option>
                    <option value="PHYSICAL">PHYSICAL</option>
                </select>
            </td>
            <td>
                <label for="product_vat_category">Vat category:</label>
                <select name="vat" id="product_vat_category">
                    <option value="HIGH">HIGH</option>
                    <option value="LOW">LOW</option>
                    <option value="ZERO">ZERO</option>
                    <option value="NONE">NONE</option>
                </select>
            </td>
            <td>
                <label for="product_tax_enabled">Tax enabled:</label>
                <input type="checkbox" name="taxEnabled" id="product_tax_enabled" value="true">
            </td>
            <td>
                <label for="product_quantity">Quantity:</label>
                <input name="quantity" type="number" min="1" value="1" id="product_quantity">
            </td>
            <td>
                <input class="btn btn-primary" type="submit" name="submit" value="add items" id="add_to_cart">
            </td>
        </tr>
    </table>
</form>