$(function () {

    /* initialize the calendar
     -----------------------------------------------------------------*/

    let Calendar = FullCalendar.Calendar;
    let calendarEl = document.getElementById('calendar');

    // initialize the external events
    // -----------------------------------------------------------------

    let events = [];
    data.forEach(event => events.push(
        {
            title       : event["title"],
            id          : event["bookingID"],
            description : event["title"],
            start       : new Date(event["date"]+"T"+event["time"])
        }
    ));

    let calendar = new Calendar(calendarEl, {
        headerToolbar: {
            left  : 'prev,next today',
            center: 'title',
            right : 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        themeSystem: 'bootstrap',
        // events
        events: events,
        eventColor: '#378006', // green
        eventTimeFormat: { // like '14:30'
            hour: '2-digit',
            minute: '2-digit',
            hour12: false
        },
        editable  : false,
        eventClick: function(info) {
            $("#ModalDatePicker")
                .removeClass('is-invalid')
                .datetimepicker('date', moment(info.event.start, 'DD/MM/YYYY'));
            $("#ModalTimePicker")
                .removeClass('is-invalid')
                .datetimepicker('date', moment(info.event.start, 'HH:mm'));
            $("#eventModalLabel").html(info.event.title);
            $("#ModalBookingID").val(info.event.id);
            if(userID != null)
                $("#ModalDeleteBtn").attr("href", "/deletebookings/"+userID+"/"+info.event.id)
            else
                $("#ModalDeleteBtn").attr("href", "/deletebookings/"+info.event.id)
            $('#eventModal').modal('toggle');
        }
    });

    calendar.render();

    //Date picker
    $('#DatePicker').datetimepicker({
        format: 'DD/MM/YYYY'
    });
    //Timepicker
    $('#TimePicker').datetimepicker({
        format: 'HH:mm'
    });
    //Date picker
    $('#ModalDatePicker').datetimepicker({
        format: 'DD/MM/YYYY'
    });
    //Timepicker
    $('#ModalTimePicker').datetimepicker({
        format: 'HH:mm'
    });
    //form validation
    $('#bookingForm').validate({
        rules: {
            Date: {
                required: true
            },
            Time: {
                required: true
            }
        },
        messages: {
            Date: {
                required: "Please choose a date"
            },
            Time: {
                required: "Please choose a time"
            }
        },
        errorElement: 'span',
        errorPlacement: function (error, element) {
            error.addClass('invalid-feedback');
            element.closest('.form-group').append(error);
        },
        highlight: function (element, errorClass, validClass) {
            $(element).addClass('is-invalid');
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).removeClass('is-invalid');
        }
    });
    $('#modalForm').validate({
        rules: {
            Date: {
                required: true
            },
            Time: {
                required: true
            }
        },
        messages: {
            Date: {
                required: "Please choose a date"
            },
            Time: {
                required: "Please choose a time"
            }
        },
        errorElement: 'span',
        errorPlacement: function (error, element) {
            error.addClass('invalid-feedback');
            element.closest('.form-group').append(error);
        },
        highlight: function (element, errorClass, validClass) {
            $(element).addClass('is-invalid');
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).removeClass('is-invalid');
        }
    });
});