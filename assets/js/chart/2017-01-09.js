window.onload = function () {
  renderCitiesPie();
  renderPageViewsChart();
  renderUsersPie();
  renderPlatformsPie();
}

renderCitiesPie = function() {
  var chart = new CanvasJS.Chart("cities-pie", {
    title:{
      text: "2016 - 我所待过的城市"
    },
    animationEnabled: true,
    legend:{
      verticalAlign: "bottom",
      horizontalAlign: "center"
    },
    data: [
    {
      indexLabelFontSize: 13,
      indexLabelFontFamily: "Monospace",
      indexLabelFontColor: "black",
      indexLabelLineColor: "black",
      indexLabelPlacement: "black",
      type: "pie",
      showInLegend: true,
      toolTipContent: "{y} - <strong>#percent%</strong>",
      dataPoints: [
        {  y: 46, legendText:"成都", indexLabel: "成都" },
        {  y: 40, legendText:"武汉", indexLabel: "武汉" },
        {  y: 2, legendText:"合肥&铜陵", indexLabel: "合肥&铜陵" },
        {  y: 2, legendText:"其他", indexLabel: "其他" }
      ]
    }
    ]
  });
  chart.render();
}

renderPageViewsChart = function() {
  var chart = new CanvasJS.Chart("page-views-chart",
    {
      theme: "theme2",
      title:{
        text: "博客页面访问量"
      },
      animationEnabled: true,
      axisX: {
        valueFormatString: "MMM",
        interval:1,
        intervalType: "month"
      },
      axisY:{
        includeZero: false
      },
      data: [{
        type: "line",
        //lineThickness: 3,
        dataPoints: [
        { x: new Date(2016, 00, 1), y: 0 },
        { x: new Date(2016, 01, 1), y: 0 },
        { x: new Date(2016, 02, 1), y: 0 },
        { x: new Date(2016, 03, 1), y: 59 },
        { x: new Date(2016, 04, 1), y: 139 },
        { x: new Date(2016, 05, 1), y: 341 },
        { x: new Date(2016, 06, 1), y: 1974, indexLabel: "highest",markerColor: "red", markerType: "triangle" },
        { x: new Date(2016, 07, 1), y: 1965 },
        { x: new Date(2016, 08, 1), y: 1896 },
        { x: new Date(2016, 09, 1), y: 636 },
        { x: new Date(2016, 10, 1), y: 1476 },
        { x: new Date(2016, 11, 1), y: 773 }
        ]
      }]
    });
  chart.render();
}

renderUsersPie = function() {
  var chart = new CanvasJS.Chart("sources-pie",
	{
		title:{
			text: "访问来源对比"
		},
    animationEnabled: true,
		legend:{
			verticalAlign: "bottom",
			horizontalAlign: "center"
		},
		data: [
		{
			indexLabelFontSize: 13,
			indexLabelFontFamily: "Monospace",
			indexLabelFontColor: "black",
			indexLabelLineColor: "black",
			indexLabelPlacement: "outside",
			type: "pie",
			showInLegend: true,
			toolTipContent: "{y} - <strong>#percent%</strong>",
			dataPoints: [
				{  y: 61, legendText:"Direct", indexLabel: "直接访问" },
				{  y: 31, legendText:"Ref.", indexLabel: "其他网站引流" },
        {  y: 8, legendText:"S.E.", indexLabel: "搜索引擎" }
			]
		}
		]
	});
	chart.render();
}

renderPlatformsPie = function() {
  var chart = new CanvasJS.Chart("platforms-pie",
	{
		title:{
			text: "访问平台占比"
		},
    animationEnabled: true,
		legend:{
			verticalAlign: "bottom",
			horizontalAlign: "center"
		},
		data: [
		{
			indexLabelFontSize: 13,
			indexLabelFontFamily: "Monospace",
			indexLabelFontColor: "black",
			indexLabelLineColor: "black",
			indexLabelPlacement: "outside",
			type: "pie",
			showInLegend: true,
			toolTipContent: "{y} - <strong>#percent%</strong>",
			dataPoints: [
				{  y: 89.7, legendText:"Desktop", indexLabel: "桌面" },
				{  y: 10, legendText:"Mobile", indexLabel: "手机" },
        {  y: 0.3, legendText:"Tablet", indexLabel: "平板" }
			]
		}
		]
	});
	chart.render();
}
