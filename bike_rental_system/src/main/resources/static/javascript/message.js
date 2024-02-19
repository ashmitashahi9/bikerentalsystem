let buttonClicked=false;

function message(){
    if (!buttonClicked) {
        messageBox.classList.toggle("hidden");
        buttonClicked = true;
    }
}

let messageBoxCheck = document.getElementsByClassName("message");
let messageBox;
if (messageBoxCheck.item(0)!==null){
    messageBox = messageBoxCheck.item(0);
    setTimeout(message, 5980)
}