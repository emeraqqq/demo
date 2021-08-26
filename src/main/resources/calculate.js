function calculateAverage(){
    var xhttp = new XMLHttpRequest();
    // this part of code only executed after xhttp.send(),if backend give back 200, we process the data returned
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var xml = xhttp.response;
            //var result= xml.getElementById("double").value;
            console.log(xml);
            //$('#result').append(xml);
            document.getElementById("result").innerHTML=xml;

        }
    };
    //give XMLHTTPREQUEST parameter
    var symbol = document.getElementById("symbol").value;
    var days = document.getElementById("days").value;
    var date = document.getElementById("date").value;
    xhttp.open("GET", "http://localhost:8888/stock/calculateAverage/" + symbol+"?days="+days+"&date="+date, true);
    xhttp.send();
}
function calculateDailyPrice(){
    var xhttp = new XMLHttpRequest();
    // this part of code only executed after xhttp.send(),if backend give back 200, we process the data returned
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var xml = xhttp.response;
            //var result= xml.getElementById("Double").value;
            console.log(xml);
            //$('#result').append(xml);
            document.getElementById("result1").innerHTML=xml;

        }
    };
    //give XMLHTTPREQUEST parameter
    var symbol1 = document.getElementById("symbol1").value;
    var date1 = document.getElementById("date1").value;
    console.log(symbol1);
    xhttp.open("GET", "http://localhost:8888/stock/calculateDailyPrice/" +symbol1+"?date="+date1,true);
    xhttp.send();
}
function showMax(){
    var xhttp = new XMLHttpRequest();
    // this part of code only executed after xhttp.send(),if backend give back 200, we process the data returned
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var xml = xhttp.response;
            //var result= xml.getElementById("Double").value;
            console.log(xml);
            //$('#result').append(xml);
            document.getElementById("result2-1").innerHTML=xml;

        }
    };
    //give XMLHTTPREQUEST parameter
    var symbol2 = document.getElementById("symbol2").value;
    xhttp.open("GET", "http://localhost:8888/stock/showMaxPrice/" +symbol2,true);
    xhttp.send();
}
function showMin(){
    var xhttp = new XMLHttpRequest();
    // this part of code only executed after xhttp.send(),if backend give back 200, we process the data returned
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var xml = xhttp.response;
            //var result= xml.getElementById("Double").value;
            console.log(xml);
            //$('#result').append(xml);
            document.getElementById("result2-2").innerHTML=xml;

        }
    };
    //give XMLHTTPREQUEST parameter
    var symbol2 = document.getElementById("symbol2").value;
    xhttp.open("GET", "http://localhost:8888/stock/showMinPrice/" +symbol2,true);
    xhttp.send();
}
function calculatestd(){
    var xhttp = new XMLHttpRequest();
    // this part of code only executed after xhttp.send(),if backend give back 200, we process the data returned
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var xml = xhttp.response;
            //var result= xml.getElementById("double").value;
            console.log(xml);
            //$('#result').append(xml);
            document.getElementById("result3").innerHTML=xml;

        }
    };
    //give XMLHTTPREQUEST parameter
    var symbol3 = document.getElementById("symbol3").value;
    var days3 = document.getElementById("days3").value;
    var date3 = document.getElementById("date3").value;
    xhttp.open("GET", "http://localhost:8888/stock/calculateStd/" + symbol3+"?days="+days3+"&date="+date3, true);
    xhttp.send();
}