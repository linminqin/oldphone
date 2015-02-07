<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>通讯录</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<link href="css/wxly.css" rel="stylesheet" type="text/css" />
		<link href="css/prettyPhoto.css" rel="stylesheet" type="text/css" />
		<link href="css/bootstrap.css" rel="stylesheet">
		<link href="css/theme.css" rel="stylesheet">
		<link href="css/bootstrap-responsive.css" rel="stylesheet">
		<link href="css/docs.css" rel="stylesheet">
		<link href="js/google-code-prettify/prettify.css" rel="stylesheet">
		<!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->    
	</head>
	<body>
	      <!--page_container-->
    <div class="page_container">
    	<div class="breadcrumb">
        	<div class="wrap">
                <div class="container">
                    <a href="index.html">首页</a><span>/</span>用户中心
                </div>
            </div> 
        </div>
    	<!--MAIN CONTENT AREA-->
        <div class="wrap">
            <div class="container inner_content">
                <!-- Tables -->
                <section id="tables">
                  <h3>通讯录列表</h3>
                  <div class="clear"></div>
                  <div style="margin-bottom: 20px;">
                  	<a href="#" class="btn btn-success" ><i class="icon-plus" style="margin-top: -1px;"></i>&nbsp;添加&nbsp;&nbsp;</a>
	              </div>
	              <div class="clear"></div>
                  <table class="table table-bordered table-striped">
                  <thead>
                      <tr>
                        <th>好友姓名</th>
                        <th>联系电话</th>
                        <th>照片</th>
                        <th>编辑</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>Default</td>
                        <td class="muted">None</td>
                        <td>No styles, just columns and rows</td>
                        <td>
                        	<a href="#" class="btn btn-info" ><i class="icon-pencil"></i>&nbsp;编辑&nbsp;&nbsp;</a>&nbsp;&nbsp;
                        	<a href="#" class="btn btn-danger" ><i class="icon-remove"></i>&nbsp;删除&nbsp;&nbsp;</a>
                        	<!-- a href="#"><i class="icon-pencil"></i> 编辑</a-->
                        </td>
                      </tr>
                      <tr>
                        <td>Basic</td>
                        <td>
                          <code>.table</code>
                        </td>
                        <td>Only horizontal lines between rows</td>
                        <td></td>
                      </tr>
                      <tr>
                        <td>Bordered</td>
                        <td>
                          <code>.table-bordered</code>
                        </td>
                        <td>Rounds corners and adds outer border</td>
                        <td></td>
                      </tr>
                      <tr>
                        <td>Zebra-stripe</td>
                        <td>
                          <code>.table-striped</code>
                        </td>
                        <td>Adds light gray background color to odd rows (1, 3, 5, etc)</td>
                        <td></td>
                      </tr>
                      <tr>
                        <td>Condensed</td>
                        <td>
                          <code>.table-condensed</code>
                        </td>
                        <td>Cuts vertical padding in half, from 8px to 4px, within all <code>td</code> and <code>th</code> elements</td>
                        <td></td>
                      </tr>
                    </tbody>
                  </table> 
                </section>
    
            </div>
        </div>
    <!--/MAIN CONTENT AREA-->
    	
    </div>
    <!--//page_container-->
	
		<script src="js/jquery.min.js"></script>
	    <script src="js/google-code-prettify/prettify.js"></script>
	    <script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
	    <script src="js/bootstrap.js"></script>
	    <script src="js/superfish.js"></script>
	    <script type="text/javascript" src="js/jquery.tweet.js"></script>
	    <script type="text/javascript" src="js/jquery.prettyPhoto.js"></script>
	    <script type="text/javascript" src="js/myscript.js"></script>
	    <script src="js/application.js"></script>
	    <script type="text/javascript">
			$(document).ready(function(){	
			});		
		</script>
	</body>
</html>
