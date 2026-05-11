/**
 * Initializes visual behavior for mode-switching buttons.
 *
 * Attaches click listeners to the first two `.setMode` buttons, toggling
 * the `active` CSS class so that only the clicked button appears selected.
 * Buttons beyond the first two are ignored.
 */
function prepareButtonsVisual(){
    console.debug(`Entering prepareButtonsVisual()`)

    const buttons = document.querySelectorAll(".setMode");
    console.debug(`Found ${buttons.length} .setMode button(s)`)

    buttons.forEach((button, index) =>{
        if (index > 1) {
            console.debug(`Skipping button at index ${index} (only first two should be handled)`);
            return;
        }
        button.addEventListener("click", () =>{
            console.debug(`Mode button clicked at index ${index}`)

            document.querySelectorAll(".setMode")
                .forEach(btn => {
                    btn.classList.remove("active");
                })

            button.classList.add("active");
            console.debug(`Set active class on button at index ${index}`)
        })
    })

    console.debug(`Exiting prepareButtonsVisual()`)
}