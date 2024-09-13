export default class Modal {
    constructor(modalId, messageElementId, confirmButtonId) {
        this.modal = document.getElementById(modalId);
        this.modalMessage = document.getElementById(messageElementId);
        this.confirmBtn = document.getElementById(confirmButtonId);

        this.init();
    }

    init() {
        this.confirmBtn.onclick = () => this.close();

        window.onclick = (event) => {
            if(event.target === this.modal){
                this.close();
            }
        };
    }

    open(message){
        this.modalMessage.textContent = message;
        this.modal.style.display = "block";
    }

    close(){
        this.modal.style.display = "none";
    }
}
