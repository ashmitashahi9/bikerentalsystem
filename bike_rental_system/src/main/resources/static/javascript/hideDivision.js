const buttons = document.getElementsByClassName("hideDivButton");

const buttonPressed = e => {
    let targetDiv = e.target.id + " Div";

    let x = document.getElementById(targetDiv);
    x.classList.toggle("hiddenDiv")
    if (x.classList.contains("hiddenDiv")) {
        document.getElementById(e.target.id).innerText = e.target.id + ' ▾';
    } else {
        document.getElementById(e.target.id).innerText = e.target.id + ' ▴';
    }
}

for (let button of buttons) {
    button.addEventListener("click", buttonPressed);
}