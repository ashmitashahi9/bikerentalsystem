function validateName(index){
    let name = document.getElementsByClassName('text-input').item(index).value;
    if (name===""){
        document.getElementsByClassName("fas fa-user fa-xs").item(index).style.color="#e53e3e";
        document.getElementsByClassName("fas fa-user fa-xs").item(index).innerHTML="<span class='toolTipTextInvalid'>Name can't be empty!</span>"
    } else {
        document.getElementsByClassName("fas fa-user fa-xs").item(index).style.color="#71cc35";
        document.getElementsByClassName("fas fa-user fa-xs").item(index).innerHTML="<span class='toolTipTextValid'>Accepted!</span>"
    }
}

function validatePhone(){
    const pattern = "^[0-9]{10}$"
    let phone = document.getElementsByClassName('text-input').item(2).value;
    if (!phone.match(pattern)){
        document.getElementsByClassName("fas fa-phone fa-xs fa-flip-horizontal").item(0).style.color="#e53e3e";
        document.getElementsByClassName("fas fa-phone fa-xs fa-flip-horizontal").item(0).innerHTML="<span class='toolTipTextInvalid' style='transform: scale(-1, 1); width: 150px; margin-left: -75px'>Invalid Phone Number!</span>"
    } else {
        document.getElementsByClassName("fas fa-phone fa-xs fa-flip-horizontal").item(0).style.color="#71cc35";
        document.getElementsByClassName("fas fa-phone fa-xs fa-flip-horizontal").item(0).innerHTML="<span class='toolTipTextValid' style='transform: scale(-1, 1);'>Accepted!</span>"
    }
}

function validateAddress(){
    let address = document.getElementsByClassName('text-input').item(3).value;
    if (address===""){
        document.getElementsByClassName("fa fa-map-marker-alt fa-xs").item(0).style.color="#e53e3e";
        document.getElementsByClassName("fa fa-map-marker-alt fa-xs").item(0).innerHTML="<span class='toolTipTextInvalid' style='width: 150px; margin-left: -75px'>Address can't be empty!</span>"
    } else {
        document.getElementsByClassName("fa fa-map-marker-alt fa-xs").item(0).style.color="#71cc35";
        document.getElementsByClassName("fa fa-map-marker-alt fa-xs").item(0).innerHTML="<span class='toolTipTextValid'>Accepted!</span>"

    }
}

function addImage(){
    let fileInput = document.getElementsByClassName("input-img").item(0);
    let filePath = fileInput.value;

    const allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;

    if (!allowedExtensions.exec(filePath)) {
        document.getElementsByClassName('picture-uploader').item(0).style.border="2px solid #e94d4d";
        fileInput.value="";
        document.getElementsByClassName("no-image").item(0)
            .innerHTML = "<div style='width: 204px; height: 204px'>"+
            "<span class='toolTipTextInvalid' style='width: 200px; margin-left: -100px; font-weight: 900'>Invalid File Format!<br>Accepted Formats: .jpg, .jpeg, .png</span>"

    } else if (fileInput.files[0].size>5242880){
        document.getElementsByClassName('picture-uploader').item(0).style.border="2px solid #e94d4d";
        document.getElementsByClassName("no-image").item(0)
            .innerHTML = "<img src='" + URL.createObjectURL(event.target.files[0]) + "' alt='Profile Picture'>"+
            "<span class='toolTipTextInvalid' style='width: 160px; margin-left: -80px; font-weight: 900'>File Too Large!<br>Maximum File Size: 5MB</span>"

    } else {
        document.getElementsByClassName('picture-uploader').item(0).style.border="2px solid #71cc35"
        document.getElementsByClassName("no-image").item(0)
            .innerHTML = "<img src='" + URL.createObjectURL(event.target.files[0]) + "' alt='Profile Picture'>" +
            "<span class='toolTipTextValid' style='font-weight: 900'>Accepted!</span>"
    }
}

