<#include "./common/header.ftl">
<#if container??>
<div ="${container}">
<#else>
<div ="default">
</#if>

<p>List of users:
<#assign users = [{"name":"Joe", "hidden":false},
{"name":"James Bond", "hidden":true},
{"name":"Julia", "hidden":false}]>
<ul>
<#list users as user>
    <#if !user.hidden>
    <li>${user.name}
    </#if>
</#list>
</ul>
qqq${message}
<#assign x = "something">
${indexOf("met", x)}
${indexOf("foo", x)}

<p>That's all.

<#include "./common/footer.ftl">