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

    <title>Календарь </title>

    <!-- Bootstrap Core CSS -->
    <link href="resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <link href="resources/dist/css/select2.min.css" rel="stylesheet" />

    <!-- Custom Fonts -->
    <link href="resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <!-- jQuery -->
        <script src="resources/vendor/jquery/jquery.min.js"></script>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

        <style>

            body {
                margin: 0;
                padding: 0;
                font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
                font-size: 14px;
            }

            #calendar {
                max-width: 900px;
                margin: 50px auto;
            }

        </style>

        <script>

            jQuery(function() { // document ready

                jQuery('#calendar').fullCalendar({
                    now: Date.now(),
                    editable: true, // enable draggable events
                    aspectRatio: 1.8,
                    scrollTime: '00:00', // undo default 6am scrollTime
                    header: {
                        left: 'Сегодня prev,next',
                        center: 'title',
                        right: 'timelineDay,timelineThreeDays,agendaWeek,month,listWeek'
                    },
                    defaultView: 'timelineDay',
                    views: {
                        timelineThreeDays: {
                            type: 'timeline',
                            duration: { days: 3 }
                        }
                    },
                    resourceLabelText: 'Rooms',
                    resources: [
                        { id: 'a', title: 'Auditorium A' },
                        { id: 'b', title: 'Auditorium B', eventColor: 'green' },
                        { id: 'c', title: 'Auditorium C', eventColor: 'orange' }

                    ],
                    events: '${meetings}'

//                    events: [
//                        { id: '1', resourceId: 'b', start: '2016-12-07T02:00:00', end: '2016-12-07T07:00:00', title: 'event 1' },
//                        { id: '2', resourceId: 'c', start: '2016-12-07T05:00:00', end: '2016-12-07T22:00:00', title: 'event 2' },
//                        { id: '3', resourceId: 'd', start: '2016-12-06', end: '2016-12-08', title: 'event 3' },
//                        { id: '4', resourceId: 'e', start: '2016-12-07T03:00:00', end: '2016-12-07T08:00:00', title: 'event 4' },
//                        { id: '5', resourceId: 'f', start: '2016-12-07T00:30:00', end: '2016-12-07T02:30:00', title: 'event 5' }
//                    ]
                });

            });

        </script>

        <link href='resources/lib/fullcalendar.min.css' rel='stylesheet' />
        <link href='resources/lib/fullcalendar.print.min.css' rel='stylesheet' media='print' />
        <link href='resources/css/scheduler.min.css' rel='stylesheet' />
        <script src='resources/lib/moment.min.js'></script>
        <script src='resources/lib/jquery.min.js'></script>
        <script src='resources/lib/fullcalendar.min.js'></script>
        <script src='resources/js/scheduler.min.js'></script>
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
            <a class="navbar-brand" href="/Web/start">Система оповещения</a>
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
                    <li><a href="/Web/profile/${user}"><i class="fa fa-user fa-fw"></i> Личный кабинет</a>
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
                <div id='calendar'></div>
                <!-- /.col-lg-12 -->
            </div>

            <!-- /.row -->
        </div>
        <!-- /.container-fluid -->
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->



<!-- Bootstrap Core JavaScript -->
<script src="../../resources/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="resources/vendor/metisMenu/metisMenu.min.js"></script>
<!-- DataTables JavaScript -->
<script src="resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script src="resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
<script src="resources/vendor/datatables-responsive/dataTables.responsive.js"></script>

<!-- Custom Theme JavaScript -->
<script src="resources/dist/js/sb-admin-2.js"></script>
<script src="resources/dist/js/select2.min.js"></script>

<%--Ajax--%>
<script type="text/javascript">
    $(".js-example-basic-multiple").select2();
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