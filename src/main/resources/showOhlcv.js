var data,config,myChart,painted=false;
function showOhlcv(){
    var xhttp = new XMLHttpRequest();
    //this part of code only executed after xhttp.send(),if backend give back 200, we process the data returned
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
          drawOhlcv(this,symbol);
      }
    };
    //give XMLHTTPREQUEST parameter
    var symbol = document.getElementById("symbol5").value;
    var ohlcv = document.getElementById("ohlcv").value;
    xhttp.open("GET", "http://localhost:8888/stock/showOhlcv/" + symbol + "?ohlcv=" + ohlcv, true);
    xhttp.send();
}

function drawOhlcv(xhttp,symbol){
    //format of data returned is <StockResult><result>*all the data*</result></StockResult>,depend on how you implment it in backend
    //see StocksResult.java and getStock method in StockController.java
    //just use getElementsByTagName("result"), it gives you all the data
    var xml = xhttp.responseXML;
    console.log(xml);
    values = xml.getElementsByTagName("item");
    var ohlcv = document.getElementById("ohlcv").value;
    var tmp = [];
    for(i=0;i<values.length;i++){
        //the data we pass into chartjs api need to be in this format:[{x:??,y:??},{x:??,y:??}]
        //in this function x contains string and y contains float.
        tmp.push(
            //whatever data you want to get, just getElementsByTagName("xxxxxx")[0], for exmaple,getElementsByTagName("close")[0]
            {x:i.toString(),
            y:parseFloat(values[i].textContent)});
    }
    //everything below are just using api, variable data and config are just preparing paramters.Make sure you type the syntax right
    data = {
      datasets: [{
          pointRadius: 1,
          label: symbol,
          borderColor: CHART_COLORS.red,
          backgroundColor: CHART_COLORS.red,
          data: tmp
        }]
    };
    
    config = {
      type: 'line',
      data: data,
      options: {
        plugins: {
          title: {
            display: true,
            text: ohlcv + ' value of ' + symbol
          }
        }
      },
    };
    //If you want to draw graph again and again, make sure you clear the canvas before you draw!!!!!!!!!
    //I didn't implement this right now..
    if(painted){
      myChart.destroy();
    }
    myChart = new Chart(
      document.getElementById('myChart'),
      config
    );
    painted = true;
}