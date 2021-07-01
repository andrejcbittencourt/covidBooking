$("#jsGrid").jsGrid({
    height: 'auto',
    width: "100%",

    sorting: true,
    paging: true,
    pageSize: 8,
    pageButtonCount: 5,

    data: users,

    fields: [
        { name: "username", title: 'Username',type: "text"},
        { name: "firstName", title: 'First Name',type: "text"},
        { name: "lastName", title: 'Last Name', type: "text"},
        { type: "control", editButton: false, deleteButton: false,
            itemTemplate: function(value, item) {
                let result = jsGrid.fields.control.prototype.itemTemplate.apply(this, arguments);

                let customButton = $("<button>")
                    .attr({class: "btn btn-outline-secondary"})
                    .html('<i class="fas fa-eye"> Bookings</i>')
                    .click(function(e) {
                        console.log(item);
                        window.location.href = "/bookings/"+item.userID;
                        e.stopPropagation();
                    });

                return result.add(customButton);
            }
        }
    ]
});