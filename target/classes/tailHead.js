function getHead(){
    var xhttp = new XMLHttpRequest();
    //this part of code only executed after xhttp.send(),if backend give back 200, we process the data returned
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
          console.log(this);
          drawGraph(this,symbol);
      }
    };
    //give XMLHTTPREQUEST parameter
    var symbol = document.getElementById("headSymbolInput").value;
    var amount = document.getElementById("headAmountInput").value;
    xhttp.open("GET", "http://localhost:8888/stock/head/" + symbol + "/" + amount, true);
    xhttp.send();
}

function getTail(){
  var xhttp = new XMLHttpRequest();
  //this part of code only executed after xhttp.send(),if backend give back 200, we process the data returned
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        console.log(this);
        drawGraph(this,symbol);
    }
  };
  //give XMLHTTPREQUEST parameter
  var symbol = document.getElementById("tailSymbolInput").value;
  var amount = document.getElementById("tailAmountInput").value;
  xhttp.open("GET", "http://localhost:8888/stock/tail/" + symbol + "/" + amount, true);
  xhttp.send();
}