$(document).ready(function () {

	/* Documente - Table sort */
	function sortTable(column, type) {

	  //Get and set order
	  //Use -data to store wheater it will be sorted ascending or descending
	  var order = $('.table thead tr>th:eq(' + column + ')').data('order');
	  order = order === 'ASC' ? 'DESC' : 'ASC';
	  $('.table thead tr>th:eq(' + column + ')').data('order', order);

	  //Sort the table
	  $('.table tbody tr').sort(function(a, b) {
	  //                                 ^  ^
	  //                                 |  | 
	  //        The 2 parameters needed to be compared. 
	  //        Since you are sorting rows, a and b are <tr>                                 

	    //Find the <td> using the column number and get the text value.
	    //Now, the a and b are the text of the <td>
	    a = $(a).find('td:eq(' + column + ') span').text();
	    b = $(b).find('td:eq(' + column + ') span').text();

	    switch (type) {
	      case 'text':
	        //Proper way to compare text in js is using localeCompare
	        //If order is ascending you can - a.localeCompare(b)
	        //If order is descending you can - b.localeCompare(a);
	        return order === 'ASC' ? a.localeCompare(b) : b.localeCompare(a);
	        break;
	      case 'number':
	        //You can use deduct to compare if number.
	        //If order is ascending you can -> a - b. 
	        //Which means if a is bigger. It will return a positive number. b will be positioned first
	        //If b is bigger, it will return a negative number. a will be positioned first
	        return order === 'ASC' ? a - b : b - a;
	        break;
	      case 'date':
	        var dateFormat = function(dt) {
	          [d, m, y] = dt.split('.');
	          return [y, m - 1, d];
	        }

	        //convert the date string to an object using `new Date`
	        a = new Date(...dateFormat(a));
	        b = new Date(...dateFormat(b));

	        //You can use getTime() to convert the date object into numbers. 
	        //getTime() method returns the number of milliseconds between midnight of January 1, 1970
	        //So since a and b are numbers now, you can use the same process if the type is number. Just deduct the values.
	        return order === 'ASC' ? a.getTime() - b.getTime() : b.getTime() - a.getTime();
	        break;
	    }

	  }).appendTo('.table tbody');
	}

	$('#sort_doc').click(function() {
	  sortTable(0, 'text');
	  $(this).toggleClass('up_arrow down_arrow');
	});
	$('#sort_name').click(function() {
	  sortTable(1, 'text');
	  $(this).toggleClass('up_arrow down_arrow');
	});
	$('#sort_date').click(function() {
	  sortTable(2, 'date');
	  $(this).toggleClass('up_arrow down_arrow');
	});
	$('#sort_number').click(function() {
	  sortTable(3, 'number');
	  $(this).toggleClass('up_arrow down_arrow');
	});

});