var items = [];
var mapNameId = new Map();
var listId;

function print(){
    console.log("prova");
    items.forEach((a)=>(console.log(a)));
    
}

function hideAll(){
    items.forEach((a)=>{
        document.getElementById(a).style.display="none";
    });
    
}

function createItemSearch(itemId,itemName){
    var div = document.createElement("div");
    div.innerHTML = "<li class=\"list-group-item\" id=\" "+itemId+" \"> " + itemName + " <a style=\"display: inline-block;\" href=\"/ItemServlet?itemID="+itemId+"&action=viewItem&listID="+listId+"\"><button class=\"btn btn-outline-info btn-md ml-2 my-2 mr-2\"><i class=\"far fa-eye\"></i></button></a><form style=\"display: inline-block;\" class=\"form-inline\" method=\"POST\" action=\"/List?listID="+listId+"&itemID="+itemId+"&action=addItem\"><button type=\"submit\" class=\"btn btn-outline-success btn-md my-2\"><i class=\"fas fa-plus\"></i></button></form></li>";
    document.getElementById("listItems").appendChild(div);
}

function search(word){
    clearListItems();
    
    items.forEach((item)=>{
        if(item.search(word)> -1){
            createItemSearch(mapNameId.get(item),item);
        }
        
    });
    
}

function clearListItems(){
    var ul = document.getElementById("listItems");
    while(ul.firstChild)  ul.removeChild(ul.firstChild);
}

function clickButton(){
    var word = document.getElementById("searchBar").value;
    search(word);
}
