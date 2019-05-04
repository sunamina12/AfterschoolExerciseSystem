function checkStuName() {
    var stuname = document.getElementById("stuname").value;
    if (stuname == "") {
        document.getElementById("errorStuName").style.display = "block";
        return false;
    } else {
        document.getElementById("errorStuName").style.display = "none";
        return true;
    }
}