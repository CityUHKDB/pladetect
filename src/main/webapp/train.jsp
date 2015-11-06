<%--
  Created by IntelliJ IDEA.
  User: Chow Tak Shing
  Date: 11/7/2015
  Time: 12:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%@include file="header.jsp" %>
<h2>File Upload</h2>

<p>Please choose your file to upload</p>

<form action="upload" method="post" enctype="multipart/form-data">
    Author Name:
    <input type="text" name="authorName"/><br/>
    Author Type:
    <input type="text" name="authorType"/><br/>
    Document Name:
    <input type="text" name="docName"/><br/>
    Document Type:
    <select name="docType">
        <option value="book">Books</option>
        <option value="report">Reports</option>
        <option value="letter">Letters</option>
        <option value="proposal">Proposals</option>
        <option value="email">Email</option>
    </select>
    <br/>
    <input type="file" name="file" size="50"/><br/><br/>
    <input type="submit" value="upload file"/>
</form>
</body>
</html>
