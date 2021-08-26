function sortStock(){
    var xhttp = new XMLHttpRequest();
    //this part of code only executed after xhttp.send(),if backend give back 200, we process the data returned
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
          console.log(this);
          drawGraph(this,symbol,reference);
      }
    };
    //give XMLHTTPREQUEST parameter
    var symbol = document.getElementById("sortSymbolInput").value;
    var reference = document.getElementById("sortReferenceInput").value;
    var reverse = document.getElementById("sortReverseInput").value;
    xhttp.open("GET", "http://localhost:8888/stock/sort/" + symbol + "?reference=" + reference + "&reverse=" + reverse, true);
    xhttp.send();
}