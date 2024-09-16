<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
</head>
<body>

<p>検索条件を入力してください</p>
  <form action="DBConnection_JavaEE01" method="post">
  ${error1}  id: <input type="text" name="id"><br>
    name: <input type="text" name="name"><br>
  ${error2}  price: <input type="text" name="price"><br>
    <button type="submit">検索</button>
  </form>
<p>検索結果</p>
  <table border="1">
    <tr>
      <th>id</th>
      <th>name</th>
      <th>price</th>
    </tr>
    <c:forEach var="product" items="${product}">
      <tr>
        <td>${fn:escapeXml(product.id)}</td>
        <td>${fn:escapeXml(product.name)}</td>
        <td>${fn:escapeXml(product.price)}</td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>