function documentsCheck(id, index){
    let fileInput = document.getElementById(id);
    let filePath = fileInput.value;

    const allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;

    if (!allowedExtensions.exec(filePath)) {
        document.getElementsByClassName("document-name").item(index).innerHTML="<i class='fa fa-address-card fa-xs'></i>Invalid File Format!";
        document.getElementsByClassName("fa fa-address-card fa-xs").item(index).style.color="#e53e3e";
    } else if (fileInput.files[0].size>5242880){
        document.getElementsByClassName("document-name").item(index).innerHTML="<i class='fa fa-address-card fa-xs'></i>File too large! (Max size: 5MB)";
        document.getElementsByClassName("fa fa-address-card fa-xs").item(index).style.color="#e53e3e";
        document.getElementsByClassName("document-image").item(index).src = URL.createObjectURL(event.target.files[0]);
    } else {
        document.getElementsByClassName("document-name").item(index).innerHTML="<i class='fa fa-address-card fa-xs'></i>Uploaded Successfully!";
        document.getElementsByClassName("fa fa-address-card fa-xs").item(index).style.color="#71cc35";
        document.getElementsByClassName("document-image").item(index).src = URL.createObjectURL(event.target.files[0]);
    }
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

function validateEmail(index){
    let mail = document.getElementsByClassName("text-input").item(index).value;
    console.log(mail)
    const mailReg=/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    if (!mail.match(mailReg) || mail === "") {
        document.getElementsByClassName("fas fa-envelope fa-xs").item(index - 4).style.color = "#e53e3e";
    } else {
        document.getElementsByClassName("fas fa-envelope fa-xs").item(index - 4).style.color = "#71cc35";
    }
}

function validatePassword(index){
    let password = document.getElementsByClassName("password-input").item(index).value;
    const passwordReg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
    if (index===0||index===3){
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
                "<span class='toolTipTextInvalid' style='width: 216px; margin-left: -108px'>Password must be at least 8 characters long with at least one uppercase character, one lowercase character, one number and one special character!</span>";
        } else {
            document.getElementsByClassName("fas fa-lock fa-xs").item(index).style.color = "#71cc35";
            document.getElementsByClassName("fas fa-lock fa-xs").item(index).innerHTML="<span class='toolTipTextValid'>Accepted!</span>";
        }
    } else if (index===2){
        if (password!==document.getElementsByClassName("password-input").item(1).value||password===""){
            document.getElementsByClassName("fas fa-lock fa-xs").item(index).style.color = "#e53e3e";

            if(password!==document.getElementsByClassName("password-input").item(1).value) document.getElementsByClassName("fas fa-lock fa-xs").item(index).innerHTML="<span class='toolTipTextInvalid' style='width: 140px; margin-left: -70px'>Passwords don't match!</span>";
            else document.getElementsByClassName("fas fa-lock fa-xs").item(index).innerHTML="<span class='toolTipTextInvalid' style='width: 150px; margin-left: -75px'>Password can't be empty!</span>";
        } else {
            document.getElementsByClassName("fas fa-lock fa-xs").item(index).style.color = "#71cc35";
            document.getElementsByClassName("fas fa-lock fa-xs").item(index).innerHTML="<span class='toolTipTextValid'>Accepted!</span>";
        }
    }
}

function statusUpdate(){
    let status=document.getElementsByTagName("h2").item(0).innerText;
    let statusClass=document.getElementsByClassName("text-field-status").item(0);

    let para = document.createElement("p");
    let fields;
    if (status === "Not Submitted") {
        para.innerText = "You have not uploaded any of your documents to be reviewed yet." +
            "\n\nPlease note that your name, phone number, address and image are also taken into consideration in the review process.";
        statusClass.style.color = "white";
        statusClass.style.backgroundColor = "#e94d4d";
        statusClass.style.border = "solid 2px red"
        para.style.fontSize = "12px";
    } else if (status === "Rejected") {
        para.innerText = "Your documents were rejected. Please check your documents and try again." +
            "\n\nPlease note that your name, phone number, address and image are also taken into consideration in the review process.";
        statusClass.style.color = "white";
        statusClass.style.backgroundColor = "#e94d4d";
        statusClass.style.border = "solid 2px red"
        para.style.fontSize = "12px";
    } else if (status === "Submitted") {
        para.innerText = "Your documents have been submitted for review. Come back later to check the results." +
            "\n\nPlease note that you can't update your documents until the submitted ones have been reviewed.";
        statusClass.style.color = "black";
        statusClass.style.backgroundColor = "#fff36d";
        statusClass.style.border = "solid 2px #fedd00"
        para.style.fontSize = "12px";

        fields = document.getElementById("citizen-front");
        fields.disabled = true;

        fields = document.getElementById("citizen-back");
        fields.disabled = true;

        fields = document.getElementById("license");
        fields.disabled = true;

        let button = document.getElementsByClassName("submit").item(1);
        button.disabled = true;

    } else if (status === "Approved") {
        para.innerText = "Your documents are approved. You can always update your documents and submit again to keep us updated.";
        statusClass.style.color = "black";
        statusClass.style.backgroundColor = "#71cc35";
        statusClass.style.border = "solid 2px green"
    }
    statusClass.appendChild(para);
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

function validateDocumentsSubmit(){
    let citizenF = document.getElementById('citizen-front');
    let citizenB = document.getElementById('citizen-back');
    let license = document.getElementById('license');
    let flag = false;

    let docArray = [citizenF, citizenB, license];

    for (let i=0; i<docArray.length; i++){
        if (document.getElementsByClassName("fa fa-address-card fa-xs").item(i).getAttribute("color")==="#e53e3e"){
            flag=true;
        }

        if (docArray[i].value===''){
            document.getElementsByClassName("fa fa-address-card fa-xs").item(i).style.color="#e53e3e";
            document.getElementsByClassName("document-name").item(i).innerHTML="<i class='fa fa-address-card fa-xs'></i>Document Empty!";
            flag=true;
        } else {
            document.getElementsByClassName("fa fa-address-card fa-xs").item(i).style.color="#71cc35";
            document.getElementsByClassName("document-name").item(i).innerHTML="<i class='fa fa-address-card fa-xs'></i>Uploaded Successfully!";
        }
    }
    if (!flag){
        document.getElementsByClassName("submit").item(1).type='submit';
    }
}

validateName(0);
validateName(1);
validatePhone();
validateAddress();

validatePassword(0);
validatePassword(1);
validatePassword(2);

if (document.getElementsByClassName("password-input").length>3) {
    validatePassword(3);
    statusUpdate();
}