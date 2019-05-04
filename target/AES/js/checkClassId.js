function checkClassId() {
        var stuname = document.getElementById("classid").value;
        if (stuname=="") {
            document.getElementById("errorClassid").style.display = "block";
            return false;
        } else {
            document.getElementById("errorClassid").style.display = "none";
            return true;
        }
    }