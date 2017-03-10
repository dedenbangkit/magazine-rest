<?php
//Koneksi
mysql_connect("localhost", "dedenban_api", "(Z]pUB1(0Q;0") or die(mysql_error());
mysql_select_db("dedenban_api") or die(mysql_error());

//Validasi TOKEN

$key = $_SERVER ['HTTP_KEY'];
$query = mysql_query("SELECT * FROM client_list WHERE api_key='".$key."'");
			if(mysql_num_rows($query) > 0){
			
			//Buat JSON
			
			$Q = mysql_query("SELECT * FROM user_history where id=$_GET[user_id]");
			if($Q){
				 $posts = array();
					  if(mysql_num_rows($Q))
					  {
							 while($id = mysql_fetch_assoc($Q)){
									 $ids[] = $id;
							 }
					  }  
					  echo json_encode(array('Client'=>$ids));                     
			 }
			else {
			
			$Q = mysql_query("SELECT * FROM user_history")or die("Error Parameter");
			if($Q){
				 $posts = array();
					  if(mysql_num_rows($Q))
					  {
							 while($id = mysql_fetch_assoc($Q)){
									 $ids[] = $id;
							 }
					  }  
					  echo json_encode(array('All Client'=>$ids));                     
			 }
			
			}
}else{
    
    die('Error API KEY');

}
	 
     ?>