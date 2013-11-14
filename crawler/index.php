<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</head>
	<body>
		<center>
			<div style="border: 1px solid gray; border-radius: 20px; width: 300px; padding: 30px; margin-top: 100px;">
			<?php
				if (!isSet($_POST['go'])) {
				?>
					<form action="" method="post" style="margin:0px; padding: 0px;">
						<input type="submit" value="Get data from trivago" name="go" style="height: 40px;"/>
					</form>
				<?php
				}
				else {
					include("curl_core.class.php");
					include("crawler.class.php");
					include("dbmodel.class.php");
					include("hotel.class.php");
					
					set_time_limit(2109999999);
					
					$crawler = new Crawler();
					$crawler->retriveData();
					
					$dbModel = new DBModel();
					$dbModel->storeHotels(
						$crawler->getHotelList()
					);
					
					echo "<h1>Done!</h1>";
				}
			?>
			</div>
		</center>
	</body>
</html>