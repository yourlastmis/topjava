<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cr"%>

<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${meal.id == null ? 'Add ' : 'Edit '}meal page</title>
</head>
<body>


<h3>
    <a href="index.html">Home</a>
</h3>
<hr>
<h2>${meal.id == null ? 'Add meal' : 'Edit meal'}</h2>

<form method="POST" action='meals' name="frmAddMeal">
    <input type="hidden" name="id" value="<cr:out value="${meal.id}" />" />
    <table>
        <tr>
            <td><label>Enter DateTime:</label></td>
            <td>
               <input type="datetime-local" name="dateTime" value="<cr:out value="${meal.dateTime}" />" />
            </td>
        </tr>
        <tr>
            <td><label>Enter Description:</label></td>
            <td>
                <input type="text" name="description" value="<cr:out value="${meal.description}" />" />
            </td>
        </tr>
        <tr>
            <td><label>Enter Calories:</label></td>
            <td>
                <input type="text" name="calories" value="<cr:out value="${meal.calories}" />" />
            </td>
        </tr>
    </table>
    <input type="submit" value="Save">
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>

</body>
</html>