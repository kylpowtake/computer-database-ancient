function checkDate() {
	var test = true;
	if ($("#introduced").value != '' && $("#discontinued").value != '') {
		var introduced = new Date($("#introduced").value);
		var discontinued = new Date($("#discontinued").value);
		if (introduced < discontinued) {
			test = false;
			alert("Discontinued date must be greater than introduced");
		}
	}
	return test;
}