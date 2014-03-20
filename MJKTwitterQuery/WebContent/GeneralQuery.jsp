<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
function getData(){
    $("#list").html("");
    $.getJSON(
        "servlet/ServletJson",
        {name:"tese",age:23},
        function(json){
            $.each(json,function(i){
                $("#list").append("<li>name:"+json[i].name+"&nbsp; Age:"+json[i].age+"</li>")
                
            })
        }
    )
    }
</script>
</head>
<body>
<form action="servlet" method="post" name="discussions">
<fieldset>
<legend>Tracking Discussions</legend>
<span id=required>*</span>Keywords or Hashtags:<br/>
<input type=text name=keywords size=32 maxlength=250>
<p>
<label>Location:</label><br/>
Latitude<br/>
<input type=text name=lattitude size=32 maxlength=80>
<br/>Longitude<br/>
<input type=text name=longitude size=32 maxlength=80>
</p>
<p>
<input type=submit value="Go!">
</p>
</fieldset>
</form>
</body>
</html>