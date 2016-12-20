<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>

    <%--<meta charset="utf-8">--%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Личный кабинет пользователя ${user} </title>


    <!-- jQuery -->
    <script src="resources/vendor/jquery/jquery.js"></script>
    <script src="resources/vendor/datetimepicker/moment-with-locales.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="resources/vendor/bootstrap/js/bootstrap.min.js"></script>

    <script src="resources/vendor/datetimepicker/bootstrap-datetimepicker.min.js"></script>

        <!-- Bootstrap Core CSS -->
    <link href="resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/vendor/datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/Web/start">Система оповещения ${id}</a>
        </div>
        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-envelope fa-fw"></i> <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-messages"></ul>
                <!-- /.dropdown-messages -->
            </li>
            <!-- /.dropdown -->
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-tasks fa-fw"></i> <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-tasks"></ul>
                <!-- /.dropdown-tasks -->
            </li>
            <!-- /.dropdown -->
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-bell fa-fw"></i> <i class="fa fa-caret-down"></i>
                </a>

                <!-- /.dropdown-alerts -->
            </li>
            <!-- /.dropdown -->
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li><a href="/Web/profile/${user}"><i class="fa fa-user fa-fw"></i>Профиль пользователя</a>
                    </li>
                    <li><a href="#"><i class="fa fa-gear fa-fw"></i> Настройки</a>
                    </li>
                    <li class="divider"></li>
                    <li><a href="/Web/logout"><i class="fa fa-sign-out fa-fw"></i> Выход</a>
                    </li>
                </ul>
                <!-- /.dropdown-user -->
            </li>
            <!-- /.dropdown -->
        </ul>
        <!-- /.navbar-top-links -->

        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="sidebar-search">
                        <div class="input-group custom-search-form">
                            <input type="text" class="form-control" placeholder="Search...">
                            <span class="input-group-btn">
                                    <button class="btn btn-default" type="button">
                                        <i class="fa fa-search"></i>
                                    </button>
                                </span>
                        </div>
                        <!-- /input-group -->
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Мои события<span
                                class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="start"> Активные</a>
                            </li>
                            <li>
                                <a href="inactive"> Выполненные </a>
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-table fa-fw"></i> Календарь</a>
                    </li>

                </ul>
            </div>
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->
    </nav>

    <!-- Page Content -->
    <div id="page-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Личный кабинет </h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="panel-body">
                <!-- Button trigger modal -->
                <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
                    Добавить событие
                </button>

                <div class="panel-body">

                    <%--Таблица с событиями--%>
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    Таблица Событий <p>${msg}</p>
                                </div>
                                <!-- /.panel-heading -->
                                <div class="panel-body">
                                    <table width="100%" class="table table-striped table-bordered table-hover"
                                           id="dataTables-meetings">
                                        <thead>
                                        <tr>
                                            <%--<th>Meeting id</th>--%>
                                            <th>Название</th>
                                            <th>Дата начала</th>
                                            <th>Дата завершения</th>
                                            <%--<th>Admin id</th>--%>
                                            <th>Место</th>
                                            <th>Доп. информация</th>
                                            <%--<th>State</th>--%>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <%--<c:if test="${not empty meetingsList}">--%>
                                        <c:forEach items="${meetingsList}" var="item">
                                            <tr>
                                                <td><a href="/Web/event/${item.getName()}_${item.getMeetingId()}">${item.getName()}</a></td>
                                                <td>${item.getDateStart()}</td>
                                                <td>${item.getDateEnd()}</td>
                                                <%--<td>${item.getAdminId()}</td>--%>
                                                <td>${item.getPlace()}</td>
                                                <td>${item.getSummary()}</td>
                                                <%--<td>${item.getState()}</td>--%>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <%--</c:if>--%>
                                    </table>
                                    <!-- /.table-responsive -->
                                </div>
                                <!-- /.panel-body -->
                            </div>
                            <!-- /.panel -->
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>

                </div>
                <!-- Modal -->
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content" style="width: 350px;">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-hidden="true">&times;</button>
                                <h4 class="modal-title" id="myModalLabel">Добавить событие</h4>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label>Название:</label>
                                    <input type="text" name="event" id="nameEvent" style="width: 320px;">
                                </div>
                                <form name="start" style="margin-bottom: 10px;">
                                    <p>Время начала:</p>
                                    <!-- <input type="datetime-local" name="startTime" id="startTime" style="width: 320px;"> -->
                                    <div class="container">
                                        <div class="row">
                                            <div class='col-sm-6' style="padding-left: 0px !important;">
                                                <div class="form-group" style="width: 320px;">
                                                    <div class='input-group date' id='datetimepickerStart'>
                                                        <input id="startTime" type='text' class="form-control" />
                                                        <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-calendar"></span>
                                                    </span>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </form>


                                <form name="end" style="margin-bottom: 10px;">
                                    <p>Время окончания:</p>
                                    <!-- <input type="datetime-local" name="endTime" id="endTime" style="width: 320px;"> -->
                                    <div class="container">
                                        <div class="row">
                                            <div class='col-sm-6' style="padding-left: 0px !important;">
                                                <div class="form-group" style="width: 320px;">
                                                    <div class='input-group date' id='datetimepickerEnd'>
                                                        <input id="endTime" type='text' class="form-control"/>
                                                        <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-calendar"></span>
                                                    </span>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>

                                </form>
                                <div class="form-group" style="margin-bottom: 10px;">
                                    <label>Описание: </label>
                                    <input type="text" name="summary" id="summary" style="width: 320px;">
                                </div>
                                <div class="form-group" style="margin-bottom: 10px;">
                                    <label>Место: </label>
                                    <input type="text" name="place" id="place" style="width: 320px;">
                                </div>
                                <div class="form-group" style="margin-bottom: 10px;">
                                    <label>Оповестить за (мин): </label>
                                    <input type="text" value="30" name="notificationTime" id="notificationTime" style="width: 320px;">
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                                <button type="button" class="btn btn-primary" id="btn-addevent">Сохранить</button>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- /.modal -->

                <!-- Modal -->
                <div class="modal fade" id="edit-event-modal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content" style="width: 350px;">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-hidden="true">&times;</button>
                                <h4 class="modal-title" id="editModalLabel">Добавить событие</h4>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label>Название:</label>
                                    <input type="text" name="event" id="editNameEvent" style="width: 320px;">
                                </div>
                                <form name="start" style="margin-bottom: 10px;">
                                    <p>Время начала:</p>
                                    <!-- <input type="datetime-local" name="startTime" id="startTime" style="width: 320px;"> -->
                                    <div class="container">
                                        <div class="row">
                                            <div class='col-sm-6' style="padding-left: 0px !important;">
                                                <div class="form-group" style="width: 320px;">
                                                    <div class='input-group date' id='editDatetimepickerStart'>
                                                        <input id="editStartTime" type='text' class="form-control" />
                                                        <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-calendar"></span>
                                                    </span>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </form>


                                <form name="end" style="margin-bottom: 10px;">
                                    <p>Время окончания:</p>
                                    <!-- <input type="datetime-local" name="endTime" id="endTime" style="width: 320px;"> -->
                                    <div class="container">
                                        <div class="row">
                                            <div class='col-sm-6' style="padding-left: 0px !important;">
                                                <div class="form-group" style="width: 320px;">
                                                    <div class="input-group date" id="editDatetimepickerEnd">
                                                        <input id="editEndTime" type='text' class="form-control"/>
                                                        <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-calendar"></span>
                                                    </span>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>

                                </form>
                                <div class="form-group" style="margin-bottom: 10px;">
                                    <label>Описание: </label>
                                    <input type="text" name="summary" id="editSummary" style="width: 320px;">
                                </div>
                                <div class="form-group" style="margin-bottom: 10px;">
                                    <label>Место: </label>
                                    <input type="text" name="place" id="editPlace" style="width: 320px;">
                                </div>
                                <div class="form-group" style="margin-bottom: 10px;">
                                    <label>Оповестить за (мин): </label>
                                    <input type="text" value="30" name="notificationTime" id="editNotificationTime" style="width: 320px;">
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                                <button type="button" class="btn btn-primary" id="btn-editEvent">Сохранить</button>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- /.modal -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /.container-fluid -->
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<script src="resources/vendor/jquery/editEvent.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="resources/vendor/metisMenu/metisMenu.min.js"></script>
<!-- DataTables JavaScript -->
<script src="resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script src="resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
<script src="resources/vendor/datatables-responsive/dataTables.responsive.js"></script>

