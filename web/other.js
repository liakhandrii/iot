/**
 * Created by andrii on 04.06.15.
 */

$(document).ready(function(){
    var requestAddress = "https://ahome.azure-mobile.net/tables/devices?$filter=(id%20eq%20'" + get("id") + "')";
    $.get( requestAddress, function( data ) {

        JSON.parse(data[0]["actions"]).forEach(function(element){

            var rightColumn = "";

            if (element["params_type"] == undefined && element["toggle"] == undefined){
                rightColumn = "<form method=\"post\" action=\"/doTask\">"+
                "<input type=\"hidden\" name=\"id\" value=\"" + get("id") + "\">"+
                "<input type=\"hidden\" name=\"action\" value=\"" + element["name"] + "\">"+
                "<input type=\"hidden\" name=\"param\" value=\"" + "" + "\">"+
                "<button type=\"submit\" class=\"btn\">DO IT!</button>"+
                "</form>";
            }

            if (element["param"] != undefined){
                var options = "";

                element["param"].forEach(function(param){
                   options += "\n<option>" + param + "</option>";
                });

                rightColumn = "<form method=\"post\" action=\"/doTask\">"+
                "<input type=\"hidden\" name=\"id\" value=\"" + get("id") + "\">"+
                "<select name=\"param\">" + options + "\n</select>" +
                "<input type=\"hidden\" name=\"action\" value=\"" + element["name"] + "\">"+
                "<button type=\"submit\" class=\"btn\">DO IT!</button>"+
                "</form>";
            }

            $(".opttable").append("<tr>"+
            "<td>" + element["label"] + "</td>"+
                "<td class=\"tdoptleft\">"+
                    rightColumn+
                "</td>"+
            "</tr>");
        });

    });

});

function get(name){
    if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
        return decodeURIComponent(name[1]);
}