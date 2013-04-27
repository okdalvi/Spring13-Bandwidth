<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <script type='application/javascript'>
    // Send the POST when the page is loaded,
    // which will replace this whole page with the retrieved chart.
    function loadGraph() {
      var frm = document.getElementById('post_form');
	  //var bar=document.getElementById('post_form2');
      if (frm) {
       frm.submit();
	   //bar.submit();
      }
    }
  </script>
  </head>
  <body onload="loadGraph()" >

	    <form action='https://chart.googleapis.com/chart' method='POST' id='post_form'>
      <input type='hidden' name='cht' value='bvs'/>
	  
	  <input type='hidden' name='chxt' value='x,y'/>
	  <input type='hidden' name='chxl' value='0:|AT&T|Republic|T-Mobile'/>
	  
      <input type='hidden' name='chtt' value='Failures to successes'/>
	  <input type='hidden' name='chco' value='4D89F9,C6D9FD'/>
	  <input type='hidden' name='chbh' value='20,30'/>
      <input type='hidden' name='chs' value='640x200'/>
	<!-- <input type='hidden' name='chxt' value='x'/>  -->
		
      <input type='submit'/>
    </form>
  </body>
</html>