package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;

import kong.unirest.json.JSONObject;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonConvertible;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.ItemCategory;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.VatCategory;

/**
 * This class is used to contain the order item information.
 */
public final class OrderItem implements JsonConvertible {
    private final int quantity;
    private final String id;
    private final String name;
    private final String description;
    private final Money amount;
    private final Money tax;
    private final ItemCategory category;
    private final VatCategory vatCategory;

    /**
     * @param quantity    | Must be a valid Integer
     *                    | Must be greater than zero
     * @param name        | Must not be 'null'
     *                    | Maximum length of '50' characters
     * @param description | Should not be `null` or empty
     *                    | Maximum length of `100` characters
     * @param amount      | Must not be 'null'
     *                    | Must have the same Currency as tax
     * @param tax         | Must not be 'null'
     *                    | Must have the same Currency as amount
     * @param category    | Must not be 'null'
     *                    | Must be a valid category
     *
     * @deprecated Use the constructor with {@link OrderItem#OrderItem(Builder)} parameter
     */
    @Deprecated
    public OrderItem(int quantity, String name, String description, Money amount, Money tax, ItemCategory category) {
        this.quantity = quantity;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.tax = tax;
        this.category = category;
        this.id = null;
        this.vatCategory = null;
    }

    public OrderItem(Builder builder) {
        this.id = builder.id;
        this.quantity = builder.quantity;
        this.name = builder.name;
        this.description = builder.description;
        this.amount = builder.amount;
        this.tax = builder.tax;
        this.category = builder.category;
        this.vatCategory = builder.vatCategory;
    }

    public String getId() {
        return id;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public Money getTax() {
        return tax;
    }

    public Money getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public VatCategory getVatCategory() {
        return vatCategory;
    }

    @Override
    public JSONObject asJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("quantity", quantity);
        jsonObject.put("name", name);
        jsonObject.put("description", description);
        jsonObject.put("amount", amount.asJson());
        if (tax != null) {
            jsonObject.put("tax", tax.asJson());
        }

        jsonObject.put("category", category.name());
        jsonObject.put("vatCategory", getVatCategoryNullSafe(vatCategory));
        return jsonObject;
    }

    private String getVatCategoryNullSafe(VatCategory vatCategory) {
        if (vatCategory != null) {
            return vatCategory.getValue();
        }
        return null;
    }

    public static class Builder {
        private int quantity;
        private String id;
        private String name;
        private Money amount;
        private Money tax;
        private ItemCategory category;
        private VatCategory vatCategory;
        private String description;

        /**
         * @param id    | Optional
         *              | Maximum length of '25' characters
         * @return Builder
         */
        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        /**
         *
         * @param quantity  | Must be a valid Integer
         *                  | Must be greater than zero
         * @return Builder
         */
        public Builder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        /**
         * @param name  | Must not be 'null'
         *              | Maximum length of '50' characters
         * @return Builder
         */
        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * @param description   | Should not be `null` or empty
         *                      | Maximum length of `100` characters
         * @return Builder
         */
        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * @param amount    | Must not be 'null'
         *                  | Must have the same Currency as tax
         * @return Builder
         */
        public Builder withAmount(Money amount) {
            this.amount = amount;
            return this;
        }

        /**
         * @param tax   | Optional
         *              | Must have the same Currency as amount
         * @return Builder
         */
        public Builder withTax(Money tax) {
            this.tax = tax;
            return this;
        }

        /**
         * @param category  | Must not be 'null'
         *                  | Must be a valid category
         * @return Builder
         */
        public Builder withItemCategory(ItemCategory category) {
            this.category = category;
            return this;
        }

        /**
         *
         * @param vatCategory   | Optional
         *                      | Must be a valid VatCategory
         * @return Builder
         */
        public Builder withVatCategory(VatCategory vatCategory) {
            this.vatCategory = vatCategory;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
