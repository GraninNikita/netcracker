;

var oldName;
var oldPlace;
var oldSummary;

$("table tbody tr").on("dblclick", function(){
	var name = $(this).find('td:nth-child(1)').text().trim();
	var startTime = $(this).find('td:nth-child(2)').text();
	startTime = startTime[8]+startTime[9]+"." + startTime[5]+startTime[6]+"." + startTime[0]+startTime[1]+startTime[2]+startTime[3]+" "+startTime[11]+startTime[12]+":"+startTime[14]+startTime[15];
	var endTime = $(this).find('td:nth-child(3)').text();
	endTime = endTime[8]+endTime[9]+"." + endTime[5]+endTime[6]+"." + endTime[0]+endTime[1]+endTime[2]+endTime[3]+" "+endTime[11]+endTime[12]+":"+endTime[14]+endTime[15];
	var place = $(this).find('td:nth-child(4)').text();
	var summary = $(this).find('td:nth-child(5)').text();
	oldName = name;
	oldPlace = place;
	oldSummary = summary;
	var modal = $("#edit-event-modal");
	document.getElementById('editNameEvent').value = name;
	document.getElementById('editStartTime').value = startTime;
	document.getElementById('editEndTime').value = endTime;
	document.getElementById('editPlace').value = place;
	document.getElementById('editSummary').value = summary;
	$("#edit-event-modal").modal('show');
})

$("#btn-editEvent").on("click", function(){
	var data = {};
	data["editNameEvent"] = $('#editNameEvent').val();
	data["editStartTime"] = $('#editStartTime').val();
	data["editEndTime"] = $('#editEndTime').val();
	data["editPlace"] = $('#editPlace').val();
	data["editSummary"] = $('#editSummary').val();
	data["editNotificationTime"] = $('#editNotificationTime').val();
	data["user"] = '${user}';
	data["oldName"] = oldName;
	data["oldPlace"] = oldPlace;
	data["oldSummary"] = oldSummary;
	$.ajax({
		type: "POST",
        url: "/Web/update",
        dataType: 'json',
        data: data,
        timeout: 600000,
		success : function (data) {
			window.location.reload();
		},
		error: function(e){
			window.location.reload();
		}
	});
})