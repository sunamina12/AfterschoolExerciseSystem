/**
 * Created by yangitano on 4/6/17.
 */
function chred(ele,a){
    var color = a;
    var radios = document.getElementsByName(ele.name);
    for(var i = 0; i<radios.length; i++){
        if(radios[i].checked == false){
            document.getElementById(color).style.backgroundColor = "#00FF00";
        }
    }
}