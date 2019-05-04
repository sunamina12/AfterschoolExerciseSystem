/**
 * Created by yangitano on 4/10/17.
 */
function chred1(ele,a){
    var color = a;
    var text = document.getElementById(ele.id);
    if(text.value!=""){
        document.getElementById(color).style.backgroundColor = "#00FF00";
    }else{
        document.getElementById(color).style.backgroundColor = "red";
    }
}