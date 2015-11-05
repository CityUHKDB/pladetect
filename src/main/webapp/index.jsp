<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h2>File Upload</h2>

<p>Please choose your file to upload</p>

<form action="/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file" size="50"/>
    <br/>
    <input type="submit" value="upload file"/>
</form>
</body>
</html>
