window.onload = function () {
  renderCitiesPie();
}

renderCitiesPie = function() {
  var chart = new CanvasJS.Chart("cities-pie", {
  		title:{
  			text: "2016 - 我所待过的城市",
  			fontFamily: "arial black"
  		},
      animationEnabled: true,
  		legend: {
  			verticalAlign: "bottom",
  			horizontalAlign: "center"
  		},
  		theme: "theme1",
  		data: [{
  			type: "pie",
  			indexLabelFontFamily: "Garamond",
  			indexLabelFontSize: 20,
  			indexLabelFontWeight: "bold",
  			startAngle:0,
  			indexLabelFontColor: "MistyRose",
  			indexLabelLineColor: "darkgrey",
  			indexLabelPlacement: "inside",
  			toolTipContent: "{name}",
  			showInLegend: true,
  			indexLabel: "#percent%",
  			dataPoints: [
  				{  y: 46, name: "成都", legendMarkerType: "triangle"},
  				{  y: 40, name: "武汉", legendMarkerType: "square"},
  				{  y: 2, name: "合肥&铜陵", legendMarkerType: "circle"},
          {  y: 2, name: "其他", legendMarkerType: "circle"},
  			]
  		}]
  });
  chart.render();
}
