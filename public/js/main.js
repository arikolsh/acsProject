$(function () {
    // Get the form.
    var addPhoneForm = $('#add-phone-form');
    // Set up an event listener for the contact form.
    $(addPhoneForm).submit(function (event) {
        var url = 'http://localhost:8080/add/sms/'.concat($('#phone').val());
        addContactEvent(event, url, $('#form-messages-phone'));
    });

    // Get the form.
    var addEmailForm = $('#add-email-form');
    // Set up an event listener for the contact form.
    $(addEmailForm).submit(function (event) {
        var url = 'http://localhost:8080/add/email/'.concat($('#email').val());
        addContactEvent(event, url, $('#form-messages-email'));
    });

});

function addContactEvent(event, url, resultElem) {
    // Stop the browser from submitting the form.
    event.preventDefault();
    // Submit the form using AJAX.
    console.log('sending GET to ' + url);
    $.ajax({
        type: 'GET',
        url: url,
    }).done(function (response) {
        // Set the message text.
        console.log(response);
        $(resultElem).html(response + " &#10003");
        $(resultElem).css("color", "#468847");
    }).fail(function (data) {
        $(resultElem).css("color", "#b94a48");
        // Set the message text.
        if (data.responseText !== '') {
            $(resultElem).text(data.responseText);
        } else {
            $(resultElem).text('Oops! An error occured and your data could not be sent.');
        }
    });
}