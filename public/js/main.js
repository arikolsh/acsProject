$(function () {

    fetchContacts();

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

    // Get the form.
    var delPhoneForm = $('#del-phone-form');
    // Set up an event listener for the contact form.
    $(delPhoneForm).submit(function (event) {
        var url = 'http://localhost:8080/remove/sms/'.concat($('#delphone').val());
        delContactEvent(event, url, $('#form-messages-delphone'));
    });

    // Get the form.
    var delEmailForm = $('#del-email-form');
    // Set up an event listener for the contact form.
    $(delEmailForm).submit(function (event) {
        var url = 'http://localhost:8080/remove/email/'.concat($('#delemail').val());
        delContactEvent(event, url, $('#form-messages-delemail'));
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
        fetchContacts();
    }).fail(function (data) {
        $(resultElem).css("color", "#b94a48");
        // Set the message text.
        $(resultElem).text('Oops! An error occured');

    });
}

function delContactEvent(event, url, resultElem) {
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
        fetchContacts();
    }).fail(function (data) {
        $(resultElem).css("color", "#b94a48");
        // Set the message text.
        $(resultElem).text('Oops! An error occured.');

    });
}

function fetchPhones() {
    var phonesContainer = $("aside > #phone-contacts-container");
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/contacts/sms",
    }).done(function (response) {
        // Set the message text.
        console.log(response);
        empty(phonesContainer);
        phonesContainer.html("<p>" + response + "</p>");
    }).fail(function (data) {
        empty(phonesContainer);
        phonesContainer.css("color", "black");
        // Set the message text.
        phonesContainer.html('Oops! An error occured. Unable to fetch phone numbers.');
    });
}

function fetchEmails() {
    var emailsContainer = $("aside > #email-contacts-container");
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/contacts/email",
    }).done(function (response) {
        // Set the message text.
        console.log(response);
        empty(emailsContainer);
        emailsContainer.html("<p>" + response + "</p>");
    }).fail(function (data) {
        empty(emailsContainer);
        emailsContainer.css("color", "black");
        // Set the message text.
        emailsContainer.html('Oops! An error occured. Unable to fetch email addresses.');
    });
}

function fetchContacts() {
    fetchPhones();
    fetchEmails();
}

function empty(elem) {
    //empty html
    $(elem).html(function (i, old) {
        return '';
    });
}