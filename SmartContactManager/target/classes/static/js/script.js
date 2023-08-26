
//toggle btn 
const toggleSidebar=()=>{
  
  //if side bar visible then hide
  //and content marginleft 0
  if($(".sidebar").is(":visible")){
   
    $(".sidebar").css("display","none");
    $(".content").css("margin-left","0");
    $("#add-contact-main").css("margin-left","30%");
  }else{
    
    $(".sidebar").css("display","block");
    $(".content").css("margin-left","20.5rem");
    $("#add-contact-main").css("margin-left","1%");
  }
}

$("#reomveSession").click(function(){
	$("#reomveSession").val("ok");
});
















