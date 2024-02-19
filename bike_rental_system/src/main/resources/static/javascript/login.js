document.getElementsByClassName('signup-container').item(0).style.display="none";
document.getElementsByClassName('login-container').item(1).style.display="none";

document.getElementsByClassName("login").item(0).addEventListener("click", ()=>{displayLogin()})
document.getElementsByClassName("signup").item(0).addEventListener("click", ()=>{displaySignup()})

let login = document.getElementsByClassName('login').item(0);
let signup = document.getElementsByClassName('signup').item(0);
function displayLogin(){
    document.getElementsByClassName('signup-container').item(0).style.display="none";
    document.getElementsByClassName('login-container').item(1).style.display="none";
    document.getElementsByClassName('login-container').item(0).style.display="flex";

    signup.style.color="#f17300";
    signup.style.background="white";
    login.style.scale="1";

    signup.style.scale="0.8";
    login.style.color="white";
    login.style.background="#f17300"
}

function displaySignup(){
    document.getElementsByClassName('login-container').item(0).style.display="none";
    document.getElementsByClassName('login-container').item(1).style.display="none";
    document.getElementsByClassName('signup-container').item(0).style.display="flex";

    login.style.color="#f17300";
    login.style.background="white";
    signup.style.scale="1";

    login.style.scale="0.8";
    signup.style.color="white";
    signup.style.background="#f17300"
}

function displayForgot(){
    document.getElementsByClassName('login-container').item(0).style.display="none";
    document.getElementsByClassName('login-container').item(1).style.display="flex";
    document.getElementsByClassName('signup-container').item(0).style.display="none";

    signup.style.scale="1";
    signup.style.color="white";
    signup.style.background="#f17300"

    login.style.color="white";
    login.style.background="#f17300";
    login.style.scale="1";
}

function showPassword(n) {
    let x = document.getElementsByClassName("password-input").item(n);
    let buttonImage = document.getElementsByClassName("show-password-button").item(n);
    if (x.type === "password") {
        x.type = "text";
        buttonImage.innerHTML="<i class=\"fa fa-eye-slash\"></i>";
        buttonImage.style.marginRight="13px"
    } else {
        x.type = "password";
        buttonImage.innerHTML="<i class=\"fa fa-eye\"></i>";
        buttonImage.style.marginRight="12px"
    }
}

function validateEmail(element, index){
    let mail = document.getElementsByClassName(element).item(index).value;
    const mailReg=/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    if (!mail.match(mailReg) || mail === "") {
        if (index!==0) {
            document.getElementsByClassName("fas fa-envelope fa-xs").item(index - 2).style.color = "#e53e3e";
            document.getElementsByClassName("fas fa-envelope fa-xs").item(index - 2).innerHTML="<span class='toolTipTextInvalid'>Invalid email format!</span>";
        } else {
            document.getElementsByClassName("fas fa-envelope fa-xs").item(index).style.color = "#e53e3e";
            document.getElementsByClassName("fas fa-envelope fa-xs").item(index).innerHTML="<span class='toolTipTextInvalid'>Invalid email format!</span>";
        }
    } else {
        if (index!==0) {
            document.getElementsByClassName("fas fa-envelope fa-xs").item(index - 2).style.color = "#71cc35";
            document.getElementsByClassName("fas fa-envelope fa-xs").item(index - 2).innerHTML="<span class='toolTipTextValid'>Accepted!</span>";
        } else {
            document.getElementsByClassName("fas fa-envelope fa-xs").item(index).style.color = "#71cc35";
            document.getElementsByClassName("fas fa-envelope fa-xs").item(index).innerHTML="<span class='toolTipTextValid'>Accepted!</span>";
        }
    }
}

function validateName(element, index){
    let name = document.getElementsByClassName(element).item(index).value;
    if (name===""){
        document.getElementsByClassName("fas fa-user fa-xs").item(index-1).style.color="#e53e3e";
        document.getElementsByClassName("fas fa-user fa-xs").item(index-1).innerHTML="<span class='toolTipTextInvalid'>Name can't be empty!</span>"
    } else {
        document.getElementsByClassName("fas fa-user fa-xs").item(index-1).style.color="#71cc35";
        document.getElementsByClassName("fas fa-user fa-xs").item(index-1).innerHTML="<span class='toolTipTextValid'>Accepted!</span>"
    }
}

function validatePassword(element, index){
    let password = document.getElementsByClassName(element).item(index).value;
    const passwordReg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
    if (index===0){
        if (password.length>=1){
            document.getElementsByClassName("fas fa-lock fa-xs").item(index).style.color = "#71cc35";
            document.getElementsByClassName("fas fa-lock fa-xs").item(index).innerHTML="<span class='toolTipTextValid'>Accepted!</span>";
        } else {
            document.getElementsByClassName("fas fa-lock fa-xs").item(index).style.color = "#e53e3e";
            document.getElementsByClassName("fas fa-lock fa-xs").item(index).innerHTML="<span class='toolTipTextInvalid' style='width: 150px; margin-left: -75px'>Password can't be empty!</span>"
        }
    } else if (index===1) {
        if (!password.match(passwordReg)) {
            document.getElementsByClassName("fas fa-lock fa-xs").item(index).style.color = "#e53e3e";
            document.getElementsByClassName("fas fa-lock fa-xs").item(index).innerHTML=
                "<span class='toolTipTextInvalid' style='width: 260px; margin-left: -130px'>Password must be at least 8 characters long with<br>at least one uppercase character, one lowercase character, one number and one special character!</span>";
        } else {
            document.getElementsByClassName("fas fa-lock fa-xs").item(index).style.color = "#71cc35";
            document.getElementsByClassName("fas fa-lock fa-xs").item(index).innerHTML="<span class='toolTipTextValid'>Accepted!</span>";
        }
    } else if (index===2){
        if (password!==document.getElementsByClassName(element).item(1).value||password===""){
            document.getElementsByClassName("fas fa-lock fa-xs").item(index).style.color = "#e53e3e";

            if(password!==document.getElementsByClassName(element).item(1).value) document.getElementsByClassName("fas fa-lock fa-xs").item(index).innerHTML="<span class='toolTipTextInvalid' style='width: 140px; margin-left: -70px'>Passwords don't match!</span>";
            else document.getElementsByClassName("fas fa-lock fa-xs").item(index).innerHTML="<span class='toolTipTextInvalid' style='width: 150px; margin-left: -75px'>Password can't be empty!</span>";

        } else {
            document.getElementsByClassName("fas fa-lock fa-xs").item(index).style.color = "#71cc35";
            document.getElementsByClassName("fas fa-lock fa-xs").item(index).innerHTML="<span class='toolTipTextValid'>Accepted!</span>"
        }
    }
}

function validateSubmitButton(className, index){
    let signInForm=document.getElementsByClassName(className).item(0);
    let invalidFields=signInForm.getElementsByClassName("toolTipTextInvalid");

    if (invalidFields.length!==0){
        for (let i=0; i<invalidFields.length; i++){
            invalidFields[i].style.visibility="visible";
            invalidFields[i].style.opacity="1";
        }
    }
    else if (invalidFields.length===0){
        document.getElementsByClassName("submit").item(index).type="submit";
    }
}

validateEmail('text-input', 0)
validatePassword('password-input', 0)

validateName('text-input', 1)
validateName('text-input', 2)
validateEmail('text-input', 3)
validatePassword('password-input', 1)
validatePassword('password-input', 2)