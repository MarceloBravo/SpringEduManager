const hideModal = () => {
    const modal = document.getElementById("modal-query");
    modal.style.display = "none";
}

const showModal = (formId, id = null, title = "Información", message = "", btnAceptarText = "Aceptar", btnCancelarText = "Cancelar", funcion = null) => {
    const modal = document.getElementById("modal-query");
    modal.style.display = "block";
    document.getElementById("modal-title").innerHTML =title;
    document.getElementById("modal-message").innerHTML = message;
    document.getElementById("btn-modal-aceptar").innerHTML = btnAceptarText;
    document.getElementById("btn-modal-cancelar").innerHTML = btnCancelarText;
    
    
    const btnAceptar = document.getElementById("btn-modal-aceptar");
    btnAceptar.addEventListener("click", () => {
        hideModal();
        if(funcion){
            funcion(id);
        }else{
            if(id){
                document.getElementById("delete-id").value = id;
            }
            document.getElementById(formId).submit();
        }   
    });

    const btnCancelar = document.getElementById("btn-modal-cancelar");
    btnCancelar.addEventListener("click", () => { hideModal(); });
}