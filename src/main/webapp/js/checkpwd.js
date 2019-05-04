function checkPwd() {
    var pwd1 = document.getElementById("pwd1").value;
    var pwd2 = document.getElementById("pwd2").value;
    if (pwd1 != pwd2) {
        document.getElementById("errorpwd").style.display = "block";
        return false;
    } else {
        document.getElementById("errorpwd").style.display = "none";
        return true;
    }
}