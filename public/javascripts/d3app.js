$(function() {
	var fixedset = function() {
		return [5, 10, 15, 20, 25];
	}
	var fixedset2 = function() {
		return [5, 10, 13, 19, 21, 
		        25, 22, 18, 15, 13, 
		        11, 12, 15, 20, 18, 
		        17, 16, 18, 23, 25,
		        11, 12, 15, 20, 18];
	}
	var randomset = function() {
		var dataset = [];
		for (var i = 0; i < 25; i++) {
			dataset.push(Math.random() * 30);
		}
		return dataset;
	}
	var randonbars = function() {
		d3.select(".thegraph")
		.selectAll("div")
		.data(dataset)
		.enter()
		.append("div")
		.attr("class", "bar")
		.style("height", function(d) {
			return (d * 5) + "px";
		})
		;		
	};

	var numbers = function() {
		d3.select(".thegraph")
		.selectAll("p")
		.data(dataset)
		.enter()
		.append("p")
		.text(function(d) { return Math.floor(d); })
		.style("color", function(d) {
			if (d < 15) {
				return "red" 
			}
			else {
				return "green";
			}
		})
		;
	};
	
	var svg1 = function() {
		var w = 500;
		var h = 50;
		var svg = d3.select(".thegraph").append("svg")
		.attr("width", w)
		.attr("height", h)
		;
		return svg;
	};
	
	var svg2 = function(dataset) {
		var w = 500;
		var h = 50;
		var svg = d3.select(".thegraph").append("svg")
		.attr("width", w)
		.attr("height", h)
		;
		var circles = svg.selectAll("circle")
			.data(dataset)
			.enter()
			.append("circle");
		circles.attr("cx", function(d, i) {
			return (i * 50) + 25
		})
		.attr("cy", h/2)
		.attr("r", function(d) {
			return d;
		})
		.attr("fill", "yellow")
		.attr("stroke", "orange")
		.attr("stroke-width", function(d) {
			return d/2;
		})
		;
	};
	var svg3 = function(dataset) {
		var w = 500;
		var h = 100;
		var barpadding = 1;
		var svg = d3.select(".thegraph").append("svg")
		.attr("width", w)
		.attr("height", h)
		;
		var rects = svg.selectAll("rect")
		.data(dataset)
		.enter()
		.append("rect")
		.attr("x", function(d, i) { // i is loop counter
			return i * w / dataset.length;
		})
		.attr("y", function(d) { return h - d * 4; })
		.attr("width", w / dataset.length - barpadding)
		.attr("height", function(d) { return d * 4; })
		.attr("fill", function(d) { return "rgb(0, 0, " + d * 10 + ")"; })
		;
		
		var texts = svg.selectAll("text")
		.data(dataset)
		.enter()
		.append("text")
		.text(function(d) { return "" + d; })
		.attr("x", function(d, i) { // i is loop counter
			return i * w / dataset.length + (w / dataset.length - barpadding)/2;
		})
		.attr("y", function(d) { return h - d * 4 + 14; })
		.attr("font-family", "sans-serif")
		.attr("font-size", "11px")
		.attr("fill", "white")
		.attr("text-anchor", "middle")
		;
	};

	var dataset = fixedset2();
//	svg3(dataset);
	
	

})