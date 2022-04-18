<#macro address address_type>
    <#include 'address.ftl' />
</#macro>

<#macro submitButton value id>
    <div class="d-grid">
        <button class="btn btn-primary" type="submit" id="${id}">${value}</button>
    </div>
</#macro>

<#macro inputField labelFor labelText name placeholder="">
    <div class="mb-3 row">
        <label for="${labelFor}" class="col-sm-4 col-form-label">${labelText}</label>
        <div class="col-sm-8">
            <input type="text" class="form-control" name="${name}" id="${labelFor}" placeholder="${placeholder}">
        </div>
    </div>
</#macro>

<#macro inputSelect labelFor labelText name placeholder="">
    <div class="mb-3 row">
        <label for="${labelFor}" class="col-sm-4 col-form-label">${labelText}</label>
        <div class="col-sm-8">
            <select class="form-select" name="${name}" id="${labelFor}">
                <#nested>
            </select>
        </div>
    </div>
</#macro>

<#macro inputSelectWithButton labelFor labelText name btnName btnValue btnFormAction>
    <form>
        <div class="row mb-3">
            <label for="${labelFor}" class="col-sm-4 col-form-label">${labelText}</label>
            <div class="col-sm-5">
                <select class="form-select" name="${name}" id="${labelFor}">
                    <#nested>
                </select>
            </div>
            <div class="col">
                <div class="d-grid">
                    <button class="btn btn-primary" type="submit" name="${btnName}"
                            formaction="${btnFormAction}">${btnValue}</button>
                </div>
            </div>
        </div>
    </form>
</#macro>


<#macro card name>
    <div class="card">
        <div class="card-header">${name}</div>
        <div class="card-body"><#nested></div>
    </div>
</#macro>