<!-- Custom Theme JavaScript -->
<script src="resources/dist/js/sb-admin-2.js"></script>
<%--Ajax--%>
<script type="text/javascript">
    $(function () {
        $('#datetimepickerStart').datetimepicker({
            language: 'ru'
        });
    });
</script>
<script type="text/javascript">
    $(function () {
        $('#datetimepickerEnd').datetimepicker({
            language: 'ru'
        });
    });
</script>
<script type="text/javascript">
    $(function () {
        $('#editDatetimepickerStart').datetimepicker({
            language: 'ru'
        });
    });
</script>
<script type="text/javascript">
    $(function () {
        $('#editDatetimepickerEnd').datetimepicker({
            language: 'ru'
        });
    });
</script>
<script>
    jQuery(document).ready(
            function ($) {
                $("#btn-addevent").click(function (event) {
                    var data = {};
                    data["nameEvent"] = $('#nameEvent').val();
                    data["startTime"] = $('#startTime').val();
                    data["endTime"] = $('#endTime').val();
                    data["summary"] = $('#summary').val();
                    data["place"] = $('#place').val();
                    data["notificationTime"] = $('#notificationTime').val();
                    data["user"] = '${user}'
                    $.ajax({
                        type: "POST",
                        url: "/Web/save",
                        dataType: 'json',
                        data: data,
                        timeout: 600000,
                        success: function (data) {
                            window.location.reload();
                        },
                        error: function (e) {
                            window.location.reload();
                            $('#nameEvent').val('')
                            $('#startTime').val('')
                            $('#endTime').val('')
                            $('#summary').val('')
                            $('#place').val('')
                            $('#notificationTime').val('');
                        }
                    });
                });
            });
</script>
<script>
    $(document).ready(function () {
        $('#dataTables-meetings').DataTable({
            responsive: true
        });
        $('#dataTables-users').DataTable({
            responsive: true
        });
    });
</script>
</body>

</html>