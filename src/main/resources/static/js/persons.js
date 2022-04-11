'use strict';

var hostname=window.location.hostname;
var api = 'https://'+hostname+':8082/api/persons';
var personsDataTable;

$(document).ready(function() {

	let columns = [
		{ 'title': 'Name', 'data': 'name' },
		{ 'title': 'Age', 'data': 'age' },
	]

	personsDataTable = $('#personsDataTable').DataTable({
		'order': [[0, 'asc']],
		'columns': columns
	}); 

	$('#personsDataTable tbody').on('click', 'tr', function () {
		let personData = personsDataTable.row(this).data();
		edit(personData.id);
	});

	getAll();
	$('#addBtn').click(create);
	$('#saveBtn').click(insert);
	$('#deleteBtn').click(deleteItem);

	$('#form').on('shown.bs.modal', function () {
		$('.modal-body :input:visible:first').focus();
	})
})

function clearForm() {
	$('input').each(function() {
		$(this).val('');
	});
	$('input:checkbox').each(function() {
		$(this).prop('checked', false);
	});
}

function getAll() {
	$.get(api, function(persons) {
		if (persons) {
			personsDataTable.clear();
			personsDataTable.rows.add(persons);
			personsDataTable.columns.adjust().draw();
		}
	});
}

function create() {
	// Set title
	$('#title').text('New Person');

	$('#saveBtn').off('click');
	$('#saveBtn').click(insert);

	// Hide delete button
	$('#deleteBtn').hide();

	// Fill relationships selects for in the UI

	// Clear form
	clearForm();

	$('#form').modal({backdrop: 'static'}); // backdrop:static => to prevent closing the modal when clicking outside of it
}

function insert(e) {
	e.preventDefault();

	// Create obj
	let obj = {
		name: $('#name').val(), 
		age: $('#age').val(), 
	}

	console.log(obj);

	send(api, obj, 'POST');
}
function edit(id) {

	// Set title
	$('#title').text('Edit Person');

	// Show delete button
	$('#deleteBtn').show();

	// Clear form
	clearForm();

	// Get item
	$.get(api+'/'+id, function(person) {
		if (person){
			// Fill form
			$('#id').val(person.id);
			$('#name').val(person.name);
			$('#age').val(person.age);

			$('#saveBtn').off('click');
			$('#saveBtn').click(update);

			$('#form').modal({backdrop: 'static'}); // backdrop:static => to prevent closing the modal when clicking outside of it
		}
	});
}

function update(e) {
	e.preventDefault();

	let id = +$('#id').val();
	console.log('updating ...'+id);

	// Create obj
	let obj = {
		name: $('#name').val(), 
		age: $('#age').val(), 
	}

	console.log(obj);

	send(api+'/'+id, obj, 'PUT');
}

function deleteItem() {

	let id = +$('#id').val();
	let uri =  `${api}/${id}`;

	// Send data
	$.ajax({
		url: uri,
		type: 'DELETE'
	 }).then(function() {
		 $('#form').modal('toggle');
		getAll();
	});
}

function send(url, obj, method) {
	// Send data
	$.ajax({
		url: url,
		type: method,
		data: JSON.stringify(obj),
		contentType: 'application/json; charset=utf-8'
	}).then(function() {
		$('#form').modal('toggle');
		getAll();
	});
}
