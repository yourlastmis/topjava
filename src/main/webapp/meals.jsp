<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fns"%>

<html lang="ru">
<style>
    .table{
        border: 1px solid #eee;
        table-layout: fixed;
        width: 100%;
        margin-bottom: 20px;
    }
    .table th {
        font-weight: bold;
        padding: 5px;
        background: #efefef;
        border: 1px solid #dddddd;
    }
    .table td{
        padding: 5px 10px;
        border: 1px solid #eee;
        text-align: left;
    }
    .table tbody tr:nth-child(odd){
        background: #fff;
    }
    .table tbody tr:nth-child(even){
        background: #F7F7F7;
    }
</style>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
    <title>Meals</title>
</head>
<body>
<h3>
    <a href="index.html">Home</a>
</h3>
<hr>
<h2>Meals</h2>
<p><a href="meals?action=insert">Add Meal</a></p>
<table class="table">
    <thead>
    <tr>
        <th>Id</th>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <cr:forEach items="${meals}" var="meal">

        <cr:set var="textColor" value="${meal.excess ? 'color:red;' : 'color:green;' }"/>

        <tr style="${textColor}">
            <td><cr:out value="${meal.id}" /></td>
            <td><cr:out value="${fns:replace(meal.dateTime,'T',' ')}" /></td>
            <td><cr:out value="${meal.description}" /></td>
            <td><cr:out value="${meal.calories}" /></td>

            <td><a href="meals?action=edit&id=<cr:out value="${meal.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&id=<cr:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </cr:forEach>
    </tbody>
</table>
</body>
</html>