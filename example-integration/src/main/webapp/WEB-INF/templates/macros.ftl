<#macro address address_type>
    <#include 'address.ftl' />
</#macro>

<#macro submitButton value id>
    <div class="d-grid">
        <button class="btn btn-primary" type="submit" id="${id}">${value}</button>
    </div>
</#macro>

<#macro inputField labelFor labelText name>
    <div class="mb-3 row">
        <label for="${labelFor}" class="col-sm-4 col-form-label">${labelText}</label>
        <div class="col-sm-8">
            <input type="text" class="form-control" name="${name}" id="${labelFor}">
        </div>
    </div>
</#macro>