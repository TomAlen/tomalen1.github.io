<%--
  Created by IntelliJ IDEA.
  User: hp
  Date: 2019/3/27
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    //服务器请求的路径
    pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<html>
<head>
    <title>查询所有</title>
    <link href="//cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <script src="//cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <!--标题-->
    <div class="row">
        <div class="col-md-12">
            <h2>员工的增删改查</h2>
        </div>
    </div>

    <!--按钮-->
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary">编辑</button>
            <button class="btn btn-danger">删除</button>
        </div>
    </div>

    <!--表格-->
    <div class="row">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>email</th>
                    <th>部门</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.list}" var="emp">
                <tr>
                    <td>${emp.empId}</td>
                    <td>${emp.empName}</td>
                    <td>${emp.gender}</td>
                    <td>${emp.email}</td>
                    <td>${emp.department.deptName}</td>
                    <td>
                        <button class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                编辑
                        </button>
                        <button class="btn btn-danger btn-sm">
                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                            删除
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!--分页信息-->
    <div class="row">
        <!-分页的文字信息-->
        <div class="col-md-6">
            当前页码为第${page.pageNum}页，总${page.pages}页,总共有${page.total}条记录
        </div>

        <!--分页的码数信息-->
        <div class="col-md-6 col-sm-offset-6">

            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li><a href="${APP_PATH}/emp?pn=1">首页</a></li>
                    <!--从PageInfo的封装的信息取出是否有上一页的信息-->
                    <c:if test="${page.hasPreviousPage}">
                        <li>
                            <!--上一页为当前页码减一 -->
                            <a href="${APP_PATH}/emp?pn=${page.pageNum - 1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>

                    <c:forEach items="${page.navigatepageNums}" var="page_num">
                        <!--判断显示的是否是当前的页码,如果是，就显示class为active为高亮状态-->

                        <c:if test="${page_num == page.pageNum}">
                            <li class="active"><a href="#">${page_num}</a></li>
                        </c:if>

                        <c:if test="${page_num != page.pageNum}">
                            <!--服务器发送请求，带上pageNum，代表转向第几页-->
                            <li><a href="${APP_PATH}/emp?pn=${page_num}">${page_num}</a></li>
                        </c:if>
                        </c:forEach>
                    <!--从PageInfo的封装的信息取出是否有下一页的信息-->
                    <c:if test="${page.hasNextPage}">
                        <li>
                            <a href="${APP_PATH}/emp?pn=${page.pageNum + 1}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <!--page.pages为总页码，代表最后一页-->
                    <li><a href="${APP_PATH}/emp?pn=${page.pages}">尾页</a></li>
                </ul>
            </nav>
        </div>
    </div>
</div>
</body>
</html>
