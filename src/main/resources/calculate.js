function calculateAverage(){
    var xhttp = new XMLHttpRequest();
    // this part of code only executed after xhttp.send(),if backend give back 200, we process the data returned
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var xml = xhttp.response;
            // var result= xml.getElementById("Double").value;
            console.log(xml);
            $('#result').append(xml);

        }
    };
    //give XMLHTTPREQUEST parameter
    var symbol = document.getElementById("symbol").value;
    var days = document.getElementById("days").value;
    var date = document.getElementById("date").value;
    xhttp.open("GET", "http://localhost:8888/stock/calculateAverage/" + symbol+"?days="+days+"&date="+date, true);
    xhttp.send();
}