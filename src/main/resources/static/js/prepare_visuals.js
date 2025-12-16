function prepareButtonsVisual(){
    console.debug("Entering prepareButtonsVisual() with input={}")
    document.querySelectorAll(".setMode").forEach((button, index) =>{
        if (index > 1) return
        button.addEventListener("click", () =>{
            document.querySelectorAll(".setMode")
                .forEach(btn => {
                    btn.classList.remove("active");
                })
            button.classList.add("active");
        })
    })
}